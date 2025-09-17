package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelCustomerRequest;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.common.GoodsInfoTradeRequest;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoBaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.marketing.api.provider.countPrice.TradeCountMarketingPriceProvider;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionCacheQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.marketingsuits.MarketingSuitsQueryProvider;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountMarketingPriceRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTransRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGoodsRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingMapGetByGoodsIdRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountPricePluginResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardUseResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingMapGetByGoodsIdResponse;
import com.wanmi.sbc.marketing.bean.constant.MarketingPluginConstant;
import com.wanmi.sbc.marketing.bean.dto.CountCouponPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.enums.AuditState;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.GiftCardTradeCommitVO;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.optimization.trade1.commit.common.Trade1CommitBuilder;
import com.wanmi.sbc.order.optimization.trade1.commit.common.Trade1CommitPriceUtil;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitGetDataInterface;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitProcessInterface;
import com.wanmi.sbc.order.payingmemberrecord.model.root.PayingMemberRecord;
import com.wanmi.sbc.order.payingmemberrecord.service.PayingMemberRecordService;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Freight;
import com.wanmi.sbc.order.trade.model.entity.value.ProviderFreight;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.*;
import com.wanmi.sbc.order.trade.repository.GrouponInstanceRepository;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.trade.service.commit.FreightService;
import com.wanmi.sbc.order.util.mapper.OrderMapper;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.Duration.between;

/**
 * @description 社区团购订单
 * @author edz
 * @date 2023/7/27 10:44
 */
@Service
@Slf4j
public class CommunityCommitProcessService implements Trade1CommitProcessInterface {

    @Autowired CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired TradeCacheService tradeCacheService;

    @Autowired Trade1CommitGetDataInterface getDataInterface;

    @Autowired GeneratorService generatorService;

    @Autowired MarketingSuitsQueryProvider marketingSuitsQueryProvider;

    @Autowired TradeCountMarketingPriceProvider tradeCountMarketingPriceProvider;

    @Autowired FreightService freightService;

    @Autowired GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired DistributionCacheQueryProvider distributionCacheQueryProvider;

    @Autowired DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired AuditQueryProvider auditQueryProvider;

    @Autowired GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired GrouponInstanceRepository grouponInstanceRepository;

    @Autowired Trade1CommitBuilder build;

    @Autowired OrderMapper orderMapper;

    @Autowired MarketingQueryProvider marketingQueryProvider;

    @Autowired protected RedisUtil redisService;

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private PayingMemberRecordService payingMemberRecordService;

