package com.wanmi.sbc.marketing.api.request.communityassist;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>社区团购活动帮卖转发记录通用查询请求参数</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAssistRecordLeaderCountRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量-活动id
	 */
	@Schema(description = "批量-活动id")
	private List<String> activityIds;
}