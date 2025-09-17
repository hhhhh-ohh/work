package com.wanmi.sbc.marketing.countPrice;


import com.google.common.collect.Lists;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemMarketingVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
*
 * @description
 * @author  wur
 * @date: 2022/2/24 11:25
 **/
public interface CountPriceService {

    /**
     *  计算均摊
     *  1. 按照实付金额升序排序
     *  2. 循环商品
     *  3. 根据商品的实付金额计算优惠均摊比例 (保留8位有效小数)
     *  4. 根据均摊比例计算均摊金额 （四舍五入保留两位小数）
     *  5. 验证均摊金额是否大于 实付金额
     *  6. 处理商品的实付金额和优惠金额
     *  7. 验证是否是最后一个商品（用总优惠金额-已经均摊的优惠金额） 验证是否还有未均摊的优惠金额，如果有再向前均摊
     *  8. 处理未均摊完的优惠金额（由于四射五入有可能会导致剩余优惠金额）
     * @description
     * @author  wur
     * @date: 2022/2/24 16:56
     * @param itemGoodsInfoVOList
     * @param discountsAmount   优惠金额
     * @return 实际总优惠金额
     **/
    default BigDecimal divideAmount(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList, BigDecimal discountsAmount) {
        //商品总金额
        BigDecimal amount = itemGoodsInfoVOList.stream().map(CountPriceItemGoodsInfoVO::getSplitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        //已均摊金额
        BigDecimal alreadyDivideAmount = BigDecimal.ZERO;

        if (discountsAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        //如果总金额小于等于优惠金额 则商品金额全部优惠
        if(amount.compareTo(discountsAmount) <= 0) {
            itemGoodsInfoVOList.forEach(itemGoodsInfoVO -> {
                itemGoodsInfoVO.setDiscountsAmount(itemGoodsInfoVO.getDiscountsAmount().add(itemGoodsInfoVO.getSplitPrice()));
                itemGoodsInfoVO.setSplitPrice(BigDecimal.ZERO);
            });
            return amount;
        }

        itemGoodsInfoVOList = itemGoodsInfoVOList.stream().sorted(Comparator.comparing(CountPriceItemGoodsInfoVO::getSplitPrice)).collect(Collectors.toList());
        for(int index = 0; index < itemGoodsInfoVOList.size(); index++) {
            CountPriceItemGoodsInfoVO itemGoodsInfoVO = itemGoodsInfoVOList.get(index);
            if (itemGoodsInfoVO.getSplitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            //优惠金额
            BigDecimal dividePrice = BigDecimal.ZERO;
            //验证是否是最后一个商品
            if(index == itemGoodsInfoVOList.size()-1) {
                dividePrice = discountsAmount.subtract(alreadyDivideAmount);
            } else {
                //计算
                BigDecimal ratio = itemGoodsInfoVO.getSplitPrice().divide(amount, 8, RoundingMode.HALF_UP);
                dividePrice = ratio.multiply(discountsAmount).setScale(2, RoundingMode.HALF_UP);
            }
            dividePrice = dividePrice.compareTo(itemGoodsInfoVO.getSplitPrice()) <= 0 ? dividePrice : itemGoodsInfoVO.getSplitPrice();
            itemGoodsInfoVO.setDiscountsAmount(itemGoodsInfoVO.getDiscountsAmount().add(dividePrice));
            itemGoodsInfoVO.setSplitPrice(itemGoodsInfoVO.getSplitPrice().subtract(dividePrice));
            alreadyDivideAmount = alreadyDivideAmount.add(dividePrice);
        }

        //处理未均摊完的优惠金额 不再使用均摊
        if (alreadyDivideAmount.compareTo(discountsAmount) < 0) {
            BigDecimal surplus = discountsAmount.subtract(alreadyDivideAmount);
            for (CountPriceItemGoodsInfoVO itemGoodsInfoVO : itemGoodsInfoVOList) {
                if (itemGoodsInfoVO.getSplitPrice().compareTo(BigDecimal.ZERO) > 0
                        && surplus.compareTo(BigDecimal.ZERO) > 0) {
                    if (itemGoodsInfoVO.getSplitPrice().compareTo(surplus) >= 0) {
                        itemGoodsInfoVO.setDiscountsAmount(itemGoodsInfoVO.getDiscountsAmount().add(surplus));
                        itemGoodsInfoVO.setSplitPrice(itemGoodsInfoVO.getSplitPrice().subtract(surplus));
                        alreadyDivideAmount = alreadyDivideAmount.add(surplus);
                        break;
                    } else {
                        itemGoodsInfoVO.setDiscountsAmount(itemGoodsInfoVO.getDiscountsAmount().add(itemGoodsInfoVO.getSplitPrice()));
                        alreadyDivideAmount = alreadyDivideAmount.add(itemGoodsInfoVO.getSplitPrice());
                        itemGoodsInfoVO.setSplitPrice(BigDecimal.ZERO);
                    }
                }
            }
        }
        return alreadyDivideAmount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
    *
     * @description  封装计算后后的商品返回数据
     * @author  wur
     * @date: 2022/3/1 9:36
     * @param itemGoodsInfoVOList
     * @param marketingId
     * @param beforePriceMap
     **/
    default List<CountPriceItemGoodsInfoVO> wrapperItem(List<CountPriceItemGoodsInfoVO> itemGoodsInfoVOList,
                                                        Long marketingId, Long levelId,
                                                        MarketingType marketingType,
                                                        Map<String, BigDecimal> beforePriceMap) {
        itemGoodsInfoVOList.forEach(
                itemGoodsInfoVO -> {
                    BigDecimal beforePrice = beforePriceMap.getOrDefault(itemGoodsInfoVO.getGoodsInfoId(), BigDecimal.ZERO);
                    CountPriceItemMarketingVO itemMarketingVO =
                            CountPriceItemMarketingVO.builder()
                                    .marketingId(marketingId)
                                    .marketingLevelId(levelId)
                                    .marketingType(marketingType)
                                    .splitPrice(itemGoodsInfoVO.getSplitPrice())
                                    .discountsAmount(beforePrice.subtract(itemGoodsInfoVO.getSplitPrice())).build();
                    if (Objects.isNull(itemGoodsInfoVO.getMarketingList())) {
                        itemGoodsInfoVO.setMarketingList(Lists.newArrayList(itemMarketingVO));
                    } else {
                        itemGoodsInfoVO.getMarketingList().add(itemMarketingVO);
                    }
                });
        return itemGoodsInfoVOList;
    }
}
