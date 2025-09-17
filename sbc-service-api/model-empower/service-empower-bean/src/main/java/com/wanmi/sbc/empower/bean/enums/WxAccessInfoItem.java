package com.wanmi.sbc.empower.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 视频号接入任务类型枚举
 * @author malianfeng
 * @date 2022/4/25 13:18
 */
public enum WxAccessInfoItem {

    // 完成spu接口
    @ApiEnumProperty("6:完成spu接口")
    SPU(6),

    // 完成订单接口
    @ApiEnumProperty("7:完成订单接口")
    ORDER(7),

    // 完成物流接口
    @ApiEnumProperty("8:完成物流接口")
    LOGISTICS(8),

    // 完成售后接口
    @ApiEnumProperty("9:完成售后接口")
    AFTER_SALE(9),

    // 测试完成
    @ApiEnumProperty("10:测试完成")
    TEST(10),

    // 发版完成
    @ApiEnumProperty("11:发版完成")
    RELEASE(11),

    // 完成二级商户号订单
    @ApiEnumProperty("19:完成二级商户号订单")
    SECOND_LEVEL_MERCHANT_ORDER(19),

    // 完成二级商户号售后
    @ApiEnumProperty("20:完成二级商户号售后")
    SECOND_LEVEL_MERCHANT_AFTER_SALE(20);

    private final int code;

    WxAccessInfoItem(int code) {
        this.code = code;
    }

    @JsonCreator
    public WxAccessInfoItem fromValue(int code) {
        for (WxAccessInfoItem item : values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.code;
    }
}

