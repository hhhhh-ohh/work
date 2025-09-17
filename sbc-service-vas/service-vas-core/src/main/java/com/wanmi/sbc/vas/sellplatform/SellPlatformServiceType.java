package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.annotation.ApiEnum;

import java.util.Arrays;

@ApiEnum
public enum SellPlatformServiceType {

    /**
     * 申请处理
     */
    SELL_APPLY_SERVICE("sell_apply_service"),
    /**
     * 类目处理
     */
    SELL_CATE_SERVICE("sell_cate_service"),

    /**
     *  商品相关
     */
    SELL_GOODS_SERVICE("sell_goods_service"),

    /**
     * 订单相关
     */
    SELL_ORDER_SERVICE("sell_order_service"),

    /**
     * 退单相关
     */
    SELL_RETURN_ORDER_SERVICE("sell_return_order_service"),

    /**
     * 推广员相关
     */
    SELL_PROMOTER_SERVICE("sell_promoter_service"),
    ;

    SellPlatformServiceType(String service) {
        this.service = service;
    }

    private String service;

    public String getService() {
        return service;
    }

    public static SellPlatformServiceType get(String service) {
        return Arrays.stream(SellPlatformServiceType.values()).filter(data -> data.getService().equals(service)).findFirst().orElse(null);
    }
}
