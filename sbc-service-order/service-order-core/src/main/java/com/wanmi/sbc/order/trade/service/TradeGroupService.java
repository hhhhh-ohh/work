package com.wanmi.sbc.order.trade.service;


import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.marketing.bean.constant.CouponErrorCode;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.CouponDiscountMode;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.CountCouponPriceVO;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.dto.StoreCommitInfoDTO;
import com.wanmi.sbc.order.common.SystemPointsConfigService;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.trade.model.entity.TradeGrouponCommitForm;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Freight;
import com.wanmi.sbc.order.trade.model.entity.value.ProviderFreight;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.repository.TradeGroupRepository;
import com.wanmi.sbc.order.trade.service.commit.FreightService;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import io.seata.common.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TradeGroupService {

    @Autowired
    private TradeGroupRepository tradeGroupRepository;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeMarketingService tradeMarketingService;

    @Autowired private TradeService tradeService;

    @Autowired private FreightService freightService;

    @Autowired
    protected SystemPointsConfigService systemPointsConfigService;


    /**
     * 新增文档
     * 专门用于数据新增服务,不允许数据修改的时候调用
     * @param tradeGroup
     */
    public void addTradeGroup(TradeGroup tradeGroup) {
        tradeGroupRepository.save(tradeGroup);
    }


    /**
     * 构建订单组对象，同时修改订单列表中相应的价格信息
     * @param trades             订单列表
     * @param tradeCommitRequest
     * @return
     */
    public TradeGroup wrapperTradeGroup(
            List<Trade> trades, TradeCommitRequest tradeCommitRequest, List<TradeItemGroup> tradeItemGroups) {
        if (tradeCommitRequest.getCommonCodeId() == null) {
            return null;
        }

        // 非零元订单的数量
        long tradesCount =
                trades.stream()
                        .filter(
                                t -> {
                                    if (Objects.nonNull(t.getTradePrice())) {
                                        TradePrice tradePrice = t.getTradePrice();
                                        //由于总价包含了运费则扣除
                                        BigDecimal tmpTotal =
                                                tradePrice
                                                        .getTotalPrice()
                                                        .subtract(tradePrice.getDeliveryPrice());
                                        return tmpTotal.compareTo(BigDecimal.ZERO) > 0;
                                    }
                                    return false;
                                })
                        .count();
        // 没有非零元订单，无需继续使用平台券
        if (tradesCount == 0) {
            return null;
        }

        CustomerSimplifyOrderCommitVO customer = tradeCommitRequest.getCustomer();
        TradeGroup tradeGroup = new TradeGroup();

        // 1.请求营销插件，验证并包装优惠券信息
        List<TradeItem> items = trades.stream().flatMap(trade -> trade.getTradeItems().stream()).collect(Collectors
                .toList());
        TradeCouponVO tradeCoupon = tradeMarketingService.buildTradeCouponInfo(
                items,
                tradeCommitRequest.getCommonCodeId(),
                tradeCommitRequest.isForceCommit(),
                customer.getCustomerId());
        if (tradeCoupon == null) {
            return null;
        }

        // 2.找出需要均摊的商品，以及总价
        List<TradeItem> matchItems = items.stream()
                .filter(t -> tradeCoupon.getGoodsInfoIds().contains(t.getSkuId())).collect(Collectors.toList());
        BigDecimal total = tradeItemService.calcSkusTotalPrice(matchItems);

        // 3.判断是否达到优惠券使用门槛
        BigDecimal fullBuyPrice = tradeCoupon.getFullBuyPrice();
        if (fullBuyPrice != null && fullBuyPrice.compareTo(total) > 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080090);
        }

        // 优惠券面值原始金额
        BigDecimal originCouponPrice = tradeCoupon.getDiscountsAmount();
        // 4.如果商品总价小于优惠券优惠金额，设置优惠金额为商品总价
        if (total.compareTo(tradeCoupon.getDiscountsAmount()) < 0) {
            tradeCoupon.setDiscountsAmount(total);
        }

        // 5.设置关联商品的结算信息
        matchItems.forEach(item ->
                item.getCouponSettlements().add(TradeItem.CouponSettlement.builder()
                        .couponType(tradeCoupon.getCouponType())
                        .couponCodeId(tradeCoupon.getCouponCodeId())
                        .couponCode(tradeCoupon.getCouponCode())
                        .splitPrice(item.getSplitPrice()).build())
        );

        // 6.设置关联商品的均摊价
        tradeItemService.calcSplitPrice(matchItems, total.subtract(tradeCoupon.getDiscountsAmount()), total, null);

        // 7.刷新关联商品的结算信息
        matchItems.forEach(item -> {
            TradeItem.CouponSettlement settlement =
                    item.getCouponSettlements().get(item.getCouponSettlements().size() - 1);
            // 如果是定金预售
            if (item.getBookingType() == BookingType.EARNEST_MONEY && Objects.nonNull(item.getTailPrice())) {
                BigDecimal deliveryPrice  =  trades.get(0).getTradePrice().getDeliveryPrice();
                BigDecimal orderGoodsPrice =  total.subtract(deliveryPrice);
                // 如果商品总价（定金预售情况下已经包含了运费）大于优惠券优惠金额
                if (orderGoodsPrice.compareTo(originCouponPrice) > 0) {
                    // 包含运费的尾款-重新计算后的分摊价
                    settlement.setReducePrice(originCouponPrice);
//                    settlement.setReducePrice(item.getTailPrice().subtract(item.getSplitPrice()));
                } else {
                    // 不包含运费的分摊价 - 重新计算后的分摊价
//                    settlement.setReducePrice(settlement.getSplitPrice().subtract(item.getSplitPrice()));
                    settlement.setReducePrice(orderGoodsPrice);
                }
                settlement.setSplitPrice(item.getSplitPrice().subtract(item.getEarnestPrice()));
            } else {
                settlement.setReducePrice(settlement.getSplitPrice().subtract(item.getSplitPrice()));
                settlement.setSplitPrice(item.getSplitPrice());
            }

        });

        // 8.按店铺分组被均摊的商品，刷新相应订单的价格信息
        Map<Long, List<TradeItem>> itemsMap = items.stream().collect(Collectors.groupingBy(TradeItem::getStoreId));
        itemsMap.keySet().forEach(storeId -> {
            // 8.1.找到店铺对应订单的价格信息
            Trade trade = trades.stream()
                    .filter(t -> t.getSupplier().getStoreId().equals(storeId)).findFirst().orElse(null);
            TradePrice tradePrice = trade.getTradePrice();
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && trade.getBookingType() == BookingType.EARNEST_MONEY
                    && Objects.nonNull(tradePrice.getTailPrice())) {
                // 8.2.计算平台优惠券优惠额(couponPrice)，并追加至订单优惠金额、优惠券优惠金额
                BigDecimal marketTotalPrice = tradePrice.getTailPrice().subtract(tradePrice.getDiscountsPrice());
                BigDecimal couponPrice = tradeCoupon.getDiscountsAmount();
                tradePrice.setDiscountsPrice(tradePrice.getDiscountsPrice().add(couponPrice));
                tradePrice.setCouponPrice(tradePrice.getCouponPrice().add(couponPrice));
                tradePrice.setOriginPrice(tradePrice.getGoodsPrice());
            } else {
                // 8.2.计算平台优惠券优惠额(couponPrice)，并追加至订单优惠金额、优惠券优惠金额
                BigDecimal marketTotalPrice = tradePrice.getGoodsPrice().subtract(tradePrice.getDiscountsPrice());
                BigDecimal couponTotalPrice = tradeItemService.calcSkusTotalPrice(itemsMap.get(storeId));
                BigDecimal couponPrice = marketTotalPrice.subtract(couponTotalPrice);
                tradePrice.setDiscountsPrice(tradePrice.getDiscountsPrice().add(couponPrice));
                tradePrice.setCouponPrice(tradePrice.getCouponPrice().add(couponPrice));
                // 8.3.重设订单总价、原始金额
                tradePrice.setTotalPrice(couponTotalPrice);
                tradePrice.setOriginPrice(tradePrice.getGoodsPrice());
            }


            trade.getTradeItems().forEach(tradeItem -> {
                TradeItem matchItem = matchItems.stream().filter(
                        item -> item.getSkuId().equals(tradeItem.getSkuId())).findFirst().orElse(null);
                if (matchItem != null) {
                    tradeItem.setSplitPrice(matchItem.getSplitPrice());
                }
            });

            // 8.4.计算运费
            Freight freight = freightService.calcTradeFreight(trade.getConsignee(), trade.getSupplier(),
                    trade.getDeliverWay(),
                    tradePrice.getTotalPrice(), trade.getTradeItems(), trade.getGifts(), trade.getPreferential());
            BigDecimal deliveryPrice = freight.getFreight();
            // 自提订单 只计算供应上运费
            if (Objects.nonNull(trade.getPickupFlag()) && Boolean.TRUE.equals(trade.getPickupFlag())) {
                deliveryPrice = BigDecimal.ZERO;
                if (CollectionUtils.isNotEmpty(freight.getProviderFreightList())) {
                    for (ProviderFreight providerFreight : freight.getProviderFreightList()) {
                        deliveryPrice = deliveryPrice.add(providerFreight.getBearFreight() == 0 ? providerFreight.getSupplierFreight() : BigDecimal.ZERO);
                    }
                }
            }
            StoreCommitInfoDTO storeCommitInfoDTO = tradeCommitRequest.getStoreCommitInfoList().get(0);

            TradeGrouponCommitForm grouponForm =
                    tradeItemGroups.get(NumberUtils.INTEGER_ZERO).getGrouponForm();
            // 如果拼团活动包邮
            if (Objects.nonNull(grouponForm) && grouponForm.isFreeDelivery()) {
                if (Objects.nonNull(trade.getFreight())) {
                    if (CollectionUtils.isNotEmpty(trade.getFreight().getProviderFreightList())) {
                        BigDecimal supplierBearFreight = Objects.isNull(trade.getFreight().getSupplierBearFreight()) ? BigDecimal.ZERO : trade.getFreight().getSupplierBearFreight();
                        for (ProviderFreight providerFreight : freight.getProviderFreightList()) {
                            if (providerFreight.getBearFreight() == 0) {
                                providerFreight.setBearFreight(1);
                                supplierBearFreight = supplierBearFreight.add(providerFreight.getSupplierFreight());
                            }
                        }
                        trade.getFreight().setSupplierBearFreight(supplierBearFreight);
                    }

                    trade.getFreight().setFreight(BigDecimal.ZERO);
                    trade.getFreight().setSupplierFreight(BigDecimal.ZERO);
                    trade.getFreight().setPostage(1);
                }
                deliveryPrice = BigDecimal.ZERO;
            }

            // 处理运费券的逻辑
            if (deliveryPrice.compareTo(BigDecimal.ZERO) > 0) {
                if (StringUtils.isNotEmpty(storeCommitInfoDTO.getFreightCouponCodeId())) {
                    BigDecimal newTotal = total.subtract(tradeCoupon.getDiscountsAmount());
                    // 调用营销插件  计算运费券营销优惠
                    SystemPointsConfigQueryResponse pointsConfig = systemPointsConfigService.querySettingCache();
                    //2.1.查找出优惠券关联的商品，及总价
                    CountCouponPriceVO couponPriceVO =
                            freightService.freightCoupon(
                                    storeCommitInfoDTO.getFreightCouponCodeId(),
                                    trade.getTradeItems(),
                                    trade.getBuyer(),
                                    deliveryPrice,
                                    Trade1CommitParam.builder().systemPointsConfigQueryResponse(pointsConfig).build(),
                                    newTotal);
                    //处理优惠金额
                    deliveryPrice =
                            this.tradeFreightCoupon(couponPriceVO, trade, deliveryPrice);
                    //封装订单优惠信息
                    this.couponPrice(trade, tradePrice, deliveryPrice);
                }
            }

            tradePrice.setDeliveryPrice(deliveryPrice);

            // 8.5.订单总价、原始金额追加运费
            tradePrice.setOriginPrice(tradePrice.getOriginPrice().add(deliveryPrice));
            tradePrice.setTotalPrice(tradePrice.getTotalPrice().add(deliveryPrice));//应付金额 = 应付+运费
        });

        // 9.设置订单组平台优惠券
        tradeGroup.setCommonCoupon(tradeCoupon);
        return tradeGroup;
    }


    public BigDecimal tradeFreightCoupon(CountCouponPriceVO couponPriceVO, Trade trade, BigDecimal deliveryPrice) {
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
     * @description 处理运费券优惠的订单数据
     * @author wur
     * @date: 2022/10/8 17:21
     * @param trade
     * @param tradePrice
     * @param deliveryPrice
     * @return
     */
    public void couponPrice(Trade trade, TradePrice tradePrice, BigDecimal deliveryPrice) {
        if (Objects.nonNull(trade.getFreightCoupon())) {
            BigDecimal freightCouponPrice = trade.getFreightCoupon().getDiscountsAmount();
            tradePrice.setFreightCouponPrice(freightCouponPrice);
            // 设置总的优惠
            if (Objects.isNull(tradePrice.getDiscountsPrice())) {
                tradePrice.setDiscountsPrice(freightCouponPrice);
            } else {
                tradePrice.setDiscountsPrice(
                        freightCouponPrice.add(tradePrice.getDiscountsPrice()));
            }
            // 设置券优惠金额
            if (Objects.isNull(tradePrice.getStoreCouponPrice())) {
                tradePrice.setStoreCouponPrice(freightCouponPrice);
            } else {
                tradePrice.setStoreCouponPrice(
                        freightCouponPrice.add(tradePrice.getStoreCouponPrice()));
            }
            // 处理供应商运费  优惠金额优先扣除商家的运费然后再扣除代销的供应商运费
            if (Objects.nonNull(trade.getFreight())) {
                trade.getFreight().setFreight(deliveryPrice);
                if (Objects.nonNull(trade.getFreight().getSupplierFreight())
                        && trade.getFreight().getSupplierFreight().compareTo(BigDecimal.ZERO) > 0) {
                    // 优惠金额大于等于商家运费则商家运费=0
                    trade.getFreight()
                            .setSupplierFreight(
                                    freightCouponPrice.compareTo(
                                            trade.getFreight().getSupplierFreight())
                                            >= 0
                                            ? BigDecimal.ZERO
                                            : trade.getFreight()
                                            .getSupplierFreight()
                                            .subtract(freightCouponPrice));
                    // 优惠券剩余优惠金额
                    freightCouponPrice =
                            freightCouponPrice.compareTo(trade.getFreight().getSupplierFreight())
                                    > 0
                                    ? freightCouponPrice.subtract(
                                    trade.getFreight().getSupplierFreight())
                                    : BigDecimal.ZERO;
                }
                // 多出的优惠金额 则商家承担
                if (Objects.nonNull(trade.getFreight().getProviderFreight())
                        && trade.getFreight().getProviderFreight().compareTo(BigDecimal.ZERO) > 0
                        && freightCouponPrice.compareTo(BigDecimal.ZERO) > 0) {
                    trade.getFreight()
                            .setSupplierBearFreight(
                                    Objects.isNull(trade.getFreight().getSupplierBearFreight())
                                            ? freightCouponPrice
                                            : trade.getFreight()
                                            .getSupplierBearFreight()
                                            .add(freightCouponPrice));
                    for (ProviderFreight providerFreight :
                            trade.getFreight().getProviderFreightList()) {
                        if (freightCouponPrice.compareTo(BigDecimal.ZERO) > 0
                                && providerFreight.getBearFreight() == 0) {
                            if (freightCouponPrice.compareTo(providerFreight.getSupplierFreight())
                                    > 0) {
                                providerFreight.setBearFreight(Constants.ONE);
                                providerFreight.setSupplierBearFreight(providerFreight.getSupplierFreight());
                                freightCouponPrice =
                                        freightCouponPrice.subtract(
                                                providerFreight.getSupplierFreight());
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

}
