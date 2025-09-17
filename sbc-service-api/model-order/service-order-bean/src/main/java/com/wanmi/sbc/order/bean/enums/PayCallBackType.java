package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import com.wanmi.sbc.empower.bean.enums.PayType;

/**
 * @ClassName PayCallBackType
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/7/1 17:57
 **/
@ApiEnum
public enum PayCallBackType {

    @ApiEnumProperty("0: 微信支付")
    WECAHT,

    @ApiEnumProperty("1: 支付宝支付")
    ALI,

    @ApiEnumProperty("2: 银联支付")
    UNIONPAY,

    @ApiEnumProperty(" 3：银联企业支付平台")
    UNIONB2BPAY,

    @ApiEnumProperty("4: 微信视频号")
    WECHAT_CHANNELS,

    @ApiEnumProperty("5: 拉卡拉支付")
    LAKALA,

    @ApiEnumProperty("6: 微信支付-V3")
    WECAHT_V3,

    @ApiEnumProperty("7: 拉卡拉收银台支付")
    LAKALA_CASHER;

    @JsonCreator
    public static PayCallBackType fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
