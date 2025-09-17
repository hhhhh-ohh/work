package com.wanmi.sbc.goods.standard.service;

import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelBindStateRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoConsignIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoConsignIdResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardImportGoodsResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.common.GoodsCommonService;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsRepository;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.repository.GoodsPropertyDetailRelRepository;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.model.root.GoodsPropDetailRel;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsPropDetailRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsRequest;
import com.wanmi.sbc.goods.info.service.GoodsBaseInterface;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.standard.model.root.StandardGoods;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.model.root.StandardPropDetailRel;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardPropDetailRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.request.StandardImportRequest;
import com.wanmi.sbc.goods.standard.request.StandardQueryRequest;
import com.wanmi.sbc.goods.standard.request.StandardSkuQueryRequest;
import com.wanmi.sbc.goods.standardimages.model.root.StandardImage;
import com.wanmi.sbc.goods.standardimages.repository.StandardImageRepository;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSkuSpecDetailRel;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSpec;
import com.wanmi.sbc.goods.standardspec.model.root.StandardSpecDetail;
import com.wanmi.sbc.goods.standardspec.repository.StandardSkuSpecDetailRelRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecDetailRepository;
import com.wanmi.sbc.goods.standardspec.repository.StandardSpecRepository;
import com.wanmi.sbc.goods.storecate.model.root.StoreCate;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.goods.storecate.repository.StoreCateRepository;
import com.wanmi.sbc.goods.storecate.request.StoreCateQueryRequest;
import com.wanmi.sbc.goods.suppliercommissiongoods.service.SupplierCommissionGoodService;
import io.seata.common.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品库服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
@Primary
@Transactional(readOnly = false)
public class StandardImportService {

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Autowired
    private StandardSpecRepository standardSpecRepository;

    @Autowired
    private StandardSpecDetailRepository standardSpecDetailRepository;

    @Autowired
    private StandardSkuSpecDetailRelRepository standardSkuSpecDetailRelRepository;

    @Autowired
    private StandardImageRepository standardImageRepository;

    @Autowired
    private StandardPropDetailRelRepository standardPropDetailRelRepository;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsImageRepository goodsImageRepository;

    @Autowired
    private GoodsPropDetailRelRepository goodsPropDetailRelRepository;

    @Autowired
    private StoreCateRepository storeCateRepository;

    @Autowired
    private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired
    private FreightTemplateGoodsRepository freightTemplateGoodsRepository;

    @Autowired
    private GoodsCommonService goodsCommonService;

    @Autowired
    private GoodsBaseInterface goodsBaseInterface;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsPropertyDetailRelRepository goodsPropertyDetailRelRepository;

    @Autowired
    private GoodsCommissionPriceService goodsCommissionPriceService;
    @Autowired
    private GoodsCommissionConfigService goodsCommissionConfigService;
    @Autowired
    private SupplierCommissionGoodService supplierCommissionGoodService;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    //虚拟商品运费模版id
    private static final Long VIRTUAL_GOODS_FREIGHT_ID = -1L;

