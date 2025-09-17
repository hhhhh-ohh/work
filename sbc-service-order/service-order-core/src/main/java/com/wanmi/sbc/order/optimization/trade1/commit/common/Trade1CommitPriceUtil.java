package com.wanmi.sbc.order.optimization.trade1.commit.common;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.DiscountsPriceDetail;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.Trade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitPriceUtil
 * @description TODO
 * @date 2022/3/9 4:53 下午
 */
public class Trade1CommitPriceUtil {

    // 获取价格，如果存在区间价则按照数量判断区间价
    public static BigDecimal getPrice(GoodsInfoTradeVO goodsInfoTradeVO, Long num, DefaultFlag isStoreBag) {
        if (goodsInfoTradeVO.getSaleType().equals(SaleType.WHOLESALE.toValue())
                && CollectionUtils.isNotEmpty(goodsInfoTradeVO.getIntervalPriceList()) && DefaultFlag.NO.equals(isStoreBag)) {
            List<GoodsIntervalPriceVO> intervalPriceList = goodsInfoTradeVO.getIntervalPriceList();
            AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
            // 先排序再判断区间
            intervalPriceList.stream()
                    .sorted(Comparator.comparing(GoodsIntervalPriceVO::getCount))
                    .forEach(
                            i -> {
                                if (i.getCount() <= num) {
                                    price.set(i.getPrice());
                                }
                            });
            return price.get();
        } else {
            return goodsInfoTradeVO.getMarketPrice();
        }
    }

    /**
     * 计算平摊价
     *
     * @param tradeItems 订单商品
     * @param newTotal 新的总价
     */
    public static void clacSplitPrice(List<TradeItem> tradeItems, List<TradeItem> preferentialTradeItems,
                                      BigDecimal newTotal) {
        preferentialTradeItems = ObjectUtils.defaultIfNull(preferentialTradeItems, new ArrayList<TradeItem>());
        // 1.如果新的总价为0或空，设置所有商品均摊价为0
        if (newTotal == null || BigDecimal.ZERO.compareTo(newTotal) == 0) {
            tradeItems.forEach(tradeItem -> tradeItem.setSplitPrice(BigDecimal.ZERO));
            preferentialTradeItems.forEach(tradeItem -> tradeItem.setSplitPrice(BigDecimal.ZERO));
            return;
        }

        // 2.计算商品旧的总价
        BigDecimal total = calcSkusTotalPrice(tradeItems);
        total = total.add(calcSkusTotalPrice(preferentialTradeItems));

        // 3.计算商品均摊价
        calcSplitPrice(tradeItems, preferentialTradeItems, newTotal, total);
    }

