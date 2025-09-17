package com.wanmi.sbc.elastic.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * 分销记录查询入参
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsDistributionRecordByTradeIdRequest extends BaseRequest {


	/**
	 * 订单id
	 */
	@NotNull
	@Schema(description = "订单id")
	private String tradeId;
}