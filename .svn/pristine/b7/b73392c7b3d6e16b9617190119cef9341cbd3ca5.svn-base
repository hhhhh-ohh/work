package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-06 16:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeCountByPayStateResponse extends BasicResponse {

    /**
     * 待审核
     */
    @Schema(description = "待审核")
    private Long waitAudit;

    /**
     * 待付款
     */
    @Schema(description = "待付款")
    private Long waitPay = 0L;

    /**
     * 待发货
     */
    @Schema(description = "待发货")
    private Long waitDeliver;

    /**
     * 待收货
     */
    @Schema(description = "待收货")
    private Long waitReceiving;


    /**
     * 待审核订单 true:开启 false:关闭
     */
    @Schema(description = "待审核订单",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private Boolean tradeCheckFlag = Boolean.FALSE;

}
