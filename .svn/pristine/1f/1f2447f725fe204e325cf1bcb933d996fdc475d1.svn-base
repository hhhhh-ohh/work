package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName WxPaySupplierTransferRequest
 * @Description 微信支付商家转账明细接口参数
 * @Author qiyuanzhao
 * @Date 2022/8/17 14:25
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxPaySupplierTransferInfoRequest {

    @Schema(description = "商家明细单号")
    private String out_detail_no;

    /**
     * 转账总金额(单位：分)
     */
    @Schema(description = "转账金额")
    private Integer transfer_amount;

    @Schema(description = "转账备注")
    private String transfer_remark;

    /**
     * 用户在直连商户应用下的用户标示
     */
    @Schema(description = "用户在直连商户应用下的用户标示")
    private String openid;

    @Schema(description = "收款用户姓名")
    private String user_name;



}
