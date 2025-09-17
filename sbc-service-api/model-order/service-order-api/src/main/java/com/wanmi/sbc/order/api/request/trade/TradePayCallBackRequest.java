package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class TradePayCallBackRequest extends BaseRequest {

    /**
     * 交易单号
     */
    @Schema(description = "交易单号")
    private String tid;

    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal payOrderPrice;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayWay payWay;

}
