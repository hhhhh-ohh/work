package com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>微信小程序支付发货信息新增结果</p>
 * @author 吕振伟
 * @date 2023-07-24 13:53:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayShippingOrderStatusResponse implements Serializable {
    private static final long serialVersionUID = 1L;


    @Schema(description = "微信交易流水号")
    private String wxTransactionId;

    @Schema(description = "交易状态 (1) 待发货；(2) 已发货；(3) 确认收货；(4) 交易完成；(5) 已退款；(6) 资金待结算")
    private Integer status;
}
