package com.wanmi.sbc.pay.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @author zhanggaolei
 */
@ApiEnum(classType = String.class)
public enum PayChannelType{
    /**
     * 微信支付
     */
    @ApiEnumProperty(" 微信扫码")
    WX_SCAN(PayChannelType.WX_SCAN_PAY_SERVICE),
    @ApiEnumProperty(" 微信内部浏览器")
    WX_H5(PayChannelType.WX_H5_PAY_SERVICE),
    @ApiEnumProperty(" 微信外部浏览器")
    WX_OUT_H5(PayChannelType.WX_OUT_H5_PAY_SERVICE),
    @ApiEnumProperty(" 微信小程序")
    WX_MINI_PROGRAM(PayChannelType.WX_MINI_PROGRAM_PAY_SERVICE),
    @ApiEnumProperty(" 微信app")
    WX_APP(PayChannelType.WX_APP_PAY_SERVICE),
    /**
     * 微信视频号支付
     */
    @ApiEnumProperty(" 微信视频号支付平台")
    WX_VIDEO(PayChannelType.WX_VIDEO_PAY_SERVICE),
    /**
     * 支付宝APP
     */
    @ApiEnumProperty(" 支付宝支付平台")
    ALI_APP(PayChannelType.ALI_APP_PAY_SERVICE),

    /**
     * 支付宝H5支付
     */
    @ApiEnumProperty(" 支付宝支付平台")
    ALI_H5(PayChannelType.ALI_H5_PAY_SERVICE),
    /**
     * 银联云闪付支付
     */
    @ApiEnumProperty(" 银联云闪付支付平台")
    UNION_CLOUD(PayChannelType.UNION_CLOUD_PAY_SERVICE),

    @ApiEnumProperty(" 银联云闪付App支付平台")
    UNION_CLOUD_APP(PayChannelType.UNION_CLOUD_APP_PAY_SERVICE),
    /**
     * 银联企业支付
     */
    @ApiEnumProperty(" 银联企业支付平台")
    UNION_B2B(PayChannelType.UNION_B2B_PAY_SERVICE),

    /**
     * 余额支付
     */
    @ApiEnumProperty(" 余额支付")
    BALANCE(PayChannelType.BALANCE_PAY_SERVICE),

    /**
     * 授信支付
     */
    @ApiEnumProperty(" 授信支付")
    CREDIT(PayChannelType.CREDIT_PAY_SERVICE),


    /**
     * 拉卡拉-wxPay
     */
    @ApiEnumProperty(" 拉卡拉微信支付")
    LAKALA_WX(PayChannelType.LAKALA_WX_SERVICE),

    /**
     * 拉卡拉微信扫码
     */
    @ApiEnumProperty(" 拉卡拉微信扫码支付")
    LAKALA_WX_SCAN(PayChannelType.LAKALA_WX_SCAN_SERVICE),

    /**
     * 拉卡拉-aliPay
     */
    @ApiEnumProperty(" 拉卡拉阿里支付")
    LAKALA_ALI(PayChannelType.LAKALA_ALI_SERVICE),

    /**
     * 拉卡拉-unionCloudPay
     */
    @ApiEnumProperty(" 拉卡拉银联支付")
    LAKALA_UNION(PayChannelType.LAKALA_UNION_SERVICE);



    private static final String WX_SCAN_PAY_SERVICE = "wechatScanPayService";
    private static final String WX_APP_PAY_SERVICE = "wechatAppPayService";
    private static final String WX_H5_PAY_SERVICE = "wechatH5PayService";
    private static final String WX_OUT_H5_PAY_SERVICE = "wechatOutH5PayService";
    private static final String WX_MINI_PROGRAM_PAY_SERVICE = "wechatMiniProgramPayService";
    private static final String WX_VIDEO_PAY_SERVICE = "wechatVideoPayService";

    private static final String ALI_H5_PAY_SERVICE = "aliH5PayService";
    private static final String ALI_APP_PAY_SERVICE = "aliAppPayService";

    private static final String LAKALA_ALI_SERVICE = "lakalaAliPayService";

    private static final String LAKALA_WX_SERVICE = "lakalaWxPayService";

    private static final String LAKALA_WX_SCAN_SERVICE = "lakalaWxScanPayService";

    private static final String LAKALA_UNION_SERVICE = "lakalaUnionPayService";

    private static final String UNION_CLOUD_PAY_SERVICE = "unionCloudPayService";

    private static final String UNION_CLOUD_APP_PAY_SERVICE = "unionCloudAppPayService";

    private static final String UNION_B2B_PAY_SERVICE = "unionB2bPayService";

    private static final String CREDIT_PAY_SERVICE = "creditPayService";

    private static final String BALANCE_PAY_SERVICE = "balancePayService";

    private String payService;

    PayChannelType(String payService) {
        this.payService = payService;
    }

    public String getPayService() {
        return payService;
    }

    @JsonCreator
    public static PayChannelType fromValue(String value) {
        return valueOf(value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    public Long getChannelItem(){
        switch (this){
            case WX_SCAN:
                return 14L;
            case WX_H5:
                return 16L;
            case WX_OUT_H5:
                return 15L;
            case WX_MINI_PROGRAM:
                return 16L;
            case WX_APP:
                return 20L;
            case WX_VIDEO:
                return 30L;
            case ALI_H5:
                return 18L;
            case ALI_APP:
                return 19L;
            case UNION_B2B:
                return 11L;
            case UNION_CLOUD:
                return 28L;
            case UNION_CLOUD_APP:
                return 29L;
            case LAKALA_WX:
                return 38L;
            case LAKALA_WX_SCAN:
                return 35L;
            case LAKALA_ALI:
                return 37L;

        }
        return null;
    }
}
