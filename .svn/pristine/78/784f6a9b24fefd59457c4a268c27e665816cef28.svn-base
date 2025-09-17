package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信支付企业支付到零钱接口返回参数
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPaySupplierTransferRsponse implements Serializable {

    private static final long serialVersionUID = -6457221265995114891L;
    /**
     * 商户系统内部的商家批次单号
     **/
    @Schema(description = "商家批次单号")
    private String out_batch_no;

    /**
     * 微信批次单号，微信商家转账系统返回的唯一标识
     **/
    @Schema(description = "微信批次单号")
    private String batch_id;


    @Schema(description = "批次创建时间")
    private String create_time;

    @Schema(description = "描述")
    private String message;

    /**
     * 执行结果成功，还是失败
     */
    @Schema(description = "执行结果是否成功")
    private Boolean success;


}
