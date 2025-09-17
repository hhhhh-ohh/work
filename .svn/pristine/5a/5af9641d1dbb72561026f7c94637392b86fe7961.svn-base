package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author zhaiqiankun
 * @className SettingType
 * @description 视频带货应用接入设置
 * @date 2022/4/2 11:45
 **/
@ApiEnum
public enum SettingType {
    @ApiEnumProperty("0:开通小程序自定义交易组件")
    OPEN,
    @ApiEnumProperty("1:类目申请")
    CATE_REQUEST,
    @ApiEnumProperty("2:上传1款商品,等待审核通过")
    GOODS_AUDIT,
    @ApiEnumProperty("3:发起1笔订单并支付成功")
    TRADE_PAY,
    @ApiEnumProperty("4:完成该订单发货以及确认收货")
    GOODS_CONFIRM,
    @ApiEnumProperty("5:发起该订单售后")
    TRADE_VERIFY,
    @ApiEnumProperty("6:完成测试")
    FINISH_TEST,
    @ApiEnumProperty("7:开通视频号带货场景")
    VIDEO_GOODS,
    @ApiEnumProperty("8:营业执照")
    BUSINESS_LICENCE;

    @JsonCreator
    public static SettingType fromValue(int value) {
        if (value >= 0 && value < values().length) {
            return values()[value];
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
