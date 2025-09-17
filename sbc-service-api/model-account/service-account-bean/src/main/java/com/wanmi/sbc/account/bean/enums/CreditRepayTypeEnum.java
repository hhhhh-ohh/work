package com.wanmi.sbc.account.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 授信还款支付方式
 */
@ApiEnum(dataType = "java.lang.String")
public enum CreditRepayTypeEnum {
    /**
     * 银联
     */
    @ApiEnumProperty("银联")
    UNIONPAY("银联"),

    /**
     * 微信
     */
    @ApiEnumProperty("微信")
    WECHAT("微信"),

    /**
     * 支付宝
     */
    @ApiEnumProperty("支付宝")
    ALIPAY("支付宝"),

    /**
     * 余额
     */
    @ApiEnumProperty("余额")
    BALANCE("余额"),

    /**
     * 企业银联
     */
    @ApiEnumProperty("企业银联")
    UNIONPAY_B2B("企业银联");



    private String desc;

    CreditRepayTypeEnum(String desc) {
        this.desc = desc;
    }

    @JsonCreator
    public CreditRepayTypeEnum fromValue(String name) {
        return CreditRepayTypeEnum.valueOf(name);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    public String getDesc() {
        return desc;
    }

}