    /**
     * 商品库批量导入商品
     *
     * @param request 参数
     * @return
     */
    @Transactional
    public StandardImportGoodsResponse importGoods(StandardImportRequest request) {
        Boolean delFlag = standardGoodsRepository.findAllById(request.getGoodsIds()).stream().anyMatch(s -> DeleteFlag.YES == s.getDelFlag());
        if (delFlag) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030155);
        }
        if (standardGoodsRelRepository.countByStandardAndStoreIds(request.getGoodsIds(), Collections.singletonList(request.getStoreId())) > 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030156);
        }

        // 店铺分类未配置
        Long defaultStoreCateId = getDefaultStoreCateId(request);

        List<StandardGoods> standardGoodses = standardGoodsRepository.findAll(StandardQueryRequest.builder().goodsIds(request.getGoodsIds()).build().getWhereCriteria());

        //校验分账绑定关系
        List<Long> receiverIds =
                standardGoodses.stream().filter(i -> i.getGoodsSource() != 1).map(StandardGoods::getStoreId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(receiverIds)){
            LedgerReceiverRelBindStateRequest stateRequest = LedgerReceiverRelBindStateRequest.builder()
                    .supplierId(request.getCompanyInfoId()).receiverStoreIds(receiverIds).build();
            List<Long> unBindStores = ledgerReceiverRelQueryProvider.findUnBindStores(stateRequest).getContext().getUnBindStores();
            if (CollectionUtils.isNotEmpty(unBindStores)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030157);
            }
        }
        // 运费模板未配置
        FreightTemplateGoods freightTemp = null;
        if (!StoreType.O2O.equals(request.getStoreType())) {
            freightTemp = freightTemplateGoodsRepository.queryByDefault(request.getStoreId());
            Assert.assertNotNull(freightTemp, GoodsErrorCodeEnum.K030147);
        }
        List<String> newSkuIds = new ArrayList<>();
        List<String> newSpuIds = new ArrayList<>();
        List<String> resultGoodsNameList = new ArrayList<>();

        List<StandardGoodsRel> goodsRels = standardGoodsRelRepository.findByStandardIds(request.getGoodsIds());
        Map<String, String> mappingOldSpu = new HashMap<>();
        Map<String, Long> mappingOldStoreId = new HashMap<>();
        Map<Long, StoreVO> storeVOMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(standardGoodses)) {
            // 规格映射map,商品库Id -> 老商品Id
            goodsRels.forEach(goodsRel -> {
                mappingOldSpu.put(goodsRel.getStandardId() + ":" + goodsRel.getStoreId(), goodsRel.getGoodsId());
                mappingOldStoreId.put(goodsRel.getStandardId() + ":" + goodsRel.getStoreId(), goodsRel.getStoreId());
            });
            storeVOMap = this.getStoreList(standardGoodses.stream().map(StandardGoods::getStoreId).collect(Collectors.toList()));
        }
        // 运费模板
        Map<String, Long> mappingFreightTemp = new HashMap<>();
        if (!StoreType.O2O.equals(request.getStoreType())) {
            //查询供应上商品的运费模板
            List<Goods> goodsList = goodsRepository.findAllByGoodsIdIn(goodsRels.stream().map(StandardGoodsRel::getGoodsId).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(goodsList)) {
                Map<String, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getGoodsId, g -> g));
                goodsRels.forEach(goodsRel -> {
                    if (goodsMap.containsKey(goodsRel.getGoodsId())) {
                        mappingFreightTemp.put(goodsRel.getStandardId() + ":" + goodsRel.getStoreId(), goodsMap.get(goodsRel.getGoodsId()).getFreightTempId());
                    }
                });
            }
        }

        // spuId对应商品一级类目
        Map<String, Long> cateTopMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(standardGoodses)) {
            //规格映射Map,商品库Id -> 新商品Id
            Map<String, String> mappingSpu = new HashMap<>();
            //规格映射Map,商品库规格Id -> 新商品规格Id
            Map<Long, Long> mappingSpec = new HashMap<>();
            //规格映射Map,商品库规格值Id -> 新商品规格值Id
            Map<Long, Long> mappingDetail = new HashMap<>();
            //规格映射Map,商品库SkuId -> 新商品SkuId
            Map<String, String> mappingSku = new HashMap<>();
            //规格映射Map,商品库Id -> 商品来源
            Map<String, Integer> mappingGoodsSourse = new HashMap<>();
            Map<String, BigDecimal> mappingMarketPrice = new HashMap<>();

            LocalDateTime now = LocalDateTime.now();
            List<Goods> goodsList = new ArrayList<>();

            // 导入Spu
            Long freightId = Objects.nonNull(freightTemp) ? freightTemp.getFreightTempId() : null;
            Map<Long, StoreVO> finalStoreVOMap1 = storeVOMap;
            standardGoodses.forEach(standardGoods -> {
                boolean virtualFlag = standardGoods.getGoodsType() == GoodsType.VIRTUAL_GOODS.toValue();
                GoodsSaveDTO goods = new GoodsSaveDTO();
                //PS:这里有个属性拷贝
                BeanUtils.copyProperties(standardGoods, goods);
                if (GoodsSource.LINKED_MALL.toValue() == standardGoods.getGoodsSource()) {
                    goods.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
                }
                if (GoodsSource.VOP.toValue() == standardGoods.getGoodsSource()) {
                    goods.setThirdPlatformType(ThirdPlatformType.VOP);
                }
                goods.setGoodsId(null);
                goods.setAddedFlag(AddedFlag.NO.toValue());
                goods.setDelFlag(DeleteFlag.NO);
                goods.setCreateTime(now);
                goods.setUpdateTime(now);
                goods.setAddedTime(now);
                goods.setSubmitTime(now);
                goods.setAuditStatus(CheckStatus.CHECKED);
                goods.setCustomFlag(Constants.no);
                goods.setLevelDiscountFlag(Constants.no);
                goods.setCompanyInfoId(request.getCompanyInfoId());
                goods.setCompanyType(request.getCompanyType());
                goods.setStoreType(request.getStoreType());
                goods.setStoreId(request.getStoreId());
                goods.setSupplierName(request.getSupplierName());
                goods.setGoodsNo(goodsCommonService.getSpuNoByUnique());
                goods.setStoreCateIds(Collections.singletonList(defaultStoreCateId));
                goods.setFreightTempId(virtualFlag
                        ? VIRTUAL_GOODS_FREIGHT_ID : freightId);
                goods.setPriceType(GoodsPriceType.MARKET.toValue());
                //默认销售类型 零售
                goods.setSaleType(SaleType.RETAIL.toValue());
                //goods.setMarketPrice(standardGoods.getMarketPrice());

                goods.setGoodsVideo(standardGoods.getGoodsVideo());
                //商品来源，0供应商，1商家 导入后就是商家商品 以此来区分列表展示
                goods.setGoodsSource(1);
                if (standardGoods.getGoodsSource().equals(NumberUtils.INTEGER_ZERO)
                        || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardGoods.getGoodsSource())
                        || Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardGoods.getGoodsSource())
                ) {
                    String key = standardGoods.getGoodsId() + ":" + standardGoods.getStoreId();
                    goods.setSupplyPrice(standardGoods.getSupplyPrice());
                    goods.setProviderGoodsId(mappingOldSpu.get(standardGoods.getGoodsId() + ":" + standardGoods.getStoreId()));
                    goods.setProviderId(mappingOldStoreId.get(standardGoods.getGoodsId() + ":" + standardGoods.getStoreId()));
                    goods.setProviderName(standardGoods.getProviderName());
                    //商品可售性
                    if (standardGoods.getAddedFlag() != AddedFlag.NO.toValue()) {
                        goods.setVendibility(Constants.yes);
                    } else {
                        goods.setVendibility(Constants.no);
                    }
                    //运费模板
                    if(mappingFreightTemp.containsKey(key)) {
                        goods.setFreightTempId(mappingFreightTemp.get(key));
                    }

                    //店铺状态
                    StoreVO storeVO = null;
                    if (finalStoreVOMap1.containsKey(standardGoods.getStoreId())) {
                        storeVO = finalStoreVOMap1.get(standardGoods.getStoreId());
                    }
                    goods.setProviderStatus(this.getProviderStatus(storeVO));
                    //供应商导入商品市场价默认 供应商供货价
                    //goods.setMarketPrice(standardGoods.getSupplyPrice());
                }
                //允许独立设价
                goods.setAllowPriceSet(1);
                goods.setAddedTimingFlag(Boolean.FALSE);

                //初始化商品对应的数量（收藏、销量、评论数、好评数）
                if (goods.getGoodsCollectNum() == null) {
                    goods.setGoodsCollectNum(0L);
                }
                if (goods.getGoodsSalesNum() == null) {
                    goods.setGoodsSalesNum(0L);
                }
                if (goods.getGoodsEvaluateNum() == null) {
                    goods.setGoodsEvaluateNum(0L);
                }
                if (goods.getGoodsFavorableCommentNum() == null) {
                    goods.setGoodsFavorableCommentNum(0L);
                }
                goods.setGoodsBuyTypes(virtualFlag ? "1" : "0,1");
                goods.setStock(0L);
                //虚拟商品重新设置体积和重量
                if (virtualFlag) {
                    goods.setGoodsWeight(BigDecimal.ZERO);
                    goods.setGoodsCubage(BigDecimal.ZERO);
                }
                resultGoodsNameList.add(goods.getGoodsName());
                goods.setIsBuyCycle(Constants.no);

                // 商品SPU保存前切面
                goods = beforeGoodsSave(goods);
                Goods newGoods = goodsRepository.save(KsBeanUtil.copyPropertiesThird(goods, Goods.class));
                goods.setGoodsId(newGoods.getGoodsId());

                // 检查商品基本信息
                goodsBaseInterface.checkBasic(goods,0);

                cateTopMap.put(newGoods.getGoodsId(), goods.getCateTopId());
                mappingSpu.put(standardGoods.getGoodsId(), newGoods.getGoodsId());
                mappingGoodsSourse.put(standardGoods.getGoodsId(), standardGoods.getGoodsSource());
                mappingMarketPrice.put(newGoods.getGoodsId(), goods.getMarketPrice());
                goodsList.add(newGoods);
                newSpuIds.add(newGoods.getGoodsId());

                // 关联商品与商品库
                StandardGoodsRel rel = new StandardGoodsRel();
                rel.setGoodsId(newGoods.getGoodsId());
                rel.setStandardId(standardGoods.getGoodsId());
                rel.setStoreId(request.getStoreId());
                rel.setDelFlag(DeleteFlag.NO);
                // 关联对象保存前切面
                rel = beforeStandardGoodsRelSave(rel);
                standardGoodsRelRepository.save(rel);
            });

            List<StandardGoods> spuList = standardGoodses;
            List<GoodsInfo> newSkus = new ArrayList();
            //导入Sku
            List<StandardSku> skus = standardSkuRepository.findAll(StandardSkuQueryRequest.builder().goodsIds(request.getGoodsIds()).delFlag(DeleteFlag.NO.toValue()).build().getWhereCriteria());
            if (CollectionUtils.isNotEmpty(skus)) {
                Map<Long, StoreVO> finalStoreVOMap = storeVOMap;
                skus.forEach(standardSku -> {
                    boolean virtualFlag = standardSku.getGoodsType() == GoodsType.VIRTUAL_GOODS.toValue();
                    GoodsInfoSaveDTO sku = new GoodsInfoSaveDTO();
                    //PS:这里有个属性拷贝
                    BeanUtils.copyProperties(standardSku, sku);
                    if (Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardSku.getGoodsSource())) {
                        sku.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
                    }
                    if (Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardSku.getGoodsSource())) {
                        sku.setThirdPlatformType(ThirdPlatformType.VOP);
                    }
                    sku.setGoodsInfoId(null);
                    sku.setGoodsId(mappingSpu.get(standardSku.getGoodsId()));
                    sku.setGoodsSource(1);
                    sku.setCateTopId(cateTopMap.get(sku.getGoodsId()));
                    sku.setCreateTime(now);
                    sku.setUpdateTime(now);
                    sku.setAddedTime(now);
                    sku.setAddedFlag(AddedFlag.NO.toValue());
                    sku.setCompanyInfoId(request.getCompanyInfoId());
                    sku.setLevelDiscountFlag(Constants.no);
                    sku.setCustomFlag(Constants.no);
                    sku.setStoreId(request.getStoreId());
                    sku.setAuditStatus(CheckStatus.CHECKED);
                    sku.setCompanyType(request.getCompanyType());
                    sku.setStoreType(request.getStoreType());
                    sku.setGoodsInfoNo(goodsCommonService.getSkuNoByUnique());
                    sku.setAloneFlag(Boolean.FALSE);
                    sku.setSupplyPrice(standardSku.getSupplyPrice());
                    sku.setMarketPrice(standardSku.getMarketPrice());
                    sku.setAddedTimingFlag(Boolean.FALSE);
                    if (virtualFlag) {
                        sku.setGoodsWeight(BigDecimal.ZERO);
                        sku.setGoodsCubage(BigDecimal.ZERO);
                    }
                    String barCode = StringUtils.EMPTY;
                    if (StringUtils.isNotBlank(standardSku.getProviderGoodsInfoId())) {
                        //供应商商品允许批量设价
                        sku.setAloneFlag(Boolean.TRUE);
                        GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(standardSku.getProviderGoodsInfoId()).orElse(null);
                        if (providerGoodsInfo != null) {
                            barCode = providerGoodsInfo.getGoodsInfoBarcode();
                            sku.setGoodsInfoBarcode(barCode);
                        }
                    }

                    Optional<StandardGoods> standardGoods = spuList.stream().filter(goodses -> goodses.getGoodsId().equals(standardSku.getGoodsId())).findFirst();
                    sku.setCateId(standardGoods.get().getCateId());
                    sku.setBrandId(standardGoods.get().getBrandId());
                    sku.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);

                    if (NumberUtils.INTEGER_ZERO.equals(standardSku.getGoodsSource())
                            || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardSku.getGoodsSource())
                            || Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardSku.getGoodsSource())
                    ) {
                        //供应商导入商品市场价默认 供应商供货价
                        BigDecimal supplyPrice = standardSku.getSupplyPrice();
                        BigDecimal marketPrice = getMarketPrice(sku.getStoreId(), sku, supplyPrice);
                        sku.setMarketPrice(marketPrice);
                        if (Objects.isNull(mappingMarketPrice.get(sku.getGoodsId())) || mappingMarketPrice.get(sku.getGoodsId()).compareTo(marketPrice) > 0) {
                            mappingMarketPrice.put(sku.getGoodsId(), marketPrice);
                        }
                        sku.setStock(standardSku.getStock() == null ? Constants.NUM_0L : standardSku.getStock());
                    }else{
                        sku.setStock(Constants.NUM_0L);
                    }

                    //默认销售类型 零售
                    sku.setSaleType(SaleType.RETAIL.toValue());

                    if (NumberUtils.INTEGER_ZERO.equals(standardSku.getGoodsSource())
                            || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardSku.getGoodsSource())
                            || Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardSku.getGoodsSource())
                    ) {
                        sku.setProviderId(mappingOldStoreId.get(standardSku.getGoodsId() + ":" + standardGoods.get().getStoreId()));
                    }


                    if (NumberUtils.INTEGER_ZERO.equals(standardSku.getGoodsSource())
                            || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardSku.getGoodsSource())
                            || Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardSku.getGoodsSource())
                    ) {
                        //商品可售性
                        if (!Integer.valueOf(AddedFlag.NO.toValue()).equals(standardSku.getAddedFlag())) {
                            sku.setVendibility(Constants.yes);
                        } else {
                            sku.setVendibility(Constants.no);
                        }
                        //店铺状态
                        if (standardGoods.get().getStoreId() != null) {
                            StoreVO storeVO = null;
                            if (finalStoreVOMap.containsKey(standardGoods.get().getStoreId())) {
                                storeVO = finalStoreVOMap.get(standardGoods.get().getStoreId());
                            }
                            sku.setProviderStatus(this.getProviderStatus(storeVO));
                        }
                    }
                    sku.setIsBuyCycle(Constants.no);
                    sku.setQuickOrderNo(null);
                    sku.setProviderQuickOrderNo(standardSku.getQuickOrderNo());
                    // 保存前切面
                    sku = beforeSkuSave(sku);
                    GoodsInfo newSku = goodsInfoRepository.save(KsBeanUtil.copyPropertiesThird(sku, GoodsInfo.class));
                    newSkuIds.add(newSku.getGoodsInfoId());
                    mappingSku.put(standardSku.getGoodsInfoId(), newSku.getGoodsInfoId());
                    newSkus.add(newSku);
                });

                final Map<String,List<GoodsInfo>> goodsInfos = newSkus.stream().collect(Collectors.groupingBy(GoodsInfo::getGoodsId));

                //更新SPU库存 (供应商商品才更新SPU库存)
                goodsList.forEach(spu -> {
                    spu.setStock(goodsInfos.get(spu.getGoodsId()).stream().filter(s -> Objects.nonNull(s.getStock())).mapToLong(GoodsInfo::getStock).sum());
                });
            }

            //处理供应商商品和商家绑定关系
            StandardGoods standardGoods = standardGoodses.stream().findFirst().orElse(new StandardGoods());
            if (NumberUtils.INTEGER_ZERO.equals(standardGoods.getGoodsSource())
                    || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardGoods.getGoodsSource())
                    || Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardGoods.getGoodsSource())
            ) {
                //处理Goods的市场价
                /*List<Goods> updateMarketPrice = new ArrayList<>();
                goodsList.forEach(goods -> {
                    if (Objects.isNull(goods.getMarketPrice()) || mappingMarketPrice.containsKey(goods.getGoodsId())
                            && mappingMarketPrice.get(goods.getGoodsId()).compareTo(goods.getMarketPrice()) < 0) {
                        goods.setMarketPrice(mappingMarketPrice.get(goods.getGoodsId()));
                        updateMarketPrice.add(goods);
                    }
                });
                if (CollectionUtils.isNotEmpty(updateMarketPrice)) {
                    goodsRepository.saveAll(updateMarketPrice);
                }*/
                //处理供应商商品和商家绑定关系
                supplierCommissionGoodService.addCommissionGoods(goodsList, request.getUserId());
            }

            //导入规格
            List<StandardSpec> specs = standardSpecRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(specs)) {
                specs.forEach(standardSpec -> {
                    GoodsSpec spec = new GoodsSpec();
                    BeanUtils.copyProperties(standardSpec, spec);
                    spec.setSpecId(null);
                    spec.setGoodsId(mappingSpu.get(standardSpec.getGoodsId()));
                    mappingSpec.put(standardSpec.getSpecId(), goodsSpecRepository.save(spec).getSpecId());
                });
            }

            //导入规格值
            List<StandardSpecDetail> details = standardSpecDetailRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(details)) {
                details.forEach(specDetail -> {
                    GoodsSpecDetail detail = new GoodsSpecDetail();
                    BeanUtils.copyProperties(specDetail, detail);
                    detail.setSpecDetailId(null);
                    detail.setSpecId(mappingSpec.get(specDetail.getSpecId()));
                    detail.setGoodsId(mappingSpu.get(specDetail.getGoodsId()));
                    mappingDetail.put(specDetail.getSpecDetailId(), goodsSpecDetailRepository.save(detail).getSpecDetailId());
                });
            }

            //导入规格值与Sku的关系
            List<StandardSkuSpecDetailRel> rels = standardSkuSpecDetailRelRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(rels)) {
                rels.forEach(standardRel -> {
                    GoodsInfoSpecDetailRel rel = new GoodsInfoSpecDetailRel();
                    BeanUtils.copyProperties(standardRel, rel);
                    rel.setSpecDetailRelId(null);
                    rel.setSpecId(mappingSpec.get(standardRel.getSpecId()));
                    rel.setSpecDetailId(mappingDetail.get(standardRel.getSpecDetailId()));
                    rel.setGoodsInfoId(mappingSku.get(standardRel.getGoodsInfoId()));
                    rel.setGoodsId(mappingSpu.get(standardRel.getGoodsId()));
                    goodsInfoSpecDetailRelRepository.save(rel);
                });
            }

            //导入图片
            List<StandardImage> imageList = standardImageRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(imageList)) {
                imageList.forEach(standardImage -> {
                    GoodsImage image = new GoodsImage();
                    BeanUtils.copyProperties(standardImage, image);
                    image.setImageId(null);
                    image.setGoodsId(mappingSpu.get(standardImage.getGoodsId()));
                    goodsImageRepository.save(image);
                });
            }

            //导入商品属性 查询出要导入的商品库商品的属性
            List<GoodsPropertyDetailRel> propDetailRelList =
                    goodsPropertyDetailRelRepository.findByGoodsIdInAndDelFlagAndGoodsType(
                            request.getGoodsIds(), DeleteFlag.NO, GoodsPropertyType.STANDARD_GOODS);
            if (CollectionUtils.isNotEmpty(propDetailRelList)) {
                // 导入商品属性
                propDetailRelList.forEach(goodsPropDetailRel -> {
                    GoodsPropertyDetailRel newGoodsRel = new GoodsPropertyDetailRel();
                    KsBeanUtil.copyProperties(goodsPropDetailRel, newGoodsRel);
                    newGoodsRel.setDetailRelId(null);
                    newGoodsRel.setGoodsType(GoodsPropertyType.GOODS);
                    newGoodsRel.setGoodsId(mappingSpu.get(goodsPropDetailRel.getGoodsId()));
                    goodsPropertyDetailRelRepository.save(newGoodsRel);
                });
            }

            mappingSpu.values().forEach(spuId -> {
                StoreCateGoodsRela rela = new StoreCateGoodsRela();
                rela.setGoodsId(spuId);
                rela.setStoreCateId(defaultStoreCateId);
                storeCateGoodsRelaRepository.save(rela);
            });
        }
        return StandardImportGoodsResponse.builder()
                .spuIdList(newSpuIds)
                .skuIdList(newSkuIds)
                .build();
    }

    /**
     * 商品库sku增量导入商家列表
     *
     * @param request 参数
     * @return
     */
    //wait 针对spu的导入，sku导入-单条goods_info_id的导入会冲突
    //wait 可以针对单挑goods_info_id导入，有些属性就不用导入了，新增的sku在某些数据库中肯定保存了goods_id，现在只需要单独保存good_info_id
    @Transactional
    public GoodsInfoConsignIdResponse importGoodsInfo(GoodsInfoConsignIdRequest request) {
        GoodsInfoConsignIdResponse response = new GoodsInfoConsignIdResponse();
        GoodsVO goodsVO = request.getGoods(); //注意：goodsVO是商家的信息，非供应商

        Optional<Goods> oldGoodsOpt = goodsRepository.findById(goodsVO.getGoodsId());
        if (!oldGoodsOpt.isPresent() || Objects.isNull(oldGoodsOpt.get()) || DeleteFlag.YES.toValue() == oldGoodsOpt.get().getDelFlag().toValue()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030046);
        }
        //provider信息
        GoodsInfoVO goodsInfoVO = request.getGoodsInfos();
        LocalDateTime now = LocalDateTime.now();
        GoodsInfo standardSku = goodsInfoRepository.findByGoodsInfoByProviderId(goodsInfoVO.getGoodsInfoId(), goodsVO.getGoodsId());
        //判断是否重复代销了
        if(Objects.nonNull(standardSku)){
            return GoodsInfoConsignIdResponse.builder().build();
        }

        //导入Sku
        GoodsInfoSaveDTO sku = new GoodsInfoSaveDTO();
        BeanUtils.copyProperties(goodsInfoVO, sku);
        //基本属性值赋值
        basicAttributes(request, goodsVO, sku, now, goodsInfoVO);
        //一些额外属性
        extraAttributes(goodsVO, sku, goodsInfoVO);
        //处理市场价
        sku.setMarketPrice(sku.getSupplyPrice());
        GoodsCommissionConfig goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(sku.getStoreId());
        if (CommissionSynPriceType.AI_SYN.toValue() == goodsCommissionConfig.getSynPriceType().toValue()){
            //重新获取加价比例
            GoodsCommissionPriceConfig goodsCommissionPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(sku.getStoreId(), sku);
            BigDecimal addRate = BigDecimal.ZERO;
            //重新计算加价值
            if(Objects.nonNull(goodsCommissionPriceConfig)){
                addRate = goodsCommissionPriceConfig.getAddRate();
            } else {

                addRate = Objects.isNull(goodsCommissionConfig.getAddRate()) ? BigDecimal.ZERO : goodsCommissionConfig.getAddRate();
            }
            BigDecimal addPrice = goodsCommissionPriceService.getAddPrice(addRate, sku.getSupplyPrice());
            sku.setMarketPrice(sku.getSupplyPrice().add(addPrice));
            //获取SPU验证市场价
            Optional<Goods> goodsOptional = goodsRepository.findById(sku.getGoodsId());
            if(goodsOptional.isPresent() && (Objects.isNull(goodsOptional.get().getMarketPrice())
                    || sku.getMarketPrice().compareTo(goodsOptional.get().getMarketPrice()) < 0)) {
                Goods goods = goodsOptional.get();
                goods.setMarketPrice(sku.getMarketPrice());
                goodsRepository.save(goods);
                response.setGoods(KsBeanUtil.convert(goods, GoodsVO.class));
            }
        }
        GoodsInfo newSku = goodsInfoRepository.save(KsBeanUtil.copyPropertiesThird(sku, GoodsInfo.class));
        response.setGoodsInfos(KsBeanUtil.convert(newSku, GoodsInfoVO.class));
        //更新SPU库存
        goodsRepository.addStockById(sku.getStock(), goodsVO.getGoodsId());

        Goods providerGoods = goodsRepository.findById(goodsVO.getProviderGoodsId()).orElse(null);
        if(Objects.isNull(providerGoods)){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030043);
        }

        goodsRepository.updateMoreSpecFlagByGoodsId(goodsVO.getGoodsId(),Constants.ZERO);
        goodsSpecRepository.deleteByGoodsId(goodsVO.getGoodsId());
        goodsSpecDetailRepository.deleteByGoodsId(goodsVO.getGoodsId());
        goodsInfoSpecDetailRelRepository.deleteByGoodsId(goodsVO.getGoodsId());

        if (Objects.equals(Constants.ZERO,providerGoods.getMoreSpecFlag())){
            return response;
        }

        Map<String, String> mappingSku = new HashMap<>();
        Map<Long, Long> mappingSpec = new HashMap<>();
        Map<Long, Long> mappingDetail = new HashMap<>();

        List<GoodsInfo> goodsInfoList = goodsInfoRepository.findByGoodsId(goodsVO.getGoodsId());
        if (CollectionUtils.isEmpty(goodsInfoList)
                || !goodsInfoList.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList()).contains(newSku.getGoodsInfoId())) {
            goodsInfoList.add(newSku);
        }
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            mappingSku = goodsInfoList.stream().collect(Collectors.toMap(GoodsInfo::getProviderGoodsInfoId, GoodsInfo::getGoodsInfoId));
        } else {
            return response;
        }

        //处理规格
        List<GoodsSpec> specs = goodsSpecRepository.findAllByGoodsIdAndAndDelFlag(providerGoods.getGoodsId(), DeleteFlag.NO);
        if (CollectionUtils.isNotEmpty(specs)) {
            specs.forEach(standardSpec -> {
                GoodsSpec spec = new GoodsSpec();
                BeanUtils.copyProperties(standardSpec, spec);
                spec.setSpecId(null);
                spec.setGoodsId(goodsVO.getGoodsId());
                mappingSpec.put(standardSpec.getSpecId(), goodsSpecRepository.save(spec).getSpecId());
            });
        }

        //处理规格值
        List<GoodsSpecDetail> details = goodsSpecDetailRepository.findAllByGoodsIdAAndDelFlag(providerGoods.getGoodsId(), DeleteFlag.NO);
        if (CollectionUtils.isNotEmpty(details)) {
            details.forEach(specDetail -> {
                GoodsSpecDetail detail = new GoodsSpecDetail();
                BeanUtils.copyProperties(specDetail, detail);
                detail.setSpecDetailId(null);
                detail.setSpecId(mappingSpec.get(specDetail.getSpecId()));
                detail.setGoodsId(goodsVO.getGoodsId());
                mappingDetail.put(specDetail.getSpecDetailId(), goodsSpecDetailRepository.save(detail).getSpecDetailId());
            });
        }

        //处理规格值与Sku的关系
        List<GoodsInfoSpecDetailRel> rels = goodsInfoSpecDetailRelRepository.findByGoodsId(providerGoods.getGoodsId());
        if (CollectionUtils.isNotEmpty(rels)) {
            Map<String, String> finalMappingSku = mappingSku;
            rels.forEach(standardRel -> {
                if (finalMappingSku.containsKey(standardRel.getGoodsInfoId())) {
                    GoodsInfoSpecDetailRel rel = new GoodsInfoSpecDetailRel();
                    BeanUtils.copyProperties(standardRel, rel);
                    rel.setSpecDetailRelId(null);
                    rel.setSpecId(mappingSpec.get(standardRel.getSpecId()));
                    rel.setSpecDetailId(mappingDetail.get(standardRel.getSpecDetailId()));
                    rel.setGoodsInfoId(finalMappingSku.get(standardRel.getGoodsInfoId()));
                    rel.setGoodsId(goodsVO.getGoodsId());
                    goodsInfoSpecDetailRelRepository.save(rel);
                }
            });
        }
        goodsRepository.updateMoreSpecFlagByGoodsId(goodsVO.getGoodsId(),Constants.ONE);
        return response;
    }

    /**
     * sku基本属性赋值抽取
     */
    public void basicAttributes(GoodsInfoConsignIdRequest request, GoodsVO goodsVO, GoodsInfoSaveDTO sku, LocalDateTime now, GoodsInfoVO goodsInfoVO) {
        sku.setGoodsInfoId(null);
        sku.setGoodsId(goodsVO.getGoodsId());
        sku.setGoodsSource(1);
        sku.setCateTopId(goodsInfoVO.getCateTopId());
        sku.setCreateTime(now);
        sku.setUpdateTime(now);
        sku.setAddedTime(now);
        sku.setAddedFlag(AddedFlag.NO.toValue());
        if(goodsVO.getAddedFlag().intValue()  != AddedFlag.NO.toValue()) {
            sku.setAddedFlag(AddedFlag.YES.toValue());
        }
        sku.setCompanyInfoId(request.getCompanyInfoId());
        sku.setLevelDiscountFlag(Constants.no);
        sku.setCustomFlag(Constants.no);
        sku.setStoreId(goodsVO.getStoreId());
        sku.setAuditStatus(CheckStatus.CHECKED);
        sku.setCompanyType(request.getCompanyType());
        sku.setStoreType(goodsVO.getStoreType());
        sku.setGoodsInfoNo(goodsCommonService.getSkuNoByUnique());
        sku.setStock(goodsInfoVO.getStock() == null ? Long.valueOf(0) : goodsInfoVO.getStock());
        sku.setAloneFlag(Boolean.FALSE);
        sku.setSupplyPrice(goodsInfoVO.getSupplyPrice());
        //todo 加价比例是否是在商家才有，此处是否是不需要重新计算价格了
        sku.setMarketPrice(goodsInfoVO.getMarketPrice());
        //设置新sku对应的供应商sku的id
        sku.setProviderGoodsInfoId(goodsInfoVO.getGoodsInfoId());
        //设置为代销状态
        sku.setDelFlag(request.getDelFlag());
        sku.setAddedTimingFlag(Boolean.FALSE);
        sku.setGoodsInfoBarcode(goodsInfoVO.getGoodsInfoBarcode());
        //todo 独立设价
        sku.setAloneFlag(Boolean.TRUE);
        sku.setCateId(goodsVO.getCateId());
        sku.setBrandId(goodsVO.getBrandId());
        sku.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
        //默认销售类型 零售
        sku.setSaleType(SaleType.RETAIL.toValue());
        sku.setProviderId(goodsVO.getProviderId());
    }

    /**
     * sku属性赋值抽取
     */
    public void extraAttributes(GoodsVO goodsVO, GoodsInfoSaveDTO sku, GoodsInfoVO standardSku) {
        if (Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardSku.getGoodsSource())) {
            sku.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
        }
        if (Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardSku.getGoodsSource())) {
            sku.setThirdPlatformType(ThirdPlatformType.VOP);
        }

        if (NumberUtils.INTEGER_ZERO.equals(standardSku.getGoodsSource())
                || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(standardSku.getGoodsSource())
                || Integer.valueOf(GoodsSource.VOP.toValue()).equals(standardSku.getGoodsSource())
        ) {
            //商品可售性
            if (!Integer.valueOf(AddedFlag.NO.toValue()).equals(standardSku.getAddedFlag())) {
                sku.setVendibility(Constants.yes);
            } else {
                sku.setVendibility(Constants.no);
            }
            //店铺状态
            if (goodsVO.getProviderId() != null) {
                sku.setProviderStatus(this.getProviderStatus(goodsVO.getProviderId()));
            }
        }
    }


    /**
     * 商品批量导入商品库
     *
     * @param request 参数
     * @return
     */
    @Transactional
    public List<String> importStandard(GoodsRequest request) {
        if (standardGoodsRelRepository.countByGoodsIds(request.getGoodsIds()) > 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030156);
        }

        // 查询将要导入商品库的商品
        List<Goods> goodsesAll = goodsRepository.findAll(GoodsQueryRequest.builder().goodsIds(request.getGoodsIds())
                .build().getWhereCriteria());
        // 查找已被删除导入的关系
        List<StandardGoodsRel> reImportGoodses = standardGoodsRelRepository.findByDelFlagAndGoodsIdIn(DeleteFlag.YES, request.getGoodsIds());
        // 被删除的导入的商品id集合
        List<String> importedGoodsId = reImportGoodses.stream().map(StandardGoodsRel::getGoodsId).collect(Collectors.toList());
        // 可被导入的商品列表
        List<Goods> importGoodses = new ArrayList<>();
        for (Goods goods : goodsesAll) {
            if (!importedGoodsId.contains(goods.getGoodsId())) {
                // 未被删除的商品，可以导入商品库的商品
                importGoodses.add(goods);
            }
        }

        // 被删除的导入商品重新导入
        reImportStandardGoods(reImportGoodses);
        List<String> standardIds = reImportGoodses.stream().map(StandardGoodsRel::getStandardId).distinct().collect(Collectors.toList());
        standardIds.addAll(importStandardGoods(request, importGoodses));
        return standardIds;
    }

    /**
     * 被删除的导入关系商品重新导入商品库
     *
     * @param reImportGoodses
     */
    @Transactional
    public void reImportStandardGoods(List<StandardGoodsRel> reImportGoodses) {
        for (StandardGoodsRel reImportGoods : reImportGoodses) {
            // 导入商品库（商品spuId，商品库id）
            synProviderGoods(reImportGoods.getGoodsId(), reImportGoods.getStandardId());
        }
    }


    @Transactional
    public List<String> importStandardGoods(GoodsRequest request, List<Goods> importGoodses) {
        List<String> ids = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(importGoodses)) {
            //规格映射Map,商品Id -> 新商品库Id
            Map<String, String> mappingSpu = new HashMap<>();
            //规格映射Map,商品规格Id -> 新商品库规格Id
            Map<Long, Long> mappingSpec = new HashMap<>();
            //规格映射Map,商品规格值Id -> 新商品库规格值Id
            Map<Long, Long> mappingDetail = new HashMap<>();
            //规格映射Map,商品SkuId -> 新商品库SkuId
            Map<String, String> mappingSku = new HashMap<>();
            LocalDateTime now = LocalDateTime.now();

            //导入Spu
            importGoodses.forEach(goods -> {
                StandardGoods standardGoods = new StandardGoods();
                BeanUtils.copyProperties(goods, standardGoods);
                //商家用供应商
                if (Integer.valueOf(GoodsSource.SELLER.toValue()).equals(goods.getGoodsSource())) {
                    standardGoods.setProviderName(goods.getProviderName());
                } else {
                    standardGoods.setProviderName(goods.getSupplierName());
                }
                standardGoods.setGoodsId(null);
                standardGoods.setDelFlag(DeleteFlag.NO);
                standardGoods.setCreateTime(now);
                standardGoods.setUpdateTime(now);
                standardGoods.setStoreId(goods.getStoreId());
                standardGoods.setGoodsNo(goods.getGoodsNo());
                String newGoodsId = standardGoodsRepository.save(standardGoods).getGoodsId();
                mappingSpu.put(goods.getGoodsId(), newGoodsId);
                ids.add(newGoodsId);

                //关联商品与商品库
                StandardGoodsRel rel = new StandardGoodsRel();
                rel.setGoodsId(goods.getGoodsId());
                rel.setStandardId(newGoodsId);
                rel.setStoreId(goods.getStoreId());
                rel.setDelFlag(DeleteFlag.NO);
                standardGoodsRelRepository.save(rel);
            });

            //导入Sku
            List<GoodsInfo> skus = goodsInfoRepository.findAll(GoodsInfoQueryRequest.builder().goodsIds(request.getGoodsIds()).delFlag(DeleteFlag.NO.toValue()).build().getWhereCriteria());
            if (CollectionUtils.isNotEmpty(skus)) {
                skus.forEach(sku -> {
                    StandardSku standardSku = new StandardSku();
                    BeanUtils.copyProperties(sku, standardSku);
                    //商家用供应商
                    if (Integer.valueOf(GoodsSource.SELLER.toValue()).equals(sku.getGoodsSource())) {
                        standardSku.setProviderGoodsInfoId(sku.getProviderGoodsInfoId());
                    } else {
                        standardSku.setProviderGoodsInfoId(sku.getGoodsInfoId());
                    }
                    standardSku.setSupplyPrice(sku.getSupplyPrice());
                    standardSku.setStock(sku.getStock());
                    standardSku.setGoodsInfoId(null);
                    standardSku.setGoodsId(mappingSpu.get(sku.getGoodsId()));
                    standardSku.setCreateTime(now);
                    standardSku.setUpdateTime(now);
                    standardSku.setGoodsInfoNo(sku.getGoodsInfoNo());
                    standardSku.setGoodsSource(sku.getGoodsSource());
                    standardSku.setQuickOrderNo(sku.getQuickOrderNo());
                    mappingSku.put(sku.getGoodsInfoId(), standardSkuRepository.save(standardSku).getGoodsInfoId());
                });
            }

            //导入规格
            List<GoodsSpec> specs = goodsSpecRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(specs)) {
                specs.forEach(spec -> {
                    StandardSpec standardSpec = new StandardSpec();
                    BeanUtils.copyProperties(spec, standardSpec);
                    standardSpec.setSpecId(null);
                    standardSpec.setGoodsId(mappingSpu.get(spec.getGoodsId()));
                    mappingSpec.put(spec.getSpecId(), standardSpecRepository.save(standardSpec).getSpecId());
                });
            }

            //导入规格值
            List<GoodsSpecDetail> details = goodsSpecDetailRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(details)) {
                details.forEach(specDetail -> {
                    StandardSpecDetail detail = new StandardSpecDetail();
                    BeanUtils.copyProperties(specDetail, detail);
                    detail.setSpecDetailId(null);
                    detail.setSpecId(mappingSpec.get(specDetail.getSpecId()));
                    detail.setGoodsId(mappingSpu.get(specDetail.getGoodsId()));
                    mappingDetail.put(specDetail.getSpecDetailId(), standardSpecDetailRepository.save(detail).getSpecDetailId());
                });
            }

            //导入规格值与Sku的关系
            List<GoodsInfoSpecDetailRel> rels = goodsInfoSpecDetailRelRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(rels)) {
                rels.forEach(rel -> {
                    StandardSkuSpecDetailRel standardRel = new StandardSkuSpecDetailRel();
                    BeanUtils.copyProperties(rel, standardRel);
                    standardRel.setSpecDetailRelId(null);
                    standardRel.setSpecId(mappingSpec.get(rel.getSpecId()));
                    standardRel.setSpecDetailId(mappingDetail.get(rel.getSpecDetailId()));
                    standardRel.setGoodsInfoId(mappingSku.get(rel.getGoodsInfoId()));
                    standardRel.setGoodsId(mappingSpu.get(rel.getGoodsId()));
                    standardSkuSpecDetailRelRepository.save(standardRel);
                });
            }

            //导入图片
            List<GoodsImage> imageList = goodsImageRepository.findByGoodsIds(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(imageList)) {
                imageList.forEach(image -> {
                    StandardImage standardImage = new StandardImage();
                    BeanUtils.copyProperties(image, standardImage);
                    standardImage.setImageId(null);
                    standardImage.setGoodsId(mappingSpu.get(image.getGoodsId()));
                    standardImageRepository.save(standardImage);
                });
            }

            // 查询出要导入商品的属性
            List<GoodsPropertyDetailRel> propDetailRelList =
                    goodsPropertyDetailRelRepository.findByGoodsIdInAndDelFlagAndGoodsType(
                            request.getGoodsIds(), DeleteFlag.NO, GoodsPropertyType.GOODS);
            if (CollectionUtils.isNotEmpty(propDetailRelList)) {
                // 导入商品属性
                propDetailRelList.forEach(goodsPropDetailRel -> {
                    GoodsPropertyDetailRel newStandarRel = new GoodsPropertyDetailRel();
                    KsBeanUtil.copyProperties(goodsPropDetailRel, newStandarRel);
                    newStandarRel.setDetailRelId(null);
                    newStandarRel.setGoodsType(GoodsPropertyType.STANDARD_GOODS);
                    newStandarRel.setGoodsId(mappingSpu.get(goodsPropDetailRel.getGoodsId()));
                    goodsPropertyDetailRelRepository.save(newStandarRel);
                });
            }
        }
        return ids;
    }


    /**
     * 从 供应商商品 同步 到 供应商商品库
     * <p>
     * 供应商商品库同步最新商品信息  最好是把关联表全部设置删除， 同步的时候再重新同步，同步的时候商品库goodsId不能变
     *
     * @param goods 供应商商品
     */
    @Transactional
    public void synProviderGoods(GoodsSaveDTO goods) {
        // 商品编辑后，同步商品库
        StandardGoodsRel standardGoodsRel =
                standardGoodsRelRepository.findByGoodsId(goods.getGoodsId());
        if (Objects.nonNull(standardGoodsRel)) {
            this.synProviderGoods(goods.getGoodsId(), standardGoodsRel.getStandardId());
        } else {
            // 平台禁售供应商商品库商品后，商品库关系被删除com.wanmi.sbc.goods.info.service.S2bGoodsService.dealStandardGoods
            // 此时如果商品为审核通过状态，则同步到商品库
            if (Objects.equals(CheckStatus.CHECKED, goods.getAuditStatus())) {
                // 同步到商品库
                GoodsRequest synRequest = new GoodsRequest();
                synRequest.setGoodsIds(Collections.singletonList(goods.getGoodsId()));
                this.importStandard(synRequest);
            }
        }
    }

    /**
     * 从 供应商商品 同步 到 供应商商品库
     * <p>
     * 供应商商品库同步最新商品信息  最好是把关联表全部设置删除， 同步的时候再重新同步，同步的时候商品库goodsId不能变
     *
     * @param goodsId         供应商商品id
     * @param standardGoodsId 商品库商品id
     */
    @Transactional(rollbackFor = Exception.class)
    public void synProviderGoods(String goodsId, String standardGoodsId) {

        //先将所有的商品库关联设置为待同步
        List<StandardGoodsRel> standardGoodsRels = standardGoodsRelRepository.findByStandardId(standardGoodsId);
        for (StandardGoodsRel goodsRel : standardGoodsRels) {
            goodsRel.setNeedSynchronize(BoolFlag.YES);
        }
        standardGoodsRelRepository.saveAll(standardGoodsRels);

        //同步前先删除所有以前的
        standardSkuRepository.deleteByGoodsId(standardGoodsId);
        standardSpecRepository.deleteByGoodsId(standardGoodsId);
        standardSpecDetailRepository.deleteByGoodsId(standardGoodsId);
        standardSkuSpecDetailRelRepository.deleteByGoodsId(standardGoodsId);
        standardImageRepository.deleteByGoodsId(standardGoodsId);
        goodsPropertyDetailRelRepository.deleteByGoodsIdAndGoodsType(standardGoodsId, GoodsPropertyType.STANDARD_GOODS);

        //重新导入------------------------------------------------------
//        //规格映射Map,商品Id -> 新商品库Id
//        Map<String, String> mappingSpu = new HashMap<>();
        //规格映射Map,商品规格Id -> 新商品库规格Id
        Map<Long, Long> mappingSpec = new HashMap<>();
        //规格映射Map,商品规格值Id -> 新商品库规格值Id
        Map<Long, Long> mappingDetail = new HashMap<>();
        //规格映射Map,商品SkuId -> 新商品库SkuId
        Map<String, String> mappingSku = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();

        Goods goods = goodsRepository.findById(goodsId).orElse(null);
        StandardGoods standardGoods = standardGoodsRepository.getOne(standardGoodsId);
        KsBeanUtil.copyProperties(goods, standardGoods);
        // sonar 判断goods对象是否为空
        if (Objects.nonNull(goods)) {
            //商家用供应商
            if (Integer.valueOf(GoodsSource.SELLER.toValue()).equals(goods.getGoodsSource())) {
                standardGoods.setProviderName(goods.getProviderName());
            } else {
                standardGoods.setProviderName(goods.getSupplierName());
            }
            standardGoods.setGoodsNo(goods.getGoodsNo());
            standardGoods.setStoreId(goods.getStoreId());
        }

        standardGoods.setDelFlag(DeleteFlag.NO);
        standardGoods.setUpdateTime(now);
        standardGoods.setGoodsId(standardGoodsId);
        standardGoodsRepository.save(standardGoods);

//        //关联商品与商品库
        StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findALLByGoodsId(goodsId);
        // sonar 判断goods对象是否为空
        if (Objects.nonNull(goods)) {
            standardGoodsRel.setGoodsId(goods.getGoodsId());
            standardGoodsRel.setStoreId(goods.getStoreId());
        }
        standardGoodsRel.setDelFlag(DeleteFlag.NO);
        standardGoodsRel.setNeedSynchronize(BoolFlag.NO);
        standardGoodsRelRepository.save(standardGoodsRel);

        //导入Sku
        List<GoodsInfo> skus = goodsInfoRepository.findAll(GoodsInfoQueryRequest.builder().goodsId(goodsId).delFlag(DeleteFlag.NO.toValue()).build().getWhereCriteria());
        if (CollectionUtils.isNotEmpty(skus)) {
            skus.forEach(sku -> {
                StandardSku standardSku = new StandardSku();
                BeanUtils.copyProperties(sku, standardSku);
                //商家用供应商
                if (Integer.valueOf(GoodsSource.SELLER.toValue()).equals(sku.getGoodsSource())) {
                    standardSku.setProviderGoodsInfoId(sku.getProviderGoodsInfoId());
                } else {
                    standardSku.setProviderGoodsInfoId(sku.getGoodsInfoId());
                }
                standardSku.setSupplyPrice(sku.getSupplyPrice());
                standardSku.setStock(sku.getStock());
                standardSku.setGoodsInfoId(null);
                standardSku.setGoodsId(standardGoodsId);
                standardSku.setCreateTime(now);
                standardSku.setUpdateTime(now);
                standardSku.setGoodsInfoNo(sku.getGoodsInfoNo());
                standardSku.setQuickOrderNo(sku.getQuickOrderNo());
                mappingSku.put(sku.getGoodsInfoId(), standardSkuRepository.save(standardSku).getGoodsInfoId());
            });
        }

        //导入规格
        List<GoodsSpec> specs = goodsSpecRepository.findByGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(specs)) {
            specs.forEach(spec -> {
                StandardSpec standardSpec = new StandardSpec();
                BeanUtils.copyProperties(spec, standardSpec);
                standardSpec.setSpecId(null);
                standardSpec.setGoodsId(standardGoodsId);
                standardSpec.setDelFlag(DeleteFlag.NO);
                mappingSpec.put(spec.getSpecId(), standardSpecRepository.save(standardSpec).getSpecId());
            });
        }

        //导入规格值
        List<GoodsSpecDetail> details = goodsSpecDetailRepository.findByGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(details)) {
            details.forEach(specDetail -> {
                StandardSpecDetail detail = new StandardSpecDetail();
                BeanUtils.copyProperties(specDetail, detail);
                detail.setSpecDetailId(null);
                detail.setSpecId(mappingSpec.get(specDetail.getSpecId()));
                detail.setGoodsId(standardGoodsId);
                mappingDetail.put(specDetail.getSpecDetailId(), standardSpecDetailRepository.save(detail).getSpecDetailId());
            });
        }

        //导入规格值与Sku的关系
        List<GoodsInfoSpecDetailRel> rels = goodsInfoSpecDetailRelRepository.findByGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(rels)) {
            rels.forEach(rel -> {
                if (mappingDetail.containsKey(rel.getSpecDetailId())) {
                    StandardSkuSpecDetailRel standardRel = new StandardSkuSpecDetailRel();
                    BeanUtils.copyProperties(rel, standardRel);
                    standardRel.setSpecDetailRelId(null);
                    standardRel.setSpecId(mappingSpec.get(rel.getSpecId()));
                    standardRel.setSpecDetailId(mappingDetail.get(rel.getSpecDetailId()));
                    standardRel.setGoodsInfoId(mappingSku.get(rel.getGoodsInfoId()));
                    standardRel.setGoodsId(standardGoodsId);
                    standardSkuSpecDetailRelRepository.save(standardRel);
                }
            });
        }

        //导入图片
        List<GoodsImage> imageList = goodsImageRepository.findByGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(imageList)) {
            imageList.forEach(image -> {
                StandardImage standardImage = new StandardImage();
                BeanUtils.copyProperties(image, standardImage);
                standardImage.setImageId(null);
                standardImage.setGoodsId(standardGoodsId);
                standardImageRepository.save(standardImage);
            });
        }

        //导入商品属性
        List<GoodsPropertyDetailRel> propDetailRelList =
                goodsPropertyDetailRelRepository.findByGoodsIdAndDelFlagAndGoodsType(
                        goodsId, DeleteFlag.NO, GoodsPropertyType.GOODS);
        if (CollectionUtils.isNotEmpty(propDetailRelList)) {
            propDetailRelList.forEach(goodsPropDetailRel -> {
                GoodsPropertyDetailRel newDetailRel = new GoodsPropertyDetailRel();
                KsBeanUtil.copyProperties(goodsPropDetailRel, newDetailRel);
                newDetailRel.setDetailRelId(null);
                newDetailRel.setGoodsId(standardGoodsId);
                newDetailRel.setGoodsType(GoodsPropertyType.STANDARD_GOODS);
                goodsPropertyDetailRelRepository.save(newDetailRel);
            });
        }
    }

    /**
     * 从供应商商品库同步到 商家商品
     *
     * @param goodsId 商家商品id
     */
    @Transactional(rollbackFor = Exception.class)
    public void synStandardGoods(String goodsId) {
        goodsInfoRepository.updateDeleteFlagByGoodsId(goodsId);
        goodsSpecRepository.deleteByGoodsId(goodsId);
        goodsSpecDetailRepository.deleteByGoodsId(goodsId);
        goodsSpecDetailRepository.deleteByGoodsId(goodsId);
        goodsInfoSpecDetailRelRepository.deleteByGoodsId(goodsId);
        goodsImageRepository.deleteByGoodsId(goodsId);
        goodsPropDetailRelRepository.deleteByGoodsId(goodsId);

        StandardGoodsRel goodsRel = standardGoodsRelRepository.findByGoodsId(goodsId);
        //同步后后就不需要再同步了
        goodsRel.setNeedSynchronize(BoolFlag.NO);
        standardGoodsRelRepository.save(goodsRel);
        String standardGoodsId = goodsRel.getStandardId();
        StandardGoods standardGoods = standardGoodsRepository.getOne(standardGoodsId);

        //规格映射Map,商品库Id -> 新商品Id
        Map<String, String> mappingSpu = new HashMap<>();
        //规格映射Map,商品库规格Id -> 新商品规格Id
        Map<Long, Long> mappingSpec = new HashMap<>();
        //规格映射Map,商品库规格值Id -> 新商品规格值Id
        Map<Long, Long> mappingDetail = new HashMap<>();
        //规格映射Map,商品库SkuId -> 新商品SkuId
        Map<String, String> mappingSku = new HashMap<>();
        //规格映射Map,商品库Id -> 商品来源
//        Map<String, Integer> mappingGoodsSourse = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();

        //导入Spu
        Goods goods = goodsRepository.findById(goodsId).orElse(null);
        BeanUtils.copyProperties(standardGoods, goods);
        goods.setGoodsId(goodsId);
        goods.setAddedFlag(AddedFlag.NO.toValue());
        goods.setDelFlag(DeleteFlag.NO);
        goods.setAddFalseReason(UnAddedFlagReason.PROVIDERUNADDED.name());
        goods.setCreateTime(now);
        goods.setUpdateTime(now);
        goods.setAddedTime(now);
        goods.setSubmitTime(now);
        goods.setAuditStatus(CheckStatus.CHECKED);
        goods.setCustomFlag(Constants.no);
        goods.setLevelDiscountFlag(Constants.no);
        goods.setGoodsNo(goodsCommonService.getSpuNoByUnique());
        goods.setPriceType(GoodsPriceType.MARKET.toValue());
        //默认销售类型 批发
        goods.setSaleType(SaleType.WHOLESALE.toValue());
        goods.setMarketPrice(null);

        goods.setGoodsVideo(standardGoods.getGoodsVideo());
        //商品来源，0供应商，1商家 导入后就是商家商品 以此来区分列表展示
        goods.setGoodsSource(1);
        goods.setSupplyPrice(standardGoods.getSupplyPrice());
        goods.setRecommendedRetailPrice(standardGoods.getRecommendedRetailPrice());
        goods.setProviderName(standardGoods.getProviderName());
        //允许独立设价
        goods.setAllowPriceSet(1);
        //设置定时上架价flag
        goods.setAddedTimingFlag(Boolean.FALSE);

        //初始化商品对应的数量（收藏、销量、评论数、好评数）
        if (goods.getGoodsCollectNum() == null) {
            goods.setGoodsCollectNum(0L);
        }
        if (goods.getGoodsSalesNum() == null) {
            goods.setGoodsSalesNum(0L);
        }
        if (goods.getGoodsEvaluateNum() == null) {
            goods.setGoodsEvaluateNum(0L);
        }
        if (goods.getGoodsFavorableCommentNum() == null) {
            goods.setGoodsFavorableCommentNum(0L);
        }
        String newGoodsId = goodsRepository.save(goods).getGoodsId();
        // 检查商品基本信息
//        goodsService.checkBasic(goods);

        mappingSpu.put(standardGoods.getGoodsId(), newGoodsId);
//        mappingGoodsSourse.put(standardGoods.getGoodsId(), standardGoods.getGoodsSource());

        //关联商品与商品库
//        StandardGoodsRel rel = new StandardGoodsRel();
//        rel.setGoodsId(newGoodsId);
//        rel.setStandardId(standardGoods.getGoodsId());
//        rel.setStoreId(request.getStoreId());
//        standardGoodsRelRepository.save(rel);

//        List<StandardGoods> spuList = standardGoodses;
        //导入Sku
        List<StandardSku> skus = standardSkuRepository.findAll(StandardSkuQueryRequest.builder().goodsId(standardGoodsId).delFlag(DeleteFlag.NO.toValue()).build().getWhereCriteria());
        if (CollectionUtils.isNotEmpty(skus)) {
            skus.forEach(standardSku -> {
                GoodsInfo sku = new GoodsInfo();
                BeanUtils.copyProperties(standardSku, sku);
                sku.setGoodsInfoId(null);
                sku.setGoodsId(mappingSpu.get(standardSku.getGoodsId()));
//                sku.setProviderId(mappingOldStoreId.get(standardSku.getGoodsId()));
                sku.setGoodsSource(1);
                sku.setCreateTime(now);
                sku.setUpdateTime(now);
                sku.setAddedTime(now);
                sku.setAddedFlag(AddedFlag.NO.toValue());
//                sku.setCompanyInfoId(request.getCompanyInfoId());
                sku.setCompanyInfoId(goods.getCompanyInfoId());
                sku.setLevelDiscountFlag(Constants.no);
                sku.setCustomFlag(Constants.no);
//                sku.setStoreId(request.getStoreId());
                sku.setStoreId(goods.getStoreId());
                sku.setAuditStatus(CheckStatus.CHECKED);
//                sku.setCompanyType(request.getCompanyType());
                sku.setCompanyType(goods.getCompanyType());
                sku.setGoodsInfoNo(goodsCommonService.getSkuNoByUnique());
                sku.setStock(standardSku.getStock());
                sku.setAloneFlag(Boolean.FALSE);
                sku.setSupplyPrice(standardSku.getSupplyPrice());

//                Optional<StandardGoods> standardGoods = spuList.stream().filter(goodses -> goodses.getGoodsId().equals(standardSku.getGoodsId())).findFirst();
                sku.setCateId(standardGoods.getCateId());
                sku.setBrandId(standardGoods.getBrandId());
                sku.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                //默认销售类型 批发
                sku.setSaleType(SaleType.WHOLESALE.toValue());
                sku.setAddedTimingFlag(Boolean.FALSE);
                String skuId = goodsInfoRepository.save(sku).getGoodsInfoId();
//                newSkuIds.add(skuId);
                mappingSku.put(standardSku.getGoodsInfoId(), skuId);
            });
        }

        //导入规格
        List<StandardSpec> specs = standardSpecRepository.findByGoodsId(standardGoodsId);
        if (CollectionUtils.isNotEmpty(specs)) {
            specs.forEach(standardSpec -> {
                GoodsSpec spec = new GoodsSpec();
                BeanUtils.copyProperties(standardSpec, spec);
                spec.setSpecId(null);
                spec.setGoodsId(mappingSpu.get(standardSpec.getGoodsId()));
                mappingSpec.put(standardSpec.getSpecId(), goodsSpecRepository.save(spec).getSpecId());
            });
        }

        //导入规格值
        List<StandardSpecDetail> details = standardSpecDetailRepository.findByGoodsId(standardGoodsId);
        if (CollectionUtils.isNotEmpty(details)) {
            details.forEach(specDetail -> {
                GoodsSpecDetail detail = new GoodsSpecDetail();
                BeanUtils.copyProperties(specDetail, detail);
                detail.setSpecDetailId(null);
                detail.setSpecId(mappingSpec.get(specDetail.getSpecId()));
                detail.setGoodsId(mappingSpu.get(specDetail.getGoodsId()));
                mappingDetail.put(specDetail.getSpecDetailId(), goodsSpecDetailRepository.save(detail).getSpecDetailId());
            });
        }

        //导入规格值与Sku的关系
        List<StandardSkuSpecDetailRel> rels = standardSkuSpecDetailRelRepository.findByGoodsId(standardGoodsId);
        if (CollectionUtils.isNotEmpty(rels)) {
            rels.forEach(standardRel -> {
                GoodsInfoSpecDetailRel rel = new GoodsInfoSpecDetailRel();
                BeanUtils.copyProperties(standardRel, rel);
                rel.setSpecDetailRelId(null);
                rel.setSpecId(mappingSpec.get(standardRel.getSpecId()));
                rel.setSpecDetailId(mappingDetail.get(standardRel.getSpecDetailId()));
                rel.setGoodsInfoId(mappingSku.get(standardRel.getGoodsInfoId()));
                rel.setGoodsId(mappingSpu.get(standardRel.getGoodsId()));
                goodsInfoSpecDetailRelRepository.save(rel);
            });
        }

        //导入图片
        List<StandardImage> imageList = standardImageRepository.findByGoodsId(standardGoodsId);
        if (CollectionUtils.isNotEmpty(imageList)) {
            imageList.forEach(standardImage -> {
                GoodsImage image = new GoodsImage();
                BeanUtils.copyProperties(standardImage, image);
                image.setImageId(null);
                image.setGoodsId(mappingSpu.get(standardImage.getGoodsId()));
                goodsImageRepository.save(image);
            });
        }

        //导入商品属性
        List<StandardPropDetailRel> propDetailRelList = standardPropDetailRelRepository.queryByGoodsId(standardGoodsId);
        if (CollectionUtils.isNotEmpty(propDetailRelList)) {
            propDetailRelList.forEach(goodsPropDetailRel -> {
                GoodsPropDetailRel rel = new GoodsPropDetailRel();
                BeanUtils.copyProperties(goodsPropDetailRel, rel);
                rel.setRelId(null);
                rel.setGoodsId(mappingSpu.get(goodsPropDetailRel.getGoodsId()));
                goodsPropDetailRelRepository.save(rel);
            });
        }

    }

    /**
     * @param standardgoodsId 商品库id
     * @param storeId         商家id
     * @return 商家商品id
     */
    @Transactional
    public String syn(String standardgoodsId, Long storeId) {
        StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByStandardIdAndStoreId(standardgoodsId, storeId);
        synStandardGoods(standardGoodsRel.getGoodsId());
        return standardGoodsRel.getGoodsId();
    }

    /**
     * 查询店铺开店状态
     *
     * @param storeId
     * @return
     */
    public Integer getProviderStatus(Long storeId) {
        if (Objects.isNull(storeId)) {
            return Constants.yes;
        }
        StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(storeId).build()).getContext().getStoreVO();
        LocalDateTime now = LocalDateTime.now();
        if (Objects.nonNull(storeVO)) {
            //判断当前店铺是否过期 true：过期 false：没过期
            Boolean isExpired = !((now.isBefore(storeVO.getContractEndDate()) || now.isEqual(storeVO.getContractEndDate()))
                    && (now.isAfter(storeVO.getContractStartDate()) || now.isEqual(storeVO.getContractStartDate())));
            if (StoreState.OPENING.equals(storeVO.getStoreState()) && !isExpired) {
                return Constants.yes;
            }
        }
        return Constants.no;
    }

    /**
     * 根据商品Id  批量查询商家信息
     * @param storeIdList
     * @return
     */
    public Map<Long, StoreVO> getStoreList(List<Long> storeIdList) {
        List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIdList).build()).getContext().getStoreVOList();
        if (CollectionUtils.isEmpty(storeVOList)) {
            return new HashMap<>();
        }
        return storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
    }

    /**
     * 验证商家是否为有效状态
     * @param storeVO
     * @return
     */
    public Integer getProviderStatus(StoreVO storeVO) {
        if (Objects.isNull(storeVO)) {
            return Constants.yes;
        }
        LocalDateTime now = LocalDateTime.now();
        //判断当前店铺是否过期 true：过期 false：没过期
        Boolean isExpired = !((now.isBefore(storeVO.getContractEndDate()) || now.isEqual(storeVO.getContractEndDate()))
                && (now.isAfter(storeVO.getContractStartDate()) || now.isEqual(storeVO.getContractStartDate())));
        if (StoreState.OPENING.equals(storeVO.getStoreState()) && !isExpired) {
            return Constants.yes;
        }
        return Constants.no;
    }

    /***
     * 获得默认的店铺分类ID
     * @param request   导入请求对象
     * @return 默认店铺分类ID
     */
    protected Long getDefaultStoreCateId(StandardImportRequest request) {
        List<StoreCate> storeCateList = storeCateRepository.findAll(StoreCateQueryRequest.builder()
                .storeId(request.getStoreId())
                .delFlag(DeleteFlag.NO)
                .isDefault(DefaultFlag.YES).build().getWhereCriteria());

        // 断言查询结果不为空
        Assert.assertNotEmpty(storeCateList, GoodsErrorCodeEnum.K030053);
        return WmCollectionUtils.findFirst(storeCateList).getStoreCateId();
    }

    /***
     * 商品对象保存前切面
     * @param goods 商品对象
     * @return 修改后的商品对象
     */
    protected GoodsSaveDTO beforeGoodsSave(GoodsSaveDTO goods) {
        return goods;
    }

    /***
     * 关联商品与商品库保存前切面
     * @param standardGoodsRel  商品库与商品关联对象
     * @return 修改后的商品库与商品关联对象
     */
    protected StandardGoodsRel beforeStandardGoodsRelSave(StandardGoodsRel standardGoodsRel) {
        return standardGoodsRel;
    }

    /***
     * SKU保存前切面
     * @param goodsInfo 商品SKU对象
     * @return 修改后的商品SKU对象
     */
    protected GoodsInfoSaveDTO beforeSkuSave(GoodsInfoSaveDTO goodsInfo) {
        return goodsInfo;
    }

    /**
     * @description     获取市场价
     * @author  wur
     * @date: 2021/9/18 15:40
     * @param storeId    商家Id
     * @param goodsInfo  商品信息
     * @param supplyPrice 供货价
     * @return
     **/
    public BigDecimal getMarketPrice(Long storeId, GoodsInfoSaveDTO goodsInfo, BigDecimal supplyPrice) {
        GoodsCommissionConfig goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(storeId);
        //验证是否手动设价
        if (CommissionSynPriceType.HAND_SYN.toValue() == goodsCommissionConfig.getSynPriceType().toValue()) {
            return supplyPrice;
        }
        BigDecimal addRate = BigDecimal.ZERO;
        GoodsCommissionPriceConfig goodsCommissionPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(storeId, goodsInfo);
        if (Objects.isNull(goodsCommissionPriceConfig)) {
            addRate = Objects.isNull(goodsCommissionConfig.getAddRate()) ? BigDecimal.ZERO : goodsCommissionConfig.getAddRate();
        } else {
            addRate = goodsCommissionPriceConfig.getAddRate();
        }
        return supplyPrice.add(goodsCommissionPriceService.getAddPrice(addRate, supplyPrice));

    }

}
