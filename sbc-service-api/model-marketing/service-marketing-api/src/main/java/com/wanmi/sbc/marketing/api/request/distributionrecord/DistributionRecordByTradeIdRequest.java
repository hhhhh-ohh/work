package com.wanmi.sbc.marketing.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>单个查询DistributionRecord请求参数</p>
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionRecordByTradeIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * tardeId
	 */
	@NotNull
	private String tradeId;
}