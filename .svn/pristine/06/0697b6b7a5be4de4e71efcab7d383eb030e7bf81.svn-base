package com.wanmi.sbc.order;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhanggaolei
 * @className PriceTest
 * @description TODO
 * @date 2022/3/9 11:30 上午
 **/
public class PriceTest {
    public static void main(String[] args) {
        String json = "[{\"count\":1,\"price\":10},{\"count\":2,\"price\":9},{\"count\":3,\"price\":8},{\"count\":7,\"price\":4},{\"count\":5,\"price\":6},{\"count\":4,\"price\":7}]";
        List<GoodsIntervalPriceVO> intervalPriceList = JSON.parseArray(json,GoodsIntervalPriceVO.class);
        BigDecimal price  = getPrice(intervalPriceList,6L);
        System.out.println(price);
    }

    static  BigDecimal getPrice(List<GoodsIntervalPriceVO> intervalPriceList, Long num) {


            AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
            // 先排序再判断区间
            intervalPriceList.stream()
                    .sorted(Comparator.comparing(GoodsIntervalPriceVO::getPrice).reversed()).forEach(
                            i -> {
                                if (i.getCount() <= num) {
                                    price.set(i.getPrice());
                                }
                            });
            return price.get();

    }
}
