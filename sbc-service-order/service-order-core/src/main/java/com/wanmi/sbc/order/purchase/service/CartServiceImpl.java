package com.wanmi.sbc.order.purchase.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.customer.bean.vo.MiniStoreVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.marketing.GoodsMarketingProvider;
import com.wanmi.sbc.goods.api.provider.marketing.GoodsMarketingQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByGoodsIdRequest;
import com.wanmi.sbc.goods.api.request.common.InfoForPurchaseRequest;
import com.wanmi.sbc.goods.api.request.freight.CartListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.marketing.GoodsMarketingListByCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.marketing.GoodsMarketingSyncRequest;
import com.wanmi.sbc.goods.api.response.freight.CartListResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsCartPluginResponse;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.order.api.request.purchase.CartGoodsInfoRequest;
import com.wanmi.sbc.order.api.request.purchase.PurchaseInfoRequest;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.common.SystemPointsConfigService;
import com.wanmi.sbc.order.purchase.Purchase;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.util.mapper.GoodsMapper;
import com.wanmi.sbc.order.util.mapper.StoreMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className CartServiceImpl
 * @description TODO
 * @date 2021/12/27 3:51 下午
 */
@Primary
@Service
public class CartServiceImpl implements CartInterface {

    @Autowired GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired PurchaseService purchaseService;

    @Autowired StoreQueryProvider storeQueryProvider;

    @Autowired DistributionCacheService distributionCacheService;

    @Autowired SystemPointsConfigService systemPointsConfigService;

    @Autowired ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired TradeCacheService tradeCacheService;

    @Autowired DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired GoodsMarketingQueryProvider goodsMarketingQueryProvider;

    @Autowired GoodsMarketingProvider goodsMarketingProvider;

    @Autowired StoreMapper storeMapper;

    @Autowired GoodsMapper goodsMapper;

    @Autowired
    private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired
    private FreightProvider freightProvider;

    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    private static final Set<Integer> CHOOSE_MARKETING_TYPE = new HashSet<>();

