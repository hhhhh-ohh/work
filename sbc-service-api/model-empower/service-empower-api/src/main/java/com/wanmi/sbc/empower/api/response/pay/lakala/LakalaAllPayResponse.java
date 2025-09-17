package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.alibaba.fastjson2.annotation.JSONField;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * @author edz
 * @className LakalaPayVO
 * @description TODO
 * @date 2022/7/4 19:29
 **/
@Data
@Schema
public class LakalaAllPayResponse {

    @Schema(description = "商户号")
    @JSONField(alternateNames = {"merchant_no", "merchantNo"})
    private String merchantNo;

    @Schema(description = "商户请求流水号")
    @JSONField(alternateNames = {"out_trade_no", "outTradeNo"})
    private String outTradeNo;

    @Schema(description = "拉卡拉交易流水号")
    @JSONField(alternateNames = {"trade_no", "tradeNo"})
    private String tradeNo;

    @Schema(description = "拉卡拉对账单流水号")
    @JSONField(alternateNames = {"log_no", "logNo"})
    private String logNo;

    @Schema(description = "拆单信息")
    @JSONField(alternateNames = {"split_info", "splitInfo"})
    private List<SplitInfo> splitInfo;


    @Schema(description = "账户端返回信息域")
    @JSONField(alternateNames = {"acc_resp_fields", "accRespFields"})
    private Object accRespFields;

    @Data
    @Schema
    public static class SplitInfo {
        @Schema(description = "子单交易流水号")
        @JSONField(alternateNames = {"sub_trade_no", "subTradeNo"})
        private String subTradeNo;

        @Schema(description = "子单对账单流水号")
        @JSONField(alternateNames = {"sub_log_no", "subLogNo"})
        private String subLogNo;

        @Schema(description = "外部子交易流水号")
        @JSONField(alternateNames = {"out_sub_trade_no", "outSubTradeNo"})
        private String outSubTradeNo;

        @Schema(description = "商户号")
        @JSONField(alternateNames = {"merchant_no", "merchantNo"})
        private String merchantNo;

        @Schema(description = "终端号")
        @JSONField(alternateNames = {"term_no", "termNo"})
        private String termNo;

        @Schema(description = "金额")
        private String amount;
    }

    @Data
    @Schema
    static class AliPayNative{
        @Schema(description = "商户可用此参数自定义去生成二维码后展示出来进行扫码支付")
        private String code;

        @Schema(description = "商户收款二维码图片。Base64编码")
        @JSONField(alternateNames = {"code_image", "codeImage"})
        private String codeImage;
    }

    @Data
    @Schema
    static class AliPayJSApi{
        @Schema(description = "预下单Id")
        @JSONField(alternateNames = {"prepay_id", "prepayId"})
        private String prepayId;
    }

    @Data
    @Schema
    static class AliPayH5{
        @Schema(description = "为开发者生成前台页面请求需要的完整form 表单的 html")
        @JSONField(alternateNames = {"form_data", "formData"})
        private String formData;
    }

    @Data
    @Schema
    static class WxJSApi{
        @Schema(description = "预下单Id")
        @JSONField(alternateNames = {"prepay_id", "prepayId"})
        private String prepayId;

        @Schema(description = "支付签名信息")
        @JSONField(alternateNames = {"pay_sign", "paySign"})
        private String paySign;

        @Schema(description = "小程序id")
        @JSONField(alternateNames = {"app_id", "appId"})
        private String appId;

        @Schema(description = "时间戳")
        @JSONField(alternateNames = {"time_stamp", "timeStamp"})
        private String timeStamp;

        @Schema(description = "随机字符串")
        @JSONField(alternateNames = {"nonce_str", "nonceStr"})
        private String nonceStr;

        @Schema(description = "订单详情扩展字符串")
        @JSONField(alternateNames = {"package", "packageStr"})
        private String packageStr;

        @Schema(description = "签名方式")
        @JSONField(alternateNames = {"sign_type", "signType"})
        private String signType;
    }
}
