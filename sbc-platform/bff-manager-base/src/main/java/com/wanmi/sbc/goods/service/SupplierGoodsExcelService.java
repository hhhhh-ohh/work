package com.wanmi.sbc.goods.service;

import static java.util.Objects.nonNull;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.common.util.WebUtil;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateLeafByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.common.GoodsCommonBatchAddRequest;
import com.wanmi.sbc.goods.api.request.common.OperationLogAddRequest;
import com.wanmi.sbc.goods.api.request.freight.FreightTemplateGoodsDefaultByStoreIdRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreIdRequest;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;
import com.wanmi.sbc.goods.bean.vo.FreightTemplateGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateResponseVO;
import com.wanmi.sbc.goods.request.GoodsSupplierExcelImportRequest;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardInvalidRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.sensitivewords.service.SensitiveWordService;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.request.yunservice.YunGetResourceRequest;

import io.jsonwebtoken.Claims;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品EXCEL处理服务
 * Created by dyt on 2017/8/17.
 */
@Slf4j
@Service
public class SupplierGoodsExcelService {

    @Autowired
    private GoodsExcelService goodsExcelService;

    @Autowired
    private FreightTemplateGoodsQueryProvider freightTemplateGoodsQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    /**
     * 导入模板
     *
     * @return
     */
    public void implGoods(GoodsSupplierExcelImportRequest goodsRequest) {
        byte[] content = yunServiceProvider.getFile(YunGetResourceRequest.builder()
                .resourceKey(Constants.GOODS_EXCEL_DIR.concat(goodsRequest.getUserId()))
                .build()).getContext().getContent();
        if (content == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000011);
        }
        if (content.length > Constants.IMPORT_GOODS_MAX_SIZE_LIMIT * 1024 * 1024) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030064, new Object[]{Constants.IMPORT_GOODS_MAX_SIZE_LIMIT});
        }
        Integer goodsType = goodsRequest.getGoodsType();
        FreightTemplateGoodsVO freightTemp = null;
        if (NumberUtils.INTEGER_ZERO.equals(goodsType)) {
            freightTemp = getFreightTemplateGoodsVo(goodsRequest);
        }


        // 创建Workbook工作薄对象，表示整个excel
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            // 检测文档正确性
            this.goodsExcelService.checkExcel(workbook);

            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum < 1) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030066);
            }

            StoreCateListByStoreIdRequest storeCateQueryRequest = new StoreCateListByStoreIdRequest();
            storeCateQueryRequest.setStoreId(goodsRequest.getStoreId());
            List<StoreCateResponseVO> storeCateList = storeCateQueryProvider.listByStoreId(storeCateQueryRequest)
                    .getContext().getStoreCateResponseVOList();
            // 获得默认的商品类型ID
            Long defaultStoreCateId = getDefaultStoreCateId(storeCateList);
            Map<Long, Long> storeCateSet = getStoreCateSet(storeCateList);

            //SkuMap<Spu编号,Spu对象>
            Map<String, BatchGoodsDTO> goodses = new LinkedHashMap<>();
            //SkuMap<Spu编号,Sku对象>
            Map<String, List<BatchGoodsInfoDTO>> skus = new LinkedHashMap<>();

            //SkuMap<Spu编号,图片对象>
            Map<String, List<String>> images = new LinkedHashMap<>();

            //存储编码以及单元格对象，验证重复
            Map<String, List<Cell>> spuNos = new HashMap<>();
            Map<String, List<Cell>> skuNos = new HashMap<>();

            //存储编码以及单元格对象，验证规格和规格值
            Map<String, Cell> spuNoSpecs = new HashMap<>();
            Map<String, Cell> spuNoSpecDetails = new HashMap<>();

            //规格值<Spu编号,规格项集合>
            Map<String, List<BatchGoodsSpecDTO>> allSpecs = new HashMap<>();

            //规格值<规格项模拟编号_规格值名称,规格值模拟编号>，保证在同一个Spu下、同一规格项下，不允许出现重复规格值
            Map<String, Long> specDetailMap = new HashMap<>();
            //规格值<Spu编号,规格值集合>
            Map<String, List<BatchGoodsSpecDetailDTO>> allSpecDetails = new HashMap<>();

            Set<Long> brandSet = getBrandSet(goodsRequest);

            Map<Long, String> goodsCateMap = getGoodsCateMap(goodsRequest);
            // 所有敏感词
            Set<String> badwords = sensitiveWordService.getAllBadWord();

            //规格列索引
            int[] specColNum = {11, 13, 15, 17, 19};
            int[] specDetailColNum = {12, 14, 16, 18, 20};
            StoreType type = goodsRequest.getType();
            int maxCell = 34;
            int maxErrorNum = 0;
            CellStyle style = workbook.createCellStyle();
            log.info("商品导入循环开始"+System.currentTimeMillis());
            //循环除了第一行的所有行
            for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                if (Constants.IMPORT_GOODS_MAX_ERROR_NUM == maxErrorNum) {
                    break;
                }
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
                //数据都为空，则跳过去
                if (!isNotEmpty) {
                    continue;
                }

                BatchGoodsDTO goods = new BatchGoodsDTO();
                if (StoreType.SUPPLIER.equals(type) || StoreType.O2O.equals(type)) {
                    // 默认销售类型 零售  产品要求是零售 原来是批发
                    goods.setSaleType(SaleType.RETAIL.toValue());
                    goods.setGoodsSource(StoreType.SUPPLIER.toValue());
                } else {
                    goods.setSaleType(SaleType.RETAIL.toValue());
                    goods.setGoodsSource(StoreType.PROVIDER.toValue());
                }
                // 默认实体商品
                goods.setGoodsType(goodsType);
                //允许独立设价
                goods.setAllowPriceSet(1);

                if (NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                    goods.setFreightTempId(Objects.nonNull(freightTemp) ? freightTemp.getFreightTempId() : null);
                }else {
                    goods.setFreightTempId(-1L);
                }

                goods.setGoodsName(ExcelHelper.getValue(getCell(cells, 0)));
                goods.setGoodsNo(ExcelHelper.getValue(getCell(cells, 1)));
                goods.setMockGoodsId(goods.getGoodsNo());
                goods.setStoreId(goodsRequest.getStoreId());
                goods.setCompanyInfoId(goodsRequest.getCompanyInfoId());
                goods.setSupplierName(goodsRequest.getSupplierName());
                goods.setCompanyType(goodsRequest.getCompanyType());
                goods.setStoreType(goodsRequest.getStoreType());
                goods.setAddedTimingFlag(Boolean.FALSE);
                goods.setTakedownTimeFlag(Boolean.FALSE);
                goods.setIsBuyCycle(Constants.no);

                goods.setGoodsDetail("");

                // 是否第一个第一个SPU商品
                boolean isFirstGoods = false;
                if (!goodses.containsKey(goods.getGoodsNo())) {
                    isFirstGoods = true;
                }

                //如果第一个SPU商品处理
                if (isFirstGoods) {
                    goods.setCateId(NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 2)).split("_")[0]));

                    goods.setCateTopId(Long.valueOf(goodsCateMap.get(goods.getCateId()).split("\\|")[1]));


                    Long storeCateId = NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 3)).split("_")[0]);
                    if (storeCateId == 0 || (!storeCateSet.containsKey(storeCateId))) {
                        storeCateId = defaultStoreCateId;
                    }
                    List<Long> storeCateIds = new ArrayList<>();
                    storeCateIds.add(storeCateId);
                    Long parentCateId = storeCateSet.get(storeCateId);
                    //获取店铺分类的父分类
                    if (Objects.nonNull(parentCateId) && parentCateId > 0) {
                        storeCateIds.add(parentCateId);
                    }
                    goods.setStoreCateIds(storeCateIds);

                    goods.setGoodsUnit(ExcelHelper.getValue(getCell(cells, 4)));

                    Long brandId = NumberUtils.toLong(ExcelHelper.getValue(getCell(cells, 5)).split("_")[0]);
                    if (brandId > 0 && brandSet.contains(brandId)) {
                        goods.setBrandId(brandId);
                    }
                }
                String imageUrlStr = ExcelHelper.getValue(getCell(cells, 6));
                if (StringUtils.isNoneBlank(imageUrlStr)) {
                    String[] imageUrls = imageUrlStr.split("\\|");
                    List<String> imageArr = new ArrayList<>();
                    if (imageUrls.length > 0) {
                        if (!images.containsKey(goods.getGoodsNo())) {
                            boolean isFirst = true;
                            for (int i = 0; i < imageUrls.length; i++) {
                                if (WebUtil.isImage(imageUrls[i])) {
                                    if (isFirst) {
                                        goods.setGoodsImg(imageUrls[i]);
                                        isFirst = false;
                                    }
                                    imageArr.add(imageUrls[i]);
                                }
                            }
                            images.put(goods.getGoodsNo(), imageArr);
                        }
                    }
                }

                //SPU视频链接地址
                goods.setGoodsVideo(ExcelHelper.getValue(getCell(cells, 7)));
                String details = ExcelHelper.getValue(getCell(cells, 8));
                if (StringUtils.isNotBlank(details)) {
                    //敏感词验证
                    sensitiveWordService.getBadWordTxt(badwords, details);

                    goods.setGoodsDetail(Arrays.stream(details.split("\\|")).map(s -> {
                        if (s.startsWith("http")) {
                            return String.format("<img src='%s'/><br/>", s);
                        }
                        return String.format("<p>%s</p>", s);
                    }).collect(Collectors.joining()));
                }

                BatchGoodsInfoDTO goodsInfo = new BatchGoodsInfoDTO();
                goodsInfo.setMockGoodsId(goods.getMockGoodsId());
                goodsInfo.setMockSpecIds(new ArrayList<>());
                goodsInfo.setMockSpecDetailIds(new ArrayList<>());
                goodsInfo.setCateTopId(goods.getCateTopId());
                goodsInfo.setIsBuyCycle(Constants.no);

                if (StoreType.SUPPLIER.equals(type) || StoreType.O2O.equals(type)) {
                    goodsInfo.setGoodsSource(StoreType.SUPPLIER.toValue());
                } else {
                    goodsInfo.setGoodsSource(StoreType.PROVIDER.toValue());
                }
                //SKU编码
                String skuNo = ExcelHelper.getValue(getCell(cells, 9));
                if (StringUtils.isBlank(skuNo)) {
                    skuNo = goodsExcelService.getSkuNo(skuNos.keySet());
                    getCell(cells, 9).setCellValue(skuNo);
                }
                goodsInfo.setGoodsInfoNo(skuNo);
                //SKU图片
                String skuImage = ExcelHelper.getValue(getCell(cells, 10));
                if (StringUtils.isNoneBlank(skuImage)) {
                    //如果不是图片链接
                    if (!WebUtil.isImage(skuImage)) {
                        goodsInfo.setGoodsInfoImg(null);
                    } else {
                        goodsInfo.setGoodsInfoImg(skuImage);
                    }
                } else {
                    goodsInfo.setGoodsInfoImg(null);
                }

                //处理同一SPU下第一条的规格项
                if (isFirstGoods) {
                    for (int i : specColNum) {
                        BatchGoodsSpecDTO spec = new BatchGoodsSpecDTO();
                        spec.setMockGoodsId(goods.getMockGoodsId());
                        spec.setMockSpecId(NumberUtils.toLong(String.valueOf(rowNum).concat(String.valueOf(i))));
                        spec.setSpecName(ExcelHelper.getValue(cells[i]));
                        allSpecs.merge(goods.getGoodsNo(), new ArrayList<>(Collections.singletonList(spec)), (s1, s2) -> {
                            s1.addAll(s2);
                            return s1;
                        });
                    }
                    spuNoSpecs.put(goods.getGoodsNo(), cells[specColNum[0]]);
                } else {
                    Arrays.stream(specColNum).forEach(i -> cells[i].setCellValue(""));
                }

                //处理规格值
                for (int i = 0; i < specDetailColNum.length; i++) {
                    BatchGoodsSpecDTO spec = allSpecs.get(goods.getGoodsNo()).get(i);
                    BatchGoodsSpecDetailDTO specDetail = new BatchGoodsSpecDetailDTO();
                    specDetail.setDetailName(ExcelHelper.getValue(cells[specDetailColNum[i]]));
                    specDetail.setMockGoodsId(goods.getMockGoodsId());
                    specDetail.setMockSpecId(spec.getMockSpecId());
                    specDetail.setMockSpecDetailId(NumberUtils.toLong(String.valueOf(rowNum).concat(String.valueOf(i))));

                    //设置SKU与规格的扁平数据
                    goodsInfo.getMockSpecIds().add(specDetail.getMockSpecId());

                    spuNoSpecDetails.put(goodsInfo.getGoodsInfoNo(), cells[specDetailColNum[0]]);

                    //明细不为空
                    if (StringUtils.isNotBlank(specDetail.getDetailName())) {
                        String key =
                                String.valueOf(specDetail.getMockSpecId()).concat("_").concat(specDetail.getDetailName());
                        //保证在同一个Spu下、同一规格项下，不允许出现重复规格值
                        //存在同一个规格值，取其模拟Id
                        Long specDetailValue = specDetailMap.get(key);
                        if (specDetailValue != null) {
                            specDetail.setMockSpecDetailId(specDetailValue);
                        } else {
                            //不存在，则放明细模拟ID和明细
                            specDetailMap.put(key, specDetail.getMockSpecDetailId());
                            allSpecDetails.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(specDetail)), (s1,
                                                                                                                            s2) -> {
                                s1.addAll(s2);
                                return s1;
                            });
                        }
                        //设置SKU与规格值的扁平数据
                        goodsInfo.getMockSpecDetailIds().add(specDetail.getMockSpecDetailId());
                    }
                }

                int index = 21;
                // 库存
                if(getStock(cells, goodsInfo)){
                    index++;
                }

                //条形码
                goodsInfo.setGoodsInfoBarcode(ExcelHelper.getValue(getCell(cells, index)));
                index++;

                if (StoreType.SUPPLIER.equals(type) || StoreType.O2O.equals(type)) {
                    // 市场价
                    String marketPrice = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    goodsInfo.setMarketPrice(StringUtils.isBlank(marketPrice) ? BigDecimal.ZERO : new BigDecimal
                            (marketPrice));

                    //供货价
                    String supplyPrice = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    goodsInfo.setSupplyPrice(StringUtils.isBlank(supplyPrice) ? null : new BigDecimal
                            (supplyPrice));

                    //划线价
                    String linePrice = ExcelHelper.getValue(getCell(cells,index));
                    index++;
                    goodsInfo.setLinePrice(StringUtils.isBlank(linePrice) ? null : new BigDecimal(linePrice));

                    // 上下架
                    String addedFlagStr = ExcelHelper.getValue(getCell(cells, index));
                    index++;

                    if ("上架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                    } else if ("下架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                    }
                    if(NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                        // 重量
                        String weightStr = ExcelHelper.getValue(getCell(cells, index));
                        index++;
                        if (StringUtils.isNotBlank(weightStr)) {
                            goodsInfo.setGoodsWeight(new BigDecimal(weightStr).setScale(3, RoundingMode.HALF_UP));
                        }

                        // 体积
                        String cubageStr = ExcelHelper.getValue(getCell(cells, index));
                        index++;
                        if (StringUtils.isNotBlank(cubageStr)) {
                            goodsInfo.setGoodsCubage(new BigDecimal(cubageStr).setScale(6, RoundingMode.HALF_UP));
                        }
                    } else {
                        goodsInfo.setGoodsWeight(BigDecimal.ZERO);
                        goodsInfo.setGoodsCubage(BigDecimal.ZERO);
                    }

                    if (isFirstGoods) {
                        if(NumberUtils.INTEGER_ZERO.equals(goodsType)) {
                            // 下单方式，默认全部
                            goods.setGoodsBuyTypes("0,1");
                        } else {
                            goods.setGoodsBuyTypes("1");
                        }

                        goodses.put(goods.getGoodsNo(), goods);
                    }
                    // 积分价格
                    String buyPointStr = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    if (StringUtils.isNotBlank(buyPointStr)) {
                        goodsInfo.setBuyPoint(new BigDecimal(buyPointStr).longValue());
                    } else {
                        goodsInfo.setBuyPoint(0L);
                    }
                    if(Constants.TWO == goodsType) {
                        String electronicCouponsStr = ExcelHelper.getValue(getCell(cells, index));
                        long id = NumberUtils.toLong(electronicCouponsStr.split("_")[0]);
                        goodsInfo.setElectronicCouponsId(id);
                        //卡券订单以有效卡密的数量为准
                        LocalDateTime time = LocalDate.now().atTime(LocalDateTime.now().getHour(), 0, 0);
                        ElectronicCardInvalidRequest request = ElectronicCardInvalidRequest.builder()
                                .couponId(id)
                                .time(time)
                                .build();
                        Long count = electronicCardQueryProvider.countEffectiveCoupon(request).getContext().getCount();
                        goodsInfo.setStock(count);
                    }
                } else {
                    // 供货价
                    String supplyPrice = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    if(ValidateUtil.isNum(supplyPrice) ||  ValidateUtil.isFloatNum(supplyPrice)){
                        goodsInfo.setSupplyPrice(StringUtils.isBlank(supplyPrice) ? BigDecimal.ZERO : new BigDecimal
                                (supplyPrice));
                    }
                    // 建议零售价
                    goodsInfo.setRetailPrice(BigDecimal.ZERO);

                    // 上下架
                    String addedFlagStr = ExcelHelper.getValue(getCell(cells, index));
                    index++;

                    if ("上架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                    } else if ("下架".equals(addedFlagStr.trim())) {
                        goodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                    }

                    // 重量
                    String weightStr = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    if (StringUtils.isNotBlank(weightStr)) {
                        goodsInfo.setGoodsWeight(new BigDecimal(weightStr).setScale(3, RoundingMode.HALF_UP));
                    }

                    // 体积
                    String cubageStr = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    if (StringUtils.isNotBlank(cubageStr)) {
                        goodsInfo.setGoodsCubage(new BigDecimal(cubageStr).setScale(6, RoundingMode.HALF_UP));
                    }
                    if (isFirstGoods) {
                        goodses.put(goods.getGoodsNo(), goods);
                    }
                    // 积分价格
                    String buyPointStr = ExcelHelper.getValue(getCell(cells, index));
                    index++;
                    if (StringUtils.isNotBlank(buyPointStr)) {
                        goodsInfo.setBuyPoint(new BigDecimal(buyPointStr).longValue());
                    }else{
                        goodsInfo.setBuyPoint(0L);
                    }
                }

                String attributePriceSilver = ExcelHelper.getValue(getCell(cells, 30));
                if (StringUtils.isEmpty(attributePriceSilver)){
                    goodsInfo.setAttributePriceSilver(null);
                }else {
                    goodsInfo.setAttributePriceSilver(new BigDecimal(attributePriceSilver));
                }

                String attributePriceGold = ExcelHelper.getValue(getCell(cells, 31));
                if (StringUtils.isEmpty(attributePriceGold)){
                    goodsInfo.setAttributePriceGold(null);
                }else {
                    goodsInfo.setAttributePriceGold(new BigDecimal(attributePriceGold));
                }

                String attributePriceDiamond = ExcelHelper.getValue(getCell(cells, 32));
                if (StringUtils.isEmpty(attributePriceDiamond)){
                    goodsInfo.setAttributePriceDiamond(null);
                }else {
                    goodsInfo.setAttributePriceDiamond(new BigDecimal(attributePriceDiamond));
                }
                String attributePriceDisount = ExcelHelper.getValue(getCell(cells, 33));
                if (StringUtils.isEmpty(attributePriceDisount)){
                    goodsInfo.setAttributePriceDiscount(null);
                }else {
                    goodsInfo.setAttributePriceDiscount(new BigDecimal(attributePriceDisount));
                }

                skus.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(goodsInfo)), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });

                spuNos.merge(goods.getGoodsNo(), new ArrayList(Collections.singletonList(getCell(cells, 1))), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });

                skuNos.merge(goodsInfo.getGoodsInfoNo(), new ArrayList(Collections.singletonList(getCell(cells, 9))), (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                });
            }
            //指定图片与goods的mockGoodsId关系
            List<BatchGoodsImageDTO> imageDTOList = new ArrayList<>();
            goodses.values().forEach(goods -> {
                if (images.containsKey(goods.getGoodsNo())) {
                    imageDTOList.addAll(images.get(goods.getGoodsNo()).stream().filter(StringUtils::isNotBlank).map(s -> {
                        BatchGoodsImageDTO image = new BatchGoodsImageDTO();
                        image.setMockGoodsId(goods.getMockGoodsId());
                        image.setArtworkUrl(s);
                        return image;
                    }).collect(Collectors.toList()));
                }
            });
            Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
            OperationLogAddRequest operationLog = new OperationLogAddRequest();
            if (nonNull(claims)) {
                operationLog.setEmployeeId(Objects.toString(claims.get("employeeId"), StringUtils.EMPTY));
                // accountName
                operationLog.setOpAccount(Objects.toString(claims.get("EmployeeName"), StringUtils.EMPTY));
                operationLog.setStoreId(Long.valueOf(Objects.toString(claims.get("storeId"), "0")));
                operationLog.setCompanyInfoId(Long.valueOf(Objects.toString(claims.get("companyInfoId"), "0")));
                operationLog.setOpRoleName(Objects.toString(claims.get("roleName"), StringUtils.EMPTY));
                operationLog.setOpName(Objects.toString(claims.get("realEmployeeName"), StringUtils.EMPTY));
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
            //批量保存
            GoodsCommonBatchAddRequest goodsCommonBatchAddRequest = GoodsCommonBatchAddRequest.builder()
                    .type(type)
                    .operationLogAddRequest(operationLog)
                    .goodsList(new ArrayList<>(goodses.values()))
                    .goodsInfoList(skus.values().stream().flatMap(Collection::stream).collect(Collectors.toList()))
                    .specList(allSpecs.values().stream().flatMap(Collection::stream).collect(Collectors.toList()))
                    .specDetailList(allSpecDetails.values().stream().flatMap(Collection::stream).collect(Collectors.toList()))
                    .imageList(imageDTOList).build();
            //补充商品信息
            goodsCommonBatchAddRequest.getGoodsList().forEach(v->{
                BigDecimal supplyPrice = goodsCommonBatchAddRequest.getGoodsInfoList()
                        .stream()
                        .filter(sku -> Objects.equals(sku.getGoodsId(), v.getGoodsId()) && Objects.nonNull(sku.getSupplyPrice()))
                        .map(GoodsInfoDTO::getSupplyPrice)
                        .min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                v.setSupplyPrice(supplyPrice);
            });
            //发mq异步处理
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.BATCH_ADD_GOODS_INIT);
            mqSendDTO.setData(JSONObject.toJSONString(goodsCommonBatchAddRequest));
            mqSendProvider.send(mqSendDTO);
        } catch (SbcRuntimeException e) {
            log.error("商品导入异常", e);
            throw e;
        } catch (Exception e) {
            log.error("商品导入异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }

    private Cell getCell(Cell[] cells, int index) {
        return cells[index];
    }

    protected FreightTemplateGoodsVO getFreightTemplateGoodsVo(GoodsSupplierExcelImportRequest goodsRequest) {
        // 运费模板未配置
        FreightTemplateGoodsVO freightTemp = freightTemplateGoodsQueryProvider.getDefaultByStoreId(
                FreightTemplateGoodsDefaultByStoreIdRequest.builder().storeId(goodsRequest.getStoreId()).build())
                .getContext();
        Assert.assertNotNull(freightTemp, GoodsErrorCodeEnum.K030147);
        return freightTemp;
    }

    protected Long getDefaultStoreCateId(List<StoreCateResponseVO> storeCateList) {
        return storeCateList.stream()
                .filter(storeCate -> Objects.equals(DefaultFlag.YES, storeCate.getIsDefault()))
                .map(StoreCateResponseVO::getStoreCateId).findFirst().orElseThrow(() -> new SbcRuntimeException
                        (GoodsErrorCodeEnum.K030053));
    }

    protected Map<Long, Long> getStoreCateSet(List<StoreCateResponseVO> storeCateList) {
        return storeCateList.stream().collect(Collectors.toMap(StoreCateResponseVO::getStoreCateId,
                StoreCateResponseVO::getCateParentId));
    }

    protected Set<Long> getBrandSet(GoodsSupplierExcelImportRequest goodsRequest) {
        ContractBrandListRequest contractBrandQueryRequest = new ContractBrandListRequest();
        contractBrandQueryRequest.setStoreId(goodsRequest.getStoreId());
        return contractBrandQueryProvider.list(contractBrandQueryRequest).getContext().getContractBrandVOList()
                .stream().filter(vo -> Objects.nonNull(vo.getGoodsBrand()))
                .map(ContractBrandVO::getGoodsBrand).map(GoodsBrandVO::getBrandId).collect(Collectors.toSet());
    }

    protected Map<Long, String> getGoodsCateMap(GoodsSupplierExcelImportRequest goodsRequest) {
        Map<Long, String> goodsCateMap = goodsCateQueryProvider.listLeafByStoreId(
                GoodsCateLeafByStoreIdRequest.builder().storeId(goodsRequest.getStoreId()).build()).getContext().getGoodsCateList()
                .stream().collect(Collectors.toMap(GoodsCateVO::getCateId, GoodsCateVO::getCatePath));
        return goodsCateMap;
    }

    protected boolean getStock(Cell[] cells, BatchGoodsInfoDTO goodsInfo) {
        String stock = ExcelHelper.getValue(getCell(cells, 21));
        goodsInfo.setStock(StringUtils.isBlank(stock) ? 0 : new BigDecimal(stock).longValue());
        return Boolean.TRUE;
    }
}
