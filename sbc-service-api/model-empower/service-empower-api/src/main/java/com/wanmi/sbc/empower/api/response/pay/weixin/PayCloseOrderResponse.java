package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PayCloseOrderResponse
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/9 14:51
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayCloseOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "返回状态码")
    private String return_code;                      //返回状态码

    @Schema(description = "返回信息")
    private String return_msg;          //返回信息

    @Schema(description = "公众账号ID")
    private String appid;                            //公众账号ID

    @Schema(description = "商户号")
    private String mch_id;                           //商户号

    @Schema(description = "商户号")
    private String sub_mch_id;                           //商户号

    @Schema(description = "随机字符串")
    private String nonce_str;                        //随机字符串

    @Schema(description = "签名")
    private String sign;                             //签名

    @Schema(description = "业务结果")
    private String result_code;                      //业务结果

    @Schema(description = "业务结果描述")
    private String result_msg;                      //业务结果描述

    /**
     * 当result_code为FAIL时返回错误代码
     * ORDERPAID	订单已支付	订单已支付，不能发起关单
     * SYSTEMERROR	系统错误	系统错误
     * ORDERCLOSED	订单已关闭	订单已关闭，无法重复关闭
     * SIGNERROR	签名错误	参数签名结果不正确
     * REQUIRE_POST_METHOD	请使用post方法	未使用post传递参数
     * XML_FORMAT_ERROR	XML格式错误	XML格式错误
     */
    @Schema(description = "错误代码")
    private String err_code;                         //错误代码

    @Schema(description = "错误代码描述")
    private String err_code_des;                     //错误代码描述
}
