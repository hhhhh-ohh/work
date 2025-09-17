package com.wanmi.sbc.order.api.request.pointstrade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.TradeDeliverRequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradeDeliveryCheckRequest
 * @Description 发货校验Request
 * @Author lvzhenwei
 * @Date 2019/5/21 16:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeDeliveryCheckRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

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
}