    /** 计算商品集合的均摊总价 */
    public static BigDecimal calcSkusTotalPrice(List<TradeItem> tradeItems) {
        if (CollectionUtils.isEmpty(tradeItems)) return BigDecimal.ZERO;
        if (Objects.nonNull(tradeItems.get(0).getIsBookingSaleGoods())
                && tradeItems.get(0).getIsBookingSaleGoods()) {
            TradeItem tradeItem = tradeItems.get(0);
            if (tradeItem.getBookingType() == BookingType.EARNEST_MONEY
                    && Objects.nonNull(tradeItem.getTailPrice())) {
                return tradeItem.getTailPrice();
            }
        }
        return tradeItems.stream()
                .filter(
                        tradeItem ->
                                tradeItem.getSplitPrice() != null
                                        && tradeItem.getSplitPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(TradeItem::getSplitPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    /** 计算商品集合的积分抵扣均摊总价 */
    public static BigDecimal calcSkusTotalPointsPrice(List<TradeItem> tradeItems) {
        return tradeItems.stream()
                .filter(
                        tradeItem ->
                                tradeItem.getPointsPrice() != null
                                        && tradeItem.getPointsPrice().compareTo(BigDecimal.ZERO)
                                                > 0)
                .map(TradeItem::getPointsPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    /** 计算商品集合的积分抵扣均摊总数 */
    public static Long calcSkusTotalPoints(List<TradeItem> tradeItems) {
        return tradeItems.stream()
                .filter(tradeItem -> tradeItem.getPoints() != null && tradeItem.getPoints() > 0)
                .map(TradeItem::getPoints)
                .reduce(0L, (a, b) -> a + b);
    }

    /**
     * 计算商品均摊价
     *
     * @param tradeItems 待计算的商品列表
     * @param newTotal 新的总价
     * @param total 旧的商品总价
     */
    public static void calcSplitPrice(
            List<TradeItem> tradeItems, List<TradeItem> preferentialTradeItems, BigDecimal newTotal, BigDecimal total) {
        if (preferentialTradeItems == null) preferentialTradeItems = new ArrayList<>();
        // 内部总价为零或相等不用修改
        if (total.compareTo(newTotal) == 0) {
            return;
        }
        // 尾款情况重新计算实际总价
        if (CollectionUtils.isNotEmpty(tradeItems)) {
            TradeItem tradeItem = tradeItems.get(0);
            if (Objects.nonNull(tradeItem.getIsBookingSaleGoods())
                    && tradeItem.getIsBookingSaleGoods()
                    && tradeItem.getBookingType() == BookingType.EARNEST_MONEY
                    && total.compareTo(tradeItem.getTailPrice()) == 0) {
                newTotal = tradeItem.getEarnestPrice().add(newTotal);
                total = tradeItem.getEarnestPrice().add(total);
            }
        }

        int size = tradeItems.size();
        BigDecimal splitPriceTotal = BigDecimal.ZERO; // 累积平摊价，将剩余扣给最后一个元素\
        long totalNum = tradeItems.stream().map(TradeItem::getNum).reduce(0L, Long::sum);
        totalNum = totalNum + preferentialTradeItems.stream().map(TradeItem::getNum).reduce(0L, Long::sum);
        for (TradeItem tradeItem : preferentialTradeItems){
            BigDecimal splitPrice =
                    tradeItem.getSplitPrice() != null
                            ? tradeItem.getSplitPrice()
                            : BigDecimal.ZERO;
            // 全是零元商品按数量均摊
            if (BigDecimal.ZERO.compareTo(total) == 0) {
                tradeItem.setSplitPrice(
                        newTotal.multiply(BigDecimal.valueOf(tradeItem.getNum()))
                                .divide(
                                        BigDecimal.valueOf(totalNum),
                                        2,
                                        RoundingMode.HALF_UP));
            } else {
                BigDecimal newSplitPrice = splitPrice
                        .divide(total, 10, RoundingMode.DOWN)
                        .multiply(newTotal)
                        .setScale(2, RoundingMode.HALF_UP);
                tradeItem.setSplitPrice(newSplitPrice);
                if (splitPrice.subtract(tradeItem.getPointsPrice()).compareTo(newSplitPrice) < 0){
                    tradeItem.setSplitPrice(splitPrice.subtract(tradeItem.getPointsPrice()));
                }
            }
            splitPriceTotal = splitPriceTotal.add(tradeItem.getSplitPrice());
        }
        for (int i = 0; i < size; i++) {
            TradeItem tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                tradeItem.setSplitPrice(newTotal.subtract(splitPriceTotal));
            } else {
                BigDecimal splitPrice =
                        tradeItem.getSplitPrice() != null
                                ? tradeItem.getSplitPrice()
                                : BigDecimal.ZERO;
                // 全是零元商品按数量均摊
                if (BigDecimal.ZERO.compareTo(total) == 0) {
                    tradeItem.setSplitPrice(
                            newTotal.multiply(BigDecimal.valueOf(tradeItem.getNum()))
                                    .divide(
                                            BigDecimal.valueOf(totalNum),
                                            2,
                                            RoundingMode.HALF_UP));
                } else {
                    BigDecimal newSplitPrice = splitPrice
                            .divide(total, 10, RoundingMode.DOWN)
                            .multiply(newTotal)
                            .setScale(2, RoundingMode.HALF_UP);

                    tradeItem.setSplitPrice(newSplitPrice);

                    if (splitPrice.subtract(tradeItem.getPointsPrice()).compareTo(newSplitPrice) < 0){
                        tradeItem.setSplitPrice(splitPrice.subtract(tradeItem.getPointsPrice()));
                    }


                }
                splitPriceTotal = splitPriceTotal.add(tradeItem.getSplitPrice());
            }
        }
    }

    /**
     * 计算积分抵扣均摊价、均摊数量
     *
     * @param tradeItems 待计算的商品列表
     * @param pointsPriceTotal 积分抵扣总额
     * @param pointsTotal 积分抵扣总数
     */
    public static void calcPoints(
            List<TradeItem> tradeItems,
            List<TradeItem> preferentialTradeItems,
            BigDecimal pointsPriceTotal,
            Long pointsTotal,
            BigDecimal pointWorth) {
        if (preferentialTradeItems == null) preferentialTradeItems = new ArrayList<>();
        BigDecimal totalPrice =
                tradeItems.stream()
                        .filter(
                                tradeItem ->
                                        tradeItem.getSplitPrice() != null
                                                && tradeItem
                                                                .getSplitPrice()
                                                                .compareTo(BigDecimal.ZERO)
                                                        > 0)
                        .map(TradeItem::getSplitPrice)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO);
        totalPrice = preferentialTradeItems.stream()
                .filter(
                        tradeItem ->
                                tradeItem.getSplitPrice() != null
                                        && tradeItem
                                        .getSplitPrice()
                                        .compareTo(BigDecimal.ZERO)
                                        > 0)
                .map(TradeItem::getSplitPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).add(totalPrice);

        int size = tradeItems.size();
        // 累积积分平摊价，将剩余扣给最后一个元素
        BigDecimal splitPriceTotal = BigDecimal.ZERO;
        // 累积积分数量，将剩余扣给最后一个元素
        Long splitPointsTotal = 0L;

        for (TradeItem tradeItem : preferentialTradeItems){
            BigDecimal splitPrice =
                    tradeItem.getSplitPrice() != null
                            ? tradeItem.getSplitPrice()
                            : BigDecimal.ZERO;
            BigDecimal splitPointPrice =
                    splitPrice
                            .divide(totalPrice, 10, RoundingMode.DOWN)
                            .multiply(pointsPriceTotal)
                            .setScale(2, RoundingMode.HALF_UP);
            tradeItem.setPointsPrice(
                    splitPointPrice.compareTo(splitPrice) > 0 ? splitPrice : splitPointPrice);
            splitPriceTotal = splitPriceTotal.add(tradeItem.getPointsPrice());

            tradeItem.setPoints(tradeItem.getPointsPrice().multiply(pointWorth).longValue());
            splitPointsTotal = splitPointsTotal + tradeItem.getPoints();
        }

        for (int i = 0; i < size; i++) {
            TradeItem tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                BigDecimal splitPointPrice = pointsPriceTotal.subtract(splitPriceTotal);
                tradeItem.setPointsPrice(
                        splitPointPrice.compareTo(tradeItem.getSplitPrice()) > 0
                                ? tradeItem.getSplitPrice()
                                : splitPointPrice);
                tradeItem.setPoints(pointsTotal - splitPointsTotal);
            } else {
                BigDecimal splitPrice =
                        tradeItem.getSplitPrice() != null
                                ? tradeItem.getSplitPrice()
                                : BigDecimal.ZERO;
                BigDecimal splitPointPrice =
                        splitPrice
                                .divide(totalPrice, 10, RoundingMode.DOWN)
                                .multiply(pointsPriceTotal)
                                .setScale(2, RoundingMode.HALF_UP);
                tradeItem.setPointsPrice(
                        splitPointPrice.compareTo(splitPrice) > 0 ? splitPrice : splitPointPrice);
                splitPriceTotal = splitPriceTotal.add(tradeItem.getPointsPrice());

                tradeItem.setPoints(tradeItem.getPointsPrice().multiply(pointWorth).longValue());
                splitPointsTotal = splitPointsTotal + tradeItem.getPoints();
            }
        }
    }

    public static TradePrice calc(Trade trade) {
        TradePrice tradePriceTemp = trade.getTradePrice();
        if (tradePriceTemp == null) {
            tradePriceTemp = new TradePrice();
            trade.setTradePrice(tradePriceTemp);
        }
        TradePrice tradePrice = tradePriceTemp;

        // 1.计算商品总价
        handlePrice(trade.getTradeItems(), trade.getPreferential(), tradePrice);
        List<TradeMarketingVO> list =
                trade.getTradeMarketings().stream()
                        .filter(i -> i.getMarketingType() != MarketingType.GIFT
                            && i.getMarketingType() != MarketingType.PREFERENTIAL)
                        .collect(Collectors.toList());

        // 2.计算所有营销活动的总优惠金额(非满赠)
        BigDecimal discountPrice = new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(list)) {
            discountPrice =
                    list.stream()
                            .filter(i -> i.getMarketingType() != MarketingType.GIFT
                                && i.getMarketingType() != MarketingType.PREFERENTIAL)
                            .map(TradeMarketingVO::getDiscountsAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            // 营销活动优惠总额
            tradePrice.setMarketingDiscountPrice(discountPrice);
            list = Collections.EMPTY_LIST;
        }

        // 3.计算各类营销活动的优惠金额(比如:满折优惠xxx,满减优惠yyy)
        List<DiscountsPriceDetail> discountsPriceDetails = new ArrayList<>();
        list.stream()
                .collect(Collectors.groupingBy(TradeMarketingVO::getMarketingType))
                .forEach(
                        (key, value) -> {
                            DiscountsPriceDetail detail =
                                    DiscountsPriceDetail.builder()
                                            .marketingType(key)
                                            .discounts(
                                                    value.stream()
                                                            .map(
                                                                    TradeMarketingVO
                                                                            ::getDiscountsAmount)
                                                            .reduce(
                                                                    BigDecimal.ZERO,
                                                                    BigDecimal::add))
                                            .build();
                            discountsPriceDetails.add(detail);
                        });
        tradePrice.setDiscountsPriceDetails(discountsPriceDetails);

        // 4.设置店铺优惠券优惠金额
        BigDecimal couponPrice = new BigDecimal(0);
        for (TradeItem tradeItem : trade.getTradeItems()) {
            if (CollectionUtils.isNotEmpty(tradeItem.getCouponSettlements())) {
                for (TradeItem.CouponSettlement couponSettlement :
                        tradeItem.getCouponSettlements()) {
                    couponPrice = couponPrice.add(couponSettlement.getReducePrice());
                }
            }
        }
        for (TradeItem tradeItem : trade.getPreferential()) {
            if (CollectionUtils.isNotEmpty(tradeItem.getCouponSettlements())) {
                for (TradeItem.CouponSettlement couponSettlement :
                        tradeItem.getCouponSettlements()) {
                    couponPrice = couponPrice.add(couponSettlement.getReducePrice());
                }
            }
        }
        tradePrice.setCouponPrice(couponPrice);
        tradePrice.setStoreCouponPrice(couponPrice);

        // 5.设置优惠总金额、应付金额 = 商品总金额 - 总优惠金额 - 帮砍金额
        tradePrice.setDiscountsPrice(discountPrice.add(couponPrice).add(tradePrice.getBargainPrice()));

        //设置付费会员优惠总额
        if (trade.getPayingMemberInfo() != null) {
            BigDecimal totalDiscount = trade.getPayingMemberInfo().getGoodsDiscount().add(trade.getPayingMemberInfo().getCouponDiscount());
            if (totalDiscount.compareTo(BigDecimal.ZERO) > 0) {
                trade.getPayingMemberInfo().setTotalDiscount(totalDiscount);
            }
        }

        // 提货卡订单支付金额为0
        if (Objects.nonNull(trade.getOrderTag()) && Objects.nonNull(trade.getOrderTag().getPickupCardFlag()) && trade.getOrderTag().getPickupCardFlag()){
            tradePrice.setTotalPrice(BigDecimal.ZERO);
        }
        return tradePrice;
    }

    public static void handlePrice(List<TradeItem> tradeItems, List<TradeItem> preferentialTradeItems,
                                   TradePrice tradePrice) {
        if (preferentialTradeItems == null) preferentialTradeItems = new ArrayList<>();
        tradePrice.setGoodsPrice(BigDecimal.ZERO);
        tradePrice.setOriginPrice(BigDecimal.ZERO);
        tradePrice.setTotalPrice(BigDecimal.ZERO);
        tradePrice.setBargainPrice(BigDecimal.ZERO);
        tradePrice.setBuyPoints(null);
        tradeItems.forEach(
                t -> {
                    Long num = t.getNum();
                    Integer buyCycleNum = t.getBuyCycleNum();
                    //如果存在周期购的购买期数，则购买数量 = 购买数量 * 期数
                    if (Objects.nonNull(buyCycleNum) && buyCycleNum> Constants.ZERO) {
                        num = BigDecimal.valueOf(buyCycleNum).longValue() * num;
                    }
                    BigDecimal buyItemPrice = t.getPrice().multiply(BigDecimal.valueOf(num));
                    BigDecimal splitPrice = t.getSplitPrice();
                    // 订单商品总价
                    tradePrice.setGoodsPrice(tradePrice.getGoodsPrice().add(buyItemPrice));
                    // 订单应付总金额
                    tradePrice.setTotalPrice(tradePrice.getTotalPrice().add(splitPrice));
                    // 订单原始总金额
                    tradePrice.setOriginPrice(tradePrice.getOriginPrice().add(buyItemPrice));

                    //处理砍价优惠金额
                    if (Objects.nonNull(t.getBargainPrice()) && Objects.nonNull(t.getBargainGoodsId())) {
                        tradePrice.setBargainPrice(tradePrice.getBargainPrice().add(t.getBargainPrice()));
                    }

                    // 订单积分价商品总积分
                    if (Objects.nonNull(t.getBuyPoint())) {
                        tradePrice.setBuyPoints(
                                Objects.isNull(tradePrice.getBuyPoints())
                                        ? t.getBuyPoint() * num
                                        : tradePrice.getBuyPoints() + t.getBuyPoint() * num);
                    }
                });

        preferentialTradeItems.forEach(t -> {
                    Long num = t.getNum();
                    BigDecimal splitPrice = t.getSplitPrice();
                    // 订单商品总价
                    tradePrice.setGoodsPrice(tradePrice.getGoodsPrice().add(t.getPrice()));
                    // 订单应付总金额
                    tradePrice.setTotalPrice(tradePrice.getTotalPrice().add(splitPrice));
                    // 订单原始总金额
                    tradePrice.setOriginPrice(tradePrice.getOriginPrice().add(t.getPrice()));
                    // 订单积分价商品总积分
                    if (Objects.nonNull(t.getBuyPoint())) {
                        tradePrice.setBuyPoints(
                                Objects.isNull(tradePrice.getBuyPoints())
                                        ? t.getBuyPoint() * num
                                        : tradePrice.getBuyPoints() + t.getBuyPoint() * num);
                    }
                });
    }
}
