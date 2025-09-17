package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import com.wanmi.sbc.common.base.Operator;
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
public class TradeGetByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 是否需要查询linkedmall子订单
     */
    @Schema(description = "是否需要查询linkedmall子订单")
    private Boolean needLmOrder;

    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

    public TradeGetByIdRequest(String tid) {
        this.tid = tid;
    }

    public TradeGetByIdRequest(String tid, Boolean needLmOrder) {
        this.tid = tid;
        this.needLmOrder = needLmOrder;
    }

    public TradeGetByIdRequest(String tid, Boolean needLmOrder, Operator operator) {
        this.tid = tid;
        this.needLmOrder = needLmOrder;
        this.operator = operator;
    }
}
