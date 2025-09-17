package com.wanmi.sbc.marketing.bean.constant;

import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhanggaolei
 * @className MarketingPluginConstant
 * @description TODO
 * @date 2022/2/22 10:35 上午
 */
public class MarketingPluginConstant {

    /***
     * 设置立即购买过滤的营销
     */
    public static final Set<Integer> IMMEDIATE_BUY_FILTER = new HashSet<>();

    /***
     * 购物车去结算过滤的营销
     */
    public static final Set<Integer> CONFIRM_FILTER = new HashSet<>();

    /***
     * 购物车去结算时，需将以下营销商品设为普通商品
     */
    public static final Set<Integer> CHANGE_COMMON_GOODS = new HashSet<>();

    /***
     * 只能选其一的选项
     */
    public static final Set<Integer> SELECT_ONE = new HashSet<>();

    /***
     * 购物车去结算过滤的营销
     */
    public static final Set<Integer> COMMIT_FILTER = new HashSet<>();

    public static final Set<Integer> PRICE_EXIST = new HashSet<>();

    static {
        IMMEDIATE_BUY_FILTER.add(MarketingPluginType.GROUPON.getId());
        IMMEDIATE_BUY_FILTER.add(MarketingPluginType.FLASH_SALE.getId());
        IMMEDIATE_BUY_FILTER.add(MarketingPluginType.FLASH_PROMOTION.getId());

        CONFIRM_FILTER.add(MarketingPluginType.GROUPON.getId());
//        CONFIRM_FILTER.add(MarketingPluginType.FLASH_SALE.getId());
//        CONFIRM_FILTER.add(MarketingPluginType.FLASH_PROMOTION.getId());
//        CONFIRM_FILTER.add(MarketingPluginType.APPOINTMENT_SALE.getId());
//        CONFIRM_FILTER.add(MarketingPluginType.BOOKING_SALE.getId());

        CHANGE_COMMON_GOODS.add(MarketingPluginType.FLASH_SALE.getId());
        CHANGE_COMMON_GOODS.add(MarketingPluginType.FLASH_PROMOTION.getId());
        CHANGE_COMMON_GOODS.add(MarketingPluginType.APPOINTMENT_SALE.getId());
        CHANGE_COMMON_GOODS.add(MarketingPluginType.BOOKING_SALE.getId());

        SELECT_ONE.add(MarketingPluginType.REDUCTION.getId());
        SELECT_ONE.add(MarketingPluginType.DISCOUNT.getId());
        SELECT_ONE.add(MarketingPluginType.GIFT.getId());
        SELECT_ONE.add(MarketingPluginType.BUYOUT_PRICE.getId());
        SELECT_ONE.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE.getId());

        COMMIT_FILTER.add(MarketingPluginType.FLASH_SALE.getId());
        COMMIT_FILTER.add(MarketingPluginType.FLASH_PROMOTION.getId());

        PRICE_EXIST.add(MarketingPluginType.FLASH_SALE.getId());
        PRICE_EXIST.add(MarketingPluginType.FLASH_PROMOTION.getId());
        PRICE_EXIST.add(MarketingPluginType.BOOKING_SALE.getId());
        PRICE_EXIST.add(MarketingPluginType.APPOINTMENT_SALE.getId());
        PRICE_EXIST.add(MarketingPluginType.GROUPON.getId());
        PRICE_EXIST.add(MarketingPluginType.CUSTOMER_LEVEL.getId());
        PRICE_EXIST.add(MarketingPluginType.CUSTOMER_PRICE.getId());
        PRICE_EXIST.add(MarketingPluginType.ENTERPRISE_PRICE.getId());
        PRICE_EXIST.add(MarketingPluginType.PAYING_MEMBER.getId());
    }
}
