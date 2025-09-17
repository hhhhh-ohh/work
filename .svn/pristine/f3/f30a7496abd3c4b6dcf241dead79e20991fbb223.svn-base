package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 买家修改订单收货地址出参
 * @author malianfeng
 * @date 2022/11/18 11:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeModifyConsigneeByBuyerResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 修改是否成功
     */
    @Schema(description = "修改是否成功")
    private Boolean succeeded;

    /**
     * 失败原因
     */
    @Schema(description = "失败原因")
    private String failReason;
}
