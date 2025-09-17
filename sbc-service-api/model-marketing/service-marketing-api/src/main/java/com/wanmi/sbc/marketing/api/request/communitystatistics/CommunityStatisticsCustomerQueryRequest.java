package com.wanmi.sbc.marketing.api.request.communitystatistics;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动会员信息表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:49:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsCustomerQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<String> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

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