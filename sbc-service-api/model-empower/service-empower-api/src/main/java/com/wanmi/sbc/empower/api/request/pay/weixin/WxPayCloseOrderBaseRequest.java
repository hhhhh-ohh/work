package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WxPayCloseOrderBaseRequest
 * @Description 微信支付--关闭交易单基础类
 * @Author lvzhenwei
 * @Date 2020/11/9 15:54
 **/
@Schema
@Data
public class WxPayCloseOrderBaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 必传参数
     */
    @Schema(description = "公众账号ID")
    private String appid;                           //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;                          //商户号

    @Schema(description = "商户订单号")
    private String out_trade_no;                    //商户订单号

    @Schema(description = "随机字符串")
    private String nonce_str;                       //随机字符串

    @Schema(description = "签名")
    private String sign;                            //签名

    /**
     * 非必传参数
     */
    @Schema(description = "签名类型")
    private String sign_type;                        //签名类型
}
