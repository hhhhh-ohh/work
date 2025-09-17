package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className LakalaPayVO
 * @description TODO
 * @date 2022/7/4 19:29
 **/
@Data
@Schema
public class LakalaPayResponse {

    @Schema(description = "商户号")
    @JSONField(name = "merchant_no")
    private String merchantNo;

    @Schema(description = "商户请求流水号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "拉卡拉交易流水号")
    @JSONField(name = "trade_no")
    private String tradeNo;

    @Schema(description = "拉卡拉对账单流水号")
    @JSONField(name = "log_no")
    private String logNo;

    @Schema(description = "结算商户号")
    @JSONField(name = "settle_merchant_no")
    private String settleMerchantNo;

    @Schema(description = "结算终端号")
    @JSONField(name = "settle_term_no")
    private String settleTermNo;

    @Schema(description = "账户端返回信息域")
    @JSONField(name = "acc_resp_fields")
    private Object accRespFields;

    @Data
    @Schema
    static class AliPayNative{
        @Schema(description = "商户可用此参数自定义去生成二维码后展示出来进行扫码支付")
        private String code;

        @Schema(description = "商户收款二维码图片。Base64编码")
        @JSONField(name = "code_image")
        private String codeImage;
    }

    @Data
    @Schema
    static class AliPayJSApi{
        @Schema(description = "预下单Id")
        @JSONField(name = "prepay_id")
        private String prepayId;
    }

    @Data
    @Schema
    static class AliPayH5{
        @Schema(description = "为开发者生成前台页面请求需要的完整form 表单的 html")
        @JSONField(name = "form_data")
        private String formData;
    }

    @Data
    @Schema
    static class WxJSApi{
        @Schema(description = "预下单Id")
        @JSONField(name = "prepay_id")
        private String prepayId;

        @Schema(description = "支付签名信息")
        @JSONField(name = "pay_sign")
        private String paySign;

        @Schema(description = "小程序id")
        @JSONField(name = "app_id")
        private String appId;

        @Schema(description = "时间戳")
        @JSONField(name = "time_stamp")
        private String timeStamp;

        @Schema(description = "随机字符串")
        @JSONField(name = "nonce_str")
        private String nonceStr;

        @Schema(description = "订单详情扩展字符串")
        @JSONField(name = "package")
        private String packageStr;

        @Schema(description = "签名方式")
        @JSONField(name = "sign_type")
        private String signType;
    }

    @Data
    @Schema
    static class uqrCodePayNative{
        @Schema(description = "二维码信息,商户可用此参数自定义去生成二维码后展示出来进行扫码支付")
        private String code;
    }

    @Data
    @Schema
    static class uqrCodePayJSApi{
        @Schema(description = "银联JS支付返回重定向地址")
        @JSONField(name = "redirect_url")
        private String redirectUrl;
    }
}
