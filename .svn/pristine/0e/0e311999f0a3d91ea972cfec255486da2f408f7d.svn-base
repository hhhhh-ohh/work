package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WxPayOrderDetailBaseRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/9/17 14:03
 **/
@Schema
@Data
public class WxPayOrderDetailBaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;


    /************************* 必传参数 ************************************/
    @Schema(description = "公众账号ID")
    private String appid;

    @Schema(description = "商户号")
    private String mch_id;

    @Schema(description = "随机字符串")
    private String nonce_str;

    @Schema(description = "签名")
    private String sign;

    @Schema(description = "微信订单号以及商户订单号（二选一）")
    private String transaction_id;

    @Schema(description = "商户订单号")
    private String out_trade_no;

    /************************* 非必传参数 ************************************/
    @Schema(description = "签名类型")
    private String sign_type;
}