    static {
        CHOOSE_MARKETING_TYPE.add(MarketingPluginType.REDUCTION.getId());
        CHOOSE_MARKETING_TYPE.add(MarketingPluginType.DISCOUNT.getId());
        CHOOSE_MARKETING_TYPE.add(MarketingPluginType.GIFT.getId());
        CHOOSE_MARKETING_TYPE.add(MarketingPluginType.BUYOUT_PRICE.getId());
        CHOOSE_MARKETING_TYPE.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE.getId());
        CHOOSE_MARKETING_TYPE.add(MarketingPluginType.PREFERENTIAL.getId());
    }

    /**
     * @param request
     * @return
     */
    @Override
    public PurchaseInfoRequest setRequest(PurchaseInfoRequest request) {

        List<PluginType> pluginTypes = new ArrayList<>();
        pluginTypes.add(PluginType.NORMAL);
        pluginTypes.add(PluginType.CROSS_BORDER);
        request.setPluginTypes(pluginTypes);
        //合并sku，购买数量信息
        if(CollectionUtils.isNotEmpty(request.getGoodsInfoList())) {
            Map<String, CartGoodsInfoRequest> cartGoodsInfoRequestMap =  request.getGoodsInfoList().stream()
                    .filter(c -> StringUtils.isNotBlank(c.getGoodsInfoId()))
                    .collect(Collectors.toMap(CartGoodsInfoRequest::getGoodsInfoId, Function.identity(), (a,b) -> {
                CartGoodsInfoRequest r = new CartGoodsInfoRequest();
                r.setGoodsInfoId(a.getGoodsInfoId());
                r.setBuyCount(ObjectUtils.defaultIfNull(a.getBuyCount(), 0L)+ObjectUtils.defaultIfNull(b.getBuyCount(),0L));
                return r;
            }));
            request.setGoodsInfoList(new ArrayList<>(cartGoodsInfoRequestMap.values()));
        }
        return request;
    }

    /**
     * @param request
     * @return
     */
    @Override
    public List<Purchase> getPurchase(PurchaseInfoRequest request) {
        // 1.登录的情况，查询采购单信息

        if (Objects.nonNull(request.getCustomer())) {
            return purchaseService.queryPurchase(request);
        }
        return null;
    }

    @Override
    public List<GoodsInfoCartVO> getGoodsInfo(List<String> goodsInfoIds,CustomerVO customer, Long storeId) {
        GoodsInfoListByIdsRequest.builder().goodsInfoIds(goodsInfoIds).build();
        List<GoodsInfoCartVO> goodsInfoCartVOList = goodsInfoQueryProvider
                .getCartGoodsInfoByIds(
                        InfoForPurchaseRequest.builder()
                                .goodsInfoIds(goodsInfoIds)
                                .customer(customer)
                                .storeId(storeId)
                                .build())
                .getContext();
        //按照请求参数排序 未登录时需要按照请求传参顺序排序
        return goodsInfoCartVOList.stream()
                .peek(goodsInfoCartVO -> {
                    Integer isBuyCycle = goodsInfoCartVO.getIsBuyCycle();
                    if(Objects.equals(isBuyCycle, Constants.yes)) {
                        BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = buyCycleGoodsInfoQueryProvider.getById(BuyCycleGoodsInfoByGoodsIdRequest.builder()
                                .goodsInfoId(goodsInfoCartVO.getGoodsInfoId())
                                .build()).getContext().getBuyCycleGoodsInfoVO();
                        goodsInfoCartVO.setMarketPrice(buyCycleGoodsInfoVO.getCyclePrice());
                    }
                })
                .sorted(Comparator.comparing(g-> goodsInfoIds.indexOf(g.getGoodsInfoId()))).collect(Collectors.toList());
    }

    /** 校验相关的配置，设置商品的状态 */
    @Override
    public List<GoodsInfoCartVO> checkSetting(List<GoodsInfoCartVO> goodsInfoCartList,CustomerVO customer,List<StoreVO> storeVOS, String inviteeId) {
        Map<Long, StoreVO> storeMap = new HashMap<>();
        // 店铺状态是否正常
        if (CollectionUtils.isNotEmpty(storeVOS)) {
            storeMap =
                    storeVOS.stream()
                            .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
        }
        // 分销开关
        DefaultFlag distributionFlag = distributionCacheService.queryOpenFlag();

        // 积分抵扣
        boolean isGoodsPoint = systemPointsConfigService.isGoodsPoint();

        return setStatus(goodsInfoCartList, storeMap, distributionFlag, isGoodsPoint,customer, inviteeId);
    }

    /**
    *  处理商品库存  只处理状态有效的商品
     * @description
     * @author  wur
     * @date: 2022/1/29 11:31
     * @param goodsInfoCartList
     * @param address
     * @param storeId              门店ID，O2O模式用，不可删除
     **/
    @Override
    public List<GoodsInfoCartVO> setStock(List<GoodsInfoCartVO> goodsInfoCartList,PlatformAddress address, Long storeId) {

        // 普通商品
        List<String> goodsInfoIds = new ArrayList<>();
        // 第三方渠道商品
        List<GoodsInfoCartVO> channelList = new ArrayList<>();
        goodsInfoCartList.forEach(
                g -> {
                    if (g.getGoodsStatus().equals(GoodsStatus.OK)) {
                        if (g.getThirdPlatformType() != null) {
                            channelList.add(g);
                        } else {
                            if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                goodsInfoIds.add(g.getProviderGoodsInfoId());
                            } else {
                                goodsInfoIds.add(g.getGoodsInfoId());
                            }
                        }
                    }
                });
        // 正常商品库存/ 供应商商品库存
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            Map<String, Long> skuStockMap =
                    goodsInfoQueryProvider
                            .getStockByGoodsInfoIds(
                                    GoodsInfoListByIdsRequest.builder()
                                            .goodsInfoIds(goodsInfoIds)
                                            .build())
                            .getContext();
            if (MapUtils.isNotEmpty(skuStockMap)) {
                goodsInfoCartList.stream()
                        .filter(g -> GoodsStatus.OK.equals(g.getGoodsStatus()) && null == g.getThirdPlatformType())
                        .forEach(
                                g -> {
                                    Long stock = 0L;
                                    if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                        stock = skuStockMap.get(g.getProviderGoodsInfoId());
                                    } else {
                                        stock = skuStockMap.get(g.getGoodsInfoId());
                                    }
                                    g.setStock(stock);
                                    if (stock == null || stock < 1) {
                                        g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                                    }
                                });
            }
        }

        // 渠道商品库存
        if (CollectionUtils.isNotEmpty(channelList)) {
            List<GoodsInfoCartVO> channelResultList =
                    (List<GoodsInfoCartVO>) thirdPlatformTradeService.cartStatus(goodsInfoCartList, address);
        }

        return goodsInfoCartList;
    }

    /**
     * 获取营销活动信息 如果未登录用户只获取全平台用户的营销活动
     * @param goodsInfoCartList    购物车商品信息
     * @param customer           会员ID
     * @param storeId              门店ID，O2O模式用，不可删除
     * @return
     */
    @Override
    public List<GoodsInfoCartVO> getMarketing(List<GoodsInfoCartVO> goodsInfoCartList,CustomerVO customer, Long storeId,List<CartGoodsInfoRequest> cartGoodsInfoRequests) {
        // 过滤商品状态正常的商品
        List<GoodsInfoCartVO> filterList =
                goodsInfoCartList.stream()
                        .filter(
                                g ->
                                        g.getGoodsStatus() == null
                                                || g.getGoodsStatus().equals(GoodsStatus.OK)
                                                || g.getGoodsStatus().equals(GoodsStatus.OUT_STOCK))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(filterList)) {
            Map<String,Long> skuNumMap = cartGoodsInfoRequests.stream().collect(Collectors.toMap(
                    CartGoodsInfoRequest::getGoodsInfoId,
                    CartGoodsInfoRequest::getBuyCount));
            for(GoodsInfoCartVO goodsInfoCartVO : goodsInfoCartList){
                if(goodsInfoCartVO.getPriceType().equals(1)){
                    Long num = skuNumMap.get(goodsInfoCartVO.getGoodsInfoId());
                    List<GoodsIntervalPriceVO> intervalPriceList = goodsInfoCartVO.getIntervalPriceList();
                    AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
                    // 先排序再判断区间
                    if(CollectionUtils.isNotEmpty(intervalPriceList)){
                        intervalPriceList.stream()
                                .sorted(Comparator.comparing(GoodsIntervalPriceVO::getCount))
                                .forEach(
                                        i -> {
                                            if (i.getCount() <= num) {
                                                price.set(i.getPrice());
                                            }
                                        });
                        goodsInfoCartVO.setMarketPrice(price.get());
                    }
                }
            }
            List<GoodsInfoSimpleVO> goodsInfoSimpleVOS =
                    goodsMapper.goodsInfoCartVOsToGoodsInfoSimpleVOs(filterList);
            GoodsListPluginRequest goodsListPluginRequest = new GoodsListPluginRequest();
            goodsListPluginRequest.setGoodsInfoPluginRequests(goodsInfoSimpleVOS);
            if (customer != null) {

                goodsListPluginRequest.setCustomerId(customer.getCustomerId());
                if (customer.getEnterpriseCheckState() != null
                        && customer.getEnterpriseCheckState()
                                .equals(EnterpriseCheckState.CHECKED)) {
                    goodsListPluginRequest.setIepCustomerFlag(true);
                }
            }

            goodsListPluginRequest.setStoreId(storeId);
            GoodsCartPluginResponse pluginResponse =
                    newMarketingPluginProvider.cartListPlugin(goodsListPluginRequest).getContext();
            if (pluginResponse != null) {
                Map<String, List<MarketingPluginLabelDetailVO>> pluginLabelMap =
                        pluginResponse.getSkuMarketingLabelMap();
                Map<String,Integer> originalPriceFlagMap = pluginResponse.getOriginalPriceFlagMap();
                goodsInfoCartList.forEach(
                        g -> {
                            g.setMarketingPluginLabels(pluginLabelMap.get(g.getGoodsInfoId()));
                            if(g.getPriceType().equals(1)){
                                List<MarketingPluginLabelDetailVO> pluginLabelDetailVOS = pluginLabelMap.get(g.getGoodsInfoId());
                                if(CollectionUtils.isNotEmpty(pluginLabelDetailVOS)){
                                    Optional<MarketingPluginLabelDetailVO> optional = pluginLabelDetailVOS.stream()
                                            .filter(p->p.getMarketingType().equals(MarketingPluginType.CUSTOMER_LEVEL.getId())
                                                    || p.getMarketingType().equals(MarketingPluginType.PAYING_MEMBER.getId()))
                                            .findFirst();
                                    if(optional.isPresent()){
                                        BigDecimal pluginPrice = optional.get().getPluginPrice();
                                        BigDecimal ratio = pluginPrice.divide(g.getMarketPrice(), 4,
                                                RoundingMode.HALF_UP);
                                        if(CollectionUtils.isNotEmpty(g.getIntervalPriceList())){
                                            g.getIntervalPriceList().forEach(i->i.setPrice(i.getPrice().multiply(ratio)));
                                        }
                                    }
                                }
                            }
                            if(Objects.nonNull(originalPriceFlagMap) && Objects.nonNull(originalPriceFlagMap.get(g.getGoodsInfoId()))){
                                g.setIsBuyForOriginalPrice(originalPriceFlagMap.get(g.getGoodsInfoId()));
                            }
                        });

            }
        } 
        return goodsInfoCartList;
    }

    /**
     * 封装商品选择营销， 未登录用户不从：goods_marketing 获取直接设置优先级最高的
     * @param goodsInfoCartList   购物车商品信息
     * @param customerId          用户ID
     * @return
     */
