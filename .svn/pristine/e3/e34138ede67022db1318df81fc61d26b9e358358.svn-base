package com.wanmi.sbc.marketing.api.request.communityrel;

import java.math.BigDecimal;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动佣金团长关联表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCommissionLeaderRelQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 批量-活动id
	 */
	@Schema(description = "批量-活动id")
	private List<String> activityIdList;

	/**
	 * 团长Id
	 */
	@Schema(description = "团长Id")
	private String leaderId;

	/**
	 * 团长自提点Id
	 */
	@Schema(description = "团长自提点Id")
	private String pickupPointId;

	/**
	 * 自提服务佣金
	 */
	@Schema(description = "自提服务佣金")
	private BigDecimal pickupCommission;

	/**
	 * 帮卖团长佣金
	 */
	@Schema(description = "帮卖团长佣金")
	private BigDecimal assistCommission;

}