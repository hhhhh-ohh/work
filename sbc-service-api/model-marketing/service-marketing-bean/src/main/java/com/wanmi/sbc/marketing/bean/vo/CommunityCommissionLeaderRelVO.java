package com.wanmi.sbc.marketing.bean.vo;

import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动佣金团长关联表VO</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
@Schema
@Data
public class CommunityCommissionLeaderRelVO implements Serializable {
	private static final long serialVersionUID = 1L;

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