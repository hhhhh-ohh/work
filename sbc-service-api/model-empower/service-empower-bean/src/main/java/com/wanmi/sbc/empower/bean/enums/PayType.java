package com.wanmi.sbc.empower.bean.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 支付平台类型枚举类
 **/
@ApiEnum
public enum PayType {
    /**
     * 微信支付
     */
    @ApiEnumProperty(" 0：微信支付平台")
    WXPAY(PayType.WX_PAY_SERVICE),
    /**
     * 支付宝支付
     */
    @ApiEnumProperty(" 1：支付宝支付平台")
    ALIPAY(PayType.ALI_PAY_SERVICE),
    /**
     * 银联云闪付支付
     */
    @ApiEnumProperty(" 2：银联云闪付支付平台")
    UNIONCLONDPAY(PayType.UNION_CLOUD_PAY_SERVICE),
    /**
     * 银联企业支付
     */
    @ApiEnumProperty(" 3：银联企业支付平台")
    UNIONB2BPAY(PayType.UNION_B2B_PAY_SERVICE),

    @ApiEnumProperty(" 4：微信视频号支付平台")
    WXCHANNELSPAY(PayType.WX_CHANNELS_PAY),

    @ApiEnumProperty(" 5：拉卡拉支付")
    LAKALA_PAY(PayType.LAKALA_SERVICE),

    @ApiEnumProperty(" 6：微信支付V3平台")
    WXV3PAY(PayType.WX_PAYV3_SERVICE),

    @ApiEnumProperty(" 7：拉卡拉收银台支付")
    LAKALA_CASHER_PAY(PayType.LAKALA_CASH_SERVICE);


    public static final String WX_PAY_SERVICE = "wechatPayService";

    public static final String ALI_PAY_SERVICE = "aliPayService";

    public static final String LAKALA_SERVICE = "lakalaPayService";

    public static final String UNION_CLOUD_PAY_SERVICE = "unionCloudPayService";

    public static final String UNION_B2B_PAY_SERVICE = "unionB2bPayService";

    public static final String WX_CHANNELS_PAY = "wechatChannelsPayService";

    public static final String WX_PAYV3_SERVICE = "wechatPayV3Service";

    public static final String LAKALA_CASH_SERVICE = "lakalaCasherPayService";

    private String payService;

    PayType(String payService) {
        this.payService = payService;
    }

    public String getPayService() {
        return payService;
    }

    @JsonCreator
    public static PayType fromValue(int value) {
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
