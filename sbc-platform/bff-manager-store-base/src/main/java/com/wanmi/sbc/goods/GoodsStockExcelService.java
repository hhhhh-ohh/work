package com.wanmi.sbc.goods;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.common.OperationLogAddRequest;
import com.wanmi.sbc.goods.api.request.goods.BatchGoodsStockRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.bean.dto.BatchGoodsStockDTO;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.sensitivewords.service.SensitiveWordService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.nonNull;

/**
 * @author zhanggaolei
 * @type GoodsStockExcelService.java
 * @desc
 * @date 2023/3/15 10:02
 */
@Slf4j
@Service
public class GoodsStockExcelService {

    @Autowired private CommonUtil commonUtil;

    @Autowired private SensitiveWordService sensitiveWordService;

    @Autowired private GoodsQueryProvider goodsQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private GoodsAuditQueryProvider goodsAuditQueryProvider;

    @Autowired private YunServiceProvider yunServiceProvider;

    @Autowired private MqSendProvider mqSendProvider;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param userId 操作员id
     * @return 文件格式
     */
    public String upload(MultipartFile file, String userId) {
        if (file == null || file.isEmpty()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String fileExt =
                file.getOriginalFilename()
                        .substring(file.getOriginalFilename().lastIndexOf('.') + 1)
                        .toLowerCase();
        if (!(fileExt.equalsIgnoreCase(Constants.XLS)
                || fileExt.equalsIgnoreCase(Constants.XLSX))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }
        // 运费模板未配置,如果是平台/BOSS，不校验
        Platform platform = commonUtil.getOperator().getPlatform();

        String resourceKey = Constants.GOODS_STOCK_EXCEL_DIR.concat(userId);
        // 创建一个HashSet，防止卡券商品多次绑定同一个卡券
        Set<Long> electronicCouponSet = Sets.newHashSet();
        // 创建Workbook工作薄对象，表示整个excel
        try (Workbook workbook =
                WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            Assert.assertNotNull(sheet, GoodsErrorCodeEnum.K030066);
            // 检测文档正确性
            this.checkExcel(workbook);

            // 获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            // 获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            if (lastRowNum > Constants.NUM_5000) {
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_5000) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过5000条，请修改");
                }
            }

            Map<String, List<Cell>> skuNos = new HashMap<>();

