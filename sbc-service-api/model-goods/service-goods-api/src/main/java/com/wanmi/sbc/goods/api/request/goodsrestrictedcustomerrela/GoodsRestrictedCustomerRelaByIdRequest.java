package com.wanmi.sbc.goods.api.request.goodsrestrictedcustomerrela;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询限售配置会员关系表请求参数</p>
 * @author baijz
 * @date 2020-04-08 11:32:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedCustomerRelaByIdRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 限售会员的关系主键
	 */
	@Schema(description = "限售会员的关系主键")
	@NotNull
	private Long relaId;
}