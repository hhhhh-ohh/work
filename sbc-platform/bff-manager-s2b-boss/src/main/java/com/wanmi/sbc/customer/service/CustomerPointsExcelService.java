package com.wanmi.sbc.customer.service;


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
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.NoDeleteCustomerGetByAccountRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsBatchAdjustRequest;
import com.wanmi.sbc.customer.api.response.customer.NoDeleteCustomerGetByAccountResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerPointsAdjustDTO;
import com.wanmi.sbc.customer.api.request.OperationLogAddRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class CustomerPointsExcelService {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerPointsDetailQueryProvider customerPointsDetailQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private MqSendProvider mqSendProvider;
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
//        Platform platform = commonUtil.getOperator().getPlatform();

        String resourceKey = Constants.CUSTOMER_POINTS_EXCEL_DIR.concat(userId);
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


                //客户账号
                String customerAccount = ExcelHelper.getValue(getCell(cells,1));
                NoDeleteCustomerGetByAccountResponse customer = customerQueryProvider.getNoDeleteCustomerByAccount(NoDeleteCustomerGetByAccountRequest.builder().customerAccount(customerAccount).build()).getContext();

                if (StringUtils.isBlank(customerAccount)) {
                    ExcelHelper.setError(style,workbook,getCell(cells,1),"此项必填");
                    isError = true;
                    maxErrorNum++;
                } else if (Objects.isNull(customer)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 1), "客户账号不存在");
                    isError = true;
                    maxErrorNum++;
                } else if (!customer.getCustomerAccount().contains(customerAccount)) {
                    ExcelHelper.setError(style,workbook,getCell(cells,1),"客户账号不存在");
                    isError = true;
                    maxErrorNum++;
                } else if (!ValidateUtil.isNotChs(customerAccount)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 1), "仅允许英文、数字、特殊字符");
                    isError = true;
                    maxErrorNum++;
                } else if (customer.getDelFlag() == DeleteFlag.YES || customer.getCheckState() != CheckState.CHECKED
                        || customer.getLogOutStatus() != LogOutStatus.NORMAL || customer.getCustomerDetail().getCustomerStatus() != CustomerStatus.ENABLE) {
                    ExcelHelper.setError(style,workbook,getCell(cells,1),"客户账号状态异常");
                    isError = true;
                    maxErrorNum++;
                }

                //客户名称
                String customerName = ExcelHelper.getValue(getCell(cells,0));
                if (StringUtils.isBlank(customerName)) {
                    ExcelHelper.setError(style,workbook,getCell(cells,0),"此项必填");
                    isError = true;
                    maxErrorNum++;
                } else if (Objects.isNull(customer)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 0), "客户名称不存在");
                    isError = true;
                    maxErrorNum++;
                } else if (Objects.nonNull(customer.getCustomerDetail()) && !customer.getCustomerDetail().getCustomerName().contains(customerName)) {
                    ExcelHelper.setError(style,workbook,getCell(cells,0),"客户名称不存在");
                    isError = true;
                    maxErrorNum++;
                }

                // 修改积分
                List<String> types = new ArrayList<>();
                types.add("增加");
                types.add("减少");
                types.add("调整到");
                String type = ExcelHelper.getValue(getCell(cells, 2));
                if (StringUtils.isBlank(type)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "此项必填");
                    isError = true;
                    maxErrorNum++;
                } else if (!types.contains(type)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 2), "非法的修改积分类型");
                    isError = true;
                    maxErrorNum++;
                }

                // 数量
                String pointsStr = ExcelHelper.getValue(cells, 3);
                Long points =  StringUtils.isNotBlank(pointsStr) ? new BigDecimal(pointsStr).longValue() : 0;
                if (StringUtils.isBlank(pointsStr)) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 3), "此项必填");
                    isError = true;
                    maxErrorNum++;
                } else if (points < 1 || points > 9999999) {
                    ExcelHelper.setError(style, workbook, getCell(cells, 3), "必须在1-9999999范围内");
                    isError = true;
                    maxErrorNum++;
                }
            }

            if (isError) {
                this.errorExcel(userId.concat(".").concat(fileExt), workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[] {fileExt});
            }
            log.info("客户积分导入for循环开始" + System.currentTimeMillis());
            YunUploadResourceRequest yunUploadResourceRequest =
                    YunUploadResourceRequest.builder()
                            .resourceType(ResourceType.EXCEL)
                            .content(file.getBytes())
                            .resourceName(file.getOriginalFilename())
                            .resourceKey(resourceKey)
                            .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest).getContext();
        } catch (SbcRuntimeException e) {
            log.error("客户积分上传异常", e);
            throw e;
        } catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        } catch (Exception e) {
            log.error("客户积分上传异常", e);
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
                                                Constants.CUSTOMER_POINTS_EXCEL_DIR.concat(
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

        List<CustomerPointsAdjustDTO> customerPointsAdjustDTOS = new ArrayList<>();

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
            log.info("客户积分导入循环开始" + System.currentTimeMillis());
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
                String customerAccount = ExcelHelper.getValue(getCell(cells, 1));

                String customerId = customerQueryProvider.getNoDeleteCustomerByAccount(NoDeleteCustomerGetByAccountRequest.builder()
                        .customerAccount(customerAccount).build()).getContext().getCustomerId();
                    CustomerPointsAdjustDTO customerPointsAdjustDTO = new CustomerPointsAdjustDTO();
                    customerPointsAdjustDTO.setCustomerId(customerId);
                    customerPointsAdjustDTO.setCustomerName(ExcelHelper.getValue(getCell(cells, 0)));
                    customerPointsAdjustDTO.setCustomerAccount(ExcelHelper.getValue(getCell(cells, 1)));
                    customerPointsAdjustDTO.setOperateType(
                            this.getOperateType(ExcelHelper.getValue(getCell(cells, 2))));
                    customerPointsAdjustDTO.setAdjustNum(
                            Long.parseLong(ExcelHelper.getValue(this.getCell(cells, 3))));
                    customerPointsAdjustDTOS.add(customerPointsAdjustDTO);
            }

            Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
            OperationLogAddRequest operationLog = new OperationLogAddRequest();
            if (nonNull(claims)) {
                operationLog.setEmployeeId(
                        Objects.toString(claims.get("employeeId"), StringUtils.EMPTY));
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
            CustomerPointsBatchAdjustRequest customerPointsBatchAdjustRequest =
                    CustomerPointsBatchAdjustRequest.builder().pointsAdjustDTOList(customerPointsAdjustDTOS)
                            .operationLogAddRequest(operationLog).build();
            // 发mq异步处理
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.BATCH_CUSTOMER_POINTS_UPDATE);
            mqSendDTO.setData(JSONObject.toJSONString(customerPointsBatchAdjustRequest));
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
            if (!(row.getCell(0).getStringCellValue().contains("客户名称")
                    && row.getCell(1).getStringCellValue().contains("客户账号")
                    && row.getCell(2).getStringCellValue().contains("修改积分")
                    && row.getCell(3).getStringCellValue().contains("数量"))) {
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
                            .resourceKey(Constants.CUSTOMER_POINTS_ERR_EXCEL_DIR.concat(userId))
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
        OperateType operateType = null;
        switch (type) {
            case "增加":
                operateType =  OperateType.GROWTH;
                break;
            case "减少":
                operateType = OperateType.DEDUCT;
                break;
            case "调整到":
                operateType = OperateType.REPLACE;
                break;
            default:
        }
        return operateType;
    }
}
