package com.wanmi.sbc.marketing.api.request.communityactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询社区团购活动表请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;

	@Schema(description = "团长ID")
	private String leaderId;

	/**
	 * 是否查询销售范围
	 */
	@Schema(description = "是否查询销售范围 true:查询")
	private Boolean saleRelFlag;

	/**
	 * 是否查询佣金设置
	 */
	@Schema(description = "是否查询佣金设置 true:查询")
	private Boolean commissionRelFlag;

	/**
	 * 是否查询商品关联
	 */
	@Schema(description = "是否查询商品关联 true:商品关联")
	private Boolean skuRelFlag;
}