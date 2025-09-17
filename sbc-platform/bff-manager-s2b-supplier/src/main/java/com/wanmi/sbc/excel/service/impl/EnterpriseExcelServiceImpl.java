package com.wanmi.sbc.excel.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoEnterpriseBatchAuditRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.excel.service.MarketingExcelService;
import com.wanmi.sbc.goods.api.provider.enterprise.EnterpriseGoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterprisePriceBatchUpdateRequest;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.bean.dto.BatchEnterPrisePriceDTO;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsExcelService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2022/2/10 18:54
 * @description <p> 企业购商品导入 </p>
 */
@Slf4j
@Service
public class EnterpriseExcelServiceImpl implements MarketingExcelService {

    @Autowired
    private GoodsExcelProvider goodsExcelProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private GoodsExcelService goodsExcelService;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private EnterpriseGoodsInfoProvider enterpriseGoodsInfoProvider;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Override
    public void downloadTemplate(Integer activityType) {
        MarketingTemplateRequest request = MarketingTemplateRequest.builder()
                .activityType(activityType)
                .build();
        GoodsExcelExportTemplateResponse response = goodsExcelProvider.getTemplate(request).getContext();
        String file = response.getFile();
        this.write(file,"企业购商品导入模板.xls");
    }

    @Override
    public void downloadErrorFile(String fileExt) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.ENTERPRISE_GOODS_ERR_EXCEL.concat(userId);
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
        String resourceKey = Constants.ENTERPRISE_GOODS_EXCEL.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()))) {
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
            int maxCell = 7;
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
                String price = ExcelHelper.getValue(getCell(cells, 1));
                if(StringUtils.isNotBlank(price)){
                    this.checkPrice(price,workbook,cells,1,isError);
                } else {
                    ExcelHelper.setCellError(workbook, getCell(cells, 1), "此项必填");
                    isError.set(true);
                }

            }
            this.checkGoodsInfoNo(skuNoMap,goodsInfoQueryProvider,workbook,isError,goodsQueryProvider,Boolean.FALSE,
                    commonUtil.getStoreId(),Boolean.TRUE,Boolean.FALSE,Boolean.FALSE, Boolean.FALSE);
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.ENTERPRISE_GOODS_ERR_EXCEL.concat(userId);
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
        String resourceKey = Constants.ENTERPRISE_GOODS_EXCEL.concat(userId);
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            int maxCell = 7;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Map<String, BigDecimal> importGoodsInfoMap = Maps.newHashMap();
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
                //库存
                String price = ExcelHelper.getValue(getCell(cells, 1));
                BigDecimal enterprisePrice = NumberUtils.createBigDecimal(price);

                importGoodsInfoMap.putIfAbsent(skuNo,enterprisePrice);
            }
            List<String> skuNoList = new ArrayList<>(importGoodsInfoMap.keySet());
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
            List<BatchEnterPrisePriceDTO> addList = goodsInfoList.stream().map(goodsInfoVO -> {
                BatchEnterPrisePriceDTO enterPrisePriceDTO = new BatchEnterPrisePriceDTO();
                BigDecimal price = importGoodsInfoMap.get(goodsInfoVO.getGoodsInfoNo());
                enterPrisePriceDTO.setGoodsInfoId(goodsInfoVO.getGoodsInfoId());
                enterPrisePriceDTO.setEnterPrisePrice(price);
                return enterPrisePriceDTO;
            }).collect(Collectors.toList());
            this.batchUpdateEnterprisePrice(addList);
            return goodsInfoList;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    @GlobalTransactional
    private void batchUpdateEnterprisePrice(List<BatchEnterPrisePriceDTO> addList){

        EnterprisePriceBatchUpdateRequest request = new EnterprisePriceBatchUpdateRequest();
        request.setBatchEnterPrisePriceDTOS(addList);
        //判断是否购买了企业购服务
        IepSettingVO iepSettingVO = commonUtil.getIepSettingInfo();
        //审核开关入参
        request.setEnterpriseGoodsAuditFlag(iepSettingVO.getEnterpriseGoodsAuditFlag());

        enterpriseGoodsInfoProvider.batchUpdateEnterprisePrice(request);

        if (DefaultFlag.NO.equals(iepSettingVO.getEnterpriseGoodsAuditFlag())) {
            //更新es
            esGoodsInfoElasticProvider.updateEnterpriseGoodsInfo(EsGoodsInfoEnterpriseBatchAuditRequest.builder().
                    batchEnterPrisePriceDTOS(request.getBatchEnterPrisePriceDTOS()).
                    enterpriseAuditState(EnterpriseAuditState.CHECKED).build());
        } else {
            esGoodsInfoElasticProvider.updateEnterpriseGoodsInfo(EsGoodsInfoEnterpriseBatchAuditRequest.builder().
                    batchEnterPrisePriceDTOS(request.getBatchEnterPrisePriceDTOS()).
                    enterpriseAuditState(EnterpriseAuditState.WAIT_CHECK).build());
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
                    && row.getCell(1).getStringCellValue().contains("企业价"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

}
