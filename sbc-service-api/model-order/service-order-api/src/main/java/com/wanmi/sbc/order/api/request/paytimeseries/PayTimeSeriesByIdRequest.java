package com.wanmi.sbc.order.api.request.paytimeseries;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询支付流水记录请求参数</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesByIdRequest extends BaseRequest {
	private static final long serialVersionUID = -1733179414646055320L;

	/**
	 * 支付流水号
	 */
	@Schema(description = "支付流水号")
	@NotNull
	private String payNo;

}