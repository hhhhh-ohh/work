package com.wanmi.sbc.order.api.request.trade;

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
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class TradeDeliverRequest extends BaseRequest {

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


    /**
     * 周期购提前几天提醒发货
     */
    @Schema(description = "周期购提前几天提醒发货")
    private Integer remindShipping;
}
