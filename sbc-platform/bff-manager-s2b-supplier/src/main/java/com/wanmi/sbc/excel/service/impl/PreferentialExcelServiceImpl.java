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
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsExcelService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author edz
 * @className PreferentialExcelServiceImpl
 * @description 加价购商品导入
 * @date 2022/11/22 11:12
 **/
@Service
@Slf4j
public class PreferentialExcelServiceImpl implements MarketingExcelService {
    @Autowired
    private GoodsExcelProvider goodsExcelProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsExcelService goodsExcelService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Override
    public void downloadTemplate(Integer activityType) {
        MarketingTemplateRequest request = MarketingTemplateRequest.builder()
                .activityType(activityType)
                .build();
        GoodsExcelExportTemplateResponse response = goodsExcelProvider.getTemplate(request).getContext();
        String file = response.getFile();
        this.write(file, "加价购活动商品导入模板.xls");
    }

    @Override
    public void downloadErrorFile(String fileExt) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.PREFERENTIAL_GOODS_ERR_EXCEL.concat(userId);
        goodsExcelService.getErrExcel(resourceKey,fileExt);

    }

    @Override
    public String uploadFile(MultipartFile uploadFile) {
        if(Objects.isNull(uploadFile) || uploadFile.isEmpty()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        String filename = uploadFile.getOriginalFilename();
        boolean extension = FilenameUtils.isExtension(filename, "xls", "xlsx");
        if (!extension) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030063);
        }
        String fileExt = FilenameUtils.getExtension(filename);
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.PREFERENTIAL_GOODS_EXCEL.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(uploadFile.getBytes()))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (Objects.isNull(sheet)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            //检测文档正确性
            this.checkExcel(workbook);
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
            int maxCell = 1;
            AtomicBoolean isError = new AtomicBoolean(false);
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Map<String, List<Cell>> skuNoMap = Maps.newHashMap();
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
                    skuNoMap.merge(skuNo, Lists.newArrayList(getCell(cells, 0)), (s1, s2) -> {s1.addAll(s2);return s1;});
                }
            }
            this.checkGoodsInfoNo(skuNoMap,goodsInfoQueryProvider,workbook,isError,null,
                    Boolean.FALSE,commonUtil.getStoreId(),Boolean.FALSE,Boolean.FALSE,Boolean.FALSE, Boolean.TRUE);
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.PREFERENTIAL_GOODS_ERR_EXCEL.concat(userId);
                goodsExcelService.errorExcel(newFileName,resourceKey,workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{fileExt});
            }
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(uploadFile.getBytes())
                    .resourceName(uploadFile.getOriginalFilename())
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
        String resourceKey = Constants.PREFERENTIAL_GOODS_EXCEL.concat(userId);
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
            List<String> skuNoList = Lists.newArrayList();
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
                String skuNo = ExcelHelper.getValue(getCell(cells, 0));
                skuNoList.add(skuNo);
            }
            EsSkuPageRequest esSkuPageRequest = new EsSkuPageRequest();
            esSkuPageRequest.setPageSize(50);
            esSkuPageRequest.setGoodsInfoNos(skuNoList);
            esSkuPageRequest.setStoreId(commonUtil.getStoreId());
            esSkuPageRequest.setDelFlag(DeleteFlag.NO.toValue());
            EsSkuPageResponse response = esSkuQueryProvider.page(esSkuPageRequest).getContext();
            List<GoodsInfoVO> goodsInfoList = response.getGoodsInfoPage().getContent();
            if (CollectionUtils.isEmpty(goodsInfoList)) {
                return Collections.emptyList();
            }
            return goodsInfoList;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    public void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if(!(row.getCell(0).getStringCellValue().contains("SKU编码"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }
}
