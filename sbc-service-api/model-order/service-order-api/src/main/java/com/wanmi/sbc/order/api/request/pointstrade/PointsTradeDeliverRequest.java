package com.wanmi.sbc.order.api.request.pointstrade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.TradeDeliverDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradeDeliverRequest
 * @Description 积分订单确认收货Request
 * @Author lvzhenwei
 * @Date 2019/5/21 15:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeDeliverRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 交易单物流信息
     */
    @Schema(description = "交易单物流信息")
    private TradeDeliverDTO tradeDeliver;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

}
