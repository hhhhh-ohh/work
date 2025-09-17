package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>社区团购活动佣金团长关联表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCommissionLeaderRelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 团长Id
	 */
	@Schema(description = "团长Id")
	private String leaderId;

	/**
	 * 团长自提点Id
	 */
	@NotBlank
	@Schema(description = "团长自提点Id")
	private String pickupPointId;

	/**
	 * 自提服务佣金
	 */
	@Schema(description = "自提服务佣金")
	@DecimalMin(value = "0")
	@DecimalMax(value = "100")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal pickupCommission;

	/**
	 * 帮卖团长佣金
	 */
	@Schema(description = "帮卖团长佣金")
	@DecimalMin(value = "0")
	@DecimalMax(value = "100")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal assistCommission;

}