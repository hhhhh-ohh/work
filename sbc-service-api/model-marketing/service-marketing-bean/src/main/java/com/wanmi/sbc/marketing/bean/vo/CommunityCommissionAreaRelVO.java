package com.wanmi.sbc.marketing.bean.vo;

import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动佣金区域关联表VO</p>
 * @author dyt
 * @date 2023-07-24 14:40:22
 */
@Schema
@Data
public class CommunityCommissionAreaRelVO implements Serializable {
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
	 * 区域Id
	 */
	@Schema(description = "区域Id")
	private List<String> areaId;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	private List<String> areaName;

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