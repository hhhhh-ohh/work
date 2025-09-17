package com.wanmi.sbc.marketing.api.request.marketingsuits;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询组合商品主表请求参数</p>
 * @author zhk
 * @date 2020-04-01 20:54:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsValidRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 促销id
	 */
	@Schema(description = "促销id")
	@NotNull
	private Long marketingId;

}