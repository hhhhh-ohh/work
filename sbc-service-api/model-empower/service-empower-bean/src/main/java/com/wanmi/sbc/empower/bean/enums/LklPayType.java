package com.wanmi.sbc.empower.bean.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 拉卡拉收银台支付类型枚举类
 **/
@ApiEnum
public enum LklPayType {
    /**
     * 微信支付
     */
    @ApiEnumProperty(" 0：微信支付")
    WECHAT("WECHAT"),

    /**
     * 支付宝支付
     */
    @ApiEnumProperty(" 1：支付宝支付")
    ALIPAY("ALIPAY"),

    /**
     * 银联云闪付
     */
    @ApiEnumProperty(" 2：银联云闪付")
    UNION("UNION"),

    /**
     * POS刷卡交易
     */
    @ApiEnumProperty(" 3：POS刷卡交易")
    CARD("CARD"),

    /**
     * 线上转帐
     */
    @ApiEnumProperty(" 4：线上转帐")
    LKLAT("LKLAT"),

    /**
     * 快捷支付
     */
    @ApiEnumProperty(" 5：快捷支付")
    QUICK_PAY("QUICK_PAY"),

    /**
     * 网银支付
     */
    @ApiEnumProperty(" 6：网银支付")
    EBANK("EBANK"),

    /**
     * 银联支付
     */
    @ApiEnumProperty(" 7：银联支付")
    UNION_CC("UNION_CC");


    private String lklPayType;

    LklPayType(String lklPayType) {
        this.lklPayType = lklPayType;
    }

    public String getLklPayType() {
        return lklPayType;
    }

    @JsonCreator
    public static LklPayType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
