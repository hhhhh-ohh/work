package com.wanmi.sbc.marketing.newplugin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelCustomerRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelMapGetResponse;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberDiscountRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsCustomerPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsLevelPriceQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoListRequest;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsListRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsCustomerPriceBySkuIdsAndCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsLevelPriceBySkuIdsAndLevelIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.api.response.price.GoodsCustomerPriceBySkuIdsAndCustomerIdResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.dto.MarketingPluginConfigDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import com.wanmi.sbc.marketing.common.request.MarketingRequest;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.common.service.MarketingService;
import com.wanmi.sbc.marketing.common.service.MarketingServiceInterface;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.service.CouponCacheService;
import com.wanmi.sbc.marketing.coupon.service.CouponCacheServiceInterface;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.service.NewcomerPurchaseConfigService;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.service.NewcomerPurchaseCouponService;
import com.wanmi.sbc.marketing.newplugin.bean.MarketingPluginBaseParam;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.service.SkuCacheMarketingService;
import com.wanmi.sbc.marketing.util.mapper.MarketingGoodsInfoMapper;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className MarketingPluginBaseService
 * @description
 * @date 2021/6/29 13:57
 */
@Slf4j
@Service
public class MarketingPluginBaseService {

    @Autowired MarketingService marketingService;

    @Autowired private MarketingServiceInterface marketingServiceInterface;

    @Autowired StoreCateQueryProvider storeCateQueryProvider;

    @Autowired CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired SkuCacheMarketingService skuCacheMarketingService;

    @Autowired CouponCacheService couponCacheService;

    @Autowired
    private CouponCacheServiceInterface couponCacheServiceInterface;

    @Autowired MarketingGoodsInfoMapper marketingGoodsInfoMapper;

    @Autowired GoodsLevelPriceQueryProvider goodsLevelPriceQueryProvider;

    @Autowired GoodsCustomerPriceQueryProvider goodsCustomerPriceQueryProvider;

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private NewcomerPurchaseCouponService newcomerPurchaseCouponService;

    @Autowired
    private NewcomerPurchaseGoodsQueryProvider newcomerPurchaseGoodsQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;
    @Autowired
    private NewcomerPurchaseConfigService newcomerPurchaseConfigService;

    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;
    /**
     * 设置上下文
     * @param goodsInfoSimpleVOList
     * @param customerId
     * @param isStart
     * @param pluginConfigList
     * @param storeId
     */
    public void setBaseRequest(
            List<GoodsInfoSimpleVO> goodsInfoSimpleVOList,
            String customerId,
            boolean isStart,
            List<MarketingPluginConfigDTO> pluginConfigList,
            Long storeId) {
        // 兜底如果没有设置storeCateId则重新查询设置
        goodsInfoSimpleVOList = setGoodsStoreCateIdList(goodsInfoSimpleVOList);
        List<MarketingGoods> marketingGoods =
                marketingGoodsInfoMapper.goodsInfoPluginRequestListToMarketingGoodsList(
                        goodsInfoSimpleVOList);
        List<String> goodsInfoIds = new ArrayList<>();
        List<Long> storeIds = new ArrayList<>();

        marketingGoods
                .forEach(
                        mg -> {
                            goodsInfoIds.add(mg.getGoodsInfoId());
                            storeIds.add(mg.getStoreId());
                        });
        Map<Long, CommonLevelVO> customerLevel = getCustomerLevels(storeIds, customerId);
        MarketingPluginBaseParam baseRequest =
                MarketingPluginBaseParam.builder().levelMap(customerLevel)
                        .payingMemberLevel(getPayingMemberLevel(customerId)).build();
        MarketingContext.setBaseRequest(baseRequest);

        // 如果参数为空，直接返回
        if (WmCollectionUtils.isEmpty(pluginConfigList)) {
            return;
        }

        // 转为Set
        Set<MarketingPluginType> pluginConfigTypeSet =
                pluginConfigList.stream()
                        .map(MarketingPluginConfigDTO::getMarketingType)
                        .collect(Collectors.toSet());


        if (pluginConfigTypeSet.contains(MarketingPluginType.COUPON)) {
            List<CouponCache> couponCaches = getCouponCache(marketingGoods, customerLevel, storeId);
            // 筛选出还有剩余的券
            couponCaches = couponCaches.stream().filter(coupon -> DefaultFlag.YES == coupon.getHasLeft()).collect(Collectors.toList());
            baseRequest.setCouponCaches(couponCaches);
        }
        if (!Collections.disjoint(MarketingContext.SKU_TYPE_MAP, pluginConfigTypeSet) && StringUtils.isNotBlank(customerId)) {
            baseRequest.setSkuMarketingMap(
                    skuCacheMarketingService.getSkuCacheMarketing(goodsInfoIds, isStart));
        }

        if (!Collections.disjoint(MarketingContext.MULTI_TYPE_MAP, pluginConfigTypeSet) && StringUtils.isNotBlank(customerId)) {
            baseRequest.setMultiTypeMarketingMap(
                    getMultiTypeMarketingMapFast(marketingGoods, storeId));
        }

        if (pluginConfigTypeSet.contains(MarketingPluginType.CUSTOMER_LEVEL)) {
            Map<MarketingPluginType, Map<String, GoodsInfoDetailPluginResponse>> map =
                    Optional.ofNullable(baseRequest.getCustomerPriceMap())
                            .orElseGet(Maps::newHashMap);
            map.put(MarketingPluginType.CUSTOMER_LEVEL, setPrice(goodsInfoSimpleVOList));
            baseRequest.setCustomerPriceMap(map);
        }

        if (pluginConfigTypeSet.contains(MarketingPluginType.PAYING_MEMBER)) {
            Map<MarketingPluginType, Map<String, GoodsInfoDetailPluginResponse>> map =
                    Optional.ofNullable(baseRequest.getCustomerPriceMap())
                            .orElseGet(Maps::newHashMap);
            map.put(MarketingPluginType.PAYING_MEMBER, setPayingMemberPrice(goodsInfoSimpleVOList));
            baseRequest.setCustomerPriceMap(map);
        }

        if (pluginConfigTypeSet.contains(MarketingPluginType.CUSTOMER_PRICE)) {
            Map<MarketingPluginType, Map<String, GoodsInfoDetailPluginResponse>> map =
                    Optional.ofNullable(baseRequest.getCustomerPriceMap())
                            .orElseGet(Maps::newHashMap);
            map.put(
                    MarketingPluginType.CUSTOMER_PRICE,
                    setPrice(goodsInfoSimpleVOList, customerId));

            baseRequest.setCustomerPriceMap(map);
        }

        CustomerVO customerVO = this.getCustomerNoThirdImgById(customerId);
        baseRequest.setCustomerVO(customerVO);
        if (pluginConfigTypeSet.contains(MarketingPluginType.NEW_COMER_COUPON)) {
            Map<String, List<CouponInfoVO>> newComerCoupon = getNewComerCoupon(goodsInfoSimpleVOList, customerVO);
            baseRequest.setNewComerMap(newComerCoupon);
        }



        MarketingContext.setBaseRequest(baseRequest);
    }


