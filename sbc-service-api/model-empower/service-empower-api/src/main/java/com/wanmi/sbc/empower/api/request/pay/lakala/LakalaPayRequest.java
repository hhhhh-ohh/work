package com.wanmi.sbc.empower.api.request.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * @author edz
 * @className LakalaPayRequest
 * @description TODO
 * @date 2022/6/30 10:46
 */
@Data
@Schema
public class LakalaPayRequest implements Serializable {

    @JSONField(name = "merchant_no")
    @Schema(description = "拉卡拉分配的商户号")
    private String merchantNo;

    @Schema(description = "拉卡拉分配的业务终端号")
    @JSONField(name = "term_no")
    private String termNo;

    @Schema(description = "商户系统唯一订单号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "微信：WECHAT 支付宝：ALIPAY 银联：UQRCODEPAY 翼支付: BESTPAY 苏宁易付宝: SUNING 拉卡拉支付账户：LKLACC")
    @JSONField(name = "account_type")
    private String accountType;

    @Schema(description = "41:NATIVE（（ALIPAY，云闪付支持）51:JSAPI（微信公众号支付，支付宝服务窗支付，银联JS支付，翼支付JS支付、拉卡拉钱包支付）71:微信小程序支付")
    @JSONField(name = "trans_type")
    private String transType;

    @Schema(description = "单位分，整数型字符")
    @JSONField(name = "total_amount")
    private String totalAmount;

    @Schema(description = "地址位置信息，风控要求必送")
    @JSONField(name = "location_info")
    private LocationInfo locationInfo;

    @Schema(description = "订单标题, 最多42个字符")
    private String subject;

    @Schema(description = "通知回调地址")
    @JSONField(name = "notify_url")
    private String notifyUrl;

    @Schema(description = "结算类型:“0”或者空，常规结算方式，如需接拉卡拉分账需传“1”；")
    @JSONField(name = "settle_type")
    private String settleType = "1";

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "拉卡拉分配的商户号")
    @JSONField(name = "acc_busi_fields")
    private Object accBusiFields;

    @Data
    @Schema
    public static class LocationInfo {
        @Schema(description = "请求方的IP地址")
        @JSONField(name = "request_ip")
        private String requestIp;
    }

    @Data
    @Schema
    public static class AliPayAccBusi {

        @Schema(description = "预下单的订单的有效时间，以分钟为单位")
        private String timeout_express;

        @Schema(description = "用户付款中途退出返回商户网站的地址")
        private String quit_url;
    }

    @Data
    @Schema
    public static class WxPayAccBusi {
        @Schema(description = "预下单的订单的有效时间，以分钟为单位")
        private String timeout_express;

        @Schema(description = "微信分配的子商户公众账号ID，sub_appid（即微信小程序支付-71、公众号支付-51），此参数必传")
        private String sub_appid;

        @Schema(description = "用户在子商户sub_appid下的唯一标识，sub_openid，（即微信小程序支付-71、众号支付-51），此参数必传，只对微信支付有效")
        private String user_id;
    }

    @Data
    @Schema
    public static class uqrCodePayAccBusi{
        @Schema(description = "用户id")
        private String user_id;

        @Schema(description = "预下单有效时间")
        private String timeout_express;

        @Schema(description = "银联前台通知地址")
        private String front_url;

        @Schema(description = "银联失败交易前台通知地址")
        private String front_fail_url;
    }
}
