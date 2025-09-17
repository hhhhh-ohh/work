package com.wanmi.sbc.account.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * <p>支付方式</p>
 * Created by of628-wenzhi on 2017-12-05-下午4:18.
 */
@ApiEnum(dataType = "java.lang.String")
public enum PayWay {

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
     * 预存款
     */
    @ApiEnumProperty("预存款")
    ADVANCE("预存款"),

    /**
     * 积分
     */
    @ApiEnumProperty("积分")
    POINT("积分"),

    /**
     * 转账汇款
     */
    @ApiEnumProperty("线下支付")
    CASH("线下支付"),

    /**
     * 企业银联
     */
    @ApiEnumProperty("企业银联")
    UNIONPAY_B2B("企业银联"),

    /**
     * 银联
     */
    @ApiEnumProperty("云闪付")
    UNIONPAY("云闪付"),

    /**
     * 优惠券
     */
    @ApiEnumProperty("优惠券")
    COUPON("优惠券"),

    /**
     * 余额
     */
    @ApiEnumProperty("余额")
    BALANCE("余额"),

    /**
     * 授信支付
     */
    @ApiEnumProperty("授信")
    CREDIT("授信"),

    @ApiEnumProperty("拉卡拉")
    LAKALA("拉卡拉"),

    @ApiEnumProperty("拉卡拉收银台")
    LAKALACASHIER("拉卡拉收银台");


    private String desc;

    PayWay(String desc) {
        this.desc = desc;
    }

    @JsonCreator
    public PayWay fromValue(String name) {
        if(StringUtils.isBlank(name)) {
            return null;
        }
        return PayWay.valueOf(name);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    public String getDesc() {
        return desc;
    }

    /***
     * 判断是否三方支付
     * @return
     */
    public static Boolean isThirdPartyPay(PayWay payWay){
        if(Objects.isNull(payWay)) {
            return Boolean.FALSE;
        }
        return payWay == ALIPAY || payWay == WECHAT || payWay == UNIONPAY || payWay == UNIONPAY_B2B;
    }

    public static PayWay findPayWayByDesc(String desc){
        return Arrays.stream(PayWay.values()).filter(payWay -> payWay.desc.equals(desc)).findFirst().orElse(null);
    }

}
