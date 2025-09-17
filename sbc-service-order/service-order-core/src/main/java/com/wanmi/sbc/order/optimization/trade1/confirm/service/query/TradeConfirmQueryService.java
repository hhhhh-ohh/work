package com.wanmi.sbc.order.optimization.trade1.confirm.service.query;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelSkuOffAddedSyncRequest;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.marketing.GoodsMarketingQueryProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoVerifyRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.request.marketing.ListByCustomerIdAndGoodsInfoIdReq;
import com.wanmi.sbc.goods.api.response.distributor.goods.DistributorGoodsInfoVerifyResponse;
import com.wanmi.sbc.goods.api.response.info.TradeConfirmGoodsResponse;
import com.wanmi.sbc.goods.api.response.marketing.ListByCustomerIdAndGoodsInfoIdResp;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.countPrice.TradeCountMarketingPriceProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponrecord.GrouponRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountMarketingPriceRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardDetailQueryRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityFreeDeliveryByIdRequest;
import com.wanmi.sbc.marketing.api.request.grouponrecord.GrouponRecordByCustomerRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleListResponse;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountPricePluginResponse;
import com.wanmi.sbc.marketing.api.response.grouponrecord.GrouponRecordByCustomerResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.GiftType;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.enums.BuyType;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.common.SystemPointsConfigService;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.optimization.trade1.confirm.TradeConfirmParam;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.entity.GrouponTradeValid;
import com.wanmi.sbc.order.trade.model.entity.TradeGrouponCommitForm;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.service.VerifyService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liguang
 * @description note something here
 * @date 2022/3/1 17:17
 */
@Service
@Slf4j
public class TradeConfirmQueryService {
    @Autowired
    private CustomerQueryProvider customerQueryProvider;
    @Autowired
    private StoreQueryProvider storeQueryProvider;
    @Autowired
    private NewMarketingPluginProvider newMarketingPluginProvider;
    @Autowired
    private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;
    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;
    @Autowired
    private TradeCountMarketingPriceProvider tradeCountMarketingPriceProvider;
    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Autowired
    private ChannelGoodsProvider channelGoodsProvider;
    @Autowired
    private MqSendProvider mqSendProvider;
    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;
    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;
    @Autowired
    private SystemPointsConfigService systemPointsConfigService;
    @Autowired
    private DistributionCacheService distributionCacheService;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private RedisUtil redisService;
    @Autowired
    ThirdPlatformTradeService thirdPlatformTradeService;
    @Autowired
    private GrouponOrderService grouponOrderService;
    @Autowired
    private GrouponRecordQueryProvider grouponRecordQueryProvider;
    @Autowired
    private GrouponActivityQueryProvider grouponActivityQueryProvider;
    @Autowired
    private GoodsMarketingQueryProvider goodsMarketingQueryProvider;

