package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.OrderType;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.UserPickupCardRequest;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.optimization.trade1.commit.common.Trade1CommitBuilder;
import com.wanmi.sbc.order.optimization.trade1.commit.common.Trade1CommitPriceUtil;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitProcessInterface;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Freight;
import com.wanmi.sbc.order.trade.model.entity.value.ProviderFreight;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.service.commit.FreightService;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitProcessService
 * @description
 * @date 2022/3/1 3:39 下午
 */
@Service
@Slf4j
public class PackupCardCommitProcessService implements Trade1CommitProcessInterface {

    @Autowired CustomerLevelQueryProvider customerLevelQueryProvider;
    @Autowired GeneratorService generatorService;
    @Autowired FreightService freightService;
    @Autowired Trade1CommitBuilder build;

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Override
    public List<Trade> buildTrade(TradeCommitRequest tradeCommitRequest, Trade1CommitParam param) {
        String customerId = tradeCommitRequest.getOperator().getUserId();
        List<TradeItemGroup> tradeItemGroups = param.getSnapshot().getItemGroups();
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

        Map<Long, PickSettingInfo> finalPickUpInfoMap = param.getPickSettingInfoMap();
        // 生成父订单号
        String parentId = generatorService.generatePoId();

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
                            trade.setTransitReturn(Boolean.FALSE);
                            // 20、支付顺序，先货后款/先款后货
                            trade.setPaymentOrder(build.buildPayment());

                            // 21、订单明细
                            trade.setTradeItems(
                                    build.buildItem(
                                            group.getTradeItems(),
                                            param.getGoodsInfoTradeVOS(),
                                            group.getStoreBagsFlag(),
                                            param.getBargainVO(),
                                            group.getTradeBuyCycleDTO()));

                            // 22、设置第三方类型
                            trade.setThirdPlatformTypes(
                                    trade.getTradeItems().stream()
                                            .filter(i -> i.getThirdPlatformType() != null)
                                            .map(TradeItem::getThirdPlatformType)
                                            .collect(Collectors.toList()));

                            // 27、设置订单状态
                            trade.setTradeState(build.buildTradeState());

                            // 28、设置营销
                            trade.setTradeMarketings(new ArrayList<>());

                            // 29、设置订单标识
                            trade.setOrderTag(group.getOrderTag());

                            //33. 默认支付版本号10。
                            // 为什么是10不是1呢？版本号的增长使用了在更新语句中直接+1的操作，所以mongo中存的是数字类型。存01最终就是1。
                            // 版本号是和订单ID拼接一块使用的。为了保证拼接后位数相同所以从10开始
                            trade.setPayVersion(10);

                            //设置 需要预约发货提货卡的订单 的预约发货时间(以旧送新活动送提货卡预约发货)
                            trade.setAppointmentShipmentFlag(tradeCommitRequest.getAppointmentShipmentFlag());
                            trade.setAppointmentShipmentTime(tradeCommitRequest.getAppointmentShipmentTime());

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
            List<Trade> tradeList, Trade1CommitParam param, TradeCommitRequest request) {}

    /**
     * 优惠券处理
     *
     * @param tradeList
     * @param request
     */
    @Override
    public void couponProcess(List<Trade> tradeList, TradeCommitRequest request) {

    }

