package com.wanmi.sbc.order.api.request.leadertradedetail;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>跟团记录按活动分组做统计跟团次数查询请求参数</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderTradeDetailTradeCountLeaderRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量-社区团购活动ID
	 */
	@NotEmpty
	@Schema(description = "批量-社区团购活动ID")
	private List<String> activityIds;

	/**
	 * 是否帮卖
	 */
	@Schema(description = "是否帮卖")
	private BoolFlag boolFlag;
}