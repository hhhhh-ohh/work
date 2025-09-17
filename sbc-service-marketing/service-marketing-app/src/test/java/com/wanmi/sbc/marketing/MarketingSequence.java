package com.wanmi.sbc.marketing;

import com.google.common.collect.Lists;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className MarketingSequence
 * @description
 * @date 2021/6/2 11:48
 */
public class MarketingSequence {

    private static Map<String, List<MarketingPluginType>> map = new HashMap<>();

    static {
        map.put(MarketingPluginType.COUPON.name(), Lists.newArrayList(MarketingPluginType.SUITS,
            MarketingPluginType.GROUPON,
            MarketingPluginType.BOOKING_SALE,
            MarketingPluginType.APPOINTMENT_SALE,
            MarketingPluginType.REDUCTION,
            MarketingPluginType.DISCOUNT,
            MarketingPluginType.GIFT,
            MarketingPluginType.BUYOUT_PRICE,
            MarketingPluginType.HALF_PRICE_SECOND_PIECE,
            MarketingPluginType.CUSTOMER_PRICE,
            MarketingPluginType.CUSTOMER_LEVEL));

        map.put(MarketingPluginType.SUITS.name(), Lists.newArrayList(MarketingPluginType.GROUPON));
        map.put(MarketingPluginType.GROUPON.name(), Lists.newArrayList(MarketingPluginType.COUPON));
        map.put(MarketingPluginType.BOOKING_SALE.name(), Lists.newArrayList(MarketingPluginType.COUPON));

        /** 预售 */
        map.put(MarketingPluginType.APPOINTMENT_SALE.name(), Lists.newArrayList(MarketingPluginType.COUPON));

        /** 秒杀 */
        map.put(MarketingPluginType.FLASH_SALE.name(),Lists.newArrayList());

        /** 限时购 */
        map.put(MarketingPluginType.FLASH_PROMOTION.name(),Lists.newArrayList());

        /** 分销 */
        map.put(MarketingPluginType.DISTRIBUTION.name(),Lists.newArrayList(MarketingPluginType.COUPON));

        /** 企业价 */
        map.put(MarketingPluginType.ENTERPRISE_PRICE.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.REDUCTION,
            MarketingPluginType.DISCOUNT,
            MarketingPluginType.GIFT,
            MarketingPluginType.BUYOUT_PRICE,
            MarketingPluginType.HALF_PRICE_SECOND_PIECE
        ));

        /** 满减 */
        map.put(MarketingPluginType.REDUCTION.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.ENTERPRISE_PRICE,
            MarketingPluginType.CUSTOMER_PRICE,
            MarketingPluginType.CUSTOMER_LEVEL
        ));

        /** 满折 */
        map.put(MarketingPluginType.DISCOUNT.name(), Lists.newArrayList(MarketingPluginType.COUPON));

        /** 满赠 */
        map.put(MarketingPluginType.GIFT.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.ENTERPRISE_PRICE,
            MarketingPluginType.CUSTOMER_PRICE,
            MarketingPluginType.CUSTOMER_LEVEL));

        /** 打包一口价 */
        map.put(MarketingPluginType.BUYOUT_PRICE.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.ENTERPRISE_PRICE,
            MarketingPluginType.CUSTOMER_PRICE,
            MarketingPluginType.CUSTOMER_LEVEL));

        /** 第二件半价 */
        map.put(MarketingPluginType.HALF_PRICE_SECOND_PIECE.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.ENTERPRISE_PRICE,
            MarketingPluginType.CUSTOMER_PRICE,
            MarketingPluginType.CUSTOMER_LEVEL));

        /** 指定价 */
        map.put(MarketingPluginType.CUSTOMER_PRICE.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.REDUCTION,
            MarketingPluginType.DISCOUNT,
            MarketingPluginType.GIFT,
            MarketingPluginType.BUYOUT_PRICE,
            MarketingPluginType.HALF_PRICE_SECOND_PIECE));

        /** 等级价 */
        map.put(MarketingPluginType.CUSTOMER_LEVEL.name(), Lists.newArrayList(MarketingPluginType.COUPON,
            MarketingPluginType.REDUCTION,
            MarketingPluginType.DISCOUNT,
            MarketingPluginType.GIFT,
            MarketingPluginType.BUYOUT_PRICE,
            MarketingPluginType.HALF_PRICE_SECOND_PIECE));
    }

    public static void main(String[] args) {
        //    List<String> sqlList = new ArrayList<>();
        for (MarketingPluginType m : MarketingPluginType.values()) {
            StringBuilder sql = new StringBuilder();
            sql.append("insert into marketing_plugin(id,marketing_type,sort,coexist,description) values(")
                    .append(m.getId())
                    .append(",'")
                    .append(m.name())
                    .append("',")
                    .append(m.getId())
                    .append(",'")
                    .append(StringUtils.join(map.get(m.name()), ","))
                    .append("','")
                    .append(m.getDescription())
                    .append("');");
            System.out.println(sql);
        }
    }
}