    /**
     * 返回当前商品是不是包含对应的营销活动
     *
     * @param marketingGoods 商品信息
     * @param customerId 会员id
     * @param marketingPluginType 营销插件类型
     * @param isStart 是否显示未开始的营销活动
     * @return true-包含，false-不包含
     */
    public boolean checkBaseRequest(
            MarketingGoods marketingGoods,
            String customerId,
            MarketingPluginType marketingPluginType,
            boolean isStart) {
        return checkBaseRequest(marketingGoods, customerId, marketingPluginType, isStart, null);
    }

    /**
     * 返回当前商品是不是包含对应的营销活动
     *
     * @param marketingGoods 商品信息
     * @param customerId 会员id
     * @param marketingPluginType 营销插件类型
     * @param isStart 是否显示未开始的营销活动
     * @return true-包含，false-不包含
     */
    public boolean checkBaseRequest(
            MarketingGoods marketingGoods,
            String customerId,
            MarketingPluginType marketingPluginType,
            boolean isStart,
            Long storeId) {

        String id = marketingGoods.getGoodsInfoId();
        if (MarketingContext.getBaseRequest() == null) {
            MarketingPluginBaseParam baseRequest = new MarketingPluginBaseParam();
            MarketingContext.setBaseRequest(baseRequest);
        }

        // 设置默认值
        if (MarketingContext.getBaseRequest().getLevelMap() == null) {
            MarketingContext.getBaseRequest()
                    .setLevelMap(
                            getCustomerLevels(
                                    Collections.singletonList(marketingGoods.getStoreId()),
                                    customerId));
        }

        //设置付费会员等级
        if (MarketingContext.getBaseRequest().getPayingMemberLevel() == null) {
            MarketingContext.getBaseRequest().setPayingMemberLevel(getPayingMemberLevel(customerId));
        }

        // 校验是不是存在对应skuTypeMap的营销
        if (MarketingContext.SKU_TYPE_MAP.contains(marketingPluginType)) {
            if (StringUtils.isBlank(customerId) || !skuTypeMap(marketingGoods, customerId, marketingPluginType, isStart, id)) {
                return false;
            }
        }
        // 校验是不是存在对应MulitTypeMap的营销
        if (MarketingContext.MULTI_TYPE_MAP.contains(marketingPluginType)) {
            if (StringUtils.isBlank(customerId) || !checkMultiTypeMap(marketingGoods, marketingPluginType, id, storeId)) {
                return false;
            }
        }

        //校验虚拟商品是不是存在对应virtualTypeMap限制的营销
        if (MarketingContext.VIRTUAL_TYPE_MAP.contains(marketingPluginType)
                && Objects.nonNull(marketingGoods.getGoodsType())
                && GoodsType.VIRTUAL_GOODS.toValue() == marketingGoods.getGoodsType()) {
            return false;
        }

        //校验卡券商品是不是存在对应virtualTypeMap限制的营销
        if (MarketingContext.ELECTRONIC_TYPE_MAP.contains(marketingPluginType)
                && Objects.nonNull(marketingGoods.getGoodsType())
                && GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == marketingGoods.getGoodsType()) {
            return false;
        }
        return true;
    }