    @Autowired
    private UserGiftCardProvider userGiftCardProvider;

    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    /**
     * 获取会员信息，包括 会员等级Id 会员可用积分
     */
    public CustomerVO getCustomer(String customerId) {
        BaseResponse<CustomerGetByIdResponse> response = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder().customerId(customerId).build());
        if (!response.isSuccess() || isNull(response.getContext())
                || LogOutStatus.LOGGING_OFF==response.getContext().getLogOutStatus()
                || LogOutStatus.LOGGED_OUT==response.getContext().getLogOutStatus()) {
            // 客户已被删除
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010016);
        }

        if (response.getContext().getCustomerDetail().getCustomerStatus() == CustomerStatus.DISABLE) {
            // 客户已被禁用
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010018);
        }
        return response.getContext();
    }

    /**
     * 查询分销员信息
     *
     * @param inviteeId
     * @return
     */
    public DistributionCustomerVO getDistributionCustomer(String inviteeId) {
        if (Constants.PURCHASE_DEFAULT.equals(inviteeId)) {
            return null;
        } else {
            DistributionCustomerByCustomerIdRequest request = new DistributionCustomerByCustomerIdRequest();
            request.setCustomerId(inviteeId);
            return Optional.ofNullable(distributionCustomerQueryProvider.getByCustomerId(request))
                    .map(BaseResponse::getContext)
                    .map(DistributionCustomerByCustomerIdResponse::getDistributionCustomerVO)
                    .orElse(null);
        }
    }

    /**
     * 拼团信息填充
     *
     * @param request
     * @return
     */
    public TradeGrouponCommitForm dealGroupon(TradeItem tradeItem, TradeBuyRequest request) {
        if (isNull(request.getOpenGroupon())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 1.校验拼团商品
        GrouponGoodsInfoVO grouponGoodsInfo = this.grouponOrderService.validGrouponImmediately(
                GrouponTradeValid.builder()
                        .buyCount(request.getNum().intValue())
                        .customerId(request.getCustomerId())
                        .goodsInfoId(request.getSkuId())
                        .grouponNo(request.getGrouponNo())
                        .openGroupon(request.getOpenGroupon())
                        .build());
        if (isNull(grouponGoodsInfo)) {
            log.error("拼团单下的不是拼团商品");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        tradeItem.setPrice(grouponGoodsInfo.getGrouponPrice());
        // 回填均摊价，用于给优惠券判断使用门槛
        tradeItem.setSplitPrice(
                tradeItem.getPrice().multiply(new BigDecimal(tradeItem.getNum()))
                        .setScale(2, RoundingMode.HALF_UP));
        TradeGrouponCommitForm grouponForm = new TradeGrouponCommitForm();
        grouponForm.setOpenGroupon(request.getOpenGroupon());
        grouponForm.setGrouponNo(request.getGrouponNo());
        grouponForm.setGrouponActivityId(grouponGoodsInfo.getGrouponActivityId());
        grouponForm.setLimitSellingNum(grouponGoodsInfo.getLimitSellingNum());
        grouponForm.setGrouponPrice(grouponGoodsInfo.getGrouponPrice());
        // 校验限售数量
        GrouponRecordByCustomerRequest grouponRecordRequest = new GrouponRecordByCustomerRequest();
        grouponRecordRequest.setGrouponActivityId(grouponGoodsInfo.getGrouponActivityId());
        grouponRecordRequest.setGoodsInfoId(request.getSkuId());
        grouponRecordRequest.setCustomerId(request.getCustomerId());
        GrouponRecordVO grouponRecord = Optional.ofNullable(this.grouponRecordQueryProvider.getByCustomerIdAndGoodsInfoId(grouponRecordRequest))
                .map(BaseResponse::getContext)
                .map(GrouponRecordByCustomerResponse::getGrouponRecordVO)
                .orElse(null);
        Integer startSellingNum = ObjectUtils.defaultIfNull(grouponGoodsInfo.getStartSellingNum(), NumberUtils.INTEGER_ZERO);
        Integer limitSellingNum = ObjectUtils.defaultIfNull(grouponGoodsInfo.getLimitSellingNum(), NumberUtils.INTEGER_ZERO);
        Long num = ObjectUtils.defaultIfNull(tradeItem.getNum(), NumberUtils.LONG_ZERO);
        boolean bool = num <= NumberUtils.LONG_ZERO
                || (!startSellingNum.equals(NumberUtils.INTEGER_ZERO) && startSellingNum > num)
                || (!limitSellingNum.equals(NumberUtils.INTEGER_ZERO) && limitSellingNum < num)
                || (nonNull(grouponRecord) && !limitSellingNum.equals(NumberUtils.INTEGER_ZERO) && (limitSellingNum - grouponRecord.getBuyNum() < num));
        if (bool) {
            log.error("商品购买数量超过或者低于限售数量");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "商品购买数量超过或者低于限售数量");
        }
        // 2.设置拼团活动信息
        boolean freeDelivery = this.grouponActivityQueryProvider.getFreeDeliveryById(
                new GrouponActivityFreeDeliveryByIdRequest(grouponGoodsInfo.getGrouponActivityId())).getContext().isFreeDelivery();
        grouponForm.setFreeDelivery(freeDelivery);
        grouponForm.setShareUserId(request.getShareUserId());
        return grouponForm;
    }

    /**
     * 获取店铺信息
     *
     * @param storeIds
     * @return
     */
    public List<StoreVO> getStoreByStoreIds(List<Long> storeIds) {
        if (CollectionUtils.isEmpty(storeIds)) {
            return Lists.newArrayList();
        }
        // 获取店铺信息
        BaseResponse<ListStoreByIdsResponse> response = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build());
        return Optional.ofNullable(response)
                .map(BaseResponse::getContext)
                .map(ListStoreByIdsResponse::getStoreVOList)
                .orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010105));
    }

    /**
     * 获取第三方渠道开关
     *
     * @param param
     */
    public void getChanelFlag(TradeConfirmParam param) {
        // linkedmall 渠道开关
        String linkedMallFlag = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
        param.setLinkedMallFlag((StringUtils.isNotEmpty(linkedMallFlag) && VASStatus.ENABLE.toValue().equalsIgnoreCase(linkedMallFlag)));
        // VOP 渠道开关
        String vopFlag = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
        param.setVopFlag(StringUtils.isNotEmpty(vopFlag) && VASStatus.ENABLE.toValue().equalsIgnoreCase(vopFlag));
    }

    /**
     * 获取系统积分开关
     *
     * @return
     */
    public Boolean querySystemPointConfig() {
        SystemPointsConfigQueryResponse response = systemPointsConfigService.querySettingCache();
        return EnableStatus.ENABLE.equals(response.getStatus()) && PointsUsageFlag.GOODS.equals(response.getPointsUsageFlag());
    }

    /**
     * 获取系统分销开关
     *
     * @return
     */
    public Boolean querySystemDistributionConfig() {
        DefaultFlag defaultFlag = distributionCacheService.queryOpenFlag();
        return DefaultFlag.YES.equals(defaultFlag);
    }

    /**
     * 查询店铺精选商品
     *
     * @param skuIdList
     * @param inviteeId
     * @return
     */
    public List<String> getInvalidDistributeSkuIds(List<String> skuIdList, String inviteeId) {
        if (Constants.PURCHASE_DEFAULT.equals(inviteeId) || "undefined".equalsIgnoreCase(inviteeId)) {
            return Lists.newArrayList();
        } else {
            DistributorGoodsInfoVerifyRequest verifyRequest = new DistributorGoodsInfoVerifyRequest();
            verifyRequest.setGoodsInfoIds(skuIdList);
            verifyRequest.setDistributorId(inviteeId);
            //验证是否在店铺精选范围内
            return Optional.ofNullable(distributorGoodsInfoQueryProvider.verifyDistributorGoodsInfo(verifyRequest))
                    .map(BaseResponse::getContext)
                    .map(DistributorGoodsInfoVerifyResponse::getInvalidIds)
                    .orElseGet(Lists::newArrayList);
        }
    }

    /**
     * 获取营销信息
     *
     * @param goodsInfos
     * @param customerId
     * @return sku对应的营销标签
     * <goodsInfoId, List<MarketingPluginLabelDetailVO>>
     */
    public Map<String, List<MarketingPluginLabelDetailVO>> getMarketing(List<GoodsInfoVO> goodsInfos, String customerId, Boolean iepCustomerFlag, Boolean bargain) {
        GoodsListPluginRequest pluginRequest = GoodsListPluginRequest.builder()
                .goodsInfoPluginRequests(KsBeanUtil.convertList(goodsInfos, GoodsInfoSimpleVO.class))
                .pluginType(PluginType.NORMAL)
                .build();
        pluginRequest.setCustomerId(customerId);
        pluginRequest.setIepCustomerFlag(iepCustomerFlag);
        //处理 砍价订单的优惠券
        if (Objects.equals(Boolean.TRUE, bargain)) {
            //验证是否支持优惠券叠加使用
            ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
            configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
            configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            SystemConfigTypeResponse response =
                    systemConfigQueryProvider
                            .findByConfigTypeAndDelFlag(configQueryRequest)
                            .getContext();
            if (Objects.isNull(response.getConfig())
                    || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                return new HashMap<>();
            }
            pluginRequest.setMarketingPluginType(MarketingPluginType.COUPON);
            pluginRequest.setHandlePosit(Boolean.FALSE);
        }


        return Optional.ofNullable(newMarketingPluginProvider.confirmPlugin(pluginRequest))
                .map(BaseResponse::getContext)
                .map(GoodsTradePluginResponse::getSkuMarketingLabelMap)
                .orElseGet(Maps::newHashMap);
    }

    /**
     * 获取商品在购物车时选择的营销
     *
     * @param skuIds
     * @param customerId
     * @return
     */
    public Map<String, Long> getGoodsMarketing(List<String> skuIds, String customerId) {
        ListByCustomerIdAndGoodsInfoIdReq goodsInfoIdsReq = ListByCustomerIdAndGoodsInfoIdReq.builder().customerId(customerId).goodsInfoIds(skuIds).build();
        return Optional.ofNullable(this.goodsMarketingQueryProvider.queryByCustomerIdAndGoodsInfoId(goodsInfoIdsReq))
                .map(BaseResponse::getContext)
                .map(ListByCustomerIdAndGoodsInfoIdResp::getGoodsMarketings)
                .orElseGet(Lists::newArrayList)
                .stream()
                .collect(Collectors.toMap(GoodsMarketingVO::getGoodsInfoId, GoodsMarketingVO::getMarketingId, (v1, v2) -> v1));
    }

    /**
     * 计算营销价格
     *
     * @param countPriceVOList
     * @param customerId
     * @return
     */
    public List<CountPriceItemVO> countPriceVOList(List<CountPriceDTO> countPriceVOList, String customerId) {
        TradeCountMarketingPriceRequest tradeCountMarketingPriceRequest = TradeCountMarketingPriceRequest.builder()
                .customerId(customerId)
                .forceCommit(Boolean.FALSE)
                .countPriceVOList(countPriceVOList)
                .defaultMate(Boolean.TRUE)
                .build();
        try {
            return Optional.ofNullable(this.tradeCountMarketingPriceProvider.tradeCountMarketingPrice(tradeCountMarketingPriceRequest))
                    .map(BaseResponse::getContext)
                    .map(TradeCountPricePluginResponse::getCountPriceVOList)
                    .orElseGet(Lists::newArrayList);
        } catch (SbcRuntimeException e) {
            log.error("查询营销价格接口报错：", e);
            return Lists.newArrayList();
        }
    }

    /**
     * 填充TradeItem价格
     *
     * @return
     */
    public void fillTradeItemPrice(TradeItem tradeItem, GoodsInfoVO goodsInfo, GoodsVO goods, List<GoodsIntervalPriceVO> goodsIntervalPrices) {
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            // 订货区间设价
            goodsIntervalPrices.stream()
                    .filter(item -> item.getGoodsInfoId().equals(tradeItem.getSkuId()))
                    .filter(intervalPrice -> tradeItem.getNum() >= intervalPrice.getCount())
                    .max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount))
                    .ifPresent(goodsIntervalPrice -> {
                        tradeItem.setLevelPrice(goodsIntervalPrice.getPrice());
                        tradeItem.setOriginalPrice(goodsInfo.getMarketPrice());
                        tradeItem.setPrice(goodsIntervalPrice.getPrice());
                        tradeItem.setSplitPrice(tradeItem.getLevelPrice().multiply(new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
                    });
        } else {
            BigDecimal price = ObjectUtils.defaultIfNull(goodsInfo.getSalePrice(), goodsInfo.getMarketPrice());
            tradeItem.setLevelPrice(price);
            tradeItem.setOriginalPrice(price);
            tradeItem.setPrice(price);
            tradeItem.setSplitPrice(tradeItem.getLevelPrice().multiply(new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
        }
    }

    /**
     * 填充goodsInfo 基础信息
     *
     * @param goodsInfo
     * @param goods
     * @param specDetailMap
     */
    public void fillBaseGoodsInfo(GoodsInfoVO goodsInfo, GoodsVO goods, Map<String, String> specDetailMap) {
        if (isNull(goods.getGoodsInfoIds())) {
            goods.setGoodsInfoIds(Lists.newArrayList());
        }
        goods.getGoodsInfoIds().add(goodsInfo.getGoodsInfoId());
        goodsInfo.setPriceType(goods.getPriceType());
        goodsInfo.setGoodsUnit(goods.getGoodsUnit());
        goodsInfo.setCateId(goods.getCateId());
        goodsInfo.setBrandId(goods.getBrandId());
        goodsInfo.setFreightTempId(goods.getFreightTempId());
        goodsInfo.setGoodsEvaluateNum(goods.getGoodsEvaluateNum());
        goodsInfo.setGoodsSalesNum(goods.getGoodsSalesNum());
        goodsInfo.setGoodsCollectNum(goods.getGoodsCollectNum());
        goodsInfo.setGoodsFavorableCommentNum(goods.getGoodsFavorableCommentNum());
        goodsInfo.setThirdPlatformSpuId(goods.getThirdPlatformSpuId());

        // 为空，则以商品主图
        if (StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())) {
            goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
        }

        // 填充规格值
        if (MapUtils.isNotEmpty(specDetailMap) && Constants.yes.equals(goods.getMoreSpecFlag())) {
            goodsInfo.setSpecText(specDetailMap.getOrDefault(goodsInfo.getGoodsInfoId(), StringUtils.SPACE));
        }
    }

    /**
     * 填充tradeItem
     *
     * @param tradeItem
     * @param goodsInfo
     */
    public void fillBaseTradeItem(TradeItem tradeItem, GoodsInfoVO goodsInfo, TradeItemRequest tradeItemRequest, BuyType buyType) {
        //商品条形码存储
        tradeItem.setGoodsInfoBarcode(goodsInfo.getGoodsInfoBarcode());
        tradeItem.setPluginType(goodsInfo.getPluginType());
        tradeItem.setStoreId(goodsInfo.getStoreId());
        tradeItem.setSpuId(goodsInfo.getGoodsId());
        if (!BuyType.STORE_BAGS_BUY.equals(buyType)) {
            tradeItem.setBuyPoint(goodsInfo.getBuyPoint());
        }
        tradeItem.setSkuId(goodsInfo.getGoodsInfoId());
        tradeItem.setSkuName(goodsInfo.getGoodsInfoName());
        tradeItem.setSkuNo(goodsInfo.getGoodsInfoNo());
        tradeItem.setThirdPlatformSpuId(goodsInfo.getThirdPlatformSpuId());
        tradeItem.setThirdPlatformSkuId(goodsInfo.getThirdPlatformSkuId());
        tradeItem.setThirdPlatformType(goodsInfo.getThirdPlatformType());
        tradeItem.setGoodsSource(goodsInfo.getGoodsSource());
        tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
        tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
        tradeItem.setFreightTempId(goodsInfo.getFreightTempId());
        tradeItem.setCateTopId(goodsInfo.getCateTopId());
        tradeItem.setCateId(goodsInfo.getCateId());
        tradeItem.setCateName(goodsInfo.getCateName());
        tradeItem.setPic(goodsInfo.getGoodsInfoImg());
        tradeItem.setBrand(goodsInfo.getBrandId());
        tradeItem.setUnit(goodsInfo.getGoodsUnit());
        tradeItem.setSpecDetails(goodsInfo.getSpecText());
        if (isNull(tradeItem.getDistributionGoodsAudit())) {
            tradeItem.setDistributionGoodsAudit(goodsInfo.getDistributionGoodsAudit());
            tradeItem.setDistributionCommission(goodsInfo.getDistributionCommission());
        }
        tradeItem.setCommissionRate(goodsInfo.getCommissionRate());
        tradeItem.setProviderId(goodsInfo.getProviderId());
        tradeItem.setSupplyPrice(goodsInfo.getSupplyPrice());
        tradeItem.setGoodsStatus(goodsInfo.getGoodsStatus());
        if (nonNull(tradeItemRequest)) {
            tradeItem.setNum(tradeItemRequest.getNum());
            tradeItem.setBuyPoint(tradeItemRequest.getBuyPoint());
            tradeItem.setIsBookingSaleGoods(tradeItemRequest.getIsBookingSaleGoods());
            tradeItem.setBookingSaleId(tradeItemRequest.getBookingSaleId());
            tradeItem.setIsAppointmentSaleGoods(tradeItemRequest.getIsAppointmentSaleGoods());
            tradeItem.setAppointmentSaleId(tradeItemRequest.getAppointmentSaleId());
        }
    }

    /**
     * 查询商品详情
     *
     * @param param
     * @param goodsRequest
     */
    public void getGoodsInfos(TradeConfirmParam param, TradeConfirmGoodsRequest goodsRequest) {
        TradeConfirmGoodsResponse goodsResponse = Optional.ofNullable(this.goodsInfoQueryProvider.tradeConfirmGoodsInfo(goodsRequest))
                .map(BaseResponse::getContext)
                .orElseThrow(() -> new SbcRuntimeException(goodsRequest.getSkuIds(), GoodsErrorCodeEnum.K030035));
        param.setGoodses(goodsResponse.getGoodses());
        param.setGoodsInfos(goodsResponse.getGoodsInfos());
        param.setProviderGoodsInfos(goodsResponse.getProviderGoodsInfos());
        param.setGoodsIntervalPriceVOList(goodsResponse.getGoodsIntervalPriceVOList());
        param.setGoodsInfoSpecDetailRelVOList(goodsResponse.getGoodsInfoSpecDetailRelVOS());
        param.setSkuRedisStockMap(goodsResponse.getSkuRedisStockMap());

        List<GoodsInfoVO> goodsInfos = param.getGoodsInfos();
        List<GoodsVO> goodses = param.getGoodses();
        // 校验商品不存在
        if (CollectionUtils.isEmpty(goodsInfos) || CollectionUtils.isEmpty(goodses)) {
            throw new SbcRuntimeException(param.getSkuIds(), GoodsErrorCodeEnum.K030035);
        }
        Map<String, Integer> goodsPriceTypeMap = goodses.stream().collect(Collectors.toMap(GoodsVO::getGoodsId, GoodsVO::getPriceType, (v1, v2) -> v1));
        // 校验商品上下架状态
        for (GoodsInfoVO goodsInfo : goodsInfos) {
            if (DeleteFlag.YES.equals(goodsInfo.getDelFlag())) {
                throw new SbcRuntimeException(Collections.singletonList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050118);
            } else if (AddedFlag.NO.equals(
                    AddedFlag.fromValue(goodsInfo.getAddedFlag()))) {
                throw new SbcRuntimeException(Collections.singletonList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050027);
            } else if (Objects.equals(DefaultFlag.NO.toValue(), this.verifyService.buildGoodsInfoVendibility(goodsInfo)) || CheckStatus.FORBADE.equals(goodsInfo.getAuditStatus())) {
                throw new SbcRuntimeException(Collections.singletonList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050120);
            } else if (!CheckStatus.CHECKED.equals(goodsInfo.getAuditStatus())) {
                throw new SbcRuntimeException(Collections.singletonList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050121);
            }
            goodsInfo.setPriceType(goodsPriceTypeMap.get(goodsInfo.getGoodsId()));
            if (isNull(goodsInfo.getSalePrice())) {
                goodsInfo.setSalePrice(goodsInfo.getMarketPrice());
            }
        }

        WmCollectionUtils.notEmpty2Loop(goodsInfos, goodsInfo -> {
            // 订货区间设价
            if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goodsInfo.getPriceType())
                    && CollectionUtils.isNotEmpty(goodsResponse.getGoodsIntervalPriceVOList())) {
                // 映射Map
                Map<String, Long> skuNumMap = WmCollectionUtils.notEmpty2Map(param.getTradeBuyRequest().getTradeItemRequests(),
                        TradeItemRequest::getSkuId, TradeItemRequest::getNum);

                Optional<GoodsIntervalPriceVO> first =
                        goodsResponse.getGoodsIntervalPriceVOList().stream()
                                .filter(item -> skuNumMap.containsKey(item.getGoodsInfoId()))
                                .filter(item -> item.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId()))
                                .filter(item -> skuNumMap.get(item.getGoodsInfoId()) >= item.getCount())
                                .max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount));

                if (first.isPresent()) {
                    GoodsIntervalPriceVO goodsIntervalPrice = first.get();
                    if (Objects.nonNull(goodsInfo.getSalePrice())) {
                        goodsInfo.setSalePrice(goodsIntervalPrice.getPrice());
                    }
                    goodsInfo.setMarketPrice(goodsIntervalPrice.getPrice());
                }
            }
        });
    }

    /**
     * 查询赠品商品详情
     *
     * @param param
     * @param goodsRequest
     */
    public void getGiftGoodsInfos(TradeConfirmParam param, TradeConfirmGoodsRequest goodsRequest) {
        BaseResponse<TradeConfirmGoodsResponse> response = this.goodsInfoQueryProvider.tradeConfirmGoodsInfo(goodsRequest);
        TradeConfirmGoodsResponse goodsResponse = Optional.ofNullable(response)
                .map(BaseResponse::getContext)
                .orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
        List<String> providerGoodsInfoIds =
                goodsResponse.getGoodsInfos().stream().map(GoodsInfoVO::getProviderGoodsInfoId).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)){
            List<GoodsInfoVO> goodsInfosList = goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                    .goodsInfoIds(providerGoodsInfoIds).build()).getContext().getGoodsInfos();
            Map<String, GoodsInfoVO> providerGoodsInfoMap = goodsInfosList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
            goodsResponse.getGoodsInfos().forEach(goodsInfoVO -> {
                GoodsInfoVO goodsInfoVO1 = providerGoodsInfoMap.get(goodsInfoVO.getProviderGoodsInfoId());
                if(Objects.nonNull(goodsInfoVO1)){
                    goodsInfoVO.setStock(goodsInfoVO1.getStock());
                }
            });
        }
        param.setGiftGoodses(goodsResponse.getGoodses());
        param.setGiftGoodsInfos(goodsResponse.getGoodsInfos());
        param.setGiftGoodsInfoSpecDetailRelVOList(goodsResponse.getGoodsInfoSpecDetailRelVOS());
        Map<String, GoodsVO> goodsVOMap = param.getGiftGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        List<GoodsInfoVO> goodsInfos = param.getGiftGoodsInfos();
        Map<String, String> specDetailMap = goodsResponse.getGoodsInfoSpecDetailRelVOS().stream().collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId, GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));
        for (GoodsInfoVO goodsInfo : goodsInfos) {
            this.fillBaseGoodsInfo(goodsInfo, goodsVOMap.get(goodsInfo.getGoodsId()), specDetailMap);
        }
    }

    /**
     * @description 查询加价购商品详情
     * @author  edz
     * @date: 2022/12/9 11:15
     * @param param
     * @param goodsRequest
     * @return void
     */
    public void getPreferentialGoodsInfos(TradeConfirmParam param, TradeConfirmGoodsRequest goodsRequest) {
        BaseResponse<TradeConfirmGoodsResponse> response = this.goodsInfoQueryProvider.tradeConfirmGoodsInfo(goodsRequest);
        TradeConfirmGoodsResponse goodsResponse = Optional.ofNullable(response)
                .map(BaseResponse::getContext)
                .orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
        param.setPreferentialGoodses(goodsResponse.getGoodses());
        param.setPreferentialGoodsInfos(goodsResponse.getGoodsInfos());
        param.setPreferentialGoodsInfoSpecDetailRelVOList(goodsResponse.getGoodsInfoSpecDetailRelVOS());
        Map<String, GoodsVO> goodsVOMap = param.getPreferentialGoodses().stream().collect(Collectors.toMap(GoodsVO::getGoodsId,
                Function.identity()));
        List<GoodsInfoVO> goodsInfos = param.getPreferentialGoodsInfos();
        Map<String, String> specDetailMap = goodsResponse.getGoodsInfoSpecDetailRelVOS().stream()
                .collect(Collectors.toMap(GoodsInfoSpecDetailRelVO::getGoodsInfoId,
                        GoodsInfoSpecDetailRelVO::getDetailName, (v1, v2) -> v1.concat(StringUtils.SPACE).concat(v2)));
        for (GoodsInfoVO goodsInfo : goodsInfos) {
            this.fillBaseGoodsInfo(goodsInfo, goodsVOMap.get(goodsInfo.getGoodsId()), specDetailMap);
        }
    }

    /**
     * 如果系统或者店铺分销关闭则设置商品为普通商品
     *
     * @param param
     */
    public void checkDistributFlag(TradeConfirmParam param, boolean grouponFlag, boolean suitMarketingFlag) {
        List<GoodsInfoVO> goodsInfos = param.getGoodsInfos();
        Map<Long, DefaultFlag> storeOpenFlag = param.getStoreOpenFlag();
        for (GoodsInfoVO goodsInfo : goodsInfos) {
            if (!param.getIsDistributFlag()
                    || DefaultFlag.NO.equals(storeOpenFlag.get(goodsInfo.getStoreId()))
                    || grouponFlag
                    || suitMarketingFlag) {
                goodsInfo.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                if (suitMarketingFlag) {
                    goodsInfo.setBuyPoint(NumberUtils.LONG_ZERO);
                }
            }
            // 如果积分设置是订单抵扣 则商品积分设置为0
            if (!param.getSystemPointFlag()) {
                goodsInfo.setBuyPoint(NumberUtils.LONG_ZERO);
            }
        }
    }

    /**
     * 校验库存
     *
     * @param param
     */
    public void checkGoodsStock(TradeConfirmParam param) {
        List<GoodsInfoVO> goodsInfos = param.getGoodsInfos();
        List<String> skuIds = Lists.newArrayList();
        List<GoodsInfoVO> channelSkus = Lists.newArrayList();
        Map<String, GoodsInfoVO> goodsInfoVoMap = Maps.newHashMap();
        for (GoodsInfoVO goodsInfo : goodsInfos) {
            goodsInfoVoMap.put(goodsInfo.getGoodsInfoId(), goodsInfo);
            if (goodsInfo.getGoodsStatus().equals(GoodsStatus.OK)) {
                if (StringUtils.isNotEmpty(goodsInfo.getProviderGoodsInfoId())) {
                    skuIds.add(goodsInfo.getProviderGoodsInfoId());
                    if (nonNull(goodsInfo.getThirdPlatformType())) {
                        channelSkus.add(goodsInfo);
                    }
                } else {
                    skuIds.add(goodsInfo.getGoodsInfoId());
                }
            }
        }

        // 正常商品库存 / 供应商商品库存
        if (MapUtils.isNotEmpty(param.getSkuRedisStockMap())) {
            for (GoodsInfoVO goodsInfo : goodsInfos) {
                if (GoodsStatus.OK.equals(goodsInfo.getGoodsStatus())) {
                    Long stock = StringUtils.isNotEmpty(goodsInfo.getProviderGoodsInfoId())
                            ? param.getSkuRedisStockMap().get(goodsInfo.getProviderGoodsInfoId())
                            : param.getSkuRedisStockMap().get(goodsInfo.getGoodsInfoId());
                    if (nonNull(stock)) {
                        goodsInfo.setStock(stock);
                    }
                    if (isNull(stock) || stock < 1) {
                        goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                }
            }
        }
        // 渠道商品库存
        if (CollectionUtils.isNotEmpty(channelSkus)) {
            String[] address = StringUtils.defaultString(param.getTradeBuyRequest().getAddressId(), StringUtils.EMPTY).split(Constants.CATE_PATH_SPLITTER);
            PlatformAddress platformAddress = PlatformAddress.builder()
                    .provinceId(address[0])
                    .cityId(address[1])
                    .areaId(address[2])
                    .streetId(address[3])
                    .build();
            List<GoodsInfoBaseVO> goodsInfoBaseVOS = KsBeanUtil.convert(goodsInfos, GoodsInfoBaseVO.class);
            this.thirdPlatformTradeService.cartStatus(goodsInfoBaseVOS, platformAddress);
            Map<String, GoodsInfoBaseVO> goodsInfoBaseVoMap = goodsInfoBaseVOS.stream().collect(Collectors.toMap(GoodsInfoBaseVO::getGoodsInfoId, Function.identity()));
            for (GoodsInfoVO goodsInfo : goodsInfos) {
                GoodsInfoBaseVO goodsInfoBaseVO = goodsInfoBaseVoMap.get(goodsInfo.getGoodsInfoId());
                if (nonNull(goodsInfoBaseVO)) {
                    goodsInfo.setGoodsStatus(goodsInfoBaseVO.getGoodsStatus());
                    goodsInfo.setVendibility(goodsInfoBaseVO.getVendibility());
                    goodsInfo.setStock(goodsInfoBaseVO.getStock());
                }
            }
        }
        Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        List<TradeItemRequest> tradeItemRequests = param.getTradeBuyRequest().getTradeItemRequests();
        for (TradeItemRequest tradeItemRequest : tradeItemRequests) {
            GoodsInfoVO goodsInfoVO = goodsInfoVOMap.get(tradeItemRequest.getSkuId());
            if (tradeItemRequest.getNum() > goodsInfoVO.getStock()) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050026);
            }
        }
    }

    //购物车结算不支持周期购商品
    public void checkBuyCycle(TradeConfirmParam param){
        param.getGoodsInfos().forEach(goodsInfoVO -> {
            if (Objects.equals(goodsInfoVO.getIsBuyCycle(),Constants.yes)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
            }
        });
    }

    /**
     * 渠道校验
     *
     * @param param
     */
    public void checkChanelGoods(TradeConfirmParam param) {
        List<GoodsInfoVO> goodsInfos = param.getGoodsInfos();
        List<TradeItemRequest> tradeItemRequests = param.getTradeBuyRequest().getTradeItemRequests();
        List<ThirdPlatformType> platformTypes = goodsInfos.stream().map(GoodsInfoVO::getThirdPlatformType).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if ((platformTypes.contains(ThirdPlatformType.LINKED_MALL) && !param.getLinkedMallFlag())
                || (platformTypes.contains(ThirdPlatformType.VOP) && !param.getVopFlag())) {
            log.error("渠道商商品校验失败");
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
        List<ChannelGoodsInfoDTO> goodsInfoList = KsBeanUtil.convert(goodsInfos, ChannelGoodsInfoDTO.class);
        goodsInfoList.forEach(s -> s.setBuyCount(1L));
        if (CollectionUtils.isNotEmpty(tradeItemRequests)) {
            goodsInfoList =
                    IteratorUtils.zip(
                            goodsInfoList,
                            tradeItemRequests,
                            (a, b) -> a.getGoodsInfoId().equals(b.getSkuId()),
                            (c, d) -> {
                                c.setBuyCount(d.getNum());
                            });
        }
        ChannelGoodsVerifyRequest verifyRequest = new ChannelGoodsVerifyRequest();
        verifyRequest.setGoodsInfoList(goodsInfoList);
        ChannelGoodsVerifyResponse verifyResponse = this.channelGoodsProvider.verifyGoods(verifyRequest).getContext();
        if (verifyResponse.getInvalidGoods()) {
            // 下架商品
            if (CollectionUtils.isNotEmpty(verifyResponse.getOffAddedSkuId())) {
                List<String> providerSkuId = verifyResponse.getOffAddedSkuId();
                try {
                    ChannelSkuOffAddedSyncRequest request = new ChannelSkuOffAddedSyncRequest();
                    request.setProviderSkuId(providerSkuId);
                    MqSendDTO mqSendDTO = new MqSendDTO();
                    mqSendDTO.setTopic(ProducerTopic.THIRD_PLATFORM_SKU_OFF_ADDED_FLAG);
                    mqSendDTO.setData(JSONObject.toJSONString(request));
                    this.mqSendProvider.send(mqSendDTO);
                } catch (Exception e) {
                    log.error("第三方订单验证调用-下架SKU商品MQ异常", e);
                }
            }
            log.error("渠道商商品失效");
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
    }

    /**
     * 处理满系营销
     *
     * @param skuId
     * @param marketingPlugin
     * @param countPriceMarketingMap
     * @return
     */
    public void dealMultiTypeMap(String skuId, MarketingPluginLabelDetailVO marketingPlugin,
                                 Map<Long, CountPriceMarketingDTO> countPriceMarketingMap, List<TradeMarketingDTO> selectMarketings) {
        TradeMarketingDTO selectMarketing = CollectionUtils.size(selectMarketings) > 0 ? selectMarketings.get(0) : null;
        Object detail = marketingPlugin.getDetail();
        MarketingPluginType marketingType = MarketingPluginType.fromId(marketingPlugin.getMarketingType());
        Long marketingId = Long.parseLong(marketingPlugin.getMarketingId().toString());
        CountPriceMarketingDTO countPriceMarketing = countPriceMarketingMap.getOrDefault(marketingId,
                CountPriceMarketingDTO.builder()
                        .marketingId(marketingId)
                        .marketingPluginType(marketingType)
                        .marketingLevelId(nonNull(selectMarketing) ? selectMarketing.getMarketingLevelId() : null)
                        .detail(detail)
                        .build()
        );
        List<String> reductionSkuIds = countPriceMarketing.getSkuIds();
        if (CollectionUtils.isNotEmpty(reductionSkuIds)) {
            reductionSkuIds.add(skuId);
        } else {
            countPriceMarketing.setSkuIds(Lists.newArrayList(skuId));
        }
        countPriceMarketingMap.put(marketingId, countPriceMarketing);
        switch (marketingType) {
            case BUYOUT_PRICE:
                // 一口价
                break;
            case REDUCTION:
                // 满减
                break;
            case DISCOUNT:
                // 满折
                break;
            case HALF_PRICE_SECOND_PIECE:
                // 第二件半价
                break;
            case GIFT:
                // 满赠
                List<MarketingFullGiftLevelVO> marketingFullGiftLevelVOS = JSONArray.parseArray(JSONObject.toJSONString(detail), MarketingFullGiftLevelVO.class);
                List<String> giftSkuIds = ObjectUtils.defaultIfNull(countPriceMarketing.getGiftSkuIds(), Lists.newArrayList());
                for (MarketingFullGiftLevelVO level : marketingFullGiftLevelVOS) {
                    List<String> allGiftSkuIds = level.getFullGiftDetailList().stream().map(MarketingFullGiftDetailVO::getProductId).collect(Collectors.toList());
                    if (nonNull(selectMarketing) && Objects.equals(level.getGiftLevelId(), selectMarketing.getMarketingLevelId())) {
                        if (level.getGiftType() == GiftType.ONE) {
                            if (CollectionUtils.size(selectMarketing.getGiftSkuIds()) == NumberUtils.INTEGER_ONE && allGiftSkuIds.containsAll(selectMarketing.getGiftSkuIds())) {
                                giftSkuIds.addAll(selectMarketing.getGiftSkuIds());
                            }
                        } else {
                            // 全增
                            if (CollectionUtils.isNotEmpty(selectMarketing.getGiftSkuIds()) && allGiftSkuIds.containsAll(selectMarketing.getGiftSkuIds())) {
                                giftSkuIds.addAll(selectMarketing.getGiftSkuIds());
                            }
                        }
                        break;
                    }
                }
                countPriceMarketing.setGiftSkuIds(giftSkuIds);
                break;
            case PREFERENTIAL:
                if (nonNull(selectMarketing)){
                    countPriceMarketing.setPreferentialSkuIds(selectMarketing.getPreferentialSkuIds());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 处理其他营销
     */
    public void dealOtherPlugin(TradeItem tradeItem, MarketingPluginLabelDetailVO marketingPlugin) {
        // 这里的营销是不需要再次计算的。直接取pluginPrice就是计算好的营销价格
        tradeItem.setPrice(marketingPlugin.getPluginPrice());
        tradeItem.setLevelPrice(marketingPlugin.getPluginPrice());
        tradeItem.setSplitPrice(marketingPlugin.getPluginPrice().multiply(new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 查询预售商品
     *
     * @param param
     * @return
     */
    public List<BookingSaleVO> queryBookingSales(TradeConfirmParam param) {
        // 订单中是否包含预售商品
        List<String> bookingSkuIds = Lists.newArrayList();
        param.getMarketingMap().forEach((skuId, marketingPlugins) -> {
            if (marketingPlugins.stream().anyMatch(marketingPlugin -> marketingPlugin.getMarketingType() == MarketingPluginType.BOOKING_SALE.getId())) {
                bookingSkuIds.add(skuId);
            }
        });
        if (CollectionUtils.isEmpty(bookingSkuIds)) {
            return Lists.newArrayList();
        }
        BookingSaleInProgressRequest progressRequest = BookingSaleInProgressRequest.builder()
                .goodsInfoIdList(bookingSkuIds)
                .build();
        List<BookingSaleVO> bookingSales = Optional.ofNullable(this.bookingSaleQueryProvider
                        .inProgressBookingSaleInfoByGoodsInfoIdList(progressRequest))
                .map(BaseResponse::getContext)
                .map(BookingSaleListResponse::getBookingSaleVOList)
                .orElseGet(Lists::newArrayList);
        if (bookingSales.size() != bookingSkuIds.size()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050128);
        }
        return bookingSales;
    }

    /**
     * 校验地址信息
     *
     * @param param
     */
    public void checkAddress(TradeConfirmParam param) {
        GoodsInfoVO goodsInfoVO = param.getGoodsInfos().get(0);
        Integer goodsType = goodsInfoVO.getGoodsType();
        CustomerDeliveryAddressRequest queryRequest = new CustomerDeliveryAddressRequest();
        queryRequest.setCustomerId(param.getCustomerVO().getCustomerId());
        CustomerDeliveryAddressResponse address;
        if (NumberUtils.INTEGER_ZERO.equals(goodsType)) {
            if (StringUtils.isEmpty(param.getTradeBuyRequest().getAddressId())) {
                address = Optional.ofNullable(this.customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest))
                        .map(BaseResponse::getContext)
                        .orElseThrow(() -> new SbcRuntimeException(OrderErrorCodeEnum.K050131));
                List<Long> addressIds = Lists.newArrayList(address.getProvinceId(), address.getCityId(), address.getAreaId(), address.getStreetId());
                param.getTradeBuyRequest().setAddressId(StringUtils.join(addressIds, Constants.CATE_PATH_SPLITTER));
            }
            // 完善收货地址
            if (StringUtils.split(param.getTradeBuyRequest().getAddressId(), Constants.CATE_PATH_SPLITTER).length < Constants.THREE) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
            }
        } else {
            address = Optional.ofNullable(this.customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest))
                    .map(BaseResponse::getContext)
                    .orElse(null);
            if (Objects.nonNull(address)) {
                List<Long> addressIds = Lists.newArrayList(address.getProvinceId(), address.getCityId(), address.getAreaId(), address.getStreetId());
                param.getTradeBuyRequest().setAddressId(StringUtils.join(addressIds, Constants.CATE_PATH_SPLITTER));
            } else {
                param.getTradeBuyRequest().setAddressId(null);
            }
        }

    }

    /**
     * @description 校验店铺
     * @author  edz
     * @date: 2023/6/30 15:09
     * @return void
     */
    public void verifyStore(TradeConfirmParam param){
        // 校验店铺状态
        if (!verifyService.checkStore(param.getStores())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }
    }

    /**
     * @description 获取用户提货卡信息
     * @author  edz
     * @date: 2023/6/30 15:04
     * @param userGiftCardId
     * @param customerId
     * @return com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO
     */
    public UserGiftCardInfoVO getUserGiftCard(Long userGiftCardId, String customerId){
        return userGiftCardProvider.getUserGiftCardDetail(UserGiftCardDetailQueryRequest.builder()
                        .userGiftCardId(userGiftCardId)
                        .customerId(customerId)
                        .build())
                .getContext()
                .getUserGiftCardInfoVO();
    }

    public void validatePickUpCard(TradeConfirmParam param) {
        UserGiftCardInfoVO userGiftCardInfoVO = param.getUserGiftCardInfoVO();
        if (Objects.isNull(userGiftCardInfoVO)){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
        }
        // 提货卡有效期校验
        switch (userGiftCardInfoVO.getExpirationType()){
            case MONTH:
                Long rangeMonth = userGiftCardInfoVO.getRangeMonth();
                LocalDateTime activationTime = userGiftCardInfoVO.getActivationTime();
                if (activationTime.plusMonths(rangeMonth).isBefore(LocalDateTime.now())){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
                }
                break;
            case SPECIFIC_TIME:
                LocalDateTime expirationTime = userGiftCardInfoVO.getExpirationTime();
                if (expirationTime.isBefore(LocalDateTime.now())){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
                }
                break;
            default:
                log.error("未有匹配，默认输出");
                break;
        }

        // 提货卡状态校验
        if (Objects.nonNull(userGiftCardInfoVO.getInvalidStatus())){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
        }

        // 适用商品校验
        param.getTradeBuyRequest().getTradeItemRequests().forEach(g -> {
            if (!userGiftCardInfoVO.getSkuIdList().contains(g.getSkuId())){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050177);
            }
        });

        int total = param.getTradeBuyRequest().getTradeItemRequests().size();
        Integer i = userGiftCardInfoVO.getScopeGoodsNum();
        if (i == -1 && total > 1){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050176, new Object[]{1});
        } else if (i > 0 && total > i){
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050176, new Object[]{i});
        }
    }
}
