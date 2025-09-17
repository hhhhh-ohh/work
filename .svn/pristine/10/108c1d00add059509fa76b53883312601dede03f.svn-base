package com.wanmi.sbc.empower.sellplatform;

import com.wanmi.sbc.common.annotation.ApiEnum;

import java.util.Arrays;

@ApiEnum
public enum PlatformServiceType {

    /**
     * 注册申请相关服务
     */
    REGIST_APPLY_SERVICE("regist_apply_service"),

    /**
     * 类目处理
     */
    CATE_SERVICE("cate_service"),

    /**
     *  商品相关
     */
    GOODS_SERVICE("goods_service"),

    /**
     * 订单相关
     */
    ORDER_SERVICE("order_service"),

    /**
     * 退单相关
     */
    RETURN_ORDER_SERVICE("return_order_service"),

    /**
     * 小程序订阅消息相关处理
     */
    MINI_PROGRAM_SUBSCRIBE_SERVICE("mini_program_subscribe_service"),

    /**
     * 推广员
     */
    PROMOTER_SERVICE("promoter_service");

    PlatformServiceType(String service) {
        this.service = service;
    }

    private String service;

    public String getService() {
        return service;
    }

    public static PlatformServiceType get(String service) {
        return Arrays.stream(PlatformServiceType.values()).filter(data -> data.getService().equals(service)).findFirst().orElse(null);
    }
}