            int maxCell = 5;
            boolean isError = false;
            int maxErrorNum = 0;
            CellStyle style = workbook.createCellStyle();
            log.info("商品导入循环开始" + System.currentTimeMillis());
            // 循环除了第一行的所有行
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                if (Constants.IMPORT_GOODS_MAX_ERROR_NUM == maxErrorNum) {
                    break;
                }
                // 获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Cell[] cells = new Cell[maxCell];
                boolean isNotEmpty = false;
                for (int i = 0; i < maxCell; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cells[i] = cell;
                    if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                        isNotEmpty = true;
                    }
                }
                // 数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }

                String goodsInfoNo = ExcelHelper.getValue(getCell(cells, 2));

                List<GoodsInfoVO> goodsInfoVOS =
                        goodsInfoQueryProvider
                                .listByCondition(
                                        GoodsInfoListByConditionRequest.builder()
                                                .goodsInfoNos(Lists.newArrayList(goodsInfoNo))
                                                .storeId(commonUtil.getStoreId())
                                                .delFlag(DeleteFlag.NO.toValue())
                                                .build())
                                .getContext()
                                .getGoodsInfos();

                if (StringUtils.isBlank(goodsInfoNo)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "此项必填");
                    isError = true;
                    maxErrorNum++;
                } else if (!ValidateUtil.isBetweenLen(goodsInfoNo, 1, 20)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "长度必须1-20个字");
                    isError = true;
                    maxErrorNum++;
                } else if (!ValidateUtil.isNotChs(goodsInfoNo)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "仅允许英文、数字、特殊字符");
                    isError = true;
                    maxErrorNum++;
                } else if (CollectionUtils.isEmpty(goodsInfoVOS)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "SKU编码无效或不存在");
                    isError = true;
                    maxErrorNum++;
                } else if (goodsInfoVOS.stream()
                        .anyMatch(g -> g.getAuditStatus().equals(CheckStatus.WAIT_CHECK))) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "待审核商品无法进行编辑");
                    isError = true;
                    maxErrorNum++;
                } else if (goodsInfoVOS.stream().anyMatch(g -> g.getGoodsType() != 0)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "虚拟商品/卡券商品无法编辑库存");
                    isError = true;
                    maxErrorNum++;
                } else if (goodsInfoVOS.stream()
                        .anyMatch(g -> g.getProviderGoodsInfoId() != null)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "供应商商品无法编辑库存");
                    isError = true;
                    maxErrorNum++;
                }
                //调整方式
                List<String> types = new ArrayList<>();
                types.add("增加");
                types.add("减少");
                types.add("调整到");
                String type = ExcelHelper.getValue(getCell(cells, 3));
                if (StringUtils.isBlank(type)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 3), "此项必填");
                    isError = true;
                    maxErrorNum++;
                }
                if (!types.contains(type)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 3), "非法的修改库存类型");
                    isError = true;
                    maxErrorNum++;
                }

                // 库存
                String stockStr = ExcelHelper.getValue(cells, 4);
                Integer stock = StringUtils.isNotBlank(stockStr) ? new BigDecimal(stockStr).intValue() : 0;
                //                goodsInfo.setStock(StringUtils.isBlank(stock) ? 0 : new
                // BigDecimal(stock).longValue());
                if (stock < 0 || stock > 9999999) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 4), "必须在0-9999999范围内");
                    isError = true;
                    maxErrorNum++;
                }
                if (skuNos.containsKey(goodsInfoNo)) {
                    // 为单元格设置重复错误提示
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "该编码重复");
                    isError = true;
                    maxErrorNum++;
                } else {
                    skuNos.put(goodsInfoNo, Arrays.asList(cells));
                }
            }

            if (isError) {
                this.errorExcel(userId.concat(".").concat(fileExt), workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[] {fileExt});
            }
            log.info("商品库存导入for循环开始" + System.currentTimeMillis());
            YunUploadResourceRequest yunUploadResourceRequest =
                    YunUploadResourceRequest.builder()
                            .resourceType(ResourceType.EXCEL)
                            .content(file.getBytes())
                            .resourceName(file.getOriginalFilename())
                            .resourceKey(resourceKey)
                            .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
        } catch (SbcRuntimeException e) {
            log.error("商品库存上传异常", e);
            throw e;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        } catch (Exception e) {
            log.error("商品库存上传异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return fileExt;
    }

    /**
     * 导入模板
     *
     * @return
     */
    public void implGoods(String userId) {
        byte[] content =
                yunServiceProvider
                        .getFile(
                                YunGetResourceRequest.builder()
                                        .resourceKey(
                                                Constants.GOODS_STOCK_EXCEL_DIR.concat(
                                                       userId))
                                        .build())
                        .getContext()
                        .getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        if (content.length > Constants.IMPORT_GOODS_MAX_SIZE_LIMIT * 1024 * 1024) {
            throw new SbcRuntimeException(
                    GoodsErrorCodeEnum.K030064,
                    new Object[] {Constants.IMPORT_GOODS_MAX_SIZE_LIMIT});
        }

        List<BatchGoodsStockDTO> batchGoodsStockDTOS = new ArrayList<>();

        // 创建Workbook工作薄对象，表示整个excel
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            // 检测文档正确性
            this.checkExcel(workbook);

            // 获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            // 获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            int maxCell = 5;
            int maxErrorNum = 0;
            log.info("商品库存导入循环开始" + System.currentTimeMillis());
            // 循环除了第一行的所有行
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                if (Constants.IMPORT_GOODS_MAX_ERROR_NUM == maxErrorNum) {
                    break;
                }
                // 获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Cell[] cells = new Cell[maxCell];
                boolean isNotEmpty = false;
                for (int i = 0; i < maxCell; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cells[i] = cell;
                    if (StringUtils.isNotBlank(ExcelHelper.getValue(cell))) {
                        isNotEmpty = true;
                    }
                }
                // 数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }
                BatchGoodsStockDTO batchGoodsStockDTO = new BatchGoodsStockDTO();
                batchGoodsStockDTO.setGoodsInfoNo(ExcelHelper.getValue(getCell(cells, 2)));
                batchGoodsStockDTO.setOperateType(
                        this.getOperateType(ExcelHelper.getValue(getCell(cells, 3))));
                batchGoodsStockDTO.setStock(
                        Integer.parseInt(ExcelHelper.getValue(this.getCell(cells, 4))));
                batchGoodsStockDTO.setOperatorId(userId);
                batchGoodsStockDTOS.add(batchGoodsStockDTO);
            }

            Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
            OperationLogAddRequest operationLog = new OperationLogAddRequest();
            if (nonNull(claims)) {
                operationLog.setEmployeeId(
                        Objects.toString(claims.get("employeeId"), StringUtils.EMPTY));
                // accountName
                operationLog.setOpAccount(
                        Objects.toString(claims.get("EmployeeName"), StringUtils.EMPTY));
                operationLog.setStoreId(Long.valueOf(Objects.toString(claims.get("storeId"), "0")));
                operationLog.setCompanyInfoId(
                        Long.valueOf(Objects.toString(claims.get("companyInfoId"), "0")));
                operationLog.setOpRoleName(
                        Objects.toString(claims.get("roleName"), StringUtils.EMPTY));
                operationLog.setOpName(
                        Objects.toString(claims.get("realEmployeeName"), StringUtils.EMPTY));
            } else {
                operationLog.setEmployeeId(StringUtils.EMPTY);
                operationLog.setOpAccount(StringUtils.EMPTY);
                operationLog.setOpName(StringUtils.EMPTY);
                operationLog.setStoreId(0L);
                operationLog.setCompanyInfoId(0L);
                operationLog.setOpRoleName(StringUtils.EMPTY);
            }
            operationLog.setOpIp(HttpUtil.getIpAddr());
            operationLog.setOpTime(LocalDateTime.now());
            BatchGoodsStockRequest batchGoodsStockRequest =
                    BatchGoodsStockRequest.builder().batchGoodsStockDTOS(batchGoodsStockDTOS)
                            .operationLogAddRequest(operationLog).build();
            // 发mq异步处理
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.BATCH_GOODS_STOCK_UPDATE);
            mqSendDTO.setData(JSONObject.toJSONString(batchGoodsStockRequest));
            mqSendProvider.send(mqSendDTO);
        } catch (SbcRuntimeException e) {
            log.error("商品导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    /**
     * 验证EXCEL
     *
     * @param workbook
     */
    public void checkExcel(Workbook workbook) {
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if (!(row.getCell(0).getStringCellValue().contains("商品名称"))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
            if (!(row.getCell(2).getStringCellValue().contains("SKU编码"))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
            if (!(row.getCell(3).getStringCellValue().contains("修改库存"))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
            if (!(row.getCell(4).getStringCellValue().contains("数量"))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    /**
     * EXCEL错误文件-本地生成
     *
     * @param newFileName 新文件名
     * @param wk Excel对象
     * @return 新文件名
     * @throws SbcRuntimeException
     */
    public String errorExcel(String newFileName, Workbook wk) throws SbcRuntimeException {
        String userId = commonUtil.getOperatorId();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            wk.write(os);
            YunUploadResourceRequest yunUploadResourceRequest =
                    YunUploadResourceRequest.builder()
                            .resourceType(ResourceType.EXCEL)
                            .content(os.toByteArray())
                            .resourceName(newFileName)
                            .resourceKey(Constants.GOODS_ERR_EXCEL_DIR.concat(userId))
                            .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
            return newFileName;
        } catch (IOException e) {
            log.error("生成的错误文件上传至云空间失败", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }

    private OperateType getOperateType(String type) {
        switch (type) {
            case "增加":
                return OperateType.GROWTH;
            case "减少":
                return OperateType.DEDUCT;
            case "调整到":
                return OperateType.REPLACE;
            default:
                return null;
        }

    }
}
