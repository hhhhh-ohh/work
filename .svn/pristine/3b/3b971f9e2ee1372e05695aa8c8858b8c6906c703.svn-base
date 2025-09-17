package com.wanmi.sbc.excel.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.response.goodsrestrictedsale.GoodsRestrictedSalePurchaseResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedPurchaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;
import com.wanmi.sbc.goods.bean.vo.OrderForCustomerGoodsInfoExcelVO;
import com.wanmi.sbc.goods.service.GoodsExcelService;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;
import com.wanmi.sbc.setting.api.request.yunservice.YunUploadResourceRequest;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author EDZ
 * @className OrderForCustomerGoodsExcelService
 * @description TODO
 * @date 2022/4/8 14:06
 **/
@Slf4j
@Service
public class OrderForCustomerGoodsExcelService {

    @Resource
    private EsSkuQueryProvider esSkuQueryProvider;

    @Resource
    private GoodsExcelService goodsExcelService;

    @Resource
    private YunServiceProvider yunServiceProvider;

    @Resource
    private CommonUtil commonUtil;

    @Resource
    private CustomerQueryProvider customerQueryProvider;

    @Resource
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Resource
    private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Resource
    private SystemPointsConfigService systemPointsConfigService;

    @Resource
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    public String uploadFile(MultipartFile file, List<OrderForCustomerGoodsInfoExcelVO> selectedSkus) {
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
        String resourceKey = Constants.ORDER_FOR_CUSTOMER_GOODS_EXCEL.concat(userId);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file.getBytes()));
             ByteArrayOutputStream os = new ByteArrayOutputStream();) {
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
            int emptyNum = ExcelHelper.getOrderForCustomerEmptyRowNum(sheet);
            if((lastRowNum - emptyNum) < 1){
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }
            if(lastRowNum > Constants.NUM_200){
                int emptyRowNum = ExcelHelper.getOrderForCustomerEmptyRowNum(sheet);
                if ((lastRowNum - emptyRowNum) > Constants.NUM_200) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "文件数据超过200条，请修改");
                }
            }
            int maxCell = 2;
            AtomicBoolean isError = new AtomicBoolean(false);
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Map<String, List<Cell>> skuNoMap = Maps.newHashMap();
            Map<String, List<Cell>> oldSkuNoMap = Maps.newHashMap();
            //表格里商品对应的数量
            Map<String, Long> skuNoToGoodsNumMap = Maps.newHashMap();
            Map<String, Cell> skuNoToGoodsNumCellMap = Maps.newHashMap();
            //循环除了第一行的所有行
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Cell[] cells = new Cell[maxCell];
                //当前行为空否
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
                    //取出表格的商品数量
                    if(i == 1 && isNotEmpty == true){
                        String goodsNumStr = ExcelHelper.getValue(cell);
                        BigDecimal bigDecimal = null;
                        Long goodsNum = null;
                        if(StringUtils.isNotBlank(goodsNumStr)){
                            bigDecimal = new BigDecimal(goodsNumStr);
                            goodsNum = bigDecimal.longValue();
                        }else {
                            cell.setCellValue(1L);
                        }
                        skuNoToGoodsNumMap.put(ExcelHelper.getValue(cells[0]),StringUtils.isNotBlank(goodsNumStr)
                        ? goodsNum : 1L);
                        skuNoToGoodsNumCellMap.put(ExcelHelper.getValue(cells[0]),cell);
                    }
                }
                if(!isNotEmpty){
                    continue;
                }
                //校验 skuNo
                String skuNo = ExcelHelper.getValue(cells[0]);
                if(StringUtils.isBlank(skuNo)){
                    ExcelHelper.setCellError(workbook, cells[0], "此项必填");
                    isError.set(true);
                }else if(!ValidateUtil.isBetweenLen(skuNo, 1, 20)){
                    ExcelHelper.setCellError(workbook, cells[0], "长度必须1-20个字");
                    isError.set(true);
                }else if(!ValidateUtil.isNotChs(skuNo)){
                    ExcelHelper.setCellError(workbook, cells[0], "仅允许英文、数字、特殊字符");
                    isError.set(true);
                }else {
                    skuNoMap.merge(skuNo, Lists.newArrayList(cells[0]), (s1, s2) -> {s1.addAll(s2);return s1;});
                }
            }

            //根据skuNo查询表格商品详细信息(条件与手动选择相比只缺少上架条件)
            EsSkuPageRequest queryRequest = new EsSkuPageRequest();
            Set<String> skuNoList = skuNoMap.keySet();
            oldSkuNoMap.putAll(skuNoMap);
            queryRequest.setGoodsInfoNos(new ArrayList<>(skuNoList));
            List<GoodsInfoVO> goodsInfoList = getESGoodsInfo(queryRequest).getGoodsInfoPage().getContent();
            //库存map
            Map<String, Long> skuNoToStockMap = goodsInfoList
                    .stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, GoodsInfoVO::getStock));

            mergeGoodsInfos(selectedSkus,skuNoToGoodsNumMap,oldSkuNoMap);
            EsSkuPageRequest pageRequest = new EsSkuPageRequest();
            Set<String> newSkuNoList = oldSkuNoMap.keySet();
            pageRequest.setGoodsInfoNos(new ArrayList<>(newSkuNoList));
            List<GoodsInfoVO> newGoodsInfoList = getESGoodsInfo(pageRequest).getGoodsInfoPage().getContent();

            this.checkGoodsInfoNo(skuNoMap,workbook,isError,commonUtil.getStoreId(),newGoodsInfoList);
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.ORDER_FOR_CUSTOMER_GOODS_ERR_EXCEL.concat(userId);
                goodsExcelService.errorExcel(newFileName,resourceKey,workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{"请下载错误表格查看"});
            }
            //校验过的表格中商品不存在重复和商品不存在等问题,然后合并，校验200条和商品数量
            this.mergeGoodsNum(selectedSkus,skuNoToGoodsNumMap,workbook,skuNoMap,skuNoToGoodsNumCellMap,isError,skuNoToStockMap);
            if (isError.get()) {
                String newFileName = userId.concat(".").concat(fileExt);
                resourceKey = Constants.ORDER_FOR_CUSTOMER_GOODS_ERR_EXCEL.concat(userId);
                goodsExcelService.errorExcel(newFileName,resourceKey,workbook);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030065, new Object[]{"请下载错误表格查看"});
            }


            workbook.write(os);
            YunUploadResourceRequest yunUploadResourceRequest = YunUploadResourceRequest
                    .builder()
                    .resourceType(ResourceType.EXCEL)
                    .content(os.toByteArray())
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

    /**
     * 验证EXCEL
     * @param workbook
     */
    public void checkExcel(Workbook workbook){
        try {
            Sheet sheet1 = workbook.getSheetAt(0);
            Row row = sheet1.getRow(0);
            if(!(row.getCell(0).getStringCellValue().contains("SKU编码"))
                    || !(row.getCell(1).getStringCellValue().contains("商品数量"))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
            }
        }catch (Exception e){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000024);
        }
    }

    private void checkGoodsInfoNo(Map<String, List<Cell>> skuNoMap,Workbook workbook,AtomicBoolean isError,Long storeId,
                                  List<GoodsInfoVO> goodsInfoList){

        //校验sku是否存在、上架或者重复
        if(CollectionUtils.isNotEmpty(goodsInfoList)){
            Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoList.stream()
                    .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoNo, Function.identity()));
            List<Integer> collect = goodsInfoList.stream().map(GoodsInfoVO::getGoodsType).distinct().collect(Collectors.toList());
            long virtualGoodsCount = goodsInfoList.stream().filter(goodsInfoVO -> goodsInfoVO.getGoodsType() == GoodsType.VIRTUAL_GOODS.toValue()).count();
            long electronicCouponGoodsCount = goodsInfoList.stream().filter(goodsInfoVO -> goodsInfoVO.getGoodsType() == GoodsType.ELECTRONIC_COUPON_GOODS.toValue()).count();
            long buyCycleCount = goodsInfoList.parallelStream().filter(goodsInfoVO -> Objects.equals(goodsInfoVO.getIsBuyCycle(), Constants.yes)).count();
            skuNoMap.forEach((key, value) -> {
                if (Objects.isNull(goodsInfoMap.get(key))) {
                    value.forEach(cell -> {
                        //查询出来的sku列表没有该sku
                        ExcelHelper.setCellError(workbook, cell, "该商品编码不存在或不符合条件");
                    });
                    isError.set(true);
                } else {
                    GoodsInfoVO goodsInfoVO = goodsInfoMap.get(key);
                    Integer addedFlag = goodsInfoVO.getAddedFlag();

                    if (buyCycleCount >  Constants.ZERO) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "无法选择周期购商品");
                        });
                        isError.set(true);
                    }

                    //代客下单商品导入只能选择一种类型的商品
                    if (collect.size() > Constants.ONE){
                        if (collect.contains(GoodsType.REAL_GOODS.toValue()) ){
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "电子卡券或者虚拟商品不可和实物商品混选");
                            });
                            isError.set(true);
                        }else {
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "只可选择一种虚拟商品");
                            });
                            isError.set(true);
                        }
                    }

                    //虚拟商品和电子卡券只能选择一件
                    if (virtualGoodsCount > Constants.ONE || electronicCouponGoodsCount > Constants.ONE){
                        if (goodsInfoVO.getGoodsType() == GoodsType.VIRTUAL_GOODS.toValue() || goodsInfoVO.getGoodsType() == GoodsType.ELECTRONIC_COUPON_GOODS.toValue()){
                            value.forEach(cell -> {
                                ExcelHelper.setCellError(workbook, cell, "只可选择一种虚拟商品");
                            });
                            isError.set(true);
                        }
                    }


                    if (CollectionUtils.size(value) > 1) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该商品编码重复");
                        });
                        isError.set(true);
                    } else if (Objects.equals(addedFlag, AddedFlag.NO.toValue())) {
                        value.forEach(cell -> {
                            ExcelHelper.setCellError(workbook, cell, "该商品未上架");
                        });
                        isError.set(true);
                    }
                }
            });
        }else if (CollectionUtils.isEmpty(goodsInfoList) && CollectionUtils.isNotEmpty(skuNoMap.keySet())) {
            skuNoMap.forEach((key, value) -> {
                value.forEach(cell -> {
                    //为单元格设置重复错误提示
                    ExcelHelper.setCellError(workbook, cell, "该商品编码不存在或不符合条件");
                });
                isError.set(true);
            });
        }

    }

    private EsSkuPageResponse getESGoodsInfo(EsSkuPageRequest queryRequest){
        //已上架字段预留给校验
        //过滤积分的商品
        queryRequest.setIntegralPriceFlag(Boolean.TRUE);
        //已审核
        queryRequest.setAuditStatus(CheckStatus.CHECKED);
        //可售
        queryRequest.setVendibility(NumberUtils.INTEGER_ONE);
        //可用
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setStoreId(commonUtil.getStoreId());
        queryRequest.setPageSize(200);
        queryRequest.setShowPointFlag(Boolean.TRUE);
        queryRequest.setShowProviderInfoFlag(Boolean.TRUE);
        queryRequest.setFillLmInfoFlag(Boolean.TRUE);
        queryRequest.setShowPointFlag(Boolean.TRUE);
        EsSkuPageResponse response = esSkuQueryProvider.page(queryRequest).getContext();
        return response;
    }


    private void mergeGoodsInfos(List<OrderForCustomerGoodsInfoExcelVO> selectedSkus,Map<String, Long> skuNoToGoodsNumMap, Map<String, List<Cell>> skuNoMap){
        //过滤出未重复和重复的sku，<skuNo,goodsNum>
        Map<String, Long> noMultiSku = new HashMap<>();
        selectedSkus.forEach(sku ->{
            Long goodsNum = skuNoToGoodsNumMap.get(sku.getGoodsInfoNo());
            if(Objects.isNull(goodsNum)){
                noMultiSku.put(sku.getGoodsInfoNo(),sku.getGoodsNum());
            }
        });
        //将传入的未重复sku放入表格中
        if(!noMultiSku.isEmpty()){
            noMultiSku.forEach((k,v) ->{
                List<Cell> noMultiList = new ArrayList<>();
                skuNoMap.put(k,noMultiList);
            });

        }
    }

    private void mergeGoodsNum(List<OrderForCustomerGoodsInfoExcelVO> selectedSkus,Map<String, Long> skuNoToGoodsNumMap,
                               Workbook workbook,Map<String, List<Cell>> skuNoMap,Map<String, Cell> skuNoToGoodsNumCellMap,
                               AtomicBoolean isError,Map<String, Long> skuNoToStockMap){

        //过滤出未重复和重复的sku，<skuNo,goodsNum>
        Map<String, Long> noMultiSku = new HashMap<>();
        Map<String, Long> multiSku = new HashMap<>();
        selectedSkus.forEach(sku ->{
            Long goodsNum = skuNoToGoodsNumMap.get(sku.getGoodsInfoNo());
            if(Objects.isNull(goodsNum)){
                noMultiSku.put(sku.getGoodsInfoNo(),sku.getGoodsNum());
            }else {
                multiSku.put(sku.getGoodsInfoNo(),sku.getGoodsNum());
            }
        });
        //todo
        //将传入的未重复sku放入表格中
        if(!noMultiSku.isEmpty()){
            Sheet sheet = workbook.getSheetAt(0);
            int[] rowIndex = {sheet.getLastRowNum()};
            noMultiSku.forEach((k,v) ->{
                Row row = sheet.createRow(++rowIndex[0]);
                Cell cell = row.createCell(0);
                Cell cell1 = row.createCell(1);
                cell.setCellValue(k);
                cell1.setCellValue(v);
                //将未重复放入一系列表格map<skuNo,List<skuNo>>...
                List<Cell> noMultiList = new ArrayList<>();
                noMultiList.add(cell);
                skuNoMap.put(k,noMultiList);
                skuNoToGoodsNumMap.put(k,v);
                skuNoToGoodsNumCellMap.put(k,cell1);
            });
            //查询未重复的库存
            EsSkuPageRequest queryRequest = new EsSkuPageRequest();
            Set<String> skuNoList = noMultiSku.keySet();
            queryRequest.setGoodsInfoNos(new ArrayList<>(skuNoList));
            getESGoodsInfo(queryRequest).getGoodsInfoPage().getContent().forEach(sku -> {
                skuNoToStockMap.put(sku.getGoodsInfoNo(),sku.getStock());
            });

        }
        //将传入的重复sku在表格中合并数量
        if(!multiSku.isEmpty()){
            multiSku.forEach((k,v) ->{
                //表格中的数量
                Long goodsNum = skuNoToGoodsNumMap.get(k);
                //加上传入的数量
                Long newGoodsNum = goodsNum + v;
                Cell cell = skuNoToGoodsNumCellMap.get(k);
                cell.setCellValue(newGoodsNum);
                skuNoToGoodsNumMap.put(k,newGoodsNum);
            });
        }
        //校验总的（页面+表格）商品条数（200）和商品数量
        if(skuNoMap.size() > Constants.NUM_200){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "总数据超过200条，请修改");
        }

        skuNoToGoodsNumMap.forEach((k,v) -> {
            if (v > skuNoToStockMap.get(k)) {
                ExcelHelper.setCellError(workbook, skuNoToGoodsNumCellMap.get(k), "商品数量大于库存");
                isError.set(true);
            }
        });
    }

    public BaseResponse<EsSkuPageResponse> getGoodsInfoList(EsSkuPageRequest queryRequest) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.ORDER_FOR_CUSTOMER_GOODS_EXCEL.concat(userId);
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
            Map<String, Long> skuNoToGoodsNumMap = Maps.newHashMap();
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
                    //取出表格的商品数量
                    if(i == 1 && isNotEmpty == true){
                        String goodsNumStr = ExcelHelper.getValue(cell);
                        BigDecimal bigDecimal = new BigDecimal(goodsNumStr);
                        Long goodsNum = bigDecimal.longValue();
                        skuNoToGoodsNumMap.put(ExcelHelper.getValue(cells[0]),goodsNum );
                    }
                }
                if(!isNotEmpty){
                    continue;
                }
            }
            //获取sku信息
            //根据skuNo查询表格商品详细信息(条件与手动选择相比只缺少上架条件)
            //获取会员
            CustomerGetByIdResponse customer = null;
            if (StringUtils.isNotBlank(queryRequest.getCustomerId())) {
                customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(queryRequest.getCustomerId())
                ).getContext();
                if (Objects.isNull(customer)) {
                    throw new SbcRuntimeException(CustomerErrorCodeEnum.K010001);
                }
            }
            queryRequest.setStoreId(commonUtil.getStoreId());
            //按创建时间倒序、ID升序
            if(StringUtils.isBlank(queryRequest.getSortColumn()) &&
                    MapUtils.isEmpty(queryRequest.getSortMap())){
                queryRequest.putSort("addedTime", SortType.DESC.toValue());
            }else{
                if (StringUtils.isNotBlank(queryRequest.getSortColumn())){
                    queryRequest.putSort(queryRequest.getSortColumn(), queryRequest.getSortRole());
                }
            }

            //塞入skuNo列表
            Set<String> skuNoList = skuNoToGoodsNumMap.keySet();
            queryRequest.setGoodsInfoNos(new ArrayList<>(skuNoList));
            EsSkuPageResponse response = getESGoodsInfo(queryRequest);
            List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfoPage().getContent();


            Map<String, List<Long>> storeCateIdsMap = new HashMap<>();
            for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
                //经过下面的一系列替换goodsInfoVOList操作，店铺分类id会丢，所以记录一下用于还原
                storeCateIdsMap.put(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getStoreCateIds());
            }
            Map<String, String> storeCateNameMap = new HashMap<>();
            for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
                //经过下面的一系列替换goodsInfoVOList操作，店铺分类id会丢，所以记录一下用于还原
                storeCateNameMap.put(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getStoreCateName());
            }

            if (customer != null && StringUtils.isNotBlank(customer.getCustomerId())) {
                GoodsIntervalPriceByCustomerIdResponse priceResponse =
                        goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList, customer.getCustomerId());
                response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
                goodsInfoVOList = priceResponse.getGoodsInfoVOList();
            } else {
                GoodsIntervalPriceResponse priceResponse =
                        goodsIntervalPriceService.getGoodsIntervalPriceVOList(goodsInfoVOList);
                response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
                goodsInfoVOList = priceResponse.getGoodsInfoVOList();
            }

            //计算会员价
            if (customer != null && StringUtils.isNotBlank(customer.getCustomerId())) {
                goodsInfoVOList = marketingLevelPluginProvider.goodsListFilter(
                        MarketingLevelGoodsListFilterRequest.builder()
                                .customerId(customer.getCustomerId())
                                .goodsInfos(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class)).build())
                        .getContext().getGoodsInfoVOList();
            }
            if (Objects.nonNull(customer)) {
                goodsInfoVOList = this.setRestrictedNum(goodsInfoVOList, customer);
            }
            for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
                //还原storeCateIds
                goodsInfoVO.setStoreCateIds(storeCateIdsMap.get(goodsInfoVO.getGoodsInfoId()));
                goodsInfoVO.setStoreCateName(storeCateNameMap.get(goodsInfoVO.getGoodsInfoId()));
            }
            systemPointsConfigService.clearBuyPointsForGoodsInfoVO(goodsInfoVOList);

            List<OrderForCustomerGoodsInfoExcelVO> newGoodsInfoVOList =
                    KsBeanUtil.convert(goodsInfoVOList, OrderForCustomerGoodsInfoExcelVO.class);
            newGoodsInfoVOList.forEach(sku -> {
                sku.setGoodsNum(skuNoToGoodsNumMap.get(sku.getGoodsInfoNo()));
            });

            response.setGoodsInfoPage(new MicroServicePage(newGoodsInfoVOList, queryRequest.getPageRequest(),
                    response.getGoodsInfoPage().getTotalElements()));

            if (CollectionUtils.isEmpty(newGoodsInfoVOList)) {
                return BaseResponse.success(new EsSkuPageResponse());
            }
            return BaseResponse.success(response);
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    public void downloadErrorFile(String fileExt) {
        String userId = commonUtil.getOperatorId();
        String resourceKey = Constants.ORDER_FOR_CUSTOMER_GOODS_ERR_EXCEL.concat(userId);
        goodsExcelService.getErrExcel(resourceKey,fileExt);
    }

    /**
     * 设置限售数据
     *
     * @param goodsInfoVOS
     * @param customerVO
     * @return
     */
    private List<GoodsInfoVO> setRestrictedNum(List<GoodsInfoVO> goodsInfoVOS, CustomerVO customerVO) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(goodsInfoVOS)) {
            List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS = new ArrayList<>();
            goodsInfoVOS = goodsInfoVOS.stream().map(g -> {
                GoodsRestrictedValidateVO rvv = new GoodsRestrictedValidateVO();
                rvv.setNum(g.getBuyCount());
                rvv.setSkuId(g.getGoodsInfoId());
                goodsRestrictedValidateVOS.add(rvv);
                if (Objects.equals(DeleteFlag.NO, g.getDelFlag())
                        && Objects.equals(CheckStatus.CHECKED, g.getAuditStatus())) {
                    g.setGoodsStatus(GoodsStatus.OK);
                    if (Objects.isNull(g.getStock()) || g.getStock() < 1) {
                        g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                } else {
                    g.setGoodsStatus(GoodsStatus.INVALID);
                }
                return g;
            }).collect(Collectors.toList());
            GoodsRestrictedSalePurchaseResponse response = goodsRestrictedSaleQueryProvider.validatePurchaseRestricted(
                    GoodsRestrictedBatchValidateRequest.builder()
                            .goodsRestrictedValidateVOS(goodsRestrictedValidateVOS)
                            .customerVO(customerVO)
                            .build()).getContext();
            if (Objects.nonNull(response) && org.apache.commons.collections4.CollectionUtils.isNotEmpty(response.getGoodsRestrictedPurchaseVOS())) {
                List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS = response.getGoodsRestrictedPurchaseVOS();
                Map<String, GoodsRestrictedPurchaseVO> purchaseMap =
                        goodsRestrictedPurchaseVOS.stream().collect((Collectors.toMap(GoodsRestrictedPurchaseVO::getGoodsInfoId, g -> g)));
                goodsInfoVOS = goodsInfoVOS.stream().map(g -> {
                    GoodsRestrictedPurchaseVO goodsRestrictedPurchaseVO = purchaseMap.get(g.getGoodsInfoId());
                    if (Objects.nonNull(goodsRestrictedPurchaseVO)) {
                        if (DefaultFlag.YES.equals(goodsRestrictedPurchaseVO.getDefaultFlag())) {
                            g.setMaxCount(goodsRestrictedPurchaseVO.getRestrictedNum());
                            g.setCount(goodsRestrictedPurchaseVO.getStartSaleNum());
                        } else {
                            g.setGoodsStatus(GoodsStatus.INVALID);
                        }
                    }
                    return g;
                }).collect(Collectors.toList());
            }
        }
        return goodsInfoVOS;
    }
}