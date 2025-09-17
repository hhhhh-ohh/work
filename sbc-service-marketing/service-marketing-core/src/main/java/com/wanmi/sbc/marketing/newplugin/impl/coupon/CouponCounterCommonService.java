package com.wanmi.sbc.marketing.newplugin.impl.coupon;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeAutoSelectDTO;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.coupon.model.entity.TradeCouponSnapshot;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description 券计算通用服务
 * @author malianfeng
 * @date 2022/10/9 11:19
 */
@Service
public class CouponCounterCommonService {

    /**
     * 根据券列表和订单剩余金额，计算最优目标券
     * @param totalPrice 适用商品或运费金额
     * @param checkGoodsInfos 商品快照列表
     * @param checkCouponCodes 券快照列表
     * @return
     */
    public CouponCodeAutoSelectDTO getTargetCoupon(BigDecimal totalPrice,
                                                   List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos,
                                                   List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes) {
        // 数据校验
        if (CollectionUtils.isEmpty(checkGoodsInfos) || CollectionUtils.isEmpty(checkCouponCodes)) {
            return null;
        }

        // 券适用商品总额Map
        Map<String, BigDecimal> couponTotalSplitPriceMap = new HashMap<>();
        // 将商品转为Map，[skuId] => [CheckGoodsInfo]
        Map<String, TradeCouponSnapshot.CheckGoodsInfo> goodsInfoMap = checkGoodsInfos.stream()
                .collect(Collectors.toMap(TradeCouponSnapshot.CheckGoodsInfo::getGoodsInfoId, Function.identity()));

        // 1. 计算每个券能适用商品总的均摊价
        for (TradeCouponSnapshot.CheckCouponCode checkCouponCode : checkCouponCodes) {
            // 券ID
            String couponId = checkCouponCode.getCouponId();
            // 计算当前券可用的商品均摊价总额
            BigDecimal totalSplitPrice = checkCouponCode.getGoodsInfoIds().stream().map(skuId -> goodsInfoMap.get(skuId).getSplitPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            couponTotalSplitPriceMap.put(couponId, totalSplitPrice);
        }
        // 2. 根据门槛先过滤一遍
        checkCouponCodes = checkCouponCodes.stream().filter(coupon -> {
            // 当前券可用的商品均摊价总额
            BigDecimal totalSplitPrice = couponTotalSplitPriceMap.get(coupon.getCouponId());
            // 商品券适用商品的总额已被优惠完，该券无需使用
            if (CouponMarketingType.FREIGHT_COUPON != coupon.getCouponMarketingType() && totalSplitPrice.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
            // 满足门槛或无门槛
            return FullBuyType.NO_THRESHOLD == coupon.getFullBuyType() || totalSplitPrice.compareTo(coupon.getFullBuyPrice()) >= 0;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(checkCouponCodes)) {
            // 过滤完后无可用的券，直接返回
            return null;
        }
        return baseSelectRule(totalPrice, checkCouponCodes);
    }

    /**
     * 券是否达到使用门槛
     * @param checkGoodsInfos 商品列表
     * @param checkCouponCode 券快照
     * @return
     */
    public boolean isReachedThreshold(List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos,
                                      TradeCouponSnapshot.CheckCouponCode checkCouponCode) {
        BigDecimal totalPrice = this.calcApplySkuTotalPriceForCoupon(checkGoodsInfos, checkCouponCode);
        BigDecimal fullBuyPrice = checkCouponCode.getFullBuyPrice();
        fullBuyPrice = Objects.nonNull(fullBuyPrice) ? fullBuyPrice : BigDecimal.ZERO;
        return totalPrice.compareTo(fullBuyPrice) >= 0;
    }

    /**
     * 基本规则，Min(abs(denomination-price)) => Min(endTime) => Min(acquireTime)
     * @param totalPrice 商品均摊后总金额/店铺运费
     * @param checkCouponCodes 券列表
     * @return
     */
    public CouponCodeAutoSelectDTO baseSelectRule(BigDecimal totalPrice, List<TradeCouponSnapshot.CheckCouponCode> checkCouponCodes) {
        // 最优目标券
        TradeCouponSnapshot.CheckCouponCode targetCoupon = null;
        // 3. 首先按[(商品均摊后总金额/店铺运费)-面值]的绝对值分组，绝对值从小到大排序
        TreeMap<BigDecimal, List<TradeCouponSnapshot.CheckCouponCode>> denominationAbsMap = checkCouponCodes.stream()
                .collect(Collectors.groupingBy(item -> totalPrice.subtract(item.getDenomination()).abs(), TreeMap::new, Collectors.toList()));

        // 3.1 优先取绝对值最小的优惠券列表
        List<TradeCouponSnapshot.CheckCouponCode> minAbsCouponInfos = denominationAbsMap.firstEntry().getValue();
        if (minAbsCouponInfos.size() == 1) {
            // 若命中的券唯一，直接返回
            targetCoupon = minAbsCouponInfos.get(0);
        }

        // 4. 其次取最早过期的券，按过期时间分组，过期时间从小到大排序
        if (Objects.isNull(targetCoupon)) {
            TreeMap<LocalDateTime, List<TradeCouponSnapshot.CheckCouponCode>> endTimeMap = minAbsCouponInfos.stream()
                    .collect(Collectors.groupingBy(TradeCouponSnapshot.CheckCouponCode::getEndTime, TreeMap::new, Collectors.toList()));
            // 4.1 过期时间最早的优惠券列表
            List<TradeCouponSnapshot.CheckCouponCode> minEndTimeInfos = endTimeMap.firstEntry().getValue();
            if (minEndTimeInfos.size() == 1) {
                // 若命中的券唯一，直接返回
                targetCoupon = minEndTimeInfos.get(0);
            }
            // 5. 最后取最早领取的券
            if (Objects.isNull(targetCoupon)) {
                targetCoupon =
                        minEndTimeInfos.stream().sorted(Comparator.comparing(TradeCouponSnapshot.CheckCouponCode::getAcquireTime)).collect(Collectors.toList()).get(0);
            }
        }
        return KsBeanUtil.convert(targetCoupon, CouponCodeAutoSelectDTO.class);
    }

    /**
     * 返回2个券中实际优惠金额更多的券
     * @param c1 券1
     * @param c2 券2
     * @return
     */
    public CouponCodeAutoSelectDTO maxByActualDiscount(CouponCodeAutoSelectDTO c1, CouponCodeAutoSelectDTO c2) {
        // 仅当c1、c2任意不为空时才处理
        if (ObjectUtils.anyNotNull(c1, c2)) {
            // 券1为空，返回券2
            if (Objects.isNull(c1)) {
                return c2;
            }
            // 券2为空，返回券1
            if (Objects.isNull(c2)) {
                return c1;
            }
            // 都不为空时，比较实际优惠金额
            int compareRes = c1.getActualDiscount().compareTo(c2.getActualDiscount());
            if (compareRes > 0) {
                // 券1优惠更多，返回券1
                return c1;
            } else if (compareRes < 0) {
                // 券2优惠更多，返回券2
                return c2;
            } else {
                // 优惠金额相同，取最早过期的券，再取最早领取
                Optional<CouponCodeAutoSelectDTO> targetCoupon =
                        Stream.of(c1, c2).min(
                                Comparator.comparing(CouponCodeAutoSelectDTO::getEndTime)
                                        .thenComparing(CouponCodeAutoSelectDTO::getAcquireTime));
                return targetCoupon.get();
            }
        }
        return null;
    }

    /**
     * 计算指定券适用商品的总额
     * @param checkGoodsInfos 商品列表
     * @param checkCouponCode 券快照
     * @return
     */
    public BigDecimal calcApplySkuTotalPriceForCoupon(List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos,
                                                      TradeCouponSnapshot.CheckCouponCode checkCouponCode) {
        // 筛选出该券适用的商品列表
        List<TradeCouponSnapshot.CheckGoodsInfo> applyCheckGoodsInfos = checkGoodsInfos.stream()
                .filter(goodsInfo -> checkCouponCode.getGoodsInfoIds().contains(goodsInfo.getGoodsInfoId()))
                .collect(Collectors.toList());
        // 累加适用商品的总价，用于均摊优惠金额
        return applyCheckGoodsInfos.stream().map(TradeCouponSnapshot.CheckGoodsInfo::getSplitPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    /**
     * 将指定券的实际优惠金额，均摊至适用商品上
     * @param checkGoodsInfos 商品列表
     * @param checkCouponCode 券快照
     * @param actualDiscount 该券实际抵扣
     */
    public void splitSkuPriceForCoupon(List<TradeCouponSnapshot.CheckGoodsInfo> checkGoodsInfos,
                                       TradeCouponSnapshot.CheckCouponCode checkCouponCode,
                                       BigDecimal actualDiscount) {
        if (actualDiscount.compareTo(BigDecimal.ZERO) != 0) {
            // 筛选出改券适用的商品列表
            List<TradeCouponSnapshot.CheckGoodsInfo> applyCheckGoodsInfos = checkGoodsInfos.stream()
                    .filter(goodsInfo -> checkCouponCode.getGoodsInfoIds().contains(goodsInfo.getGoodsInfoId()))
                    .collect(Collectors.toList());
            // 记录已均优惠的总金额，用于兜底
            BigDecimal alreadyDiscountPrice = BigDecimal.ZERO;
            // 累加适用商品的总价，用于均摊优惠金额
            BigDecimal totalPrice = applyCheckGoodsInfos.stream().map(TradeCouponSnapshot.CheckGoodsInfo::getSplitPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 1. 优惠金额 >= 适用商品总价，直接赋值0
            if (actualDiscount.compareTo(totalPrice) >= 0) {
                applyCheckGoodsInfos.forEach(sku -> sku.setSplitPrice(BigDecimal.ZERO));
                return;
            }

            applyCheckGoodsInfos = applyCheckGoodsInfos.stream().sorted(Comparator.comparing(TradeCouponSnapshot.CheckGoodsInfo::getSplitPrice)).collect(Collectors.toList());
            // 2. 循环均摊优惠金额
            for (int index = 0; index < applyCheckGoodsInfos.size(); index++) {
                TradeCouponSnapshot.CheckGoodsInfo checkGoodsInfo = applyCheckGoodsInfos.get(index);
                // 不处理金额为0的商品
                if (checkGoodsInfo.getSplitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                // 优惠金额
                BigDecimal discountPrice;
                if(index == applyCheckGoodsInfos.size() - 1) {
                    // 最后一个商品，兜底剩余金额
                    discountPrice = actualDiscount.subtract(alreadyDiscountPrice);
                } else {
                    // 计算商品金额占比
                    BigDecimal ratio = checkGoodsInfo.getSplitPrice().divide(totalPrice, 8, RoundingMode.HALF_UP);
                    // 计算商品均摊到的优惠金额
                    discountPrice = ratio.multiply(actualDiscount).setScale(2, RoundingMode.HALF_UP);
                }
                // 设置优惠后的金额
                checkGoodsInfo.setSplitPrice(checkGoodsInfo.getSplitPrice().subtract(discountPrice));
                // 累加已优惠金额
                alreadyDiscountPrice = alreadyDiscountPrice.add(discountPrice);
            }
        }
    }
}

