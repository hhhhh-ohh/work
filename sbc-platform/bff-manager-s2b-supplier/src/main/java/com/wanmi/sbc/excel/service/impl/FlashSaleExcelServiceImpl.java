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
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.excel.service.MarketingExcelService;
import com.wanmi.sbc.goods.api.provider.excel.GoodsExcelProvider;
import com.wanmi.sbc.goods.api.provider.flashsalecate.FlashSaleCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.excel.MarketingTemplateRequest;
import com.wanmi.sbc.goods.api.request.flashsalecate.FlashSaleCateListRequest;
import com.wanmi.sbc.goods.api.response.excel.GoodsExcelExportTemplateResponse;
import com.wanmi.sbc.goods.api.response.flashsalecate.FlashSaleCateListResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.ExcelFlashSaleGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleCateVO;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
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
 * @description <p> 秒杀商品导入 </p>
 */
@Slf4j
@Service
public class FlashSaleExcelServiceImpl implements MarketingExcelService {

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
    private FlashSaleCateQueryProvider flashSaleCateQueryProvider;

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
        this.write(file,"秒杀商品导入模板.xls");
    }

    @Override
    public void downloadErrorFile(String fileExt) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.FLASH_SALE_GOODS_ERR_EXCEL.concat(userId);
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
        List<FlashSaleCateVO> flashSaleCateList = this.getFlashSaleCateList();
        List<String> cateNameList = flashSaleCateList.stream().map(FlashSaleCateVO::getCateName).collect(Collectors.toList());
        String fileExt = FilenameUtils.getExtension(filename);
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.FLASH_SALE_GOODS_EXCEL.concat(userId);
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

                //库存
                String stock = ExcelHelper.getValue(getCell(cells, 1));
                if(StringUtils.isNotBlank(stock)){
                    this.checkNumber(stock,workbook,cells,1,isError);
                }else {
                    ExcelHelper.setCellError(workbook, getCell(cells, 1), "此项必填");
                    isError.set(true);
                }
                //校验秒杀价
                String price = ExcelHelper.getValue(getCell(cells, 2));

                if(StringUtils.isNotBlank(price)){
                    this.checkPrice(price,workbook,cells,2,isError);
                } else {
                    ExcelHelper.setCellError(workbook, getCell(cells, 2), "此项必填");
                    isError.set(true);
                }

                String cateName = ExcelHelper.getValue(getCell(cells, 3));
                if(StringUtils.isNotBlank(cateName)){
                    if(!cateNameList.contains(cateName)){
                        ExcelHelper.setCellError(workbook, getCell(cells, 3), "该分类不存在");
                        isError.set(true);
                    }
                }

                String restrict = ExcelHelper.getValue(getCell(cells, 4));
                if(StringUtils.isNotBlank(restrict)){
                    if (!NumberUtils.isDigits(restrict)) {
                        ExcelHelper.setCellError(workbook, getCell(cells, 4), "此项必须为整数");
                        isError.set(true);
                    } else if (NumberUtils.toLong(restrict) < 1 || NumberUtils.toLong(restrict) > 100) {
                        ExcelHelper.setCellError(workbook, getCell(cells, 4), "此项必须在1-100范围内");
                        isError.set(true);
                    }
                }
                //起售数量
                String sale = ExcelHelper.getValue(getCell(cells, 5));
                if(StringUtils.isNotBlank(sale)){
                    if (!NumberUtils.isDigits(sale)) {
                        ExcelHelper.setCellError(workbook, getCell(cells, 5), "此项必须为整数");
                        isError.set(true);
                    } else if (NumberUtils.toLong(sale) < 1 || NumberUtils.toLong(sale) > 100) {
                        ExcelHelper.setCellError(workbook, getCell(cells, 5), "此项必须在1-100范围内");
                        isError.set(true);
                    }
                }

            }
            this.checkGoodsInfoNo(skuNoMap,goodsInfoQueryProvider,workbook,isError,goodsQueryProvider,
                    Boolean.FALSE,commonUtil.getStoreId(),Boolean.FALSE,Boolean.FALSE,Boolean.TRUE, Boolean.FALSE);
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.FLASH_SALE_GOODS_ERR_EXCEL.concat(userId);
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
        String resourceKey = Constants.FLASH_SALE_GOODS_EXCEL.concat(userId);
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
            List<FlashSaleCateVO> flashSaleCateList = this.getFlashSaleCateList();
            Map<String, Long> cateMap = flashSaleCateList.stream()
                    .collect(Collectors.toMap(FlashSaleCateVO::getCateName, FlashSaleCateVO::getCateId));
            Map<String, ExcelFlashSaleGoodsInfoVO> importGoodsInfoMap = Maps.newHashMap();
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
                String stock = ExcelHelper.getValue(getCell(cells, 1));
                long flashStock = NumberUtils.toLong(stock);
                //校验秒杀价
                String price = ExcelHelper.getValue(getCell(cells, 2));


                BigDecimal flashPrice = NumberUtils.createBigDecimal(price);
                String max = ExcelHelper.getValue(getCell(cells, 4));
                Long maxNum = NumberUtils.toLong(max);
                //起售数量
                String min = ExcelHelper.getValue(getCell(cells, 5));
                Long minNum = NumberUtils.toLong(min);
                //是否包邮
                String postage = ExcelHelper.getValue(getCell(cells, 6));
                Integer isPostage = StringUtils.isBlank(postage) || StringUtils.equals(postage,"否")
                        ? NumberUtils.INTEGER_ZERO : NumberUtils.INTEGER_ONE;
                ExcelFlashSaleGoodsInfoVO flashSaleGoodsInfoVO = ExcelFlashSaleGoodsInfoVO.builder()
                        .flashStock(flashStock)
                        .price(flashPrice)
                        .postage(DefaultFlag.fromValue(isPostage))
                        .minNum(minNum)
                        .maxNum(maxNum > 0 ? maxNum : flashStock <=100 ? flashStock : 100)
                        .build();
                flashSaleGoodsInfoVO.setGoodsInfoNo(skuNo);

                String cateName = ExcelHelper.getValue(getCell(cells, 3));
                if(StringUtils.isNotBlank(cateName)){
                    Long cateId = cateMap.get(cateName);
                    flashSaleGoodsInfoVO.setFlashCateId(cateId);
                }
                importGoodsInfoMap.putIfAbsent(skuNo,flashSaleGoodsInfoVO);
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
            return goodsInfoList.stream().map(source -> {
                String goodsInfoNo = source.getGoodsInfoNo();
                ExcelFlashSaleGoodsInfoVO target = importGoodsInfoMap.get(goodsInfoNo);
                BeanUtils.copyProperties(source, target);
                return target;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    private List<FlashSaleCateVO> getFlashSaleCateList(){
        FlashSaleCateListRequest cateListRequest = FlashSaleCateListRequest.builder()
                .delFlag(DeleteFlag.NO)
                .build();
        FlashSaleCateListResponse listResponse = flashSaleCateQueryProvider.list(cateListRequest).getContext();
        List<FlashSaleCateVO> flashSaleCateVOList = listResponse.getFlashSaleCateVOList();
        if(CollectionUtils.isEmpty(flashSaleCateVOList)){
            return Collections.emptyList();
        }
        return flashSaleCateVOList;
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
                    && row.getCell(1).getStringCellValue().contains("抢购独立库存")
                    && row.getCell(2).getStringCellValue().contains("抢购价"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }
}
