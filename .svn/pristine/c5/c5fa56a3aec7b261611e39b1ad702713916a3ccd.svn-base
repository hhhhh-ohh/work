package com.wanmi.sbc.empower.api.request.pay.weixin;

import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信企业付款到零钱请求参数
 */
@Schema
@Data
public class WxPayCompanyPaymentInfoRequest implements Serializable {

    private static final long serialVersionUID = -2993699463665143363L;
    /**
     * 必传字段
     */
    @Schema(description = "商户订单号")
    private String partner_trade_no;    //商户订单号

    @Schema(description = "用户openid")
    private String openid;              //用户openid,商户appid下，某用户的openid

    @Schema(description = "校验用户姓名")
    private String check_name;          //校验用户姓名;选项NO_CHECK：不校验真实姓名；FORCE_CHECK：强校验真实姓名

    @Schema(description = "收款用户姓名")
    private String re_user_name;        //收款用户姓名;收款用户真实姓名。如果check_name设置为FORCE_CHECK，则必填用户真实姓名

    @Schema(description = "企业付款金额")
    private String amount;              //金额,企业付款金额，单位为分

    @Schema(description = "企业付款备注")
    private String desc;                //企业付款备注,企业付款备注，必填。注意：备注中的敏感词会被转成字符*

    @Schema(description = "终端IP")
    private String spbill_create_ip;    //终端IP

    @Schema(description = "付款类型")
    private WxPayTradeType payType;

    /**
     * 非必传字段
     */
    @Schema(description = "设备号")
    private String device_info;         //设备号


    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /**
     * 小程序appid
     * 小程序配置是在setting模块。pay模块无法获取。被迫无奈，从bff传下去
     */
    @Schema(description = "小程序appid")
    private String miniAppId;

}
