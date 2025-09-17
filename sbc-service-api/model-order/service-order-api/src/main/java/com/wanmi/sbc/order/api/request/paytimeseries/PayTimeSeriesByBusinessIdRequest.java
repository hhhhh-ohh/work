package com.wanmi.sbc.order.api.request.paytimeseries;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询支付流水记录请求参数</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesByBusinessIdRequest {
	private static final long serialVersionUID = 2680824474792204492L;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	@NotNull
	private String businessId;

}