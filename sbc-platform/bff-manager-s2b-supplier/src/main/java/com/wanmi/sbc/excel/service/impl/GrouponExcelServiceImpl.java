package com.wanmi.sbc.excel.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.excel.service.MarketingExcelService;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.service.GoodsExcelService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2022/2/8 15:15
 * @description <p> 拼团商品导入 </p>
 */
@Slf4j
@Service
public class GrouponExcelServiceImpl implements MarketingExcelService {

    @Autowired
    private GoodsExcelProvider goodsExcelProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private GoodsExcelService goodsExcelService;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;


    @Override
    public void downloadTemplate(Integer activityType) {
        MarketingTemplateRequest request = MarketingTemplateRequest.builder()
                .activityType(activityType)
                .build();
        GoodsExcelExportTemplateResponse response = goodsExcelProvider.getTemplate(request).getContext();
        String file = response.getFile();
        this.write(file,"拼团商品导入模板.xls");
    }

    @Override
    public void downloadErrorFile(String fileExt) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.MARKETING_GOODS_ERR_EXCEL.concat(userId);
        goodsExcelService.getErrExcel(resourceKey,fileExt);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if(Objects.isNull(file) || file.isEmpty()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String filename = file.getOriginalFilename();
        boolean extension = FilenameUtils.isExtension(filename, "xls", "xlsx");
        if (!extension) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }
        String fileExt = FilenameUtils.getExtension(filename);
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.MARKETING_GOODS_EXCEL.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (Objects.isNull(sheet)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            //检测文档正确性
            //this.checkExcel(workbook);
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            int emptyNum = ExcelHelper.getEmptyRowNum(sheet);
            if((lastRowNum - emptyNum) < 1){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            if(lastRowNum > Constants.NUM_50){
                int emptyRowNum = ExcelHelper.getEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_50) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过50条，请修改");
                }
            }
            int maxCell = 4;
            AtomicBoolean isError = new AtomicBoolean(false);
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Map<String, List<Cell>> spuNoMap = Maps.newHashMap();
            //循环除了第一行的所有行
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
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
                if(!isNotEmpty){
                    continue;
                }
                //校验 skuNo
                String skuNo = ExcelHelper.getValue(getCell(cells, 0));
                if(StringUtils.isBlank(skuNo)){
                    ExcelHelper.setCellError(workbook, getCell(cells, 0), "此项必填");
                    isError.set(true);
                }else if(!ValidateUtil.isBetweenLen(skuNo, 1, 20)){
                    ExcelHelper.setCellError(workbook, getCell(cells, 0), "长度必须1-20个字");
                    isError.set(true);
                }else if(!ValidateUtil.isNotChs(skuNo)){
                    ExcelHelper.setCellError(workbook, getCell(cells, 0), "仅允许英文、数字、特殊字符");
                    isError.set(true);
                }else {
                    spuNoMap.merge(skuNo, Lists.newArrayList(getCell(cells, 0)), (s1, s2) -> {s1.addAll(s2);return s1;});
                }
                //校验拼团价
                String price = ExcelHelper.getValue(getCell(cells, 1));
                if(StringUtils.isNotBlank(price)){
                    this.checkPrice(price,workbook,cells,1,isError);
                } else {
                    ExcelHelper.setCellError(workbook, getCell(cells, 1), "此项必填");
                    isError.set(true);
                }
                //起售数量
                String sale = ExcelHelper.getValue(getCell(cells, 2));
                if(StringUtils.isNotBlank(sale)){
                    this.checkNumber(sale,workbook,cells,2,isError);
                }
                String restrict = ExcelHelper.getValue(getCell(cells, 3));
                if(StringUtils.isNotBlank(restrict)){
                    this.checkNumber(restrict,workbook,cells,3,isError);
                }
            }
            ArrayList<String> spuNoList = new ArrayList<>(spuNoMap.keySet());
            GoodsByConditionRequest request = GoodsByConditionRequest.builder()
                    .goodsNos(spuNoList)
                    .delFlag(DeleteFlag.NO.toValue())
                    .storeId(commonUtil.getStoreId())
                    .build();
            GoodsByConditionResponse response = goodsQueryProvider.listByCondition(request).getContext();
            List<GoodsVO> goodsVOList = response.getGoodsVOList();
            if (CollectionUtils.isNotEmpty(goodsVOList)) {
                Map<String, GoodsVO> goodsMap = goodsVOList.stream().collect(Collectors.toMap(GoodsVO::getGoodsNo, Function.identity()));
                //判断是否有不同商品类型混选
                Boolean diffGoodsType = Boolean.FALSE;
                long realGoodsCount = goodsVOList.stream().filter(goodsVO -> GoodsType.REAL_GOODS.toValue() == goodsVO.getGoodsType()).count();
                if (realGoodsCount > 0 && realGoodsCount != goodsVOList.size()) {
                    diffGoodsType = Boolean.TRUE;
                }
                for (Map.Entry<String, List<Cell>> entry: spuNoMap.entrySet()) {
                    String key = entry.getKey();
                    List<Cell> value = entry.getValue();
                    if (Objects.isNull(goodsMap.get(key))) {
                        value.forEach(cell -> {
                            //为单元格设置重复错误提示
                            ExcelHelper.setCellError(workbook, cell, "该商品编码不存在");
                        });
                        isError.set(true);
                    } else {
                        GoodsVO goodsVO = goodsMap.get(key);
                        CheckStatus auditStatus = goodsVO.getAuditStatus();
                        if (CollectionUtils.size(value) > 1) {
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "该商品编码重复");
                            });
                            isError.set(true);
                        } else if (Objects.equals(auditStatus, CheckStatus.FORBADE)) {
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "该编码商品禁售中");
                            });
                            isError.set(true);
                        } else if (Objects.equals(auditStatus, CheckStatus.WAIT_CHECK) || Objects.equals(auditStatus, CheckStatus.NOT_PASS)) {
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "该编码商品审核中");
                            });
                            isError.set(true);
                        } else if (diffGoodsType && GoodsType.REAL_GOODS.toValue() != goodsVO.getGoodsType()) {
                            //对虚拟和卡券商品标注
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "卡券和虚拟商品不可与实物商品混选");
                            });
                            isError.set(true);
                        }
                    }
                }
            } else if (CollectionUtils.isEmpty(goodsVOList) && CollectionUtils.isNotEmpty(spuNoList)) {
                spuNoMap.forEach((key, value) -> {
                    value.forEach(cell -> {
                        //为单元格设置重复错误提示
                        ExcelHelper.setCellError(workbook, cell, "该商品编码不存在");
                    });
                    isError.set(true);
                });
            }
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.MARKETING_GOODS_ERR_EXCEL.concat(userId);
                goodsExcelService.errorExcel(newFileName,resourceKey,workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{fileExt});
            }
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(file.getBytes())
                    .resourceName(file.getOriginalFilename())
                    .resourceKey(resourceKey)
                    .build();
            yunServiceProvider.uploadFileExcel(yunUploadResourceRequest);
        }catch (IOException e) {
            log.error("Excel文件上传到云空间失败->resourceKey为:".concat(resourceKey), e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }catch (SbcRuntimeException e) {
            log.error("商品导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
        return fileExt;
    }

    @Override
    public List<GoodsInfoVO> getGoodsInfoList() {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.MARKETING_GOODS_EXCEL.concat(userId);
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            int maxCell = 4;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Map<String,GoodsInfoVO> importGoodsInfoMap = Maps.newHashMap();
            //循环除了第一行的所有行
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
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
                if(!isNotEmpty){
                    continue;
                }

                //skuNo
                String spuNo = ExcelHelper.getValue(getCell(cells, 0));
                //拼团价
                String price = ExcelHelper.getValue(getCell(cells, 1));
                BigDecimal grouponPrice = NumberUtils.createBigDecimal(price);
                //起售数量
                String startSelling = ExcelHelper.getValue(getCell(cells, 2));
                long startSellingNum = NumberUtils.toLong(startSelling);
                String limitSelling = ExcelHelper.getValue(getCell(cells, 3));
                long limitSellingNum = NumberUtils.toLong(limitSelling);
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                goodsInfoVO.setLimitSellingNum(limitSellingNum);
                goodsInfoVO.setStartSellingNum(startSellingNum);
                goodsInfoVO.setGrouponPrice(grouponPrice);
                importGoodsInfoMap.putIfAbsent(spuNo,goodsInfoVO);
            }
            List<String> spuNoList = new ArrayList<>(importGoodsInfoMap.keySet());
            EsSkuPageRequest esSkuPageRequest = new EsSkuPageRequest();
            esSkuPageRequest.setPageSize(50);
            esSkuPageRequest.setGoodsNos(spuNoList);
            esSkuPageRequest.setStoreId(commonUtil.getStoreId());
            esSkuPageRequest.setDelFlag(DeleteFlag.NO.toValue());
            EsSkuPageResponse response = esSkuQueryProvider.page(esSkuPageRequest).getContext();
            List<GoodsInfoVO> goodsInfoList = response.getGoodsInfoPage().getContent();
            if (CollectionUtils.isEmpty(goodsInfoList)) {
                return Collections.emptyList();
            }
            //组装数据
            return goodsInfoList.stream().peek(goodsInfoVO -> {
                String goodsNo = goodsInfoVO.getGoodsNo();
                GoodsInfoVO target = importGoodsInfoMap.get(goodsNo);
                goodsInfoVO.setGrouponPrice(target.getGrouponPrice());
                goodsInfoVO.setLimitSellingNum(target.getLimitSellingNum());
                goodsInfoVO.setStartSellingNum(target.getStartSellingNum());
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }

    }
    /**
     * 验证EXCEL
     * @param workbook
     */
    public void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if(!(row.getCell(0).getStringCellValue().contains("SKU编码")
                && row.getCell(1).getStringCellValue().contains("拼团价格")
                && row.getCell(2).getStringCellValue().contains("起售数量")
                && row.getCell(3).getStringCellValue().contains("限购数量"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }
}
