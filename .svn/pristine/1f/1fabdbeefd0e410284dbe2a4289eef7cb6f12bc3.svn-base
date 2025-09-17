package com.wanmi.sbc.marketing.api.request.communitystatistics;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>社区团购活动统计会员信息表列表查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsCustomerListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 批量活动id
	 */
	@Schema(description = "批量活动id")
	private List<String> activityIds;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 批量团长id
	 */
	@Schema(description = "批量团长id")
	private List<String> leaderIds;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;
}