package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信支付退款异步回调返回数据参数
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPayRefundCallBackResponse implements Serializable {

    private static final long serialVersionUID = 8978321812884947905L;
    @Schema(description = "返回状态码")
    private String return_code;         //返回状态码

    @Schema(description = "返回信息")
    private String return_msg;          //返回信息

    @Schema(description = "公众账号ID")
    private String appid;               //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;              //商户号

    @Schema(description = "随机字符串")
    private String nonce_str;           //随机字符串

    @Schema(description = "加密信息")
    private String req_info;            //加密信息

}
