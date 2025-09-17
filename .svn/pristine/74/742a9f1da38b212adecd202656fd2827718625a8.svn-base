package com.wanmi.sbc.empower.bean.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>达达配送接口响应</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Data
public class DadaDeliverFeeVO implements Serializable {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "配送距离")
	private BigDecimal distance;

    @Schema(description = "实际运费")
    private BigDecimal fee;

    @Schema(description = "运费")
    private BigDecimal deliverFee;

    @Schema(description = "优惠券费用")
    private BigDecimal couponFee;

    @Schema(description = "小费")
    private BigDecimal tips;

    @Schema(description = "保价费")
    private BigDecimal insuranceFee;
}