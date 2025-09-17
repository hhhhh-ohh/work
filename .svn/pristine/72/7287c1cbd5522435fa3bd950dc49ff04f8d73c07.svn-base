package com.wanmi.sbc.order.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @ClassName PayCallBackType
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/7/1 17:57
 **/
@ApiEnum
public enum PayAndRefundCallBackServiceType {

    @ApiEnumProperty("0: 微信支付")
    WECAHT(PayAndRefundCallBackServiceType.WX_PAY_CALL_BACK_SERVICE),

    @ApiEnumProperty("1: 支付宝支付")
    ALI(PayAndRefundCallBackServiceType.ALI_PAY_CALL_BACK_SERVICE),

    @ApiEnumProperty("2: 银联支付")
    UNIONPAY(PayAndRefundCallBackServiceType.UNION_CLOUD_PAY_CALL_BACK_SERVICE),

    @ApiEnumProperty("3: 微信视频号")
    WECHAT_CHANNELS(PayAndRefundCallBackServiceType.WECHAT_CHANNELS_PAY_CALL_BACK_SERVICE),

    @ApiEnumProperty("4: 拉卡拉支付")
    LAKALA(PayAndRefundCallBackServiceType.LAKALA_PAY_CALL_BACK_SERVICE),

    @ApiEnumProperty("5: 微信-V3支付")
    WECAHT_V3(PayAndRefundCallBackServiceType.WX_PAY_V3_CALL_BACK_SERVICE),

    @ApiEnumProperty("6: 拉卡拉收银台支付")
    LAKALA_CASHER(PayAndRefundCallBackServiceType.LAKALA_CASHER_PAY_CALL_BACK_SERVICE);;


    public static final String WX_PAY_CALL_BACK_SERVICE = "wxPayAndRefundCallBackService";

    public static final String ALI_PAY_CALL_BACK_SERVICE = "aliPayAndRefundCallBackService";

    public static final String UNION_CLOUD_PAY_CALL_BACK_SERVICE = "unionCloudPayAndRefundCallBackService";

    public static final String WECHAT_CHANNELS_PAY_CALL_BACK_SERVICE = "wxChannelsPayAndRefundCallBackService";

    public static final String LAKALA_PAY_CALL_BACK_SERVICE = "lakalaPayCallBackService";

    public static final String WX_PAY_V3_CALL_BACK_SERVICE = "wxPayV3AndRefundCallBackService";

    public static final String LAKALA_CASHER_PAY_CALL_BACK_SERVICE = "lakalaCasherPayCallBackService";


    private String payCallBackService;

    PayAndRefundCallBackServiceType(String payCallBackService) {
        this.payCallBackService = payCallBackService;
    }


    public String getPayCallBackService() {
        return payCallBackService;
    }

    @JsonCreator
    public PayAndRefundCallBackServiceType fromValue(int value){
        return values()[value];
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
