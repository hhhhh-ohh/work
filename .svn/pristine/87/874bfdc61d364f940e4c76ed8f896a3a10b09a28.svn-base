package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.TradeDeliverRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 19:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeDeliveryCheckRequest extends BaseRequest {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String tid;

    /**
     * 交易物流
     */
    @Schema(description = "交易物流")
    private TradeDeliverRequestDTO tradeDeliver;

    @Schema(description = "当前操作人信息")
    private Operator operator;

}