//    @Override
//    public List<GoodsMarketingVO> setGoodsMarketing(List<GoodsInfoCartVO> goodsInfoCartList,String customerId) {
//
//        List<GoodsMarketingVO> retList = new ArrayList<>();
//        List<GoodsMarketingVO> addList = new ArrayList<>();
//        Set<String> delIds = new HashSet<>();
//        //查询用户已选择优惠活动
//        List<GoodsMarketingVO> goodsMarketingList = null;
//        if (StringUtils.isNotBlank(customerId)) {
//            GoodsMarketingListByCustomerIdRequest request = new GoodsMarketingListByCustomerIdRequest();
//            request.setCustomerId(customerId);
//            goodsMarketingList =
//                    goodsMarketingQueryProvider
//                            .listByCustomerId(request)
//                            .getContext()
//                            .getGoodsMarketings();
//        }
//
//
//        Map<String, GoodsInfoCartVO> goodsInfoCartVOMap =
//                goodsInfoCartList.stream()
//                        .filter(
//                                g ->
//                                        g.getGoodsStatus() == null
//                                                || g.getGoodsStatus().equals(GoodsStatus.OK)
//                                                || g.getGoodsStatus().equals(GoodsStatus.OUT_STOCK))
//                        .distinct()
//                        .collect(
//                                Collectors.toMap(
//                                        GoodsInfoCartVO::getGoodsInfoId, a -> a, (k1, k2) -> k1));
//
//        if (CollectionUtils.isNotEmpty(goodsMarketingList)
//                && MapUtils.isNotEmpty(goodsInfoCartVOMap)) {
//            goodsMarketingList.forEach(
//                    goodsMarketing -> {
//                        GoodsInfoCartVO cartVO =
//                                goodsInfoCartVOMap.get(goodsMarketing.getGoodsInfoId());
//                        if (cartVO == null) {
//                            delIds.add(goodsMarketing.getGoodsInfoId());
//                        } else {
//                            List<MarketingPluginLabelDetailVO> pluginLabelDetailVOS =
//                                    cartVO.getMarketingPluginLabels();
//                            if (CollectionUtils.isNotEmpty(pluginLabelDetailVOS)) {
//                                List<MarketingPluginLabelDetailVO> tempList =
//                                        pluginLabelDetailVOS.stream()
//                                                .filter(
//                                                        p ->
//                                                                CHOOSE_MARKETING_TYPE.contains(
//                                                                        p.getMarketingType()))
//                                                .collect(Collectors.toList());
//                                if (CollectionUtils.isNotEmpty(tempList)) {
//                                    List<Long> marketingIds =
//                                            tempList.stream()
//                                                    .map(
//                                                            MarketingPluginLabelDetailVO
//                                                                    ::getMarketingId)
//                                                    .map(t -> ((Number) t).longValue())
//                                                    .collect(Collectors.toList());
//                                    if (!marketingIds.contains(goodsMarketing.getMarketingId())) {
//                                        delIds.add(cartVO.getGoodsInfoId());
//                                        GoodsMarketingVO goodsMarketingVO = new GoodsMarketingVO();
//                                        goodsMarketingVO.setMarketingId(
//                                                ((Number) tempList.get(0).getMarketingId())
//                                                        .longValue());
//                                        goodsMarketingVO.setGoodsInfoId(cartVO.getGoodsInfoId());
//                                        goodsMarketingVO.setCustomerId(customerId);
//                                        addList.add(goodsMarketingVO);
//                                        retList.add(goodsMarketingVO);
//                                    } else {
//                                        retList.add(goodsMarketing);
//                                    }
//                                } else {
//                                    delIds.add(cartVO.getGoodsInfoId());
//                                }
//                            } else {
//                                delIds.add(goodsMarketing.getGoodsInfoId());
//                            }
//                        }
//                    });
//        } else if (CollectionUtils.isEmpty(goodsMarketingList)
//                && MapUtils.isNotEmpty(goodsInfoCartVOMap)) {
//            goodsInfoCartVOMap.forEach(
//                    (skuId, cartVO) -> {
//                        List<MarketingPluginLabelDetailVO> pluginLabelDetailVOS =
//                                cartVO.getMarketingPluginLabels();
//                        if (CollectionUtils.isNotEmpty(pluginLabelDetailVOS)) {
//                            List<MarketingPluginLabelDetailVO> tempList =
//                                    pluginLabelDetailVOS.stream()
//                                            .filter(
//                                                    p ->
//                                                            CHOOSE_MARKETING_TYPE.contains(
//                                                                    p.getMarketingType()))
//                                            .collect(Collectors.toList());
//                            if (CollectionUtils.isNotEmpty(tempList)) {
//
//                                GoodsMarketingVO goodsMarketingVO = new GoodsMarketingVO();
//                                //按照营销类型排序
//                                tempList.sort(Comparator.comparing(MarketingPluginLabelDetailVO::getMarketingType));
//                                goodsMarketingVO.setMarketingId(
//                                        ((Number) tempList.get(0).getMarketingId()).longValue());
//                                goodsMarketingVO.setGoodsInfoId(cartVO.getGoodsInfoId());
//                                goodsMarketingVO.setCustomerId(customerId);
//                                addList.add(goodsMarketingVO);
//                                retList.add(goodsMarketingVO);
//                            }
//                        }
//                    });
//        } else if (CollectionUtils.isNotEmpty(goodsMarketingList)
//                && MapUtils.isEmpty(goodsInfoCartVOMap)) {
//            goodsMarketingList.forEach(
//                    goodsMarketing -> {
//                        delIds.add(goodsMarketing.getGoodsInfoId());
//                    });
//        }
//        // 同步会员 营销活动信息
//        if(StringUtils.isNotBlank(customerId)) {
//            goodsMarketingProvider.sync(
//                    GoodsMarketingSyncRequest.builder()
//                            .goodsMarketings(addList)
//                            .delSkuIds(new ArrayList<String>(delIds))
//                            .customerId(customerId)
//                            .build());
//        }
//
//        return retList;
//    }

    /**
    *  处理购物车商品 选择的营销活动
     * 如果商品中的活动信息存在表 goods_marketing 则直接返回该活动，如果不存则需要删除此商品关联的活动并按照优先级排序重新加入到表 goods_marketing
     * 如果商品没有活动数据 而表 goods_marketing中存在则需要删除表中数据
     * @description
     * @author  wur
     * @date: 2022/2/10 11:02
     * @param goodsInfoCartList
     * @param customerId
     * @param sceneType 场景：0购物车，1快速下单
     **/
    @Override
    public List<GoodsMarketingVO> setGoodsMarketing(List<GoodsInfoCartVO> goodsInfoCartList,String customerId, Integer sceneType) {

        List<GoodsMarketingVO> retList = new ArrayList<>();
        List<GoodsMarketingVO> addList = new ArrayList<>();
        Set<String> delIds = new HashSet<>();
        //查询用户已选择优惠活动
        Map<String, GoodsMarketingVO> goodsMarketingMap = null;
        if (StringUtils.isNotBlank(customerId) && !Integer.valueOf(1).equals(sceneType)) {
            GoodsMarketingListByCustomerIdRequest request = new GoodsMarketingListByCustomerIdRequest();
            request.setCustomerId(customerId);
            List<GoodsMarketingVO> goodsMarketingList =
                    goodsMarketingQueryProvider
                            .listByCustomerId(request)
                            .getContext()
                            .getGoodsMarketings();
            if (CollectionUtils.isNotEmpty(goodsMarketingList)) {
                goodsMarketingMap = goodsMarketingList.stream().collect(
                        Collectors.toMap( GoodsMarketingVO::getGoodsInfoId, a -> a, (k1, k2) -> k1));
            }
        }
        //只处理有效或缺货的商品
        Map<String, GoodsMarketingVO> finalGoodsMarketingMap = goodsMarketingMap;
        goodsInfoCartList.stream()
                .filter(g -> g.getGoodsStatus() == null
                                        || g.getGoodsStatus().equals(GoodsStatus.OK)
                                        || g.getGoodsStatus().equals(GoodsStatus.OUT_STOCK))
                .forEach(goodsInfoCartVO -> {
                    if(CollectionUtils.isNotEmpty(goodsInfoCartVO.getMarketingPluginLabels())) {
                        List<MarketingPluginLabelDetailVO> tempList =
                                goodsInfoCartVO.getMarketingPluginLabels().stream()
                                        .filter( p -> CHOOSE_MARKETING_TYPE.contains( p.getMarketingType()))
                                        .collect(Collectors.toList());
                        List<Long> marketingIdList = CollectionUtils.isNotEmpty(tempList) ?
                                tempList.stream().map(MarketingPluginLabelDetailVO :: getMarketingId).map(t -> ((Number) t).longValue()).collect(Collectors.toList())
                                : new ArrayList<>();

                        Boolean needAdd = false;
                        if (MapUtils.isNotEmpty(finalGoodsMarketingMap) && finalGoodsMarketingMap.containsKey(goodsInfoCartVO.getGoodsInfoId())) {
                            //验证绑定活动是否存在
                            if(marketingIdList.contains(finalGoodsMarketingMap.get(goodsInfoCartVO.getGoodsInfoId()).getMarketingId())) {
                                retList.add(finalGoodsMarketingMap.get(goodsInfoCartVO.getGoodsInfoId()));
                            } else {
                                needAdd = true;
                                delIds.add(goodsInfoCartVO.getGoodsInfoId());
                            }
                        } else {
                            needAdd = true;
                        }
                        // 加价购
                        tempList.stream().filter(g -> g.getMarketingType() == MarketingType.PREFERENTIAL.toValue()).findFirst().ifPresent(g-> {
                            retList.add(GoodsMarketingVO.builder()
                                    .marketingId(Long.parseLong(g.getMarketingId().toString()))
                                    .goodsInfoId(goodsInfoCartVO.getGoodsInfoId())
                                    .customerId(customerId)
                                    .id("-1")
                                    .build());
                        });
                        if (needAdd) {
                            //按照 营销活动优先级排序取第一个
                            List<MarketingPluginLabelDetailVO> pluginLabelDetailVOS =
                                    goodsInfoCartVO.getMarketingPluginLabels();
                            if (CollectionUtils.isNotEmpty(pluginLabelDetailVOS)) {
                                List<MarketingPluginLabelDetailVO> preferentialTempList =
                                        tempList.stream().filter(g -> Objects.equals(MarketingPluginType.PREFERENTIAL.getId()
                                        , g.getMarketingType())).collect(Collectors.toList());

                                List<MarketingPluginLabelDetailVO> otherTempList =
                                tempList.stream().filter(g -> !Objects.equals(MarketingPluginType.PREFERENTIAL.getId()
                                        , g.getMarketingType())).collect(Collectors.toList());
                                if (CollectionUtils.isNotEmpty(otherTempList)) {
                                    GoodsMarketingVO goodsMarketingVO = new GoodsMarketingVO();
                                    //按照营销类型排序
                                    tempList.sort(Comparator.comparing(MarketingPluginLabelDetailVO::getMarketingType));
                                    goodsMarketingVO.setMarketingId(
                                            ((Number) tempList.get(0).getMarketingId()).longValue());
                                    goodsMarketingVO.setGoodsInfoId(goodsInfoCartVO.getGoodsInfoId());
                                    goodsMarketingVO.setCustomerId(customerId);
                                    addList.add(goodsMarketingVO);
                                    retList.add(goodsMarketingVO);
                                } else if(CollectionUtils.isNotEmpty(preferentialTempList)){
                                    GoodsMarketingVO goodsMarketingVO = new GoodsMarketingVO();
                                    //按照营销类型排序
                                    goodsMarketingVO.setMarketingId(
                                            ((Number) preferentialTempList.get(0).getMarketingId()).longValue());
                                    goodsMarketingVO.setGoodsInfoId(goodsInfoCartVO.getGoodsInfoId());
                                    goodsMarketingVO.setCustomerId(customerId);
                                    goodsMarketingVO.setId("-1");
                                    retList.add(goodsMarketingVO);
                                }
                            }
                        }
                    } else if (MapUtils.isNotEmpty(finalGoodsMarketingMap) && finalGoodsMarketingMap.containsKey(goodsInfoCartVO.getGoodsInfoId())){
                        delIds.add(goodsInfoCartVO.getGoodsInfoId());
                    }
        });

        // 同步会员 营销活动信息
        if(!Integer.valueOf(1).equals(sceneType) && StringUtils.isNotBlank(customerId) && (CollectionUtils.isNotEmpty(addList) || CollectionUtils.isNotEmpty(delIds))) {
            goodsMarketingProvider.sync(
                    GoodsMarketingSyncRequest.builder()
                            .goodsMarketings(addList)
                            .delSkuIds(new ArrayList<String>(delIds))
                            .customerId(customerId)
                            .build());
        }

        return retList;
    }

    @Override
    public List<GoodsInfoCartVO> setBuyCount(
            List<GoodsInfoCartVO> goodsInfoCartVOList, List<Purchase> purchaseList) {
        Map<String, Purchase> purchaseMap =
                purchaseList.stream()
                        .collect(
                                Collectors.toMap(Purchase::getGoodsInfoId, a -> a, (k1, k2) -> k1));
        goodsInfoCartVOList.forEach(
                goodsInfoCartVO -> {
                    // 设置购买数量
                    Purchase purchase = purchaseMap.get(goodsInfoCartVO.getGoodsInfoId());
                    if (purchase != null) {
                        goodsInfoCartVO.setBuyCount(purchase.getGoodsNum());
                        if (goodsInfoCartVO.getStock() != null
                                && goodsInfoCartVO.getStock() < purchase.getGoodsNum()) {
                            goodsInfoCartVO.setBuyCount(goodsInfoCartVO.getStock());
                        }
//                        if (goodsInfoCartVO.getCount() == null
//                                || goodsInfoCartVO.getCount() == 0
//                                || goodsInfoCartVO.getCount() < purchase.getGoodsNum()) {
//                            goodsInfoCartVO.setBuyCount(purchase.getGoodsNum());
//                        } else {
//                            goodsInfoCartVO.setBuyCount(goodsInfoCartVO.getCount());
//                        }
//                        if (goodsInfoCartVO.getMaxCount() != null
//                                && goodsInfoCartVO.getMaxCount() != 0
//                                && purchase.getGoodsNum() > goodsInfoCartVO.getMaxCount()) {
//                            goodsInfoCartVO.setBuyPoint(goodsInfoCartVO.getMaxCount());
//                        }

                        goodsInfoCartVO.setSortTime(purchase.getCreateTime());
                    }

                });
        //按照订单行更新时间倒序
        return goodsInfoCartVOList.stream().sorted(Comparator.comparing(GoodsInfoCartVO::getSortTime).reversed()).collect(Collectors.toList());
    }

    /***
     * 设置店铺信息
     * @param goodsInfoCartList
     * @param purchaseList
     * @return
     */
    @Override
    public List<StoreVO> getStoreInfo(List<GoodsInfoCartVO> goodsInfoCartList, List<Purchase> purchaseList) {
        List<Long> storeIds =
                goodsInfoCartList.stream()
                        .map(GoodsInfoCartVO::getStoreId)
                        .collect(Collectors.toList());
        List<StoreVO> storeVOS = tradeCacheService.queryStoreList(storeIds);
        //购物车数据不为空则按照购加入购物车倒序
        if (CollectionUtils.isNotEmpty(purchaseList)) {
            purchaseList = purchaseList.stream().sorted(Comparator.comparing(Purchase::getCreateTime).reversed()).collect(Collectors.toList());
            List<Long> sortStoreIds = purchaseList.stream().map(Purchase::getStoreId).distinct().collect(Collectors.toList());
            storeVOS = storeVOS.stream().sorted(Comparator.comparingInt(a -> sortStoreIds.indexOf(a.getStoreId()))).collect(Collectors.toList());
        } else {
            //按照商品顺序
            List<Long> sortStoreIds = goodsInfoCartList.stream().map(GoodsInfoCartVO::getStoreId).distinct().collect(Collectors.toList());
            storeVOS = storeVOS.stream().sorted(Comparator.comparingInt(a -> sortStoreIds.indexOf(a.getStoreId()))).collect(Collectors.toList());
        }
        return storeVOS;
    }

    @Override
    public List<GoodsInfoCartVO> afterProcess(List<GoodsInfoCartVO> goodsInfoCartVOList, CustomerVO customer) {
        //用户没登陆不返回企业价信息
        if(Objects.isNull(customer)
                || Objects.isNull(customer.getEnterpriseCheckState())
                || EnterpriseCheckState.CHECKED.toValue() != customer.getEnterpriseCheckState().toValue()) {
            goodsInfoCartVOList.stream().filter(goodsInfo-> EnterpriseAuditState.CHECKED.equals(goodsInfo.getEnterPriseAuditState()))
                    .forEach(goodsInfo-> {
                        goodsInfo.setEnterPrisePrice(null);
                        goodsInfo.setEnterPriseAuditState(EnterpriseAuditState.INIT);
                        if (CollectionUtils.isNotEmpty(goodsInfo.getMarketingPluginLabels())) {
                            List<MarketingPluginLabelDetailVO> removePluginLabels = goodsInfo.getMarketingPluginLabels().stream().filter(label-> MarketingPluginType.ENTERPRISE_PRICE.getId() == label.getMarketingType()).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(removePluginLabels)) {
                                goodsInfo.getMarketingPluginLabels().removeAll(removePluginLabels);
                            }
                        }
                    });
        }
        //处理秒杀、限时抢购 商品的分销佣金
        goodsInfoCartVOList.stream().filter(goodsInfo-> DistributionGoodsAudit.CHECKED.equals(goodsInfo.getDistributionGoodsAudit()) &&
                Objects.nonNull(goodsInfo.getDistributionCommission())).forEach(goodsInfo->{
                    if (CollectionUtils.isNotEmpty(goodsInfo.getMarketingPluginLabels())) {
                        List<MarketingPluginLabelDetailVO> marketingPluginLabels = goodsInfo.getMarketingPluginLabels().stream()
                                .filter(label-> MarketingPluginType.FLASH_SALE.getId() == label.getMarketingType()
                                        || MarketingPluginType.FLASH_PROMOTION.getId() == label.getMarketingType()).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(marketingPluginLabels)) {
                            goodsInfo.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                            goodsInfo.setDistributionCommission(null);
                        }
                    }
        });
        return goodsInfoCartVOList;
    }

    @Override
    public List<MiniStoreVO> perfectStore(List<GoodsInfoCartVO> goodsInfoCartVOList, List<MiniStoreVO> storeVOList) {
        //商品按照商家分组
        Map<Long, List<GoodsInfoCartVO>> goodsInfoMap = goodsInfoCartVOList.stream().collect(Collectors.groupingBy(GoodsInfoCartVO::getStoreId));
        //处理商家的商品和优惠券标识
        storeVOList.forEach(miniStoreVO -> {
            miniStoreVO.setIsStoreCoupon(Boolean.FALSE);
            if (goodsInfoMap.containsKey(miniStoreVO.getStoreId())) {
                List<GoodsInfoCartVO> goodsInfoCartVOS = goodsInfoMap.get(miniStoreVO.getStoreId());
                //封装GoodsId 前端查询优惠券活动传参接口使用
                miniStoreVO.setGoodsIds(goodsInfoCartVOS.stream().map(GoodsInfoCartVO::getGoodsId).distinct().collect(Collectors.toList()));
                miniStoreVO.setGoodsInfoIds(goodsInfoCartVOS.stream().map(GoodsInfoCartVO::getGoodsInfoId).collect(Collectors.toList()));
                goodsInfoCartVOS.forEach(goodsInfoCartVO -> {
                    if (CollectionUtils.isNotEmpty(goodsInfoCartVO.getMarketingPluginLabels())) {
                        goodsInfoCartVO.getMarketingPluginLabels().forEach(marketingPluginLabelDetailVO -> {
                            if (MarketingPluginType.COUPON.getId() == marketingPluginLabelDetailVO.getMarketingType()) {
                                //标识商家有优惠券活动
                                miniStoreVO.setIsStoreCoupon(Boolean.TRUE);
                            }
                        });
                    }
                });
            }
        });
        return storeVOList;
    }

    /**
     * 填充运费模板
     *
     * @param goodsInfoCartVOList 商品信息
     * @param storeVOS 店铺信息
     * @param address 收货地址
     * @return
     */
    @Override
    public List<FreightCartVO> setFreight(
            List<GoodsInfoCartVO> goodsInfoCartVOList,
            List<StoreVO> storeVOS,
            PlatformAddress address) {
        if (Objects.isNull(address)
                || StringUtils.isBlank(address.getProvinceId())
                || StringUtils.isBlank(address.getCityId())) {
            return Collections.emptyList();
        }

        // 需要排除非实体商品
        goodsInfoCartVOList =
                goodsInfoCartVOList.stream()
                        .filter(goodsInfoCartVO -> 0 == goodsInfoCartVO.getGoodsType())
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsInfoCartVOList)) {
            return Collections.emptyList();
        }
        CartListResponse response =
                freightProvider
                        .cartList(
                                CartListRequest.builder()
                                        .goodsInfoCartVOList(goodsInfoCartVOList)
                                        .address(address)
                                        .storeVOS(storeVOS)
                                        .build())
                        .getContext();
        return response.getFreightCateVOList();
    }

    @Override
    public void populateReductionPrice(List<GoodsInfoCartVO> goodsInfoCartVOList, List<Purchase> purchaseList, CustomerVO customer ) {
        //购物车信息转化为goodsInfoId为key，FirstPurchasePrice为value的map
        Map<String, BigDecimal> firstPriceMap = purchaseList.stream().collect(Collectors.toMap(Purchase::getGoodsInfoId, b->{
            BigDecimal firstPurchasePrice = b.getFirstPurchasePrice();
            if (Objects.isNull(firstPurchasePrice)){
                firstPurchasePrice = BigDecimal.ZERO;
            }
            return firstPurchasePrice;
        }));

        for (GoodsInfoCartVO goodsInfoCartVO : goodsInfoCartVOList){

            //只处理零售
            Integer saleType = goodsInfoCartVO.getSaleType();
            if (saleType != Constants.ONE){
                continue;
            }

            String goodsInfoId = goodsInfoCartVO.getGoodsInfoId();
            //第一次加入购物车的价格
            BigDecimal firstPrice = firstPriceMap.get(goodsInfoId);

            //1.第一次加入购物车或者不是在列表和详情加入的商品
            //如果购物车中第一次加入购物车中的价格为空，差价为零
            if (firstPrice.compareTo(BigDecimal.ZERO) == Constants.ZERO){
                goodsInfoCartVO.setReductionPrice(BigDecimal.ZERO);
                continue;
            }
            //2.加入购物车时价格为会员等级价/会员价/企业会员价
            List<MarketingPluginLabelDetailVO> marketingPluginLabels = goodsInfoCartVO.getMarketingPluginLabels();
            if (CollectionUtils.isNotEmpty(marketingPluginLabels)){
                //过滤出会员等级，会员，企业会员营销
                MarketingPluginLabelDetailVO marketingPluginLabelDetailVO = marketingPluginLabels.stream().filter(marketingPlugin ->
                        marketingPlugin.getMarketingType() == MarketingPluginType.CUSTOMER_LEVEL.getId() ||
                                marketingPlugin.getMarketingType() == MarketingPluginType.CUSTOMER_PRICE.getId() ||
                                marketingPlugin.getMarketingType() == MarketingPluginType.ENTERPRISE_PRICE.getId() ||
                                marketingPlugin.getMarketingType() == MarketingPluginType.PAYING_MEMBER.getId()).findFirst().orElse(null);
                if (Objects.nonNull(marketingPluginLabelDetailVO)){
                    //获取会员等级价/会员价/企业会员价
                    BigDecimal pluginPrice = marketingPluginLabelDetailVO.getPluginPrice();
                    //降价金额 = 第一次加入的价格 - 企业会员价格
                    BigDecimal reductionPrice = firstPrice.subtract(pluginPrice);
                    //如果涨价则不展示
                    reductionPrice = reductionPrice.compareTo(BigDecimal.ZERO) > 0 ? reductionPrice : BigDecimal.ZERO;
                    goodsInfoCartVO.setReductionPrice(reductionPrice);
                    continue;
                }
            }

            //3.加入购物车时价格为市场价
            BigDecimal marketPrice = goodsInfoCartVO.getMarketPrice();
            BigDecimal reductionPrice = firstPrice.subtract(marketPrice);
            reductionPrice = reductionPrice.compareTo(BigDecimal.ZERO) > 0 ? reductionPrice : BigDecimal.ZERO;
            goodsInfoCartVO.setReductionPrice(reductionPrice);
        }
    }

    private List<GoodsInfoCartVO> setStatus(
            List<GoodsInfoCartVO> goodsInfoList,
            Map<Long, StoreVO> storeMap,
            DefaultFlag distributionFlag,
            boolean isGoodsPoint,CustomerVO customer, String inviteeId) {
        LocalDateTime now = LocalDateTime.now();
        List<String> distributionIds = new ArrayList<>();
        Map<Long, DefaultFlag> storeDistributionMap = new HashMap<>();
        if (distributionFlag.equals(DefaultFlag.YES)) {

            storeDistributionMap.putAll(
                    distributionCacheService.queryStoreListOpenFlag(
                            new ArrayList<>(storeMap.keySet())));
        }
        goodsInfoList.forEach(
                g -> {
                    StoreVO store = storeMap.get(g.getStoreId());
                    if (store == null
                            || Objects.equals(DeleteFlag.YES, store.getDelFlag())
                            || Objects.equals(StoreState.CLOSED, store.getStoreState())
                            || now.isAfter(store.getContractEndDate())
                            || now.isBefore(store.getContractStartDate())
                            || DeleteFlag.YES.equals(g.getDelFlag())) {
                        g.setGoodsStatus(GoodsStatus.INVALID);
                    }

                    if (distributionFlag.equals(DefaultFlag.NO)
                            || storeDistributionMap.get(g.getStoreId()) == null
                            || storeDistributionMap.get(g.getStoreId()).equals(DefaultFlag.NO)) {
                        g.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                    } else if (g.getDistributionGoodsAudit()
                            .equals(DistributionGoodsAudit.CHECKED)) {
                        distributionIds.add(g.getGoodsInfoId());
                    }
                    if (!isGoodsPoint) {
                        g.setBuyPoint(0L);
                    }
                    /** 如果会员是非企业会员设置商品企业状态为非企业购商品 */
//                    if (customer != null
//                            && !customer.getEnterpriseCheckState()
//                                    .equals(EnterpriseCheckState.CHECKED)) {
//                        g.setEnterPriseAuditState(EnterpriseAuditState.INIT);
//                    }
                });

        // 设置分销等级价
        if (distributionFlag.equals(DefaultFlag.YES)
                && org.apache.commons.collections.CollectionUtils.isNotEmpty(distributionIds)) {

            BaseResponse<DistributorLevelByCustomerIdResponse> baseResponse = customer == null ? null :
                    distributorLevelQueryProvider.getByCustomerId(
                            new DistributorLevelByCustomerIdRequest(customer.getCustomerId()));
            goodsInfoList.stream().filter(g -> DistributionGoodsAudit.CHECKED == g.getDistributionGoodsAudit()).forEach(
                    g -> {
                        //不是分销员则佣金为：0
                        if ((StringUtils.isBlank(inviteeId) || Constants.PURCHASE_DEFAULT.equals(inviteeId))
                                && Objects.nonNull(baseResponse)
                                && Objects.nonNull(baseResponse.getContext().getDistributorLevelVO())
                                && DefaultFlag.NO.toValue() == baseResponse.getContext().getDistributorLevelVO().getForbiddenFlag().toValue()) {
                            DistributorLevelVO distributorLevelVO =
                                    baseResponse.getContext().getDistributorLevelVO();
                            if ( Objects.nonNull(g.getDistributionCommission())
                                    && Objects.nonNull(distributorLevelVO.getCommissionRate())) {
                                BigDecimal commissionRate = distributorLevelVO.getCommissionRate();
                                BigDecimal distributionCommission = g.getDistributionCommission();
                                distributionCommission =
                                        DistributionCommissionUtils.calDistributionCommission(
                                                distributionCommission, commissionRate);
                                g.setDistributionCommission(distributionCommission);
                            }
                        } else {
                            g.setDistributionCommission(null);
                        }
                    });
        }
        return goodsInfoList;
    }
}