    /**
     * 获取满系营销
     *
     * @param marketingGoods
     * @return
     */
    public Map<String, List<MarketingResponse>> getMultiTypeMarketingMap(
            List<MarketingGoods> marketingGoods, Long storeId) {

        Map<String, List<StoreCateGoodsRelaVO>> storeCateMap =
                storeCateQueryProvider
                        .listByGoods(
                                new StoreCateListByGoodsRequest(
                                        WmCollectionUtils.convert(
                                                marketingGoods, MarketingGoods::getGoodsId)))
                        .getContext()
                        .getStoreCateGoodsRelaVOList()
                        .stream()
                        .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));
        marketingGoods.stream()
                .forEach(
                        mg -> {
                            List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOS =
                                    storeCateMap.get(mg.getGoodsId());
                            if (CollectionUtils.isNotEmpty(storeCateGoodsRelaVOS)) {
                                mg.setStoreCateIds(
                                        storeCateGoodsRelaVOS.stream()
                                                .map(StoreCateGoodsRelaVO::getStoreCateId)
                                                .collect(Collectors.toList()));
                            } else {
                                mg.setStoreCateIds(new ArrayList<>());
                            }
                        });
        MarketingRequest marketingRequest =
                MarketingRequest.builder()
                        .deleteFlag(DeleteFlag.NO)
                        .cascadeLevel(Boolean.TRUE)
                        .marketingStatus(MarketingStatus.STARTED)
                        .build();
        marketingRequest.setMarketingGoods(marketingGoods);
        if (Objects.nonNull(storeId)) {
            marketingRequest.setPluginType(PluginType.O2O);
            marketingRequest.setStoreIds(Lists.newArrayList(storeId));
        } else {
            marketingRequest.setPluginType(PluginType.NORMAL);
        }
        // 查询正在进行中的有效营销信息
        Map<String, List<MarketingResponse>> marketingMap =
                marketingServiceInterface.getMarketingMapByGoodsId(marketingRequest);

        return marketingMap;
    }

    /**
     * 获取满系营销
     *
     * @param marketingGoods
     * @return
     */
    public Map<String, List<MarketingResponse>> getMultiTypeMarketingMapFast(
            List<MarketingGoods> marketingGoods, Long storeId) {

        MarketingRequest marketingRequest =
                MarketingRequest.builder()
                        .deleteFlag(DeleteFlag.NO)
                        .cascadeLevel(Boolean.TRUE)
                        .marketingStatus(MarketingStatus.STARTED)
                        .build();
        marketingRequest.setMarketingGoods(marketingGoods);
        if (Objects.nonNull(storeId)) {
            marketingRequest.setPluginType(PluginType.O2O);
            marketingRequest.setStoreIds(Lists.newArrayList(storeId));
        } else {
            marketingRequest.setPluginType(PluginType.NORMAL);
        }
        // 查询正在进行中的有效营销信息
        Map<String, List<MarketingResponse>> marketingMap =
                marketingServiceInterface.getMarketingMapByGoodsId(marketingRequest);

        return marketingMap;
    }

    /**
     * 获取会员等级
     *
     * @param storeIds 店铺id
     * @param customerId 客户
     */
    public Map<Long, CommonLevelVO> getCustomerLevels(List<Long> storeIds, String customerId) {

        if (StringUtils.isBlank(customerId) || CollectionUtils.isEmpty(storeIds)) {
            return new HashMap<>();
        }
        CustomerLevelMapByCustomerIdAndStoreIdsRequest
                customerLevelMapByCustomerIdAndStoreIdsRequest =
                        new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
        customerLevelMapByCustomerIdAndStoreIdsRequest.setCustomerId(customerId);
        customerLevelMapByCustomerIdAndStoreIdsRequest.setStoreIds(storeIds);
        BaseResponse<CustomerLevelMapGetResponse> customerLevelMapGetResponseBaseResponse =
                customerLevelQueryProvider.listCustomerLevelMapByCustomerIdAndIdsByDefault(
                        customerLevelMapByCustomerIdAndStoreIdsRequest);
        return customerLevelMapGetResponseBaseResponse.getContext().getCommonLevelVOMap();
    }

    /***
     * 获得优惠券缓存
     * @param marketingGoodsList    营销商品集合
     * @param levelVOMap            级别VOMap
     * @param storeId               门店ID，SBC模式下为null
     * @return
     */
    public List<CouponCache> getCouponCache(
            List<MarketingGoods> marketingGoodsList, Map<Long, CommonLevelVO> levelVOMap, Long storeId) {
        return couponCacheServiceInterface.listCouponForGoodsList(marketingGoodsList,
                levelVOMap, storeId, Objects.isNull(storeId) ? PluginType.NORMAL : PluginType.O2O);
    }

    /**
     * 公共私有方法-处理等级价/客户价
     *
     * @param list
     */
    public Map<String, GoodsInfoDetailPluginResponse> setPrice(List<GoodsInfoSimpleVO> list) {
        Map<String, GoodsInfoDetailPluginResponse> skuResponseMap = new HashMap<>();
        Map<Long, CommonLevelVO> levelMap = MarketingContext.getBaseRequest().getLevelMap();
        List<Long> levels = new ArrayList<>();
        List<String> skuIds = new ArrayList<>();
        for (GoodsInfoSimpleVO goodsInfoPluginRequest : list) {
            if (goodsInfoPluginRequest.getPriceType().equals(0) || (goodsInfoPluginRequest.getPriceType().equals(1)&& goodsInfoPluginRequest.getLevelDiscountFlag().equals(1))) {
                skuIds.add(goodsInfoPluginRequest.getGoodsInfoId());

                CommonLevelVO customerLevel = levelMap.get(goodsInfoPluginRequest.getStoreId());
                if (customerLevel != null && customerLevel.getLevelDiscount() != null) {
                    if(BoolFlag.NO.equals(customerLevel.getLevelType()) && BoolFlag.YES.equals(goodsInfoPluginRequest.getCompanyType())) {
                        levels.add(Constants.GOODS_PLATFORM_LEVEL_ID);
                    } else {
                        levels.add(customerLevel.getLevelId());
                    }
                } else {
                    levels.add(Constants.GOODS_PLATFORM_LEVEL_ID);
                }
            }
        }
        List<GoodsLevelPriceVO> levelPrices = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(skuIds) && CollectionUtils.isNotEmpty(levels)) {
            levelPrices =
                    goodsLevelPriceQueryProvider
                            .listBySkuIdsAndLevelIds(
                                    new GoodsLevelPriceBySkuIdsAndLevelIdsRequest(skuIds, levels))
                            .getContext()
                            .getGoodsLevelPriceList();
        }

        for (GoodsInfoSimpleVO pluginRequest : list) {
            if (pluginRequest.getPriceType() == 0 ||pluginRequest.getLevelDiscountFlag().equals(DeleteFlag.YES.toValue())) {
                GoodsInfoDetailPluginResponse goodsInfoDetailPluginResponse =
                        new GoodsInfoDetailPluginResponse();
                goodsInfoDetailPluginResponse.setGoodsInfoId(pluginRequest.getGoodsInfoId());
                goodsInfoDetailPluginResponse.setGoodsStatus(pluginRequest.getGoodsStatus());
                // salePrice为null，取市场价
                if (Objects.nonNull(pluginRequest.getSalePrice())) {
                    goodsInfoDetailPluginResponse.setPluginPrice(pluginRequest.getSalePrice());
                } else {
                    goodsInfoDetailPluginResponse.setPluginPrice(pluginRequest.getMarketPrice());
                }
                CommonLevelVO customerLevel = levelMap.get(pluginRequest.getStoreId());

                setDefaultPrice(customerLevel, pluginRequest, goodsInfoDetailPluginResponse);
                setLevelPrice(
                        customerLevel, levelPrices, pluginRequest, goodsInfoDetailPluginResponse);
                if (goodsInfoDetailPluginResponse.getPluginPrice()!=null) {
                    skuResponseMap.put(
                            pluginRequest.getGoodsInfoId(), goodsInfoDetailPluginResponse);
                }
            }
        }
        return skuResponseMap;
    }

    /**
     * 设定价格
     *
     * @param list
     * @param customerId
     * @return
     */
    public Map<String, GoodsInfoDetailPluginResponse> setPrice(
            List<GoodsInfoSimpleVO> list, String customerId) {
        Map<String, GoodsInfoDetailPluginResponse> skuResponseMap = new HashMap<>();

        List<String> skuIds =
                list.stream()
                        .filter(goodsInfo -> Constants.yes.equals(goodsInfo.getCustomFlag()))
                        .map(GoodsInfoSimpleVO::getGoodsInfoId)
                        .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(skuIds)) {

            GoodsCustomerPriceBySkuIdsAndCustomerIdResponse
                    goodsCustomerPriceBySkuIdsAndCustomerIdResponse =
                            goodsCustomerPriceQueryProvider
                                    .listBySkuIdsAndCustomerId(
                                            new GoodsCustomerPriceBySkuIdsAndCustomerIdRequest(
                                                    skuIds, customerId))
                                    .getContext();
            if (Objects.nonNull(goodsCustomerPriceBySkuIdsAndCustomerIdResponse)
                    && CollectionUtils.isNotEmpty(
                            goodsCustomerPriceBySkuIdsAndCustomerIdResponse
                                    .getGoodsCustomerPriceVOList())) {
                Map<String, GoodsCustomerPriceVO> customerPriceMap =
                        goodsCustomerPriceBySkuIdsAndCustomerIdResponse
                                .getGoodsCustomerPriceVOList()
                                .stream()
                                .collect(
                                        Collectors.toMap(
                                                GoodsCustomerPriceVO::getGoodsInfoId,
                                                Function.identity()));
                if(MapUtils.isEmpty(customerPriceMap)) {
                    return skuResponseMap;
                }
                //处理有 客户设价的商品粉丝价
                for (GoodsInfoSimpleVO pluginRequest : list) {
                    if (customerPriceMap.containsKey(pluginRequest.getGoodsInfoId())) {
                        GoodsInfoDetailPluginResponse goodsInfoDetailPluginResponse =
                                new GoodsInfoDetailPluginResponse();
                        goodsInfoDetailPluginResponse.setGoodsInfoId(
                                pluginRequest.getGoodsInfoId());
                        goodsInfoDetailPluginResponse.setGoodsStatus(
                                pluginRequest.getGoodsStatus());
                        goodsInfoDetailPluginResponse.setPluginPrice(pluginRequest.getSalePrice());
                        GoodsCustomerPriceVO customerPrice =
                                customerPriceMap.get(pluginRequest.getGoodsInfoId());
                        if (customerPrice.getPrice() != null) {
                            goodsInfoDetailPluginResponse.setPluginPrice(customerPrice.getPrice());
                        }

                        // 无货或库存低于起订量
                        if (pluginRequest.getStock() < 1
                                || (pluginRequest.getCount() != null
                                        && pluginRequest.getCount() > pluginRequest.getStock())) {
                            //  goodsInfo.setValidFlag(Constants.no);//无效
                            if (GoodsStatus.OK.equals(pluginRequest.getGoodsStatus())) {
                                goodsInfoDetailPluginResponse.setGoodsStatus(GoodsStatus.OUT_STOCK);
                            }
                        }

                        if (goodsInfoDetailPluginResponse.getPluginPrice() != null) {
                            skuResponseMap.put(
                                    pluginRequest.getGoodsInfoId(), goodsInfoDetailPluginResponse);
                        }
                    }
                }
            }
        }
        return skuResponseMap;
    }

    // 设置默认会员折扣
    private void setDefaultPrice(
            CommonLevelVO customerLevel,
            GoodsInfoSimpleVO pluginRequest,
            GoodsInfoDetailPluginResponse goodsInfoDetailPluginResponse) {
        if (customerLevel != null && customerLevel.getLevelDiscount() != null) {
            if (BoolFlag.NO.equals(customerLevel.getLevelType())
                    && BoolFlag.YES.equals(pluginRequest
                    .getCompanyType())) {
                return;
            }
            goodsInfoDetailPluginResponse.setPluginPrice(
                    pluginRequest
                            .getMarketPrice()
                            .multiply(customerLevel.getLevelDiscount())
                            .setScale(2, RoundingMode.HALF_UP));
        }
    }

    /**
     * 设置付费会员价
     */
    public Map<String, GoodsInfoDetailPluginResponse> setPayingMemberPrice(List<GoodsInfoSimpleVO> list) {
        PayingMemberLevelVO level = MarketingContext.getBaseRequest().getPayingMemberLevel();
        if (level.getLevelId() == null) {
            return new HashMap<>();
        }
        Map<String, GoodsInfoDetailPluginResponse> skuResponseMap = new HashMap<>();
        for (GoodsInfoSimpleVO goodsInfoSimpleVO : list) {
            if (SaleType.RETAIL.toValue() == goodsInfoSimpleVO.getSaleType()) {
                Boolean storeFlag;
                //商家范围校验
                if (NumberUtils.INTEGER_ZERO.equals(level.getLevelStoreRange())) {
                    //自营
                    storeFlag = BoolFlag.NO.equals(goodsInfoSimpleVO.getCompanyType());
                } else {
                    //自定义
                    storeFlag = level.getPayingMemberStoreRelVOS().stream()
                            .anyMatch(rel -> rel.getStoreId().equals(goodsInfoSimpleVO.getStoreId()));
                }

                //设置商品折扣价
                BigDecimal payingMemberPrice = null;
                if (storeFlag) {
                    if (NumberUtils.INTEGER_ZERO.equals(level.getLevelDiscountType())) {
                        //统一折扣
                        payingMemberPrice = goodsInfoSimpleVO.getMarketPrice()
                                .multiply(level.getLevelAllDiscount().divide(BigDecimal.TEN))
                                .setScale(2, RoundingMode.HALF_UP);
                    } else {
                        //自定义商品折扣
                        Optional<PayingMemberDiscountRelVO> optional = level.getPayingMemberDiscountRelVOS().stream()
                                .filter(rel -> rel.getGoodsInfoId().equals(goodsInfoSimpleVO.getGoodsInfoId()))
                                .findFirst();
                        if (optional.isPresent()) {
                            payingMemberPrice = goodsInfoSimpleVO.getMarketPrice()
                                    .multiply(optional.get().getDiscount().divide(BigDecimal.TEN))
                                    .setScale(2, RoundingMode.HALF_UP);
                        }
                    }
                }

                if (payingMemberPrice != null) {
                    GoodsInfoDetailPluginResponse goodsInfoDetailPluginResponse =
                            new GoodsInfoDetailPluginResponse();
                    goodsInfoDetailPluginResponse.setGoodsInfoId(goodsInfoSimpleVO.getGoodsInfoId());
                    goodsInfoDetailPluginResponse.setGoodsStatus(goodsInfoSimpleVO.getGoodsStatus());
                    goodsInfoDetailPluginResponse.setPluginPrice(payingMemberPrice);
                    // 无货或库存低于起订量
                    if (goodsInfoSimpleVO.getStock() < 1
                            || (goodsInfoSimpleVO.getCount() != null
                            && goodsInfoSimpleVO.getCount() > goodsInfoSimpleVO.getStock())) {
                        if (GoodsStatus.OK.equals(goodsInfoSimpleVO.getGoodsStatus())) {
                            goodsInfoDetailPluginResponse.setGoodsStatus(GoodsStatus.OUT_STOCK);
                        }
                    }
                    skuResponseMap.put(goodsInfoSimpleVO.getGoodsInfoId(), goodsInfoDetailPluginResponse);
                }
            }
        }
        return skuResponseMap;
    }


            /** 设置级别价 */
    private void setLevelPrice(
            CommonLevelVO customerLevel,
            List<GoodsLevelPriceVO> levelPrices,
            GoodsInfoSimpleVO pluginRequest,
            GoodsInfoDetailPluginResponse goodsInfoDetailPluginResponse) {
        if (CollectionUtils.isNotEmpty(levelPrices)) {
            // 提取符合等级取同一个SkuId的进行设定
            levelPrices.stream()
                    .filter(
                            levelPrice -> {
                                if (customerLevel != null) {
                                    if (customerLevel.getLevelType().equals(BoolFlag.NO)
                                            && BoolFlag.YES.equals(pluginRequest.getCompanyType())) {
                                        return levelPrice
                                                        .getLevelId()
                                                        .equals(Constants.GOODS_PLATFORM_LEVEL_ID)
                                                && levelPrice
                                                        .getGoodsInfoId()
                                                        .equals(pluginRequest.getGoodsInfoId());
                                    } else {
                                        return levelPrice
                                                        .getLevelId()
                                                        .equals(customerLevel.getLevelId())
                                                && levelPrice
                                                        .getGoodsInfoId()
                                                        .equals(pluginRequest.getGoodsInfoId());
                                    }
                                }
                                return levelPrice
                                                .getLevelId()
                                                .equals(Constants.GOODS_PLATFORM_LEVEL_ID)
                                        && levelPrice
                                                .getGoodsInfoId()
                                                .equals(pluginRequest.getGoodsInfoId());
                            })
                    .findFirst()
                    .ifPresent(
                            levelPrice -> {
                                if (levelPrice.getPrice() != null) {
                                    goodsInfoDetailPluginResponse.setPluginPrice(
                                            levelPrice.getPrice());
                                }
                                // 无货或库存低于起订量
                                if (Objects.isNull(pluginRequest.getStock())
                                        || pluginRequest.getStock() < 1
                                        || (pluginRequest.getCount() != null
                                                && pluginRequest.getCount()
                                                        > pluginRequest.getStock())) {
                                    // pluginRequest.setValidFlag(Constants.no);//无效
                                    if (GoodsStatus.OK.equals(pluginRequest.getGoodsStatus())) {
                                        goodsInfoDetailPluginResponse.setGoodsStatus(
                                                GoodsStatus.OUT_STOCK);
                                    }
                                }
                            });
        }
    }

    /**
     * skuTypeMap对应的营销活动是不是存在，存在true，不存在false
     *
     * @description
     * @author zhanggaolei
     * @date 2021/7/28 5:34 下午
     * @param marketingGoods
     * @param customerId
     * @param marketingPluginType
     * @param isStart
     * @param id
     * @return boolean
     */
    private boolean skuTypeMap(
            MarketingGoods marketingGoods,
            String customerId,
            MarketingPluginType marketingPluginType,
            boolean isStart,
            String id) {

        if (MapUtils.isEmpty(MarketingContext.getBaseRequest().getSkuMarketingMap())) {
            Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> skuMap =
                    skuCacheMarketingService.getSkuCacheMarketing(
                            Collections.singletonList(id), isStart);
            if (MapUtils.isEmpty(skuMap) || !skuMap.containsKey(id)) {
                skuMap = new HashMap<>();
                skuMap.put(id, null);
                MarketingContext.getBaseRequest().setSkuMarketingMap(skuMap);
                return false;
            }
            MarketingContext.getBaseRequest().setSkuMarketingMap(skuMap);
        }
        if (!MarketingContext.getBaseRequest().getSkuMarketingMap().containsKey(id)
                || MarketingContext.getBaseRequest().getSkuMarketingMap().get(id) == null) {
            return false;
        } else {
            Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>> cacheMap =
                    MarketingContext.getBaseRequest().getSkuMarketingMap().get(id);
            if (!cacheMap.containsKey(marketingPluginType)) {
                return false;
            }
            boolean retFlag = false;
            List<GoodsInfoMarketingCacheDTO> cacheDTOList = cacheMap.get(marketingPluginType);
            for (GoodsInfoMarketingCacheDTO cacheDTO : cacheDTOList) {
                //                    if (isStart) {
                //                        if
                // (LocalDateTime.now().isBefore(cacheDTO.getBeginTime())
                //                                ||
                // LocalDateTime.now().isAfter(cacheDTO.getEndTime())) {
                //                            log.info(
                //                                    "商品：{}，营销活动：{}，营销id:{}，未开始或者已结束",
                //                                    goodsInfoId,
                //                                    marketingPluginType.name(),
                //                                    cacheDTO.getId());
                //                            //return false;
                //                        }else{
                //                            retFlag=true;
                //                        }
                //                    }
                if (CollectionUtils.isNotEmpty(cacheDTO.getJoinLevel())) {
                    if (MarketingContext.getBaseRequest().getLevelMap() == null) {
                        MarketingContext.getBaseRequest()
                                .setLevelMap(
                                        getCustomerLevels(
                                                Collections.singletonList(
                                                        marketingGoods.getStoreId()),
                                                customerId));
                    }

                    CommonLevelVO commonLevel =
                            MarketingContext.getBaseRequest()
                                    .getLevelMap()
                                    .get(marketingGoods.getStoreId());
                    if (commonLevel != null
                            && commonLevel
                            .getLevelType()
                            .equals(BoolFlag.NO)) {
                        if (cacheDTO.getJoinLevel().contains(-1L)
                                || cacheDTO.getJoinLevel().contains(commonLevel.getLevelId())) {
                            return true;
                        } else {
                            retFlag = false;
                        }
                    } else {
                        if (commonLevel != null) {
                            if (cacheDTO.getJoinLevel().contains(-1L)
                                    || cacheDTO.getJoinLevel().contains(0L)
                                    || cacheDTO.getJoinLevel()
                                    .contains(commonLevel.getLevelId())) {
                                return true;
                            } else {
                                retFlag = false;
                            }
                        }else{
                            return cacheDTO.getJoinLevel().contains(-1L);
                        }
                    }
                } else {
                    retFlag = true;
                }
            }
            /*if (!retFlag) {
                return false;
            }*/
            return retFlag;
        }
    }

    /**
     * MulitTypeMap对应的营销活动是不是存在，存在true，不存在false
     *
     * @description
     * @author zhanggaolei
     * @date 2021/7/28 5:35 下午
     * @param marketingGoods
     * @param marketingPluginType
     * @param id
     * @return boolean
     */
    private boolean checkMultiTypeMap(
            MarketingGoods marketingGoods,
            MarketingPluginType marketingPluginType,
            String id,
            Long storeId) {
        if (MapUtils.isEmpty(MarketingContext.getBaseRequest().getMultiTypeMarketingMap())) {

            Map<String, List<MarketingResponse>> multiTypeMap =
                    getMultiTypeMarketingMap(Lists.newArrayList(marketingGoods), storeId);
            if (MapUtils.isEmpty(multiTypeMap) || !multiTypeMap.containsKey(id)) {
                multiTypeMap = new HashMap<>();
                multiTypeMap.put(id, null);
                MarketingContext.getBaseRequest().setMultiTypeMarketingMap(multiTypeMap);
                return false;
            }
            MarketingContext.getBaseRequest().setMultiTypeMarketingMap(multiTypeMap);
        }
        if (!MarketingContext.getBaseRequest().getMultiTypeMarketingMap().containsKey(id)
                || MarketingContext.getBaseRequest().getMultiTypeMarketingMap().get(id) == null) {
            return false;
        } else {
            Map<Long, CommonLevelVO> levelMap = MarketingContext.getBaseRequest().getLevelMap();
            CommonLevelVO commonLevelVO = null;
            if (MapUtils.isNotEmpty(levelMap)) {
                commonLevelVO = levelMap.get(marketingGoods.getStoreId());
            }
            List<MarketingResponse> marketingResponses =
                    MarketingContext.getBaseRequest().getMultiTypeMarketingMap().get(id);
            if (CollectionUtils.isNotEmpty(marketingResponses)) {
                CommonLevelVO finalCommonLevelVO = commonLevelVO;
                List<MarketingResponse> temp =
                        marketingResponses.stream()
                                .filter(
                                        m -> {
                                            String strLevel = m.getJoinLevel();
                                            if (StringUtils.isNotEmpty(strLevel)) {
                                                List<String> levels =
                                                        Arrays.asList(strLevel.split(","));
                                                if (finalCommonLevelVO != null
                                                        && finalCommonLevelVO
                                                                .getLevelType()
                                                                .equals(BoolFlag.NO)) {

                                                    if (levels.contains(Constants.STR_MINUS_1)
                                                            || levels.contains(
                                                                    finalCommonLevelVO
                                                                            .getLevelId()
                                                                            .toString())) {
                                                        return true;
                                                    } else {
                                                        return false;
                                                    }
                                                } else {
                                                    if (finalCommonLevelVO == null
                                                            && !levels.contains("-1")) {
                                                        return false;
                                                    } else if (finalCommonLevelVO != null
                                                            && !(levels.contains("-1")
                                                                    || levels.contains("0")
                                                                    || levels.contains(
                                                                            finalCommonLevelVO
                                                                                    .getLevelId()
                                                                                    .toString()))) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                }
                                            }
                                            return true;
                                        })
                                .collect(Collectors.toList());

                MarketingContext.getBaseRequest().getMultiTypeMarketingMap().put(id, temp);
            }
            List<String> list =
                    MarketingContext.getBaseRequest().getMultiTypeMarketingMap().get(id).stream()
                            .map(MarketingResponse::getMarketingType)
                            .map(MarketingType::name)
                            .toList();
            return list.contains(marketingPluginType.name());
        }
    }

    /**
     * //兜底如果没有设置storeCateId则重新查询设置
     *
     * @param goodsInfoList
     * @return
     */
    public List<GoodsInfoSimpleVO> setGoodsStoreCateIdList(List<GoodsInfoSimpleVO> goodsInfoList) {
        List<Long> storeCateIds = new ArrayList<>();
        List<String> goodsInfoIds = Lists.newArrayList();
        goodsInfoList.forEach(
                item -> {
                    Integer isBuyCycle = item.getIsBuyCycle();
                    if(Objects.equals(isBuyCycle, Constants.yes)) {
                        goodsInfoIds.add(item.getGoodsInfoId());
                    }
                    if (item.getStoreCateIds() != null) {
                        storeCateIds.addAll(item.getStoreCateIds());
                    }
                });
        Map<String, List<StoreCateGoodsRelaVO>> storeCateMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(storeCateIds)) {
            storeCateMap =
                    storeCateQueryProvider
                            .listByGoods(
                                    new StoreCateListByGoodsRequest(
                                            goodsInfoList.stream()
                                                    .map(GoodsInfoSimpleVO::getGoodsId)
                                                    .collect(Collectors.toList())))
                            .getContext()
                            .getStoreCateGoodsRelaVOList()
                            .stream()
                            .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));
        }
        Map<String, BuyCycleGoodsInfoVO> buyCycleGoodsInfoVOMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
             buyCycleGoodsInfoVOMap = buyCycleGoodsInfoQueryProvider.list(BuyCycleGoodsInfoListRequest.builder()
                    .goodsInfoIdList(goodsInfoIds)
                    .cycleState(Constants.ZERO)
                    .delFlag(DeleteFlag.NO)
                    .build()).getContext().getBuyCycleGoodsInfoVOList()
                    .stream().collect(Collectors.toMap(BuyCycleGoodsInfoVO::getGoodsInfoId, Function.identity()));
        }
        Map<String, List<StoreCateGoodsRelaVO>> finalStoreCateMap = storeCateMap;
        Map<String, BuyCycleGoodsInfoVO> finalBuyCycleGoodsInfoVOMap = buyCycleGoodsInfoVOMap;
        goodsInfoList
                    .forEach(
                            mg -> {
                                if (MapUtils.isNotEmpty(finalStoreCateMap)) {
                                    List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOS =
                                            finalStoreCateMap.get(mg.getGoodsId());
                                    if (CollectionUtils.isNotEmpty(storeCateGoodsRelaVOS)) {
                                        mg.setStoreCateIds(
                                                storeCateGoodsRelaVOS.stream()
                                                        .map(StoreCateGoodsRelaVO::getStoreCateId)
                                                        .collect(Collectors.toList()));
                                    } else {
                                        mg.setStoreCateIds(new ArrayList<>());
                                    }
                                }
                                if (MapUtils.isNotEmpty(finalBuyCycleGoodsInfoVOMap)) {
                                    BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = finalBuyCycleGoodsInfoVOMap.get(mg.getGoodsInfoId());
                                    if (Objects.nonNull(buyCycleGoodsInfoVO)) {
                                        mg.setMarketPrice(buyCycleGoodsInfoVO.getCyclePrice());
                                        mg.setSalePrice(buyCycleGoodsInfoVO.getCyclePrice());
                                        mg.setBuyPoint(BigDecimal.ZERO.longValue());
                                    }
                                }
                            });

        return goodsInfoList;
    }

    /**
     * //兜底如果没有设置storeCateId则重新查询设置
     *
     * @param goodsInfoSimpleVO
     * @return
     */
    public GoodsInfoSimpleVO setGoodsStoreCateIdList(GoodsInfoSimpleVO goodsInfoSimpleVO) {

        if (CollectionUtils.isEmpty(goodsInfoSimpleVO.getStoreCateIds())) {
            Map<String, List<StoreCateGoodsRelaVO>> storeCateMap =
                    storeCateQueryProvider
                            .listByGoods(
                                    new StoreCateListByGoodsRequest(
                                            Collections.singletonList(
                                                    goodsInfoSimpleVO.getGoodsId())))
                            .getContext()
                            .getStoreCateGoodsRelaVOList()
                            .stream()
                            .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));

            List<StoreCateGoodsRelaVO> storeCateGoodsRelaVOS =
                    storeCateMap.get(goodsInfoSimpleVO.getGoodsId());
            if (CollectionUtils.isNotEmpty(storeCateGoodsRelaVOS)) {
                goodsInfoSimpleVO.setStoreCateIds(
                        storeCateGoodsRelaVOS.stream()
                                .map(StoreCateGoodsRelaVO::getStoreCateId)
                                .collect(Collectors.toList()));
            } else {
                goodsInfoSimpleVO.setStoreCateIds(new ArrayList<>());
            }
        }

        return goodsInfoSimpleVO;
    }

    /**
     * 获取付费会员等级
     * @param customerId
     * @return
     */
    public PayingMemberLevelVO getPayingMemberLevel(String customerId){
        List<PayingMemberLevelVO> payingMemberLevelVOList = payingMemberLevelQueryProvider
                .listByCustomerId(PayingMemberLevelCustomerRequest.builder()
                        .customerId(customerId)
                        .defaultFlag(Boolean.TRUE).build())
                .getContext().getPayingMemberLevelVOList();
        PayingMemberLevelVO payingMemberLevelVO = CollectionUtils.isNotEmpty(payingMemberLevelVOList)
                ? payingMemberLevelVOList.get(NumberUtils.INTEGER_ZERO) : new PayingMemberLevelVO();
        return payingMemberLevelVO;
    }

    public CustomerGetByIdResponse getCustomerNoThirdImgById(String customerId) {
        if (StringUtils.isBlank(customerId)) return null;
        return customerQueryProvider
                .getCustomerNoThirdImgById(
                        CustomerGetByIdRequest.builder().customerId(customerId).build())
                .getContext();
    }

    /**
     * 新人专享券
     * @param list
     * @param customerVO
     * @return
     */
    public Map<String, List<CouponInfoVO>> getNewComerCoupon(List<GoodsInfoSimpleVO> list, CustomerVO customerVO){
        Map<String, List<CouponInfoVO>> map = new HashMap<>();

        // 查询是否新人
        if (Objects.isNull(customerVO)) {
            return map;
        }

        Integer isNew = customerVO.getIsNew();
        if (!NumberUtils.INTEGER_ZERO.equals(isNew)) {
            return map;
        }


        //查询优惠券
        List<CouponInfoVO> couponInfos = newcomerPurchaseConfigService.getNewcomerCouponInfos(customerVO.getCustomerId());
        if (CollectionUtils.isEmpty(couponInfos)) {
            return map;
        }

        //查询新人专享商品
        List<NewcomerPurchaseGoodsVO> newcomerGoods = newcomerPurchaseGoodsQueryProvider
                .list(NewcomerPurchaseGoodsListRequest.builder().delFlag(DeleteFlag.NO).build())
                .getContext().getNewcomerPurchaseGoodsVOList();
        List<String> newcomerGoodsIds = newcomerGoods.stream().map(NewcomerPurchaseGoodsVO::getGoodsInfoId).collect(Collectors.toList());

        for (GoodsInfoSimpleVO sku : list) {
            if (SaleType.WHOLESALE.toValue() == sku.getSaleType()) {
                continue;
            }
            //校验商品是否是新人专享商品
            if (!newcomerGoodsIds.contains(sku.getGoodsInfoId())) {
                continue;
            }

            List<CouponInfoVO> goodsCoupons = new ArrayList<>();
            couponInfos.forEach(couponInfo -> {
                switch (couponInfo.getScopeType()) {
                    case ALL:
                        goodsCoupons.add(couponInfo);
                        break;
                    case BOSS_CATE:
                        if (couponInfo.getScopeIds().contains(sku.getCateId().toString())) {
                            goodsCoupons.add(couponInfo);
                        }
                        break;
                    case BRAND:
                        if (Objects.nonNull(sku.getBrandId()) && couponInfo.getScopeIds().contains(sku.getBrandId().toString())) {
                            goodsCoupons.add(couponInfo);
                        }
                        break;
                    case SKU:
                        if (couponInfo.getScopeIds().contains(sku.getGoodsInfoId())) {
                            goodsCoupons.add(couponInfo);
                        }
                        break;
                    case STORE:
                        if(couponInfo.getScopeIds().contains(sku.getStoreId())){
                            goodsCoupons.add(couponInfo);
                        }
                        break;
                    default:
                }
            });

            if (CollectionUtils.isNotEmpty(goodsCoupons)) {
                map.put(sku.getGoodsInfoId(), goodsCoupons);
            }
        }
        return map;
    }

    /**
     * 检查用户是否可领取新人券
     * @param customerId
     * @return
     */
    public Boolean checkNewComerCanFetch(String customerId){
        Boolean fetchFlag = couponCacheService.checkFetchNewcomer(customerId);
        if (fetchFlag) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


}
