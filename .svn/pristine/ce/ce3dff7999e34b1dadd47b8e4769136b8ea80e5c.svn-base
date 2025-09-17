package com.wanmi.sbc.marketing.api.request.communitystatistics;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * <p>社区团购活动统计会员信息表新增参数</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsCustomerAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

}