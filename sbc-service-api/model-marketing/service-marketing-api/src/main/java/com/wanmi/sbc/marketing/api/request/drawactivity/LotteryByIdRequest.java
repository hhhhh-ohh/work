package com.wanmi.sbc.marketing.api.request.drawactivity;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>点击抽奖请求参数</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotteryByIdRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动编号
	 */
	@Schema(description = "活动编号")
	private Long activityId;

	/**
	 * 当前查看人的用户编号
	 */
	@Schema(description = "当前查看人的用户编号")
	private String customerId;

	@Schema(description = "终端token")
	private String terminalToken;


}