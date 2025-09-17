package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeUpdateStateRequest extends BaseRequest {

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    @NotNull
    private TradeStateDTO tradeStateDTO;

    /**
     * 主订单号
     */
    @NotBlank
    @Schema(description = "主订单号")
    private String tradeId;

    @Schema(description = "操作人")
    private Operator operator;

    @Schema(description = "配送id")
    private String deliveryId;

    /***
     * 该值在订单为同城配送
     * 骑手取货回调中填充
     * 如果骑手取消->商家确认取消，则删除该字段
     */
    @Schema(description = "收货码")
    private String orderFinishCode;
}
