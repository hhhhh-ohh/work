package com.wanmi.sbc.marketing.api.request.communitystatistics;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

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
public class CommunityStatisticsCustomerCountCustomerRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长id
	 */
	@Schema(description = "批量-团长id")
	private List<String> leaderIds;
}