    @Override
    public void pointProcess(
            List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {
        SystemPointsConfigQueryResponse pointsConfig = param.getSystemPointsConfigQueryResponse();
        final BigDecimal pointWorth = BigDecimal.valueOf(pointsConfig.getPointsWorth());
        long sumPoints =
                tradeList.stream()
                        .flatMap(trade -> trade.getTradeItems().stream())
                        .filter(k -> Objects.nonNull(k.getBuyPoint()))
                        .mapToLong(k -> k.getBuyPoint() * k.getNum())
                        .sum();
        if (sumPoints <= 0) {
            return;
        }

        if (EnableStatus.ENABLE.equals(pointsConfig.getStatus())
                && PointsUsageFlag.GOODS.equals(pointsConfig.getPointsUsageFlag())) {
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
                        // 计算积分抵扣额(pointsPrice、points)，并追加积分抵现金额
                        TradePrice tradePrice = trade.getTradePrice();
                        Long points =
                                Trade1CommitPriceUtil.calcSkusTotalPoints(trade.getTradeItems());
                        tradePrice.setPoints(points);
                        tradePrice.setBuyPoints(points);
                        tradePrice.setPointsPrice(
                                BigDecimal.valueOf(points)
                                        .divide(pointWorth, 2, RoundingMode.HALF_UP));
                    });
        }
    }

    @Override
    public void freightProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {

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

                    tradePrice.setDeliveryPrice(deliveryPrice);
                    // 计算订单总价(追加运费)
                    tradePrice.setOriginPrice(tradePrice.getOriginPrice().add(deliveryPrice));
                });
    }

    @Override
    public void giftCardProcess(List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {
        UserGiftCardInfoVO userGiftCardInfoVO = param.getUserGiftCardInfoVOList().get(0);
        tradeList.forEach(trade -> {
            final BigDecimal[] total = {BigDecimal.ZERO};
            trade.getTradeItems().forEach(tradeItem -> {
                total[0] = total[0].add(tradeItem.getSplitPrice());
                tradeItem.getGiftCardItemList().add(TradeItem.GiftCardItem.builder()
                        .giftCardNo(userGiftCardInfoVO.getGiftCardNo())
                        .giftCardType(GiftCardType.PICKUP_CARD)
                        .userGiftCardID(userGiftCardInfoVO.getUserGiftCardId())
                        .price(tradeItem.getSplitPrice().add(Nutils.defaultVal(tradeItem.getPointsPrice(), BigDecimal.ZERO)))
                        .build());
            });

            // 抵扣金额 = 商品市场价 + 积分抵扣价 + 运费
            trade.getTradePrice().setGiftCardPrice(total[0]
                    .add(Nutils.defaultVal(trade.getTradePrice().getPointsPrice(), BigDecimal.ZERO))
                    .add(trade.getTradePrice().getDeliveryPrice()));
            trade.getTradePrice().setGiftCardType(GiftCardType.PICKUP_CARD);
            // 提货卡付 0.01元
            BigDecimal pickupPayPrice = BigDecimal.valueOf(0.01);
            trade.getTradePrice().setGiftCardPrice(trade.getTradePrice().getGiftCardPrice().subtract(pickupPayPrice));
            trade.getTradePrice().setTotalPrice(pickupPayPrice);

        });

        List<String> tIds = tradeList.stream().map(Trade::getId).collect(Collectors.toList());
        userGiftCardProvider.userPickupCard(UserPickupCardRequest.builder().userGiftCardId(userGiftCardInfoVO.getUserGiftCardId()).tIds(tIds).build());
    }



    @Override
    public void distributionProcess(
            List<Trade> tradeList, TradeCommitRequest request, Trade1CommitParam param) {}

    @Override
    public void auditStateProcess(List<Trade> tradeList, TradeCommitRequest request) {
        // 是否开启订单审核（同时判断是否为秒杀抢购商品订单）
        tradeList.forEach(
                trade -> {
                    TradeState tradeState =
                            TradeState.builder()
                                    .deliverStatus(DeliverStatus.NOT_YET_SHIPPED)
                                    .flowState(FlowState.AUDIT)
                                    .payState(PayState.NOT_PAID)
                                    .auditState(AuditState.CHECKED)
                                    .createTime(LocalDateTime.now())
                                    .build();
                    trade.setTradeState(tradeState);
                    if (trade.getDeliverWay() == DeliverWay.SAME_CITY) {
                        tradeState.setDistributionState(DistributionState.NONE);
                    }
                    trade.getTradeState().setRefundStatus(0);
                    trade.getTradeState().setAuditState(AuditState.CHECKED);
                    trade.setIsAuditOpen(Boolean.FALSE);
                });
    }

    @Override
    public void settingProcess(List<Trade> tradeList) {}

    /**
     * 满返处理
     *
     * @param tradeList
     * @param param
     */
    @Override
    public void fullReturnProcess(List<Trade> tradeList, Trade1CommitParam param) {

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

    }

}
