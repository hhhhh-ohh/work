package com.wanmi.sbc.excel.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.distribution.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyDistributionCommissionRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.excel.service.MarketingExcelService;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsAddRequest;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.api.response.info.DistributionGoodsAddResponse;
import com.wanmi.sbc.goods.bean.dto.DistributionGoodsInfoModifyDTO;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsExcelService;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.util.CommonUtil;

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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2022/2/8 15:15
 * @description <p> 分销商品导入 </p>
 */
@Slf4j
@Service
public class DistributionExcelServiceImpl implements MarketingExcelService {

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
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private DistributionSettingQueryProvider settingQueryProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private MarketingBaseService marketingBaseService;

    @Override
    public void downloadTemplate(Integer activityType) {
        MarketingTemplateRequest request = MarketingTemplateRequest.builder()
                .activityType(activityType)
                .build();
        GoodsExcelExportTemplateResponse response = goodsExcelProvider.getTemplate(request).getContext();
        String file = response.getFile();
        this.write(file,"分销商品导入模板.xls");
    }

    @Override
    public void downloadErrorFile(String fileExt) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.DISTRIBUTION_GOODS_ERR_EXCEL.concat(userId);
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
        String resourceKey = Constants.DISTRIBUTION_GOODS_EXCEL.concat(userId);
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
            int maxCell = 2;
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
                //分销佣金
                String rate = ExcelHelper.getValue(getCell(cells, 1));
                if(StringUtils.isNotBlank(rate)){
                    if (!NumberUtils.isDigits(rate)) {
                        ExcelHelper.setCellError(workbook, getCell(cells, 1), "此项必须为正整数");
                        isError.set(true);
                    } else if(NumberUtils.toLong(rate) < 1 || NumberUtils.toLong(rate) > 99){
                        ExcelHelper.setCellError(workbook, getCell(cells, 1), "此项必须在1-99范围内");
                        isError.set(true);
                    }
                } else {
                    ExcelHelper.setCellError(workbook, getCell(cells, 1), "此项必填");
                    isError.set(true);
                }
            }
            this.checkGoodsInfoNo(skuNoMap,goodsInfoQueryProvider,workbook,isError,goodsQueryProvider,Boolean.TRUE,
                    commonUtil.getStoreId(),Boolean.FALSE,Boolean.FALSE,Boolean.FALSE, Boolean.FALSE);
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.DISTRIBUTION_GOODS_ERR_EXCEL.concat(userId);
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
        String resourceKey = Constants.DISTRIBUTION_GOODS_EXCEL.concat(userId);
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(resourceKey)
                .build()).getContext().getContent();
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            int maxCell = 2;
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Map<String,BigDecimal> importGoodsInfoMap = Maps.newHashMap();
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
                //佣金比例
                String rate = ExcelHelper.getValue(getCell(cells, 1));
                BigDecimal commissionRate = NumberUtils.createBigDecimal(rate);
                importGoodsInfoMap.putIfAbsent(skuNo,commissionRate);
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
            //组装数据

            List<DistributionGoodsInfoModifyDTO> addList = goodsInfoList.stream().map(goodsInfoVO -> {
                DistributionGoodsInfoModifyDTO distributionGoodsInfoModifyDTO = new DistributionGoodsInfoModifyDTO();
                String goodsInfoNo = goodsInfoVO.getGoodsInfoNo();
                BigDecimal marketPrice = goodsInfoVO.getMarketPrice();
                BigDecimal commissionRate = importGoodsInfoMap.get(goodsInfoNo);
                distributionGoodsInfoModifyDTO.setGoodsInfoId(goodsInfoVO.getGoodsInfoId());
                commissionRate = commissionRate.divide(new BigDecimal(100),2,RoundingMode.DOWN);
                distributionGoodsInfoModifyDTO.setCommissionRate(commissionRate);
                BigDecimal distributionCommission = Objects.isNull(marketPrice) ?  BigDecimal.ZERO : marketPrice.multiply(commissionRate);
                distributionCommission = distributionCommission.setScale(2, RoundingMode.DOWN);
                distributionGoodsInfoModifyDTO.setDistributionCommission(distributionCommission);
                return distributionGoodsInfoModifyDTO;
            }).collect(Collectors.toList());
            //新增分销商品
            this.addDistributionGoods(addList);
            return goodsInfoList;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }

    }

    /**
     * 新增分销商品
     * @param addList
     */
    @GlobalTransactional
    private void addDistributionGoods(List<DistributionGoodsInfoModifyDTO> addList){
        DistributionGoodsAddRequest addRequest = new DistributionGoodsAddRequest();
        addRequest.setDistributionGoodsInfoModifyDTOS(addList);
        BaseResponse<Boolean> auditSwitch = settingQueryProvider.getDistributionGoodsSwitch();
        // 不用审核
        addRequest.setDistributionGoodsAudit(auditSwitch.getContext()
                ? DistributionGoodsAudit.CHECKED : DistributionGoodsAudit.WAIT_CHECK);

        //营销互斥校验
        Long storeId = commonUtil.getStoreId();
        List<String> skuList = addList.stream().map(DistributionGoodsInfoModifyDTO::getGoodsInfoId).toList();
        marketingBaseService.mutexValidateByAdd(storeId, LocalDateTime.now(), LocalDateTime.now().plusYears(100), skuList);

        DistributionGoodsAddResponse goodsAddResponse = goodsInfoProvider.addDistributionGoods(addRequest).getContext();

        if (CollectionUtils.isNotEmpty(goodsAddResponse.getGoodsInfoIds())){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030079);
        }

        // 查询店铺是否开启社交分销
        DefaultFlag defaultFlag = distributionCacheService.queryStoreOpenFlag(String.valueOf(commonUtil.getStoreId()));
        // 同步到ES
        EsGoodsInfoModifyDistributionCommissionRequest commissionRequest = new EsGoodsInfoModifyDistributionCommissionRequest();
        commissionRequest.setDistributionGoodsInfoDTOList(addRequest.getDistributionGoodsInfoModifyDTOS());
        commissionRequest.setDistributionGoodsAudit(addRequest.getDistributionGoodsAudit());
        // 开关开：0，关1
        commissionRequest.setDistributionGoodsStatus(defaultFlag == DefaultFlag.NO ? 1 : 0);
        esGoodsInfoElasticProvider.modifyDistributionCommission(commissionRequest);
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
                    && row.getCell(1).getStringCellValue().contains("佣金比例"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }


}