    @Autowired protected ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired private RedisUtil redisUtil;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Override
    public List<Trade> buildTrade(TradeCommitRequest tradeCommitRequest, Trade1CommitParam param) {
        String customerId = tradeCommitRequest.getOperator().getUserId();
        List<TradeItemGroup> tradeItemGroups = param.getSnapshot().getItemGroups();
        TradeItemSnapshot snapshot = param.getSnapshot();
        Map<Long, TradeItemGroup> tradeItemGroupsMap =
                tradeItemGroups.stream()
                        .collect(
                                Collectors.toMap(
                                        g -> g.getSupplier().getStoreId(), Function.identity()));
        List<StoreVO> storeVOList = param.getStoreVOS();
        CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapRequest =
                new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
        customerLevelMapRequest.setCustomerId(customerId);
        customerLevelMapRequest.setStoreIds(new ArrayList<>(tradeItemGroupsMap.keySet()));
        Map<Long, CommonLevelVO> storeLevelMap =
                customerLevelQueryProvider
                        .listCustomerLevelMapByCustomerIdAndIdsByDefault(customerLevelMapRequest)
                        .getContext()
                        .getCommonLevelVOMap();
        param.setStoreLevelMap(storeLevelMap);
        List<Trade> trades = new ArrayList<>();
        Map<Long, StoreVO> storeMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(storeVOList)) {
            storeMap.putAll(
                    storeVOList.stream()
                            .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity())));
        }

        Map<Long, StoreCommitInfoDTO> storeCommitInfoMap =
                tradeCommitRequest.getStoreCommitInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        StoreCommitInfoDTO::getStoreId, Function.identity()));

        // 社区团购自提信息
        Map<Long, PickSettingInfo> finalPickUpInfoMap = new HashMap<>();
        if (StringUtils.isNotBlank(tradeCommitRequest.getCommunityPickUpId())){
            CommunityLeaderPickupPointVO communityLeaderPickupPointVO = param.getCommunityLeaderPickupPointVOS().get(0);
            PickSettingInfo pickSettingInfo = new PickSettingInfo();
            pickSettingInfo.setStoreId(storeVOList.get(0).getStoreId());
            pickSettingInfo.setName(communityLeaderPickupPointVO.getPickupPointName());
            pickSettingInfo.setProvinceId(communityLeaderPickupPointVO.getPickupProvinceId());
            pickSettingInfo.setCityId(communityLeaderPickupPointVO.getPickupCityId());
            pickSettingInfo.setAreaId(communityLeaderPickupPointVO.getPickupAreaId());
            pickSettingInfo.setStreetId(communityLeaderPickupPointVO.getPickupStreetId());
            pickSettingInfo.setPickupAddress(communityLeaderPickupPointVO.getAddress());
            pickSettingInfo.setPhone(communityLeaderPickupPointVO.getContactNumber());
            pickSettingInfo.setRemark(communityLeaderPickupPointVO.getPickupTime());
            pickSettingInfo.setAreaCode(StringUtils.EMPTY);
            pickSettingInfo.setLatitude(communityLeaderPickupPointVO.getLat());
            pickSettingInfo.setLongitude(communityLeaderPickupPointVO.getLng());
            finalPickUpInfoMap.put(storeVOList.get(0).getStoreId(), pickSettingInfo);
        }
        // 查询订单在途是否可退货设置
        Boolean transitReturn = getDataInterface.isTransitReturn();
        // 生成父订单号
        String parentId = generatorService.generatePoId();

        //查询付费会员等级
        List<PayingMemberLevelVO> payingMemberLevelVOList = payingMemberLevelQueryProvider
                .listByCustomerId(PayingMemberLevelCustomerRequest.builder()
                        .customerId(customerId).build())
                .getContext().getPayingMemberLevelVOList();
        PayingMemberLevelVO payingMemberLevelVO = CollectionUtils.isNotEmpty(payingMemberLevelVOList)
                ? payingMemberLevelVOList.get(NumberUtils.INTEGER_ZERO) : null;
        PayingMemberRecord record = payingMemberLevelVO != null
                ? payingMemberRecordService.findActiveRecord(customerId, payingMemberLevelVO.getLevelId())
                : null;

        param.getSnapshot()
                .getItemGroups()
                .forEach(
                        group -> {
                            Trade trade = new Trade();

                            Long storeId = group.getSupplier().getStoreId();
                            StoreVO storeVo = storeMap.get(storeId);
                            CommonLevelVO commonLevelVO = storeLevelMap.get(storeId);
                            StoreCommitInfoDTO storeCommitInfo = storeCommitInfoMap.get(storeId);

                            // 1、设置父单号
                            trade.setParentId(parentId);
                            // 2、设置订单号
                            trade.setId(generatorService.generateTid());
                            // 3、设置订单买家信息
                            trade.setBuyer(
                                    build.buildBuyer(
                                            storeVo, commonLevelVO, param.getCustomerVO()));
                            // 4、设置订单卖家信息
                            trade.setSupplier(group.getSupplier());
                            // 5、设置收货地址
                            trade.setConsignee(build.buildConsignee(tradeCommitRequest, param));
                            // 6、设置发票信息
                            trade.setInvoice(
                                    build.buildInvoice(
                                            storeCommitInfo, param.getCustomerDeliveryAddressVO()));

                            // 7、配送方式
                            trade.setDeliverWay(storeCommitInfo.getDeliverWay());
                            // 8、设置支付方式
                            trade.setPayInfo(build.buildPayInfo(storeCommitInfo.getPayType()));

                            // 9、设置备注
                            trade.setBuyerRemark(storeCommitInfo.getBuyerRemark());
                            // 10、附件
                            trade.setEncloses(storeCommitInfo.getEncloses());
                            // 11、请求ip
                            trade.setRequestIp(tradeCommitRequest.getOperator().getIp());

                            // 12、订单价格 先设置空对象，后续单独处理
                            trade.setTradePrice(new TradePrice());
                            // 13、订单来源方
                            trade.setPlatform(Platform.CUSTOMER);

                            // 14、订单来源--区分h5,pc,app,小程序,代客下单
                            trade.setOrderSource(tradeCommitRequest.getOrderSource());
                            // 15、订单类型0：普通订单；1：积分订单；
                            trade.setOrderType(OrderType.NORMAL_ORDER);
                            // 16、分享人id
                            trade.setShareUserId(tradeCommitRequest.getShareUserId());

                            // 17、自提信息
                            build.buildPick(trade, storeId, finalPickUpInfoMap);

                            // 19、在途订单是否可退
                            trade.setTransitReturn(transitReturn);
                            // 20、支付顺序，先货后款/先款后货
                            trade.setPaymentOrder(build.buildPayment());

                            // 21、订单明细
                            trade.setTradeItems(
                                    build.buildItem(
                                            group.getTradeItems(),
                                            param.getGoodsInfoTradeVOS(),
                                            param.getSkuIdToActivityPrice()
                                    ));

                            // 22、设置第三方类型
                            trade.setThirdPlatformTypes(
                                    trade.getTradeItems().stream()
                                            .filter(i -> i.getThirdPlatformType() != null)
                                            .map(TradeItem::getThirdPlatformType)
                                            .collect(Collectors.toList()));

                            // 27、设置订单状态
                            trade.setTradeState(build.buildTradeState());

                            // 28、设置营销
                            if (CollectionUtils.isNotEmpty(group.getTradeMarketingList())) {
                                trade.setTradeMarketings(
                                        orderMapper.tradeMarketingDTOsToTradeMarketingVOs(
                                                group.getTradeMarketingList()));
                            } else {
                                trade.setTradeMarketings(new ArrayList<>());
                            }

                            // 29、设置订单标识
                            trade.setOrderTag(group.getOrderTag());

                            // 31、付费会员信息
                            trade.setPayingMemberInfo(
                                    build.buildPayingMemberInfo(payingMemberLevelVO, record));

                            //33. 默认支付版本号10。
                            // 为什么是10不是1呢？版本号的增长使用了在更新语句中直接+1的操作，所以mongo中存的是数字类型。存01最终就是1。
                            // 版本号是和订单ID拼接一块使用的。为了保证拼接后位数相同所以从10开始
                            trade.setPayVersion(10);

                            trades.add(trade);
                        });
        return trades;
    }

    @Override
    public void priceProcess(List<Trade> tradeList) {
        tradeList.forEach(Trade1CommitPriceUtil::calc);
    }

    @Override
    public void marketingProcess(
            List<Trade> tradeList, Trade1CommitParam param, TradeCommitRequest request) {
        for (Trade trade : tradeList) {

            // 设置价格
            this.setPluginPrice(
                    trade, param.getGoodsTradePluginResponse().getSkuMarketingLabelMap(), param.getSkuIdToActivityPrice());
        }
        // 其他营销
        this.marketingPriceProcess(
                param.getSnapshot(),
                param.getGoodsTradePluginResponse().getSkuMarketingLabelMap(),
                tradeList,
                request);

        // 满赠
        this.giftProcess(tradeList, param.getCustomerVO(), request.forceCommit);

        // 加价购
        this.preferentialProcess(tradeList, param.getCustomerVO(), request.forceCommit);
    }

    /**
     * 设置具有营销插件价格的价格
     *
     * @param trade
     * @param marketingMap
     */
    private void setPluginPrice(
            Trade trade, Map<String, List<MarketingPluginLabelDetailVO>> marketingMap, Map<String, BigDecimal> skuIdToActivityPrice) {
        Boolean buyCycleFlag = trade.getOrderTag().getBuyCycleFlag();
        trade.getTradeItems()
                .forEach(
                        i -> {
                            BigDecimal activityPrice = skuIdToActivityPrice.get(i.getSkuId());
                            if (Objects.nonNull(activityPrice)) {
                                return;
                            }
                            Long num = i.getNum();
                            if (buyCycleFlag) {
                                num = num * BigDecimal.valueOf(i.getBuyCycleNum()).longValue();
                            }
                            if (MapUtils.isNotEmpty(marketingMap)) {
                                List<MarketingPluginLabelDetailVO> marketingPluginLabel =
                                        marketingMap.get(i.getSkuId());
                                if (CollectionUtils.isNotEmpty(marketingPluginLabel)) {
                                    Long finalNum = num;
                                    marketingPluginLabel.forEach(
                                            m -> {
                                                if (MarketingPluginConstant.PRICE_EXIST.contains(
                                                                m.getMarketingType())
                                                        && m.getPluginPrice() != null) {

                                                    // 如果营销插件包含拼团价但是订单不是拼团订单
                                                    boolean grouponFlag =
                                                            m.getMarketingType()
                                                                            .equals(
                                                                                    MarketingPluginType
                                                                                            .GROUPON
                                                                                            .getId())
                                                                    && !trade.getGrouponFlag();
                                                    // 如果营销插件包含预售价但是订单不是预售订单
                                                    boolean bookingFlag =
                                                            m.getMarketingType()
                                                                            .equals(
                                                                                    MarketingPluginType
                                                                                            .BOOKING_SALE
                                                                                            .getId())
                                                                    && !trade
                                                                            .getIsBookingSaleGoods();
                                                    if (grouponFlag || bookingFlag) {
                                                        return;
                                                    }
                                                    if (MarketingPluginType.PAYING_MEMBER.getId() == m.getMarketingType()
                                                            && Objects.nonNull(trade.getPayingMemberInfo())) {
                                                        BigDecimal discount = i.getOriginalPrice().subtract(m.getPluginPrice()).multiply(new BigDecimal(finalNum));
                                                        if (discount.compareTo(BigDecimal.ZERO) > 0) {
                                                            BigDecimal newGoodsDiscount = trade.getPayingMemberInfo().getGoodsDiscount().add(discount);
                                                            trade.getPayingMemberInfo().setGoodsDiscount(newGoodsDiscount);
                                                        }
                                                    }
                                                    i.setPrice(m.getPluginPrice());
                                                    i.setLevelPrice(m.getPluginPrice());
                                                    i.setSplitPrice(
                                                            m.getPluginPrice()
                                                                    .multiply(
                                                                            new BigDecimal(
                                                                                    finalNum)));
                                                }
                                            });
                                }
                            }

                            i.setSplitPrice(i.getPrice().multiply(new BigDecimal(num)));
                        });
    }

    private void marketingPriceProcess(
            TradeItemSnapshot snapshot,
            Map<String, List<MarketingPluginLabelDetailVO>> marketingMap,
            List<Trade> tradeList,
            TradeCommitRequest request) {
        if (MapUtils.isNotEmpty(marketingMap)
                && tradeList.get(0).getStoreBagsFlag().equals(DefaultFlag.NO)) {
            Map<Long, List<CountPriceMarketingDTO>> storeMarketingMap = new HashMap<>();
            Map<Long, MarketingPluginLabelDetailVO> marketingPluginLabelDetailVOMap =
                    new HashMap<>();
            Map<Long, Trade> tradeMap =
                    tradeList.stream()
                            .collect(
                                    Collectors.toMap(
                                            i -> i.getSupplier().getStoreId(),
                                            Function.identity()));
            marketingMap.forEach(
                    (s, marketingPluginLabelDetailVOS) -> {
                        marketingPluginLabelDetailVOS.forEach(
                                m -> {
                                    if (MarketingPluginConstant.SELECT_ONE.contains(
                                            m.getMarketingType())
                                    || MarketingPluginType.PREFERENTIAL.getId() == m.getMarketingType()) {
                                        marketingPluginLabelDetailVOMap.put(
                                                ((Number) m.getMarketingId()).longValue(), m);
                                    }
                                });
                    });
            snapshot.getItemGroups()
                    .forEach(
                            g -> {
                                if (CollectionUtils.isNotEmpty(g.getTradeMarketingList())) {
                                    storeMarketingMap.put(
                                            g.getSupplier().getStoreId(),
                                            orderMapper.tradeMarketingDTOSTOCountPriceMarketingDTOS(
                                                    g.getTradeMarketingList()));
                                }
                            });
            if (MapUtils.isNotEmpty(storeMarketingMap)) {
                List<CountPriceDTO> countPriceVOList = new ArrayList<>();
                storeMarketingMap.forEach(
                        (s, countPriceMarketingDTOS) -> {
                            countPriceMarketingDTOS.forEach(
                                    m -> {
                                        m.setDetail(
                                                marketingPluginLabelDetailVOMap
                                                        .get(m.getMarketingId())
                                                        .getDetail());
                                        m.setMarketingPluginType(
                                                MarketingPluginType.fromId(
                                                        marketingPluginLabelDetailVOMap
                                                                .get(m.getMarketingId())
                                                                .getMarketingType()));
                                    });
                            CountPriceDTO countPriceDTO = new CountPriceDTO();
                            countPriceDTO.setStoreId(s);
                            countPriceDTO.setMarketingVOList(countPriceMarketingDTOS);
                            Trade trade = tradeMap.get(s);
                            List<TradeItem> tradeItems = trade.getTradeItems();
                            Boolean buyCycleFlag = trade.getOrderTag().getBuyCycleFlag();
                            List<CountPriceGoodsInfoDTO> countPriceGoodsInfoDTOS = this.tradeItemListToCountCouponPriceGoodsInfoDTO(
                                    null,
                                    tradeItems,
                                    CountPriceGoodsInfoDTO.class);
                            if (buyCycleFlag) {
                                countPriceGoodsInfoDTOS = countPriceGoodsInfoDTOS.parallelStream()
                                        .peek(countPriceGoodsInfoDTO ->
                                                countPriceGoodsInfoDTO.setNum(
                                                        countPriceGoodsInfoDTO.getNum() *
                                                                BigDecimal.valueOf(trade.getTradeBuyCycle().getDeliveryCycleNum())
                                                                        .longValue()
                                                )
                                        ).collect(Collectors.toList());
                            }
                            countPriceDTO.setGoodsInfoVOList(countPriceGoodsInfoDTOS);
                            countPriceVOList.add(countPriceDTO);
                        });
                if (CollectionUtils.isNotEmpty(countPriceVOList)) {
                    TradeCountMarketingPriceRequest tradeCountMarketingPriceRequest =
                            TradeCountMarketingPriceRequest.builder()
                                    .countPriceVOList(countPriceVOList)
                                    .customerId(request.getOperator().getUserId())
                                    .forceCommit(request.forceCommit)
                                    .build();
                    TradeCountPricePluginResponse response =
                            tradeCountMarketingPriceProvider
                                    .tradeCountMarketingPrice(tradeCountMarketingPriceRequest)
                                    .getContext();
                    if (response != null) {
                        Map<Long, List<CountPriceItemVO>> storeResponse =
                                response.getCountPriceVOList().stream()
                                        .collect(
                                                Collectors.groupingBy(
                                                        CountPriceItemVO::getStoreId));
                        tradeList.forEach(
                                trade -> {
                                    List<CountPriceItemVO> countPriceItemVOS =
                                            storeResponse.get(trade.getSupplier().getStoreId());
                                    if (CollectionUtils.isNotEmpty(countPriceItemVOS)) {
                                        countPriceItemVOS.forEach(
                                                countPriceItemVO -> {
                                                    if (countPriceItemVO != null) {
                                                        trade.setTradeMarketings(
                                                                countPriceItemVO
                                                                        .getTradeMarketings());
                                                        // 设置赠品
                                                        if (CollectionUtils.isNotEmpty(
                                                                countPriceItemVO.getGiftList())) {
                                                            List<TradeItem> gifts =
                                                                    countPriceItemVO
                                                                            .getGiftList()
                                                                            .stream()
                                                                            .map(
                                                                                    cm ->
                                                                                            TradeItem
                                                                                                    .builder()
                                                                                                    .skuId(
                                                                                                            cm
                                                                                                                    .getProductId())
                                                                                                    .num(
                                                                                                            cm
                                                                                                                    .getProductNum())
                                                                                                    .marketingIds(Collections.singletonList(cm.getMarketingId()))
                                                                                                    .build())
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toList());
                                                            // 过滤多赠品符合当前选择赠品的detail
                                                            trade.getTradeMarketings()
                                                                    .forEach(
                                                                            m -> {
                                                                                if (m
                                                                                                .getMarketingType()
                                                                                        == MarketingType
                                                                                                .GIFT) {
                                                                                    m.getGiftLevel()
                                                                                            .setFullGiftDetailList(
                                                                                                    m
                                                                                                            .getGiftLevel()
                                                                                                            .getFullGiftDetailList()
                                                                                                            .stream()
                                                                                                            .filter(
                                                                                                                    d ->
                                                                                                                            m.getGiftIds()
                                                                                                                                    .contains(
                                                                                                                                            d
                                                                                                                                                    .getProductId()))
                                                                                                            .collect(
                                                                                                                    Collectors
                                                                                                                            .toList()));
                                                                                }
                                                                            });
                                                            trade.setGifts(gifts);
                                                        }

                                                        // 设置加价购
                                                        List<CountPriceItemForPreferentialVO> countPriceItemForPreferentialVOList =
                                                                countPriceItemVO.getPreferentialGoodsList();
                                                        if (CollectionUtils.isNotEmpty(countPriceItemForPreferentialVOList)) {
                                                            List<TradeItem> preferentialGoodsList = countPriceItemVO
                                                                    .getPreferentialGoodsList()
                                                                    .stream()
                                                                    .map(cm -> TradeItem
                                                                                    .builder()
                                                                                    .skuId(cm.getGoodsInfoId())
                                                                                    .splitPrice(cm.getPreferentialAmount())
                                                                                    .marketingIds(Collections.singletonList(cm.getMarketingId()))
                                                                                    .num(1L)
                                                                                    .build())
                                                                    .collect(
                                                                            Collectors
                                                                                    .toList());
                                                            trade.setPreferential(preferentialGoodsList);
                                                        }
                                                        Map<String, CountPriceItemGoodsInfoVO>
                                                                skuMap =
                                                                        countPriceItemVO
                                                                                .getGoodsInfoList()
                                                                                .stream()
                                                                                .collect(
                                                                                        Collectors
                                                                                                .toMap(
                                                                                                        CountPriceItemGoodsInfoVO
                                                                                                                ::getGoodsInfoId,
                                                                                                        Function
                                                                                                                .identity()));
                                                        trade.getTradeItems()
                                                                .forEach(
                                                                        tradeItem -> {
                                                                            CountPriceItemGoodsInfoVO
                                                                                    priceItemGoodsInfoVO =
                                                                                            skuMap
                                                                                                    .get(
                                                                                                            tradeItem
                                                                                                                    .getSkuId());
                                                                            tradeItem.setSplitPrice(
                                                                                    priceItemGoodsInfoVO
                                                                                            .getSplitPrice());
                                                                        });
                                                        trade.getTradeMarketings().forEach(
                                                                m->{
                                                                    trade.getTradeItems()
                                                                            .stream()
                                                                            .filter(i->m.getSkuIds().contains(i.getSkuId()))
                                                                            .forEach(i->{
                                                                        CountPriceItemGoodsInfoVO
                                                                                priceItemGoodsInfoVO =
                                                                                skuMap
                                                                                        .get(
                                                                                                i.getSkuId());
                                                                        List<TradeItem.MarketingSettlement> settlements =
                                                                                new ArrayList<>();
                                                                        TradeItem.MarketingSettlement marketingSettlement =
                                                                                TradeItem.MarketingSettlement.builder()
                                                                                        .marketingType(m.getMarketingType())
                                                                                        .marketingId(m.getMarketingId())
                                                                                        .splitPrice(priceItemGoodsInfoVO.getSplitPrice())
                                                                                        .discountsAmount(priceItemGoodsInfoVO.getDiscountsAmount())
                                                                                        .build();
                                                                        if(m.getMarketingType().equals(MarketingType.GIFT)){
                                                                            marketingSettlement.setDiscountsAmount(BigDecimal.ZERO);
                                                                        }
                                                                        settlements.add(marketingSettlement);
                                                                        i.setMarketingSettlements(settlements);
                                                                    });
                                                                }
                                                        );
                                                    }
                                                });
                                    }
                                });
                    }
                }
            }
        }
    }

    @Override
    public void couponProcess(List<Trade> tradeList, TradeCommitRequest request) {
        Map<Long, StoreCommitInfoDTO> storeCommitInfoDTOMap =
                request.getStoreCommitInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        StoreCommitInfoDTO::getStoreId, Function.identity()));
        // 设置店铺优惠券
        tradeList.forEach(
                trade -> {
                    StoreCommitInfoDTO storeCommitInfoDTO =
                            storeCommitInfoDTOMap.get(trade.getSupplier().getStoreId());
                    if (storeCommitInfoDTO != null
                            && StringUtils.isNotEmpty(storeCommitInfoDTO.getCouponCodeId())) {
                        List<CountCouponPriceGoodsInfoDTO> countCouponPriceGoodsInfoDTOS =
                                this.tradeItemListToCountCouponPriceGoodsInfoDTO(trade.getPreferential(),
                                        trade.getTradeItems(), CountCouponPriceGoodsInfoDTO.class);
                        Map<String, List<CountCouponPriceGoodsInfoDTO>> MarketingIdToDTO = countCouponPriceGoodsInfoDTOS
                                .stream()
                                .filter(g -> Objects.nonNull(g.getPriceRate()))
                                .collect(Collectors.groupingBy(CountCouponPriceGoodsInfoDTO::getGoodsInfoId));
                        // <marketingId, <goodsInfoId, 加价购活动商品占同sku商品总价比例>>
                        Map<String, Map<Long, BigDecimal>> marketingToSkuRateMap = new HashMap<>();
                        MarketingIdToDTO.forEach((skuId, dtos) -> {
                            Map<String, Map<Long,BigDecimal>> skuIdToPriceRateMap =
                                    new HashMap<>(countCouponPriceGoodsInfoDTOS.stream()
                                            .filter(g -> Objects.nonNull(g.getPriceRate()))
                                            .collect(Collectors.toMap(CountPriceGoodsInfoDTO::getGoodsInfoId,
                                                    CountPriceGoodsInfoDTO::getPriceRate)));
                            marketingToSkuRateMap.put(skuId,skuIdToPriceRateMap.get(skuId));
                        });

                        TradeCountCouponPriceRequest countCouponPriceRequest =
                                TradeCountCouponPriceRequest.builder()
                                        .couponCodeId(storeCommitInfoDTO.getCouponCodeId())
                                        .forceCommit(request.forceCommit)
                                        .customerId(request.getOperator().getUserId())
                                        .countPriceGoodsInfoDTOList(countCouponPriceGoodsInfoDTOS)
                                        .build();

                        // 请求店铺优惠券计算
                        TradeCountCouponPriceResponse tradeCountCouponPriceResponse =
                                tradeCountMarketingPriceProvider
                                        .tradeCountCouponPrice(countCouponPriceRequest)
                                        .getContext();
                        if (tradeCountCouponPriceResponse != null) {
                            CountCouponPriceVO countCouponPriceVO =
                                    tradeCountCouponPriceResponse.getCountCouponPriceVO();
                            if (countCouponPriceVO != null
                                    && CollectionUtils.isNotEmpty(
                                            countCouponPriceVO.getGoodsInfoIds())) {
                                trade.setTradeCoupon(
                                        orderMapper.countCouponPriceVOTOTradeCouponVO(
                                                countCouponPriceVO));
                                List<CountPriceItemGoodsInfoVO> goodsInfoList =
                                        tradeCountCouponPriceResponse.getGoodsInfoList();
                                Map<String, CountPriceItemGoodsInfoVO> goodsInfoVOMap =
                                        goodsInfoList.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                CountPriceItemGoodsInfoVO
                                                                        ::getGoodsInfoId,
                                                                Function.identity()));
                                Map<String, BigDecimal> couponPriceMap = new HashMap<>();
                                Map<String, BigDecimal> reducePriceMap = new HashMap<>();

                                // 已经优惠金额
                                Map<String, BigDecimal> alreadyPriceMap = new HashMap<>();
                                trade.getPreferential().forEach(item -> {
                                    if (countCouponPriceVO
                                            .getGoodsInfoIds()
                                            .contains(item.getSkuId())) {
                                        BigDecimal rate =
                                                marketingToSkuRateMap.getOrDefault(item.getSkuId(),
                                                        new HashMap<>()).get(item.getMarketingIds().get(0));
                                        CountPriceItemGoodsInfoVO
                                                priceItemGoodsInfoVO = goodsInfoVOMap.get(item.getSkuId());

                                        // 获取优惠券优惠金额
                                        BigDecimal reducePrice =
                                                priceItemGoodsInfoVO.getDiscountsAmount().multiply(rate).setScale(2,
                                                        RoundingMode.HALF_UP);
                                        // 验证优惠金额是否已超出
                                        if(alreadyPriceMap.containsKey(item.getSkuId())) {
                                            BigDecimal alreadyReducePrice = alreadyPriceMap.get(item.getSkuId());
                                            reducePrice =
                                                    reducePrice.compareTo(priceItemGoodsInfoVO.getDiscountsAmount().subtract(alreadyReducePrice)) > 0
                                                            ? priceItemGoodsInfoVO.getDiscountsAmount().subtract(alreadyReducePrice)
                                                            : reducePrice;
                                            alreadyReducePrice = alreadyReducePrice.add(reducePrice);
                                            alreadyPriceMap.put(item.getSkuId(), alreadyReducePrice);
                                        } else {
                                            alreadyPriceMap.put(item.getSkuId(), reducePrice);
                                        }

                                        BigDecimal preferentialPrice = item.getSplitPrice().subtract(reducePrice);
                                        BigDecimal d = couponPriceMap.getOrDefault(item.getSkuId(), BigDecimal.ZERO);
                                        couponPriceMap.put(item.getSkuId(), d.add(preferentialPrice));
                                        item.setSplitPrice(preferentialPrice);

                                        // 优惠券
                                        TradeItem.CouponSettlement
                                                couponSettlement =
                                                orderMapper
                                                        .countCouponPriceVOTOCouponSettlement(
                                                                countCouponPriceVO);
                                        BigDecimal reducePriceItem = reducePriceMap.getOrDefault(item.getSkuId(), BigDecimal.ZERO);
                                        reducePriceMap.put(item.getSkuId(), reducePriceItem.add(reducePrice));
                                        couponSettlement.setSplitPrice(
                                                item.getSplitPrice());
                                        couponSettlement.setReducePrice(reducePrice);
                                        if (CollectionUtils.isEmpty(
                                                item.getCouponSettlements())) {
                                            item.setCouponSettlements(
                                                    new ArrayList());
                                        }
                                        // 添加tradeItem的优惠券数据
                                        item.getCouponSettlements()
                                                .add(couponSettlement);
                                    }
                                });
                                couponPriceMap.forEach((k, v) -> {
                                    CountPriceItemGoodsInfoVO
                                            priceItemGoodsInfoVO = goodsInfoVOMap.get(k);
                                    if (Objects.nonNull(priceItemGoodsInfoVO)){
                                        priceItemGoodsInfoVO.setSplitPrice(priceItemGoodsInfoVO.getSplitPrice().subtract(v));
                                        priceItemGoodsInfoVO.setDiscountsAmount(priceItemGoodsInfoVO.getDiscountsAmount()
                                                .subtract(reducePriceMap.get(k)));
                                    }
                                });
                                trade.getTradeItems()
                                        .forEach(
                                                item -> {
                                                    if (countCouponPriceVO
                                                            .getGoodsInfoIds()
                                                            .contains(item.getSkuId())) {
                                                        item.setSplitPrice(
                                                                goodsInfoVOMap
                                                                        .get(item.getSkuId())
                                                                        .getSplitPrice());
                                                        TradeItem.CouponSettlement
                                                                couponSettlement =
                                                                        orderMapper
                                                                                .countCouponPriceVOTOCouponSettlement(
                                                                                        countCouponPriceVO);

                                                        couponSettlement.setSplitPrice(
                                                                item.getSplitPrice());
                                                        couponSettlement.setReducePrice(goodsInfoVOMap.get(item.getSkuId()).getDiscountsAmount());
                                                        if (CollectionUtils.isEmpty(
                                                                item.getCouponSettlements())) {
                                                            item.setCouponSettlements(
                                                                    new ArrayList());
                                                        }
                                                        // 添加tradeItem的优惠券数据
                                                        item.getCouponSettlements()
                                                                .add(couponSettlement);
                                                    }
                                                });
                            }
                        }
                    }
                });

        // 设置平台优惠券
        if (StringUtils.isNotEmpty(request.getCommonCodeId())) {
            List<CountCouponPriceGoodsInfoDTO> countCouponPriceGoodsInfoDTOS = new ArrayList<>();
            for (Trade trade : tradeList) {
                countCouponPriceGoodsInfoDTOS.addAll(
                        this.tradeItemListToCountCouponPriceGoodsInfoDTO(trade.getPreferential(),
                                trade.getTradeItems(), CountCouponPriceGoodsInfoDTO.class));
            }
            Map<Long, List<CountCouponPriceGoodsInfoDTO>> storeIdToCountCouponPriceGoodsInfoMap =
                    countCouponPriceGoodsInfoDTOS.stream().collect(Collectors.groupingBy(CountCouponPriceGoodsInfoDTO::getStoreId));
            // <storeId, <marketingId, <goodsInfoId, 加价购活动商品占同sku商品总价比例>>>
            Map<Long, Map<String, Map<Long, BigDecimal>>> rateMap = new HashMap<>();
            storeIdToCountCouponPriceGoodsInfoMap.forEach((storeId, infos) -> {
                Map<String, List<CountCouponPriceGoodsInfoDTO>> MarketingIdToDTO = infos.stream()
                        .filter(g -> Objects.nonNull(g.getPriceRate()))
                        .collect(Collectors.groupingBy(CountCouponPriceGoodsInfoDTO::getGoodsInfoId));
                // <marketingId, <goodsInfoId, 加价购活动商品占同sku商品总价比例>>
                Map<String, Map<Long, BigDecimal>> marketingToSkuRateMap = new HashMap<>();
                MarketingIdToDTO.forEach((skuId, dtos) -> {
                    Map<String, Map<Long,BigDecimal>> skuIdToPriceRateMap =
                            new HashMap<>(dtos.stream()
                                    .filter(g -> Objects.nonNull(g.getPriceRate()))
                                    .collect(Collectors.toMap(CountPriceGoodsInfoDTO::getGoodsInfoId,
                                            CountPriceGoodsInfoDTO::getPriceRate)));
                    marketingToSkuRateMap.put(skuId, skuIdToPriceRateMap.get(skuId));
                });
                rateMap.put(storeId, marketingToSkuRateMap);
            });

            TradeCountCouponPriceRequest countCouponPriceRequest =
                    TradeCountCouponPriceRequest.builder()
                            .couponCodeId(request.getCommonCodeId())
                            .forceCommit(request.forceCommit)
                            .customerId(request.getOperator().getUserId())
                            .countPriceGoodsInfoDTOList(countCouponPriceGoodsInfoDTOS)
                            .build();

            // 请求优惠券计算
            TradeCountCouponPriceResponse tradeCountCouponPriceResponse =
                    tradeCountMarketingPriceProvider
                            .tradeCountCouponPrice(countCouponPriceRequest)
                            .getContext();
            if (tradeCountCouponPriceResponse != null) {
                CountCouponPriceVO countCouponPriceVO =
                        tradeCountCouponPriceResponse.getCountCouponPriceVO();
                List<CountPriceItemGoodsInfoVO> countPriceItemGoodsInfoVOS =
                        tradeCountCouponPriceResponse.getGoodsInfoList();
                if (countCouponPriceVO != null) {
                    Map<Long, Map<String, CountPriceItemGoodsInfoVO>> storeIdToGoodsInfoMap = new HashMap<>();
                    if (CollectionUtils.isNotEmpty(countPriceItemGoodsInfoVOS)) {
                        Map<Long, List<CountPriceItemGoodsInfoVO>> storeIdToCountPriceItemGoodsInfosMap =
                                countPriceItemGoodsInfoVOS.stream().collect(Collectors.groupingBy(CountPriceItemGoodsInfoVO::getStoreId));
                        storeIdToCountPriceItemGoodsInfosMap.forEach((storeId, info) -> {
                            Map<String, CountPriceItemGoodsInfoVO> goodsInfoVOMap =
                                    info.stream()
                                            .collect(
                                                    Collectors.toMap(
                                                            CountPriceItemGoodsInfoVO::getGoodsInfoId,
                                                            Function.identity()));
                            storeIdToGoodsInfoMap.put(storeId, goodsInfoVOMap);
                        });
                    }
                    tradeList.forEach(
                            trade -> {
                                Map<Long, Map<String, BigDecimal>> couponPriceMap = new HashMap<>();
                                Map<Long, Map<String, BigDecimal>> reducePriceMap = new HashMap<>();
                                // 已经优惠金额
                                Map<String, BigDecimal> alreadyPriceMap = new HashMap<>();
                                trade.getPreferential().forEach(item -> {
                                    if (countCouponPriceVO
                                            .getGoodsInfoIds()
                                            .contains(item.getSkuId())) {
                                        BigDecimal rate = rateMap.get(item.getStoreId())
                                                .getOrDefault(item.getSkuId(), new HashMap<>())
                                                .get(item.getMarketingIds().get(0));
                                        Map<String, CountPriceItemGoodsInfoVO> goodsInfoVOMap =
                                                storeIdToGoodsInfoMap.get(item.getStoreId());
                                        CountPriceItemGoodsInfoVO
                                                priceItemGoodsInfoVO = goodsInfoVOMap.get(item.getSkuId());

                                        //计算优惠金额
                                        BigDecimal newReducePrice =
                                                priceItemGoodsInfoVO.getDiscountsAmount().multiply(rate).setScale(2,RoundingMode.HALF_UP);
                                        // 验证优惠金额是否已超出
                                        if(alreadyPriceMap.containsKey(item.getSkuId())) {
                                            BigDecimal alreadyReducePrice = alreadyPriceMap.get(item.getSkuId());
                                            newReducePrice =
                                                    newReducePrice.compareTo(priceItemGoodsInfoVO.getDiscountsAmount().subtract(alreadyReducePrice)) > 0 ?
                                                            priceItemGoodsInfoVO.getDiscountsAmount().subtract(alreadyReducePrice)
                                                            : newReducePrice;
                                            alreadyReducePrice = alreadyReducePrice.add(newReducePrice);
                                            alreadyPriceMap.put(item.getSkuId(), alreadyReducePrice);
                                        } else {
                                            alreadyPriceMap.put(item.getSkuId(), newReducePrice);
                                        }


                                        BigDecimal preferentialPrice = item.getSplitPrice().subtract(newReducePrice);

                                        Map<String, BigDecimal> temp = couponPriceMap.get(item.getStoreId());
                                        if (Objects.nonNull(temp)){
                                            BigDecimal p = temp.getOrDefault(item.getSkuId(), BigDecimal.ZERO);
                                            temp.put(item.getSkuId(), p.add(preferentialPrice));
                                        } else {
                                            Map<String, BigDecimal> temp1 = new HashMap<>();
                                            temp1.put(item.getSkuId(), preferentialPrice);
                                            couponPriceMap.put(item.getStoreId(), temp1);
                                        }

                                        item.setSplitPrice(preferentialPrice);

                                        // 优惠券
                                        TradeItem.CouponSettlement
                                                couponSettlement =
                                                orderMapper
                                                        .countCouponPriceVOTOCouponSettlement(
                                                                countCouponPriceVO);
                                        couponSettlement.setSplitPrice(
                                                item.getSplitPrice());

                                        couponSettlement.setReducePrice(newReducePrice);

                                        Map<String, BigDecimal> reducePriceMapTemp =
                                                reducePriceMap.get(item.getStoreId());
                                        if (Objects.nonNull(reducePriceMapTemp)){
                                            BigDecimal p = reducePriceMapTemp.getOrDefault(item.getSkuId(), BigDecimal.ZERO);
                                            reducePriceMapTemp.put(item.getSkuId(), p.add(newReducePrice));
                                        } else {
                                            Map<String, BigDecimal> temp1 = new HashMap<>();
                                            temp1.put(item.getSkuId(), newReducePrice);
                                            reducePriceMap.put(item.getStoreId(), temp1);
                                        }


                                        if (CollectionUtils.isEmpty(
                                                item.getCouponSettlements())) {
                                            item.setCouponSettlements(
                                                    new ArrayList());
                                        }
                                        // 添加tradeItem的优惠券数据
                                        item.getCouponSettlements()
                                                .add(couponSettlement);
                                    }
                                });
                                couponPriceMap.forEach((k, map) -> {
                                    Map<String, CountPriceItemGoodsInfoVO> goodsInfoVOMap =
                                            storeIdToGoodsInfoMap.get(k);
                                    if (Objects.nonNull(goodsInfoVOMap)){
                                        goodsInfoVOMap.forEach((x, y) -> {
                                            if (Objects.nonNull(map.get(x))){
                                                y.setSplitPrice(y.getSplitPrice().subtract(map.get(x)));
                                                y.setDiscountsAmount(y.getDiscountsAmount()
                                                        .subtract(reducePriceMap.getOrDefault(k, new HashMap<>())
                                                                .getOrDefault(x, BigDecimal.ZERO)));
                                            }
                                        });
                                    }
                                });
                                trade.getTradeItems()
                                        .forEach(
                                                item -> {
                                                    if (countCouponPriceVO
                                                            .getGoodsInfoIds()
                                                            .contains(item.getSkuId())) {
                                                        Map<String, CountPriceItemGoodsInfoVO> goodsInfoVOMap =
                                                                storeIdToGoodsInfoMap.get(item.getStoreId());
                                                        CountPriceItemGoodsInfoVO
                                                                priceItemGoodsInfoVO = goodsInfoVOMap.get(item.getSkuId());
                                                        item.setSplitPrice(priceItemGoodsInfoVO.getSplitPrice());
                                                        TradeItem.CouponSettlement
                                                                couponSettlement =
                                                                        orderMapper
                                                                                .countCouponPriceVOTOCouponSettlement(
                                                                                        countCouponPriceVO);
                                                        couponSettlement.setSplitPrice(
                                                                item.getSplitPrice());
                                                        couponSettlement.setReducePrice(priceItemGoodsInfoVO.getDiscountsAmount());
                                                        if (CollectionUtils.isEmpty(
                                                                item.getCouponSettlements())) {
                                                            item.setCouponSettlements(
                                                                    new ArrayList());
                                                        }
                                                        // 添加tradeItem的优惠券数据
                                                        item.getCouponSettlements()
                                                                .add(couponSettlement);
                                                    }
                                                });
                                //付费会员优惠金额
                                if (trade.getPayingMemberInfo() != null && MarketingCustomerType.PAYING_MEMBER.equals(countCouponPriceVO.getMarketingCustomerType())) {
                                    trade.getPayingMemberInfo().setCouponDiscount(countCouponPriceVO.getDiscountsAmount());
                                }
                            });
                    // 生成平台券的tradeGroup的id ★原来使用的是uuid有可能会有重复，现在使用parentId作为groupId
                    tradeList.forEach(trade -> trade.setGroupId(trade.getParentId()));
                }
            }
        }
    }

    @Override
    public void pointProcess(
            List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {
        SystemPointsConfigQueryResponse pointsConfig = param.getSystemPointsConfigQueryResponse();
        final BigDecimal pointWorth = BigDecimal.valueOf(pointsConfig.getPointsWorth());
        OrderTag orderTag = tradeList.get(0).getOrderTag();
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        /*
         * 商品积分设置(周期购不支持商品积分)
         */
        if (!buyCycleFlag && EnableStatus.ENABLE.equals(pointsConfig.getStatus())
                && PointsUsageFlag.GOODS.equals(pointsConfig.getPointsUsageFlag())) {
            // 合计商品
            Long sumPoints =
                    tradeList.stream()
                            .flatMap(trade -> trade.getTradeItems().stream())
                            .filter(k -> Objects.nonNull(k.getBuyPoint()))
                            .mapToLong(k -> k.getBuyPoint() * k.getNum())
                            .sum();
            if (sumPoints <= 0) {
                return;
            }

            // 积分均摊，积分合计，不需要平滩价
            tradeList.forEach(
                    trade -> {
                        // 积分均摊
                        trade.getTradeItems().stream()
                                .filter(tradeItem -> Objects.nonNull(tradeItem.getBuyPoint()))
                                .forEach(
                                        tradeItem -> {
                                            tradeItem.setPoints(
                                                    tradeItem.getBuyPoint() * tradeItem.getNum());
                                            tradeItem.setPointsPrice(
                                                    BigDecimal.valueOf(tradeItem.getPoints())
                                                            .divide(
                                                                    pointWorth,
                                                                    2,
                                                                    RoundingMode.HALF_UP));
                                        });
                        trade.getPreferential().stream()
                                .filter(tradeItem -> Objects.nonNull(tradeItem.getBuyPoint()))
                                .forEach(
                                        tradeItem -> {
                                            tradeItem.setPoints(
                                                    tradeItem.getBuyPoint() * tradeItem.getNum());
                                            tradeItem.setPointsPrice(
                                                    BigDecimal.valueOf(tradeItem.getPoints())
                                                            .divide(
                                                                    pointWorth,
                                                                    2,
                                                                    RoundingMode.HALF_UP));
                                        });
                        // 计算积分抵扣额(pointsPrice、points)，并追加积分抵现金额
                        TradePrice tradePrice = trade.getTradePrice();
                        Long points =
                                Trade1CommitPriceUtil.calcSkusTotalPoints(trade.getTradeItems());
                        points = Trade1CommitPriceUtil.calcSkusTotalPoints(trade.getPreferential()) + points;
                        tradePrice.setPoints(points);
                        tradePrice.setBuyPoints(points);
                        tradePrice.setPointsPrice(
                                BigDecimal.valueOf(points)
                                        .divide(pointWorth, 2, RoundingMode.HALF_UP));
                    });
        } else {
            /*
             * 订单积分设置
             */
            // 将buyPoint置零
            tradeList.stream()
                    .flatMap(trade -> trade.getTradeItems().stream())
                    .forEach(tradeItem -> tradeItem.setBuyPoint(0L));
            tradeList.stream()
                    .flatMap(trade -> trade.getPreferential().stream())
                    .forEach(tradeItem -> tradeItem.setBuyPoint(0L));
            tradeList.forEach(
                    trade -> {
                        TradePrice tradePrice = trade.getTradePrice();
                        tradePrice.setBuyPoints(0L);
                        trade.setTradePrice(tradePrice);
                    });
            if (request.getPoints() == null || request.getPoints() <= 0) {
                return;
            }

            List<TradeItem> items =
                    tradeList.stream()
                            .flatMap(trade -> trade.getTradeItems().stream())
                            .collect(Collectors.toList());

            List<TradeItem> preferentialTradeItems =
                    tradeList.stream()
                            .flatMap(trade -> trade.getPreferential().stream())
                            .collect(Collectors.toList());

            // 设置关联商品的积分均摊
            BigDecimal pointsTotalPrice =
                    BigDecimal.valueOf(request.getPoints())
                            .divide(pointWorth, 2, RoundingMode.HALF_UP);
            Trade1CommitPriceUtil.calcPoints(
                    items, preferentialTradeItems, pointsTotalPrice, request.getPoints(), pointWorth);

            // 设置关联商品的均摊价
            BigDecimal total = Trade1CommitPriceUtil.calcSkusTotalPrice(items);
            total = total.add(Trade1CommitPriceUtil.calcSkusTotalPrice(preferentialTradeItems));
            Trade1CommitPriceUtil.calcSplitPrice(items, preferentialTradeItems, total.subtract(pointsTotalPrice),
                    total);

            Map<Long, List<TradeItem>> itemsMap =
                    items.stream().collect(Collectors.groupingBy(TradeItem::getStoreId));
            itemsMap.keySet()
                    .forEach(
                            storeId -> {
                                // 找到店铺对应订单的价格信息
                                Trade trade =
                                        tradeList.stream()
                                                .filter(
                                                        t ->
                                                                t.getSupplier()
                                                                        .getStoreId()
                                                                        .equals(storeId))
                                                .findFirst()
                                                .orElse(null);
                                TradePrice tradePrice = trade.getTradePrice();

                                // 计算积分抵扣额(pointsPrice、points)，并追加至订单优惠金额、积分抵现金额
                                BigDecimal pointsPrice =
                                        Trade1CommitPriceUtil.calcSkusTotalPointsPrice(
                                                itemsMap.get(storeId));
                                Long points =
                                        Trade1CommitPriceUtil.calcSkusTotalPoints(
                                                itemsMap.get(storeId));
                                tradePrice.setPointsPrice(pointsPrice);
                                tradePrice.setPoints(points);
                                tradePrice.setPointWorth(pointsConfig.getPointsWorth());
                                // 重设订单总价
                                tradePrice.setTotalPrice(
                                        tradePrice.getTotalPrice().subtract(pointsPrice));
                            });

            Map<Long, List<TradeItem>> preferentialTradeItemsMap =
                    preferentialTradeItems.stream().collect(Collectors.groupingBy(TradeItem::getStoreId));
            preferentialTradeItemsMap.keySet()
                    .forEach(
                            storeId -> {
                                // 找到店铺对应订单的价格信息
                                Trade trade =
                                        tradeList.stream()
                                                .filter(
                                                        t ->
                                                                t.getSupplier()
                                                                        .getStoreId()
                                                                        .equals(storeId))
                                                .findFirst()
                                                .orElse(null);
                                TradePrice tradePrice = trade.getTradePrice();

                                // 计算积分抵扣额(pointsPrice、points)，并追加至订单优惠金额、积分抵现金额
                                BigDecimal pointsPrice =
                                        Trade1CommitPriceUtil.calcSkusTotalPointsPrice(
                                                preferentialTradeItemsMap.get(storeId));
                                Long points =
                                        Trade1CommitPriceUtil.calcSkusTotalPoints(preferentialTradeItemsMap.get(storeId));
                                tradePrice.setPointsPrice(tradePrice.getPointsPrice().add(pointsPrice));
                                tradePrice.setPoints(tradePrice.getPoints() + points);
                                // 重设订单总价
                                tradePrice.setTotalPrice(
                                        tradePrice.getTotalPrice().subtract(pointsPrice));
                            });
        }
    }

    @Override
    public void freightProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {

        Map<Long, StoreCommitInfoDTO> storeCommitInfoDTOMap =
                request.getStoreCommitInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        StoreCommitInfoDTO::getStoreId, Function.identity()));

        tradeList.forEach(
                trade -> {
                    // 计算运费
                    TradePrice tradePrice = trade.getTradePrice();
                    BigDecimal deliveryPrice = tradePrice.getDeliveryPrice();
                    if (deliveryPrice == null) {
                        Freight freight =
                                freightService.calcTradeFreight(
                                        trade.getConsignee(),
                                        trade.getSupplier(),
                                        trade.getDeliverWay(),
                                        tradePrice.getTotalPrice(),
                                        trade.getTradeItems(),
                                        trade.getGifts(),
                                        trade.getPreferential());
                        // 查询商家代销运费设置
                        deliveryPrice = freight.getFreight();
                        trade.setFreight(freight);
                        trade.setThirdPlatFormFreight(freight.getProviderFreight());
                    }
                    // 运费
                    // 包邮标识用于自提订单处理
                    boolean postage = false;
                    // 自提订单不计算运费
                    if (Objects.nonNull(trade.getPickupFlag())
                            && Boolean.TRUE.equals(trade.getPickupFlag())) {
                        deliveryPrice = BigDecimal.ZERO;
                        // 用户需要承担供应商运费
                        if (Objects.nonNull(trade.getFreight())) {
                            trade.getFreight().setFreight(BigDecimal.ZERO);
                            trade.getFreight().setSupplierFreight(BigDecimal.ZERO);
                            if (!postage
                                    && CollectionUtils.isNotEmpty(
                                            trade.getFreight().getProviderFreightList())) {
                                for (ProviderFreight providerFreight :
                                        trade.getFreight().getProviderFreightList()) {
                                    if (providerFreight.getBearFreight() == 0) {
                                        deliveryPrice =
                                                deliveryPrice.add(
                                                        providerFreight.getSupplierFreight());
                                    }
                                }
                            }
                        }
                    }

                    // 处理运费券的逻辑    验证是不是砍价订单
                    Boolean bargainUseCoupon = Boolean.TRUE;
                    if (Objects.equals(Boolean.TRUE, tradeList.get(0).getBargain())) {
                        //验证允不允许叠加优惠券
                        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
                        configQueryRequest.setConfigType(ConfigType.BARGIN_USE_COUPON.toValue());
                        configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
                        SystemConfigTypeResponse response =
                                systemConfigQueryProvider
                                        .findByConfigTypeAndDelFlag(configQueryRequest)
                                        .getContext();

                        if(Objects.isNull(response.getConfig())
                                || Objects.equals(DefaultFlag.NO.toValue(), response.getConfig().getStatus())) {
                            bargainUseCoupon = Boolean.FALSE;
                        }
                    }
                    if (bargainUseCoupon && deliveryPrice.compareTo(BigDecimal.ZERO) > 0) {
                        StoreCommitInfoDTO storeCommitInfoDTO =
                                storeCommitInfoDTOMap.get(trade.getSupplier().getStoreId());
                        if (Objects.nonNull(storeCommitInfoDTO)
                                && StringUtils.isNotEmpty(
                                        storeCommitInfoDTO.getFreightCouponCodeId())) {
                            // 调用营销插件  计算运费券营销优惠
                            CountCouponPriceVO couponPriceVO =
                                    freightService.freightCoupon(
                                            storeCommitInfoDTO.getFreightCouponCodeId(),
                                            trade.getTradeItems(),
                                            trade.getBuyer(),
                                            deliveryPrice,
                                            param,
                                            null);
                            //处理优惠金额
                            deliveryPrice =
                                    this.tradeFreightCoupon(couponPriceVO, trade, deliveryPrice);
                            //封装订单优惠信息
                            this.couponPrice(trade, tradePrice, deliveryPrice);
                        }
                    }

                    tradePrice.setDeliveryPrice(deliveryPrice);
                    // 计算订单总价(追加运费)
                    tradePrice.setOriginPrice(tradePrice.getOriginPrice().add(deliveryPrice));
                    if (tradePrice.isSpecial()) {
                        // 【商品价格计算第③步】: 商品的 特价订单 均摊价 -> splitPrice
                        Trade1CommitPriceUtil.clacSplitPrice(
                                trade.getTradeItems(), trade.getPreferential(), tradePrice.getPrivilegePrice());
                        tradePrice.setTotalPrice(
                                tradePrice.getPrivilegePrice().add(deliveryPrice)); // 应付金额 = 特价+运费
                    } else {
                        tradePrice.setTotalPrice(
                                tradePrice.getTotalPrice().add(deliveryPrice)); // 应付金额 = 应付+运费
                    }
                    if (trade.getIsBookingSaleGoods() != null
                            && trade.getIsBookingSaleGoods()
                            && trade.getBookingType() != null
                            && trade.getBookingType().equals(BookingType.EARNEST_MONEY)) {
                        tradePrice.setTailPrice(tradePrice.getTailPrice().add(deliveryPrice));
                    }
                });
    }

    @Override
    public void giftCardProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {
        Trade validateTrade = tradeList.get(0);
        // 过滤预售定金订单  和 开店礼包
        if (validateTrade.getIsBookingSaleGoods() != null
                && validateTrade.getIsBookingSaleGoods()
                && validateTrade.getBookingType() != null
                && validateTrade.getBookingType().equals(BookingType.EARNEST_MONEY)
                && validateTrade.getTradeState().getFlowState() == FlowState.WAIT_PAY_EARNEST
                || DefaultFlag.YES.equals(validateTrade.getStoreBagsFlag())) {
            return;
        }

        // 参数校验
        if(CollectionUtils.isEmpty(request.getGiftCardTradeCommitVOList())
                || CollectionUtils.isEmpty(param.getUserGiftCardInfoVOList())) {
            return;
        }
        // 按照过期时间升序 过期时间为空-长期有效 领取时间倒序
        List<UserGiftCardInfoVO> gorpList =
                param.getUserGiftCardInfoVOList().parallelStream()
                        .sorted(
                                Comparator.comparing(
                                                UserGiftCardInfoVO::getExpirationTime,
                                                Comparator.nullsLast(LocalDateTime::compareTo))
                                        .thenComparing(
                                                Comparator.comparing(
                                                                UserGiftCardInfoVO::getAcquireTime)
                                                        .reversed()))
                        .collect(Collectors.toList());
        // 预估适用金额
        Map<Long, BigDecimal> useCardMap =
                request.getGiftCardTradeCommitVOList().stream()
                        .filter(c-> Objects.nonNull(c.getUserGiftCardId()) && Objects.nonNull(c.getUsePrice()))
                        .collect(
                                Collectors.toMap(
                                        GiftCardTradeCommitVO::getUserGiftCardId,
                                        GiftCardTradeCommitVO::getUsePrice));
        String key = CacheKeyConstant.ORDER_COMMIT_GIFT_CARD_SUCCESS + request.getTerminalToken();
        List<UserGiftCardUseResponse> userGiftCardUseList = new ArrayList<>();
        for (UserGiftCardInfoVO giftCardInfoVO : gorpList) {
            List<GiftCardTransBusinessVO> transBusinessVOList = new ArrayList<>();
            //适用商品的总实付金额
            BigDecimal goodsPrice = BigDecimal.ZERO;
            // 查询目标商品
            for (Trade trade: tradeList) {
                GiftCardTransBusinessVO businessVO = new GiftCardTransBusinessVO();
                BigDecimal tradePrice = BigDecimal.ZERO;
                // 过滤目标商品
                List<GiftCardTransBusinessItemVO> businessItemVOList = new ArrayList<>();
                // 处理普通商品
                List<TradeItem> tradeItemList =
                        trade.getTradeItems().stream()
                                .filter(
                                        item ->
                                                giftCardInfoVO
                                                                .getSkuIdList()
                                                                .contains(item.getSkuId())
                                                        && item.getSplitPrice()
                                                                        .compareTo(BigDecimal.ZERO)
                                                                > 0)
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(tradeItemList)) {
                    for (TradeItem tradeItem : tradeItemList) {
                        GiftCardTransBusinessItemVO businessItemVO = new GiftCardTransBusinessItemVO();
                        businessItemVO.setItemId(tradeItem.getSkuId());
                        businessItemVO.setSplitPrice(tradeItem.getSplitPrice());
                        businessItemVOList.add(businessItemVO);
                        tradePrice = tradePrice.add(tradeItem.getSplitPrice());
                    }
                }
                //处理加价购商品
                if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                    List<TradeItem> preferential = trade.getPreferential().stream()
                            .filter(
                                    item ->
                                            giftCardInfoVO
                                                    .getSkuIdList()
                                                    .contains(item.getSkuId())
                                                    && item.getSplitPrice()
                                                    .compareTo(BigDecimal.ZERO)
                                                    > 0)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(preferential)) {
                        for (TradeItem tradeItem : preferential) {
                            GiftCardTransBusinessItemVO businessItemVO = new GiftCardTransBusinessItemVO();
                            businessItemVO.setItemId(tradeItem.getSkuId());
                            if (CollectionUtils.isNotEmpty(tradeItem.getMarketingIds())) {
                                businessItemVO.setPreferentialMarketingId(tradeItem.getMarketingIds().get(0));
                            }
                            businessItemVO.setSplitPrice(tradeItem.getSplitPrice());
                            businessItemVOList.add(businessItemVO);
                            tradePrice = tradePrice.add(tradeItem.getSplitPrice());
                        }
                    }
                }

                if (CollectionUtils.isEmpty(businessItemVOList)) {
                    continue;
                }


                if (tradePrice.compareTo(BigDecimal.ZERO) > 0) {
                    goodsPrice = goodsPrice.add(tradePrice);
                    businessVO.setBusinessId(trade.getId());
                    businessVO.setTradePrice(tradePrice);
                    businessVO.setBusinessItemVOList(businessItemVOList);
                    transBusinessVOList.add(businessVO);
                }
            }
            // 计算目标商品可抵扣金额  如果非强制 ： 使用预估  强制下单：商品实付金额
            BigDecimal sumTradePrice = useCardMap.getOrDefault(giftCardInfoVO.getUserGiftCardId(), new BigDecimal("0"));
            if (request.isForceCommit() || sumTradePrice.compareTo(goodsPrice) > 0 ) {
                sumTradePrice = goodsPrice;
            }
            if (sumTradePrice.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            //封装请求
            UserGiftCardTransRequest transRequest = new UserGiftCardTransRequest();
            transRequest.setGiftCardNo(giftCardInfoVO.getGiftCardNo());
            transRequest.setBusinessType(GiftCardBusinessType.ORDER_DEDUCTION);
            transRequest.setUserGiftCardId(giftCardInfoVO.getUserGiftCardId());
            transRequest.setSumTradePrice(sumTradePrice);
            transRequest.setTransBusinessVOList(transBusinessVOList);
            transRequest.setCustomerId(request.getOperator().getUserId());
            transRequest.setTradePersonType(DefaultFlag.NO);
            transRequest.setForceCommit(request.isForceCommit());
            ExecutorService executorService = this.newThreadPoolExecutor(1);
            CompletableFuture<UserGiftCardUseResponse> future =
                    CompletableFuture.supplyAsync(
                                    () ->
                                            userGiftCardProvider
                                                    .useUserGiftCard(transRequest)
                                                    .getContext(),
                                    executorService)
                            .exceptionally(
                                    throwable -> {
                                        return UserGiftCardUseResponse.builder().usePrice(BigDecimal.ZERO).build();
                                    });
            try {
                UserGiftCardUseResponse useResponse = future.get();
                // 验证余额是否为零  是否强制下单
                if (!request.isForceCommit()
                        && (BigDecimal.ZERO.compareTo(useResponse.getUsePrice()) >= 0
                            || CollectionUtils.isEmpty(useResponse.getTransBusinessVOList()))) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080041);
                }
                // 订单价格 和 商品数据
                if (CollectionUtils.isNotEmpty(useResponse.getTransBusinessVOList())) {
                    userGiftCardUseList.add(useResponse);
                    // 更新缓存
                    redisUtil.setObj(key, userGiftCardUseList, 30*60L);
                    Map<String, GiftCardTransBusinessVO> businessVOMap =
                            useResponse.getTransBusinessVOList().stream()
                                    .collect(
                                            Collectors.toMap(
                                                    GiftCardTransBusinessVO::getBusinessId,
                                                    (b -> b)));
                    for (Trade trade : tradeList) {
                        TradePrice tradePrice = trade.getTradePrice();
                        if (!businessVOMap.containsKey(trade.getId())) {
                            continue;
                        }
                        // 获取礼品卡抵扣后的 订单关联的信息
                        GiftCardTransBusinessVO businessVO = businessVOMap.get(trade.getId());
                        if (Objects.isNull(businessVO)
                                || CollectionUtils.isEmpty(businessVO.getBusinessItemVOList())
                                || BigDecimal.ZERO.compareTo(businessVO.getTradePrice()) >= 0) {
                            continue;
                        }
                        // 处理订单的应付金额 应付金额 = 应付-礼品卡抵扣
                        BigDecimal giftCardPrice =
                                Objects.isNull(tradePrice.getGiftCardPrice())
                                        ? BigDecimal.ZERO
                                        : tradePrice.getGiftCardPrice();
                        tradePrice.setGiftCardPrice(giftCardPrice.add(businessVO.getTradePrice()));
                        tradePrice.setGiftCardType(GiftCardType.CASH_CARD);
                        tradePrice.setTotalPrice(
                                tradePrice.getTotalPrice().subtract(businessVO.getTradePrice()));
                        if (trade.getIsBookingSaleGoods() != null
                                && trade.getIsBookingSaleGoods()
                                && trade.getBookingType() != null
                                && trade.getBookingType().equals(BookingType.EARNEST_MONEY)) {
                            tradePrice.setTailPrice(tradePrice.getTailPrice().subtract(businessVO.getTradePrice()));
                        }
                        // 获取礼品卡适用商品抵扣金额 并处理商品实付信息
                        for (TradeItem tradeItem : trade.getTradeItems()) {
                            for (GiftCardTransBusinessItemVO itemVO : businessVO.getBusinessItemVOList()) {
                                if (Objects.equals(tradeItem.getSkuId(), itemVO.getItemId())
                                        && Objects.isNull(itemVO.getPreferentialMarketingId())
                                        && itemVO.getDeductionPrice().compareTo(BigDecimal.ZERO) > 0) {
                                    tradeItem.setSplitPrice(
                                            tradeItem
                                                    .getSplitPrice()
                                                    .subtract(itemVO.getDeductionPrice()));
                                    tradeItem
                                            .getGiftCardItemList()
                                            .add(
                                                    TradeItem.GiftCardItem.builder()
                                                            .giftCardNo(giftCardInfoVO.getGiftCardNo())
                                                            .giftCardType(GiftCardType.CASH_CARD)
                                                            .userGiftCardID(giftCardInfoVO.getUserGiftCardId())
                                                            .price(itemVO.getDeductionPrice())
                                                            .build());
                                }
                            }
                        }
                        // 处理加价购商品
                        if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                            for (TradeItem tradeItem : trade.getPreferential()) {
                                for (GiftCardTransBusinessItemVO itemVO : businessVO.getBusinessItemVOList()) {
                                    if (Objects.equals(tradeItem.getSkuId(), itemVO.getItemId())
                                            && Objects.equals(itemVO.getPreferentialMarketingId(), tradeItem.getMarketingIds().get(0))
                                            && itemVO.getDeductionPrice().compareTo(BigDecimal.ZERO) > 0) {
                                        tradeItem.setSplitPrice(
                                                tradeItem
                                                        .getSplitPrice()
                                                        .subtract(itemVO.getDeductionPrice()));
                                        if(CollectionUtils.isEmpty(tradeItem.getGiftCardItemList())) {
                                            tradeItem.setGiftCardItemList(new ArrayList<>());
                                        }
                                        tradeItem
                                                .getGiftCardItemList()
                                                .add(
                                                        TradeItem.GiftCardItem.builder()
                                                                .giftCardNo(giftCardInfoVO.getGiftCardNo())
                                                                .giftCardType(GiftCardType.CASH_CARD)
                                                                .userGiftCardID(giftCardInfoVO.getUserGiftCardId())
                                                                .price(itemVO.getDeductionPrice())
                                                                .build());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                log.error("giftCardProcess下单礼品卡任务执行-线程池异常", e);
                Thread.currentThread().interrupt();
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            } catch (ExecutionException e) {
                log.error("giftCardProcess下单礼品卡任务执行出错", e);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 封装线程池
     * @param corePoolSize
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = corePoolSize * 5;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * @description   封装订单运费券信息
     * @author  wur
     * @date: 2022/10/8 9:24
     * @param couponPriceVO     运费券算价返回
     * @param trade             订单信息
     * @param deliveryPrice     运费金额
     * @return
     **/
    private BigDecimal tradeFreightCoupon(CountCouponPriceVO couponPriceVO, Trade trade, BigDecimal deliveryPrice) {
        if (Objects.isNull(couponPriceVO)) {
            return deliveryPrice;
        }
        //运费券优惠金额
        BigDecimal freightCouponPrice = BigDecimal.ZERO;
        //使用运费券后的运费
        BigDecimal tradeFreight = BigDecimal.ZERO;
        TradeCouponVO freightCoupon = new TradeCouponVO();
        freightCoupon.setCouponCode(couponPriceVO.getCouponCode());
        freightCoupon.setCouponCodeId(couponPriceVO.getCouponCodeId());
        freightCoupon.setCouponMarketingType(couponPriceVO.getCouponMarketingType());
        freightCoupon.setCouponType(couponPriceVO.getCouponType());
        if (CouponDiscountMode.FREIGHT_FREE.toValue() == couponPriceVO.getCouponDiscountMode().toValue()) {
            freightCouponPrice = deliveryPrice;
            tradeFreight = BigDecimal.ZERO;
        } else {
            if (Objects.isNull(couponPriceVO.getDiscountsAmount())) {
                return deliveryPrice;
            }
            freightCouponPrice = couponPriceVO.getDiscountsAmount().compareTo(deliveryPrice) >= 0 ? deliveryPrice : couponPriceVO.getDiscountsAmount();
            tradeFreight = couponPriceVO.getDiscountsAmount().compareTo(deliveryPrice) >= 0 ? BigDecimal.ZERO : deliveryPrice.subtract(couponPriceVO.getDiscountsAmount());
        }
        freightCoupon.setDiscountsAmount(freightCouponPrice);
        trade.setFreightCoupon(freightCoupon);
        return tradeFreight;
    }

    /**
     * @description   处理运费券优惠的订单数据
     * @author  wur
     * @date: 2022/10/8 17:21
     * @param trade
     * @param tradePrice
     * @param deliveryPrice
     * @return
     **/
    private void couponPrice(Trade trade, TradePrice tradePrice, BigDecimal deliveryPrice) {
        if (Objects.nonNull(trade.getFreightCoupon())) {
            BigDecimal freightCouponPrice =
                    trade.getFreightCoupon().getDiscountsAmount();
            tradePrice.setFreightCouponPrice(freightCouponPrice);
            //设置总的优惠
            if (Objects.isNull(tradePrice.getDiscountsPrice())) {
                tradePrice.setDiscountsPrice(freightCouponPrice);
            } else {
                tradePrice.setDiscountsPrice(
                        freightCouponPrice.add(
                                tradePrice.getDiscountsPrice()));
            }
            //设置券优惠金额
            if (Objects.isNull(tradePrice.getStoreCouponPrice())) {
                tradePrice.setStoreCouponPrice(freightCouponPrice);
            } else {
                tradePrice.setStoreCouponPrice(
                        freightCouponPrice.add(
                                tradePrice.getStoreCouponPrice()));
            }
            // 处理供应商运费  优惠金额优先扣除商家的运费然后再扣除代销的供应商运费
            if (Objects.nonNull(trade.getFreight())) {
                trade.getFreight().setFreight(deliveryPrice);
                if (Objects.nonNull(trade.getFreight().getSupplierFreight())
                        && trade.getFreight()
                        .getSupplierFreight()
                        .compareTo(BigDecimal.ZERO)
                        > 0) {
                    //优惠金额大于等于商家运费则商家运费=0
                    trade.getFreight()
                            .setSupplierFreight(
                                    freightCouponPrice.compareTo(
                                            trade.getFreight()
                                                    .getSupplierFreight())
                                            >= 0
                                            ? BigDecimal.ZERO
                                            : trade.getFreight()
                                            .getSupplierFreight()
                                            .subtract(
                                                    freightCouponPrice));
                    // 优惠券剩余优惠金额
                    freightCouponPrice =
                            freightCouponPrice.compareTo(
                                    trade.getFreight()
                                            .getSupplierFreight())
                                    > 0
                                    ? freightCouponPrice.subtract(
                                    trade.getFreight()
                                            .getSupplierFreight())
                                    : BigDecimal.ZERO;
                }
                // 多出的优惠金额 则商家承担
                if (Objects.nonNull(trade.getFreight().getProviderFreight())
                        && trade.getFreight()
                        .getProviderFreight()
                        .compareTo(BigDecimal.ZERO)
                        > 0
                        && freightCouponPrice.compareTo(BigDecimal.ZERO) > 0) {
                    trade.getFreight()
                            .setSupplierBearFreight(
                                    Objects.isNull(
                                            trade.getFreight()
                                                    .getSupplierBearFreight())
                                            ? freightCouponPrice
                                            : trade.getFreight()
                                            .getSupplierBearFreight()
                                            .add(freightCouponPrice));
                    for (ProviderFreight providerFreight :
                            trade.getFreight().getProviderFreightList()) {
                        if (freightCouponPrice.compareTo(BigDecimal.ZERO) >0 && providerFreight.getBearFreight() == 0) {
                            if(freightCouponPrice.compareTo(providerFreight.getSupplierFreight()) >0 ) {
                                providerFreight.setBearFreight(Constants.ONE);
                                providerFreight.setSupplierBearFreight(providerFreight.getSupplierFreight());
                                freightCouponPrice = freightCouponPrice.subtract(providerFreight.getSupplierFreight());
                            } else {
                                providerFreight.setBearFreight(Constants.TWO);
                                providerFreight.setSupplierBearFreight(freightCouponPrice);
                                freightCouponPrice = BigDecimal.ZERO;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void distributionProcess(
            List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {}

    @Override
    public void auditStateProcess(List<Trade> tradeList, TradeCommitRequest request) {

        // 可退查询
        TradeConfigGetByTypeRequest tradeConfigGetByTypeRequest = new TradeConfigGetByTypeRequest();
        OrderTag orderTag = tradeList.get(0).getOrderTag();
        if (Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag()) {
            tradeConfigGetByTypeRequest.setConfigType(
                    ConfigType.ORDER_SETTING_VIRTUAL_APPLY_REFUND);
        } else {
            tradeConfigGetByTypeRequest.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        }
        TradeConfigGetByTypeResponse config =
                auditQueryProvider.getTradeConfigByType(tradeConfigGetByTypeRequest).getContext();
        Integer refundStatus = null;
        if (config != null) {
            refundStatus = config.getStatus();
        }
        // 是否开启订单审核（同时判断是否为秒杀抢购商品订单）
        AtomicReference<Boolean> orderAuditSwitch =
                new AtomicReference<>(tradeCacheService.isSupplierOrderAudit());
        Integer finalRefundStatus = refundStatus;
        tradeList.forEach(
                trade -> {
                    // 初始化订单提交状态
                    FlowState flowState;
                    AuditState auditState;

                    // 预售不要审核
                    if (Objects.nonNull(trade.getIsBookingSaleGoods())
                            && trade.getIsBookingSaleGoods()) {
                        flowState = FlowState.AUDIT;
                        orderAuditSwitch.set(Boolean.FALSE);
                        auditState = AuditState.CHECKED;
                    }
                    // 如果是拼团订单商品不需要审
                    else if (Objects.nonNull(trade.getGrouponFlag()) && trade.getGrouponFlag()) {
                        flowState = FlowState.GROUPON;
                        auditState = AuditState.CHECKED;
                        orderAuditSwitch.set(Boolean.FALSE);
                    }
                    // 含渠道订单不需要审
                    else if (CollectionUtils.isNotEmpty(trade.getThirdPlatformTypes())) {
                        flowState = FlowState.AUDIT;
                        auditState = AuditState.CHECKED;
                        orderAuditSwitch.set(Boolean.FALSE);
                    } else {
                        if (!orderAuditSwitch.get()) {
                            flowState = FlowState.AUDIT;
                            auditState = AuditState.CHECKED;
                        } else {
                            // 商家 boss 初始化状态是不需要审核的
                            if (request.getOperator().getPlatform() == Platform.BOSS
                                    || request.getOperator().getPlatform() == Platform.SUPPLIER) {
                                flowState = FlowState.AUDIT;
                                auditState = AuditState.CHECKED;
                            } else {
                                flowState = FlowState.INIT;
                                auditState = AuditState.NON_CHECKED;
                            }
                        }
                    }
                    TradeState tradeState =
                            TradeState.builder()
                                    .deliverStatus(DeliverStatus.NOT_YET_SHIPPED)
                                    .flowState(flowState)
                                    .payState(PayState.NOT_PAID)
                                    .auditState(auditState)
                                    .createTime(LocalDateTime.now())
                                    .build();
                    if (Objects.nonNull(trade.getIsBookingSaleGoods())
                            && trade.getIsBookingSaleGoods()
                            && Objects.nonNull(trade.getBookingType())
                            && trade.getBookingType() == BookingType.EARNEST_MONEY) {
                        tradeState =
                                TradeState.builder()
                                        .deliverStatus(DeliverStatus.NOT_YET_SHIPPED)
                                        .flowState(FlowState.WAIT_PAY_EARNEST)
                                        .payState(PayState.NOT_PAID)
                                        .auditState(auditState)
                                        .createTime(LocalDateTime.now())
                                        .handSelStartTime(
                                                trade.getTradeItems().get(0).getHandSelStartTime())
                                        .handSelEndTime(
                                                trade.getTradeItems().get(0).getHandSelEndTime())
                                        .tailStartTime(
                                                trade.getTradeItems().get(0).getTailStartTime())
                                        .tailEndTime(trade.getTradeItems().get(0).getTailEndTime())
                                        .build();
                    }
                    trade.setTradeState(tradeState);
                    if (trade.getDeliverWay() == DeliverWay.SAME_CITY) {
                        tradeState.setDistributionState(DistributionState.NONE);
                    }

                    if (Objects.nonNull(finalRefundStatus)) {
                        trade.getTradeState().setRefundStatus(finalRefundStatus);
                    }

                    trade.getTradeState().setAuditState(auditState);
                    trade.setIsAuditOpen(orderAuditSwitch.get());
                });
    }

    @Override
    public void settingProcess(List<Trade> tradeList) {}

    /**
     * 赠品处理
     *
     * @param tradeList
     * @param customer
     */
    private void giftProcess(List<Trade> tradeList, CustomerVO customer, Boolean isForceCommit) {
        tradeList.forEach(
                trade -> {
                    if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                        // 赠品信息校验与填充
                        List<String> giftIds =
                                trade.getGifts().stream()
                                        .map(TradeItem::getSkuId)
                                        .distinct()
                                        .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(giftIds)) {
                            List<GoodsInfoTradeVO> goodsInfoList =
                                    goodsInfoQueryProvider
                                            .getTradeGoodsInfoByIds(
                                                    GoodsInfoTradeRequest.builder()
                                                            .goodsInfoIds(giftIds)
                                                            .customer(customer)
                                                            .build())
                                            .getContext();
                            if (!isForceCommit) {
                                if (CollectionUtils.isEmpty(goodsInfoList)) {
                                    throw new SbcRuntimeException(
                                            CommonErrorCodeEnum.K999999, "很抱歉，赠品已经删除或者下架");
                                }
                                goodsInfoList.forEach(
                                        goodsInfo -> {
                                            if (!goodsInfo.getGoodsStatus().equals(GoodsStatus.OK)
                                                    || goodsInfo.getStock() < 1) {
                                                // 无效赠品提示
                                                String tmp = "赠品%s";
                                                String errMsg =
                                                        String.format(
                                                                tmp,
                                                                goodsInfo.getGoodsInfoName()
                                                                        + (goodsInfo.getSpecText()
                                                                                        == null
                                                                                ? ""
                                                                                : goodsInfo
                                                                                        .getSpecText()));

                                                errMsg += "已失效或缺货！";
                                                throw new SbcRuntimeException(
                                                        CommonErrorCodeEnum.K999999, errMsg);
                                            }
                                        });
                            }
                            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                                List<TradeItem> gifts = trade.getGifts();
                                Map<String, GoodsInfoTradeVO> goodsInfoTradeVOMap =
                                        goodsInfoList.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                GoodsInfoBaseVO::getGoodsInfoId,
                                                                Function.identity()));
                                gifts =
                                        trade.getGifts().stream()
                                                .filter(
                                                        g -> {
                                                            GoodsInfoTradeVO goodsInfo =
                                                                    goodsInfoTradeVOMap.get(
                                                                            g.getSkuId());
                                                            if (goodsInfo == null
                                                                    || !goodsInfo
                                                                            .getGoodsStatus()
                                                                            .equals(GoodsStatus.OK)
                                                                    || goodsInfo.getStock() < 1) {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        })
                                                .collect(Collectors.toList());
                                trade.setGifts(build.buildItem(gifts, goodsInfoList,DefaultFlag.NO, null,null));
                                trade.getGifts()
                                        .forEach(
                                                tradeItem -> {
                                                    tradeItem.setPrice(BigDecimal.ZERO);
//                                                    tradeItem.setSupplyPrice(BigDecimal.ZERO);
                                                    tradeItem.setOriginalPrice(BigDecimal.ZERO);
                                                    tradeItem.setSplitPrice(BigDecimal.ZERO);
                                                });
                                // 验证赠品类型
                                trade.getThirdPlatformTypes().addAll(
                                        trade.getGifts().stream()
                                                .filter(i -> Objects.nonNull(i.getThirdPlatformType()))
                                                .map(TradeItem::getThirdPlatformType)
                                                .collect(Collectors.toSet()));
                                // 渠道接口
                                thirdPlatformTradeService.verifyGoods(trade.getGifts(), trade.getConsignee());
                            }
                        }
                    }
                });
    }

    /**
     * @description 加价购商品处理
     * @author  edz
     * @date: 2022/11/28 16:22
     * @param tradeList
     * @param customer
     * @return void
     */
    private void preferentialProcess(List<Trade> tradeList, CustomerVO customer, Boolean isForceCommit) {
        tradeList.forEach(
                trade -> {
                    if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                        // 信息校验与填充
                        List<String> preferentialSkuIds =
                                trade.getPreferential().stream()
                                        .map(TradeItem::getSkuId)
                                        .distinct()
                                        .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(preferentialSkuIds)) {
                            AtomicBoolean errorFlag = new AtomicBoolean(false);
                            List<GoodsInfoTradeVO> goodsInfoList =
                                    goodsInfoQueryProvider
                                            .getTradeGoodsInfoByIds(
                                                    GoodsInfoTradeRequest.builder()
                                                            .goodsInfoIds(preferentialSkuIds)
                                                            .customer(customer)
                                                            .build())
                                            .getContext();
                                if (CollectionUtils.isEmpty(goodsInfoList)) {
                                    trade.setPreferential(new ArrayList<>());
                                    errorFlag.set(true);
                                    if (isForceCommit){
                                        throw new SbcRuntimeException(
                                                CommonErrorCodeEnum.K999999, "很抱歉，商品已经删除或者下架");
                                    }
                                }
                            goodsInfoList.forEach(
                                    goodsInfo -> {
                                        if (!goodsInfo.getGoodsStatus().equals(GoodsStatus.OK)
                                                || goodsInfo.getStock() < 1) {
                                            // 无效商品提示
                                            String tmp = "商品%s";
                                            String errMsg =
                                                    String.format(
                                                            tmp,
                                                            goodsInfo.getGoodsInfoName()
                                                                    + (goodsInfo.getSpecText()
                                                                    == null
                                                                    ? ""
                                                                    : goodsInfo
                                                                    .getSpecText()));

                                            errMsg += "已失效或缺货！";
                                            errorFlag.set(true);
                                            trade.setPreferential(new ArrayList<>());
                                            if (!isForceCommit){
                                                throw new SbcRuntimeException(
                                                        CommonErrorCodeEnum.K999999, errMsg);
                                            }
                                        }
                                    });
                            if (CollectionUtils.isNotEmpty(goodsInfoList) && !errorFlag.get()) {
                                Map<String, GoodsInfoTradeVO> goodsInfoTradeVOMap =
                                        goodsInfoList.stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                GoodsInfoBaseVO::getGoodsInfoId,
                                                                Function.identity()));
                                List<TradeItem> preferentialTradeItem =
                                        trade.getPreferential().stream()
                                                .filter(
                                                        g -> {
                                                            GoodsInfoTradeVO goodsInfo =
                                                                    goodsInfoTradeVOMap.get(
                                                                            g.getSkuId());
                                                            return goodsInfo != null
                                                                    && goodsInfo
                                                                    .getGoodsStatus()
                                                                    .equals(GoodsStatus.OK)
                                                                    && goodsInfo.getStock() >= 1;
                                                        })
                                                .collect(Collectors.toList());
                                Map<Long, List<TradeItem>> marketingIdToTradeItems =
                                        preferentialTradeItem.stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0)));
                                Map<Long, Map<String, BigDecimal>> MarketingIdToSkuPrice = new HashMap<>();
                                marketingIdToTradeItems.forEach((marketingId, tradeItems) -> {
                                    Map<String, BigDecimal> skuIdToPrice =
                                            tradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId,
                                                    TradeItem::getSplitPrice));
                                    MarketingIdToSkuPrice.put(marketingId, skuIdToPrice);
                                });
                                trade.setPreferential(build.buildItem(preferentialTradeItem, goodsInfoList,DefaultFlag.NO, null,
                                        null));
                                trade.getPreferential()
                                        .forEach(
                                                tradeItem -> {
                                                    BigDecimal price =
                                                            MarketingIdToSkuPrice.getOrDefault(tradeItem.getMarketingIds().get(0), new HashMap<>())
                                                                    .get(tradeItem.getSkuId());
                                                    tradeItem.setPrice(price);
                                                    tradeItem.setOriginalPrice(goodsInfoTradeVOMap.get(tradeItem.getSkuId()).getMarketPrice());
                                                    tradeItem.setSplitPrice(price);
                                                    tradeItem.setBuyPoint(NumberUtils.LONG_ZERO);
                                                });
                                // 验证商品类型
                                trade.getThirdPlatformTypes().addAll(
                                        trade.getPreferential().stream()
                                                .map(TradeItem::getThirdPlatformType)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toSet()));
                                // 渠道接口
                                thirdPlatformTradeService.verifyGoods(trade.getPreferential(), trade.getConsignee());
                            }
                        }
                    }
                });
    }

    /**
     * 满返赠券处理
     *
     * @param tradeList
     * @param param
     */
    @Override
    public void fullReturnProcess(List<Trade> tradeList, Trade1CommitParam param) {
        Trade validateTrade = tradeList.get(0);
        // 过滤线下订单
        if (PayType.OFFLINE.name().equals(validateTrade.getPayInfo().getPayTypeName())){
            return;
        }
        // 过滤预售定金订单
        if (validateTrade.getIsBookingSaleGoods() != null
                && validateTrade.getIsBookingSaleGoods()
                && validateTrade.getBookingType() != null
                && validateTrade.getBookingType().equals(BookingType.EARNEST_MONEY)) {
            if (tradeList.get(0).getTradeState().getFlowState() == FlowState.WAIT_PAY_EARNEST) {
                return;
            }
        }
        // 过滤拼团订单
        if (Objects.nonNull(validateTrade.getGrouponFlag()) && validateTrade.getGrouponFlag()) {
            return;
        }
        // 商品信息
        List<GoodsInfoTradeVO> goodsInfoTradeVOS = param.getGoodsInfoTradeVOS();
        // skuId和storeId映射
        Map<String, Long> goodsMap = goodsInfoTradeVOS.stream().collect(Collectors.toMap(GoodsInfoTradeVO::getGoodsInfoId, GoodsInfoTradeVO::getStoreId, (s, a) -> s));
        // storeId和store映射
        Map<Long, StoreVO> storeVOMap = param.getStoreVOS().stream().collect(Collectors.toMap(StoreVO::getStoreId,
                Function.identity()));
        // 查询满足的满返活动
        List<String> goodsInfoIdList = goodsInfoTradeVOS.stream().map(GoodsInfoTradeVO::getGoodsInfoId).distinct().collect(Collectors.toList());
        // 品牌列表
        List<Long> brandIds = goodsInfoTradeVOS.stream().map(GoodsInfoTradeVO::getBrandId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        // 店铺分类列表
        List<Long> storeCateIds = goodsInfoTradeVOS.stream()
                .filter(item -> WmCollectionUtils.isNotEmpty(item.getStoreCateIds()))
                .flatMap(item -> item.getStoreCateIds().stream()).distinct().collect(Collectors.toList());
        // 店铺id列表
        List<Long> storeIds = goodsInfoTradeVOS.stream().map(GoodsInfoTradeVO::getStoreId).distinct().collect(Collectors.toList());
        // 组装参数
        List<MarketingGoodsRequest> marketingGoods = goodsInfoTradeVOS.stream().map(goodsInfoVO -> MarketingGoodsRequest.builder()
                .goodsInfoId(goodsInfoVO.getGoodsInfoId())
                .brandId(goodsInfoVO.getBrandId())
                .storeCateIds(goodsInfoVO.getStoreCateIds())
                .cateId(goodsInfoVO.getCateId())
                .storeId(goodsInfoVO.getStoreId()).build()).collect(Collectors.toList());
        MarketingMapGetByGoodsIdRequest marketingMapGetByGoodsIdRequest = MarketingMapGetByGoodsIdRequest.builder()
                .deleteFlag(DeleteFlag.NO)
                .cascadeLevel(Boolean.TRUE)
                .marketingStatus(MarketingStatus.STARTED)
                .goodsInfoIdList(goodsInfoIdList)
                .brandIds(brandIds)
                .storeCateIds(storeCateIds)
                .storeIds(storeIds)
                .marketingType(MarketingType.RETURN)
                .marketingGoods(marketingGoods)
                .build();
        // 调用营销获取满返活动
        MarketingMapGetByGoodsIdResponse marketingMapGetByGoodsIdResponse =
                marketingQueryProvider.getMarketingMapByGoodsId(marketingMapGetByGoodsIdRequest).getContext();
        if (Objects.nonNull(marketingMapGetByGoodsIdResponse)){
            HashMap<String, List<MarketingForEndVO>> marketingMap = marketingMapGetByGoodsIdResponse.getListMap();
            if (MapUtils.isNotEmpty(marketingMap)){
                // 查询用户等级列表
                HashMap<Long, CommonLevelVO> levelMap = (HashMap<Long, CommonLevelVO>) param.getStoreLevelMap();
                // 处理客户等级
                Map<String, List<MarketingForEndVO>> newMarketingMap = new HashMap<>();
                marketingMap.forEach((skuId, marketingList) -> {
                    Long goodsStoreId = goodsMap.get(skuId);
                    if (!Objects.isNull(goodsStoreId)) {
                        CommonLevelVO level = levelMap.get(goodsStoreId);
                        //过滤出符合等级条件的营销信息
                        newMarketingMap.put(skuId, marketingList.stream().filter(marketing -> {
                            if (BoolFlag.YES == marketing.getIsBoss()){
                                //不限等级
                                if (Constants.STR_0.equals(marketing.getJoinLevel()) || Constants.STR_MINUS_1.equals(marketing.getJoinLevel())) {
                                    return true;
                                }
                                // 查询平台等级
                                HashMap<Long, CommonLevelVO> levelPlatFormMap =
                                        customerLevelQueryProvider.listCustomerLevelMapByCustomerId(CustomerLevelMapByCustomerIdAndStoreIdsRequest.
                                                builder().customerId(validateTrade.getBuyer().getId()).storeIds(Collections.singletonList(-1L)).build()).getContext().getCommonLevelVOMap();
                                CommonLevelVO levelPlatForm = levelPlatFormMap.get(Constants.BOSS_DEFAULT_STORE_ID);
                                if (Arrays.asList(marketing.getJoinLevel().split(",")).contains(String.valueOf(levelPlatForm.getLevelId()))) {
                                    return true;
                                }
                            }else {
                                //全平台
                                if (Constants.STR_MINUS_1.equals(marketing.getJoinLevel())) {
                                    return true;
                                } else if (levelMap.isEmpty()) {
                                    StoreVO storeVO = storeVOMap.get(goodsMap.get(skuId));
                                    return BoolFlag.NO == storeVO.getCompanyType() && "0".equals(marketing.getJoinLevel());
                                } else if (Objects.nonNull(level)) {
                                    //不限等级
                                    if (Constants.STR_0.equals(marketing.getJoinLevel())) {
                                        return true;
                                    } else if (Arrays.asList(marketing.getJoinLevel().split(",")).contains(String.valueOf(level.getLevelId()))) {
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }).collect(Collectors.toList()));
                    }
                });
                // 查询订单匹配支付金额
                if (MapUtils.isNotEmpty(newMarketingMap)){
                    // 将满足的skuId组装起来
                    Map<List<MarketingForEndVO>, List<String>> filterSkuIdMap = Maps.newHashMap();
                    for (String key:newMarketingMap.keySet()){
                        List<MarketingForEndVO> value =newMarketingMap.get(key);
                        if (filterSkuIdMap.containsKey(value)) {
                            List<String> list = filterSkuIdMap.get(value);
                            list.add(key);
                        } else {
                            List<String> list = Lists.newArrayList();
                            list.add(key);
                            filterSkuIdMap.put(value, list);
                        }
                    }
                    // 符合条件的skuId
                    List<String> skuIds = new ArrayList<>(newMarketingMap.keySet());
                    for (Trade trade:tradeList){
                        // 符合条件的券id
                        List<FullReturnCoupon> fullReturnCoupons = Lists.newArrayList();
                        List<TradeItem> tradeItems = trade.getTradeItems();
                        if (CollectionUtils.isNotEmpty(tradeItems)) {
                            // 计算实际支付价格是否满足发券
                            BigDecimal total = BigDecimal.ZERO;
                            // 将满足的skuId匹配金额
                            Map<String, BigDecimal> stringBigDecimalMap = Maps.newHashMap();
                            // 从订单中取一条符合的skuId，用来获取营销信息
                            String skuId = null;
                            // 多个商品提交获取订单中满足满返活动的金额
                            for (List<MarketingForEndVO> key:filterSkuIdMap.keySet()){
                                total = BigDecimal.ZERO;
                                for (TradeItem tradeItem:tradeItems){
                                    if (skuIds.contains(tradeItem.getSkuId())){
                                        List<String> filterSkuIds = filterSkuIdMap.get(key);
                                        if (filterSkuIds.contains(tradeItem.getSkuId())) {
                                            skuId = tradeItem.getSkuId();
                                            total = total.add(tradeItem.getSplitPrice());
                                        }
                                    }
                                }
                                // 满足条件的金额放入map中
                                if(Objects.nonNull(skuId) && total.compareTo(BigDecimal.ZERO)>0){
                                    stringBigDecimalMap.put(skuId, total);
                                }
                            }
                            if (MapUtils.isNotEmpty(stringBigDecimalMap)){
                                stringBigDecimalMap.forEach((id, totalP) -> {
                                    //根据支付金额计算是否满足发券条件
                                    if (StringUtils.isNotBlank(id)){
                                        // 获取店铺满足的营销（包含boss端及supplier端）
                                        List<MarketingForEndVO> marketingForEndVOList = newMarketingMap.get(id);
                                        marketingForEndVOList.forEach(marketingForEndVO -> {
                                            // 满足条件的最大的等级
                                            List<MarketingFullReturnLevelVO> returnLevels =
                                                    marketingForEndVO.getFullReturnLevelList().stream().filter(level -> level.getFullAmount().compareTo(totalP) <= 0).collect(Collectors.toList());
                                            dealFullReturnLevel(fullReturnCoupons, marketingForEndVO, returnLevels, 0);
                                        });
                                    }
                                });
                            }
                        }
                        //将券信息记录到订单中
                        if (CollectionUtils.isNotEmpty(fullReturnCoupons)){
                            trade.setFullReturnCoupons(fullReturnCoupons);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param tradeList
     * @param param
     * @return void
     * @description 社区团购佣金信息处理
     * @author edz
     * @date: 2023/7/27 11:11
     */
    @Override
    public void communityCommissionProcess(List<Trade> tradeList, Trade1CommitParam param) {
        CommunityActivityVO communityActivityVO = param.getCommunityActivityByIdResponse().getCommunityActivityVO();
        tradeList.forEach(trade -> {
                    CommunityTradeCommission communityTradeCommission = new CommunityTradeCommission();
                    communityTradeCommission.setActivityId(communityActivityVO.getActivityId());
                    communityTradeCommission.setBoolFlag(BoolFlag.NO);
                    communityTradeCommission.setCommissionFlag(communityActivityVO.getCommissionFlag());
                    if (Objects.nonNull(param.getCommunityLeaderVO())){
                        communityTradeCommission.setLeaderId(param.getCommunityLeaderVO().getLeaderId());
                        communityTradeCommission.setLeaderPhone(param.getCommunityLeaderVO().getLeaderAccount());
                        communityTradeCommission.setCustomerId(param.getCommunityLeaderVO().getCustomerId());
                    }
                    if (CollectionUtils.isNotEmpty(param.getCommunityLeaderPickupPointVOS())
                            && Objects.nonNull(trade.getPickupFlag()) && trade.getPickupFlag()){
                        CommunityLeaderPickupPointVO communityLeaderPickupPointVO =
                                param.getCommunityLeaderPickupPointVOS().get(0);
                        communityTradeCommission.setPickupPointId(communityLeaderPickupPointVO.getPickupPointId());
                        communityTradeCommission.setCustomerId(communityLeaderPickupPointVO.getCustomerId());
                        communityTradeCommission.setLeaderId(communityLeaderPickupPointVO.getLeaderId());
                        communityTradeCommission.setLeaderPhone(communityLeaderPickupPointVO.getLeaderAccount());
                    }
                    List<CommunityTradeCommission.GoodsInfoItem> goodsInfoItemList = new ArrayList<>();
                    trade.getTradeItems().forEach(tradeItem -> {
                        CommunityTradeCommission.GoodsInfoItem goodsInfoItem = new CommunityTradeCommission.GoodsInfoItem();
                        goodsInfoItem.setGoodsInfoId(tradeItem.getSkuId());
                        goodsInfoItem.setNum(tradeItem.getNum());
                        goodsInfoItem.setPrice(tradeItem.getSplitPrice());
                        goodsInfoItemList.add(goodsInfoItem);
                    });
                    communityTradeCommission.setGoodsInfoItem(goodsInfoItemList);
                    trade.setCommunityTradeCommission(communityTradeCommission);
                });
        // 销售类型
        List<CommunitySalesType> salesTypes = communityActivityVO.getSalesTypes();
        // 物流方式
        List<CommunityLogisticsType> logisticsTypes = communityActivityVO.getLogisticsTypes();
        String leaderId = param.getSnapshot().getItemGroups().get(0).getCommunityBuyRequest().getLeaderId();
        tradeList.forEach(trade -> {
            // 自主销售
            if (salesTypes.contains(CommunitySalesType.SELF) && Objects.isNull(leaderId)){
                trade.getCommunityTradeCommission().setSalesType(CommunitySalesType.SELF);
                // 快递 自主销售快递配送没有佣金
                if (logisticsTypes.contains(CommunityLogisticsType.EXPRESS)
                        && Objects.isNull(trade.getCommunityTradeCommission().getPickupPointId())){
                    trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                        item.setCommission(BigDecimal.ZERO);
                        item.setCommissionRate(BigDecimal.ZERO);
                    });
                    CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                    communityTradeCommission.setCommissionRate(BigDecimal.ZERO);
                }

                // 自提 自主销售。自提点产生自提服务佣金
                if (logisticsTypes.contains(CommunityLogisticsType.PICKUP)
                        && Objects.nonNull(trade.getCommunityTradeCommission().getPickupPointId())){
                    // 商品佣金设置
                    if (CommunityCommissionFlag.GOODS.equals(communityActivityVO.getCommissionFlag())){
                        Map<String, CommunitySkuRelVO> skuIdToIdentity =
                                communityActivityVO.getSkuList().stream().collect(Collectors.toMap(CommunitySkuRelVO::getSkuId,
                                        Function.identity()));
                        trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                            CommunitySkuRelVO communitySkuRelVO = skuIdToIdentity.get(item.getGoodsInfoId());
                            if (Objects.isNull(communitySkuRelVO.getPickupCommission())
                                    || communitySkuRelVO.getPickupCommission().compareTo(BigDecimal.ZERO) == 0){
                                item.setCommission(BigDecimal.ZERO);
                            } else {
                                item.setCommission(item.getPrice()
                                        .multiply(communitySkuRelVO.getPickupCommission()
                                                .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                            }
                            item.setCommissionRate(communitySkuRelVO.getPickupCommission());
                        });
                        CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                    } else if (CommunityCommissionFlag.PICKUP.equals(communityActivityVO.getCommissionFlag())){ // 团长佣金配置
                        AtomicBoolean isDone = new AtomicBoolean(false);
                        List<CommunityCommissionLeaderRelVO> commissionLeaderList = communityActivityVO.getCommissionLeaderList();
                        if (CollectionUtils.isNotEmpty(commissionLeaderList)){
                            Map<String, CommunityCommissionLeaderRelVO> pickupPointIdToIdentityMap =
                                    commissionLeaderList.stream()
                                            .collect(Collectors.toMap(CommunityCommissionLeaderRelVO::getPickupPointId, Function.identity()));
                            CommunityCommissionLeaderRelVO communityCommissionLeaderRelVO =
                                    pickupPointIdToIdentityMap.get(trade.getCommunityTradeCommission().getPickupPointId());
                            if (Objects.nonNull(communityCommissionLeaderRelVO)
                                    && Objects.nonNull(communityCommissionLeaderRelVO.getPickupCommission())){ // 指定团长、自提点设置
                                trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                    item.setCommission(item.getPrice()
                                            .multiply(communityCommissionLeaderRelVO.getPickupCommission()
                                                    .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                                    item.setCommissionRate(communityCommissionLeaderRelVO.getPickupCommission());
                                });
                                CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                                communityTradeCommission.setCommissionRate(communityCommissionLeaderRelVO.getPickupCommission());
                                isDone.set(true);
                            }
                        }

                        // 指定团长、自提点设置配置为空，取地区设置
                        if (!isDone.get()){
                            List<CommunityCommissionAreaRelVO> commissionAreaList =
                                    param.getCommunityActivityByIdResponse().getCommunityActivityVO().getCommissionAreaList();
                            if (CollectionUtils.isNotEmpty(commissionAreaList)){
                                CommunityLeaderPickupPointVO communityLeaderPickupPointVO =
                                        param.getCommunityLeaderPickupPointVOS().get(0);
                                List<Long> codes = Arrays.asList(
                                        communityLeaderPickupPointVO.getPickupProvinceId(),
                                        communityLeaderPickupPointVO.getPickupCityId(),
                                        communityLeaderPickupPointVO.getPickupAreaId(),
                                        communityLeaderPickupPointVO.getPickupStreetId());

                                commissionAreaList.stream()
                                        .filter(g -> codes.stream()
                                                .anyMatch(i -> g.getAreaId().contains(i.toString())))
                                        .findFirst()
                                        .ifPresent(t -> {
                                            if (Objects.nonNull(t.getPickupCommission())){ // 指定团长、自提点设置
                                                trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                                    item.setCommission(item.getPrice()
                                                            .multiply(t.getPickupCommission()
                                                                    .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                                                    item.setCommissionRate(t.getPickupCommission());
                                                });
                                                CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                                                communityTradeCommission.setCommissionRate(t.getPickupCommission());
                                                isDone.set(true);
                                            }
                                        });
                            }
                        }
                        // 指定团长、自提点设置\地区设置配置为空，取批量设置
                        if (!isDone.get()){
                            if (Objects.nonNull(communityActivityVO.getPickupCommission())){ // 指定团长、自提点设置
                                trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                    item.setCommission(item.getPrice()
                                            .multiply(communityActivityVO.getPickupCommission()
                                                    .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                                    item.setCommissionRate(communityActivityVO.getPickupCommission());
                                });
                                CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                                communityTradeCommission.setCommissionRate(communityActivityVO.getPickupCommission());
                                isDone.set(true);
                            }
                        }

                        if (!isDone.get()){
                            trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                item.setCommission(BigDecimal.ZERO);
                                item.setCommissionRate(BigDecimal.ZERO);
                            });
                            CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                            communityTradeCommission.setCommissionRate(BigDecimal.ZERO);
                        }
                    }
                }
            }

            // 帮卖佣金计算 帮卖佣金只计算团长的佣金，不计算自提点服务费
            if (salesTypes.contains(CommunitySalesType.LEADER) && Objects.nonNull(leaderId)){
                trade.getCommunityTradeCommission().setSalesType(CommunitySalesType.LEADER);
                // 商品佣金配置
                if (CommunityCommissionFlag.GOODS.equals(communityActivityVO.getCommissionFlag())){
                    Map<String, CommunitySkuRelVO> skuIdToIdentity =
                            communityActivityVO.getSkuList().stream().collect(Collectors.toMap(CommunitySkuRelVO::getSkuId,
                                    Function.identity()));
                    trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                        CommunitySkuRelVO communitySkuRelVO = skuIdToIdentity.get(item.getGoodsInfoId());
                        if (Objects.isNull(communitySkuRelVO.getAssistCommission())
                                || communitySkuRelVO.getAssistCommission().compareTo(BigDecimal.ZERO) == 0){
                            item.setCommission(BigDecimal.ZERO);
                        } else {
                            item.setCommission(item.getPrice()
                                    .multiply(communitySkuRelVO.getAssistCommission()
                                            .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                        }
                        item.setCommissionRate(communitySkuRelVO.getAssistCommission());
                    });
                } else if (CommunityCommissionFlag.PICKUP.equals(communityActivityVO.getCommissionFlag())){ // 团长佣金配置
                    AtomicBoolean isDone = new AtomicBoolean(false);
                    List<CommunityCommissionLeaderRelVO> commissionLeaderList = communityActivityVO.getCommissionLeaderList();
                    if (CollectionUtils.isNotEmpty(commissionLeaderList)){
                        Map<String, CommunityCommissionLeaderRelVO> pickupPointIdToIdentityMap =
                                commissionLeaderList.stream()
                                        .collect(Collectors.toMap(CommunityCommissionLeaderRelVO::getPickupPointId, Function.identity()));
                        CommunityCommissionLeaderRelVO communityCommissionLeaderRelVO =
                                pickupPointIdToIdentityMap.get(trade.getCommunityTradeCommission().getPickupPointId());
                        if (communityCommissionLeaderRelVO != null && Objects.nonNull(communityCommissionLeaderRelVO.getAssistCommission())){ // 指定团长、自提点设置
                            trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                item.setCommission(item.getPrice()
                                        .multiply(communityCommissionLeaderRelVO.getAssistCommission()
                                                .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                                item.setCommissionRate(communityCommissionLeaderRelVO.getAssistCommission());
                            });
                            CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                            communityTradeCommission.setCommissionRate(communityCommissionLeaderRelVO.getAssistCommission());
                            isDone.set(true);
                        }
                    }

                    // 指定团长、自提点设置配置为空，取地区设置
                    if (!isDone.get()){
                        List<CommunityCommissionAreaRelVO> commissionAreaList =
                                param.getCommunityActivityByIdResponse().getCommunityActivityVO().getCommissionAreaList();
                        if (CollectionUtils.isNotEmpty(commissionAreaList)){
                            CommunityLeaderPickupPointVO communityLeaderPickupPointVO =
                                    param.getCommunityLeaderPickupPointVOS().get(0);
                            List<Long> codes = Arrays.asList(
                                    communityLeaderPickupPointVO.getPickupProvinceId(),
                                    communityLeaderPickupPointVO.getPickupCityId(),
                                    communityLeaderPickupPointVO.getPickupAreaId(),
                                    communityLeaderPickupPointVO.getPickupStreetId());

                            commissionAreaList.stream()
                                    .filter(g -> codes.stream()
                                            .anyMatch(i -> g.getAreaId().contains(i.toString())))
                                    .findFirst()
                                    .ifPresent(t -> {
                                        if (Objects.nonNull(t.getAssistCommission())){ // 指定团长、自提点设置
                                            trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                                item.setCommission(item.getPrice()
                                                        .multiply(t.getAssistCommission()
                                                                .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                                                item.setCommissionRate(t.getAssistCommission());
                                            });
                                            CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                                            communityTradeCommission.setCommissionRate(t.getAssistCommission());
                                            isDone.set(true);
                                        }
                                    });
                        }
                    }
                    // 指定团长、自提点设置\地区设置配置为空，取批量设置
                    if (!isDone.get()){
                        if (Objects.nonNull(communityActivityVO.getAssistCommission())){ // 指定团长、自提点设置
                            trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                                item.setCommission(item.getPrice()
                                        .multiply(communityActivityVO.getAssistCommission()
                                                .divide(new BigDecimal("100"))).setScale(2, RoundingMode.DOWN));
                                item.setCommissionRate(communityActivityVO.getAssistCommission());
                            });
                            CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                            communityTradeCommission.setCommissionRate(communityActivityVO.getAssistCommission());
                            isDone.set(true);
                        }

                    }

                    if (!isDone.get()){
                        trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(item -> {
                            item.setCommission(BigDecimal.ZERO);
                            item.setCommissionRate(BigDecimal.ZERO);
                        });
                        CommunityTradeCommission communityTradeCommission = trade.getCommunityTradeCommission();
                        communityTradeCommission.setCommissionRate(BigDecimal.ZERO);
                    }
                }

            }
        });
    }

    /**
     * 满返等级处理
     *
     * @param fullReturnCoupons
     * @param returnLevels
     * @param marketingForEndVO
     */
    private void dealFullReturnLevel(List<FullReturnCoupon> fullReturnCoupons, MarketingForEndVO marketingForEndVO,
                                     List<MarketingFullReturnLevelVO> returnLevels, int num) {
        // 多个等级循环遍历，发券返回或遍历结束，最多循环5次
        if (CollectionUtils.isNotEmpty(returnLevels) && num++ < Constants.FIVE){
            Optional<MarketingFullReturnLevelVO> fullReturnLevelOptional =
                    returnLevels.stream().max(Comparator.comparing(MarketingFullReturnLevelVO::getFullAmount));
            if (fullReturnLevelOptional.isPresent()) {
                List<MarketingFullReturnDetailVO> marketingFullReturnDetailVOS =
                        fullReturnLevelOptional.get().getFullReturnDetailList();
                dealReturnDetail(fullReturnCoupons, marketingForEndVO, marketingFullReturnDetailVOS);
                if (CollectionUtils.isEmpty(fullReturnCoupons)){
                    returnLevels.remove(fullReturnLevelOptional.get());
                    dealFullReturnLevel(fullReturnCoupons, marketingForEndVO, returnLevels, num);
                }
            }
        }
    }
    /**
     * 满返发券处理
     *
     * @param fullReturnCoupons
     * @param marketingFullReturnDetailVOS
     * @param marketingForEndVO
     */
    private void dealReturnDetail(List<FullReturnCoupon> fullReturnCoupons, MarketingForEndVO marketingForEndVO,
                                 List<MarketingFullReturnDetailVO> marketingFullReturnDetailVOS) {
        if (CollectionUtils.isNotEmpty(marketingFullReturnDetailVOS)){
            marketingFullReturnDetailVOS.forEach(marketingFullReturnDetailVO -> {
                FullReturnCoupon fullReturnCoupon = new FullReturnCoupon();
                // 扣减券数量
                String fullReturnCouponNumKey =
                        RedisKeyConstant.FULL_RETURN_COUPON_NUM_KEY.concat(String.valueOf(marketingFullReturnDetailVO.getReturnDetailId()));
                // 如果key不存在，则初始化
                if (!redisService.hasKey(fullReturnCouponNumKey)){
                    Duration duration = between(LocalDateTime.now(), marketingForEndVO.getEndTime());
                    long existSeconds = duration.getSeconds();
                    if (existSeconds<=0){
                        existSeconds = Constants.FIVE;
                    }
                    redisService.setString(fullReturnCouponNumKey,
                            String.valueOf(marketingFullReturnDetailVO.getCouponNum()), existSeconds);
                }
                Long remainStock = redisService.decrByKey(fullReturnCouponNumKey, Constants.NUM_1L);
                if (remainStock < 0) {
                    redisService.incrByKey(fullReturnCouponNumKey, Constants.NUM_1L);
                }else {
                    fullReturnCoupon.setReturnDetailId(marketingFullReturnDetailVO.getReturnDetailId());
                    fullReturnCoupon.setCouponId(marketingFullReturnDetailVO.getCouponId());
                    fullReturnCoupons.add(fullReturnCoupon);
                }
            });
        }
    }

    private <T extends CountPriceGoodsInfoDTO> List<T> tradeItemListToCountCouponPriceGoodsInfoDTO(
            List<TradeItem> preferentialItems,
            List<TradeItem> tradeItems, Class<T> clazz) {
        if (preferentialItems == null) preferentialItems = new ArrayList<>();
        // 修改tradeItem数据。拷贝一份目的是保持源数据不变
        List<TradeItem> copyTradeItems = KsBeanUtil.convert(tradeItems, TradeItem.class);
        List<TradeItem> copyPreferentialItems = KsBeanUtil.convert(preferentialItems, TradeItem.class);
        List<T> resultList = new ArrayList<>();
        Map<String,CountCouponPriceGoodsInfoDTO> resultListMap = new HashMap<>();
        Map<String, TradeItem> skuIdToTradeItemMap =
                copyTradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId, Function.identity()));
        if (CollectionUtils.isNotEmpty(copyPreferentialItems)){
            List<String> doneSkuIds = new ArrayList<>();
            for (TradeItem tradeItem : copyPreferentialItems){
                TradeItem item = skuIdToTradeItemMap.get(tradeItem.getSkuId());
                if (Objects.nonNull(item)){
                    doneSkuIds.add(item.getSkuId());
                    item.setNum(tradeItem.getNum() + item.getNum());
                    BigDecimal total = tradeItem.getSplitPrice().add(item.getSplitPrice());
                    item.setSplitPrice(total);
                } else {
                    skuIdToTradeItemMap.put(tradeItem.getSkuId(),tradeItem);
                }
            }

            //二次循环
            List<TradeItem> copyPreferentialItems1 = KsBeanUtil.convert(preferentialItems, TradeItem.class);
            for (TradeItem tradeItem : copyPreferentialItems1){
                TradeItem item = skuIdToTradeItemMap.get(tradeItem.getSkuId());
                CountCouponPriceGoodsInfoDTO dto = resultListMap.get(tradeItem.getSkuId());
                if(Objects.isNull(dto)){
                    dto = this.orderMapper.tradeItemToCountPriceGoodsInfoDTO(item);
                }
                Map<Long,BigDecimal> priceRateMap = dto.getPriceRate();
                BigDecimal priceRate = tradeItem.getSplitPrice().divide(item.getSplitPrice(), 5, RoundingMode.DOWN);
                if(MapUtils.isEmpty(priceRateMap)){
                    priceRateMap = new HashMap<>();
                }
                priceRateMap.put(tradeItem.getMarketingIds().get(0),priceRate);
                dto.setPriceRate(priceRateMap);
                resultListMap.put(item.getSkuId(),dto);
            }

            if(MapUtils.isNotEmpty(resultListMap)){
                List<CountCouponPriceGoodsInfoDTO> count = resultListMap.values().stream().collect(Collectors.toList());
                count.forEach(c -> {
                    resultList.add((T)c);
                });
            }

            List<TradeItem> itemList =
                    copyTradeItems.stream().filter(g -> !doneSkuIds.contains(g.getSkuId())).collect(Collectors.toList());
            itemList.forEach(tradeItem -> {
                resultList.add(
                        (T) this.orderMapper.tradeItemToCountPriceGoodsInfoDTO(tradeItem));
            });
        } else {
            copyTradeItems.forEach(tradeItem -> {
                resultList.add(
                        (T) this.orderMapper.tradeItemToCountPriceGoodsInfoDTO(tradeItem));
            });
        }
        return resultList;
    }
}
