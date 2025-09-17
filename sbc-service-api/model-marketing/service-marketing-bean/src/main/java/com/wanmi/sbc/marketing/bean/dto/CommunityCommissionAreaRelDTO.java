package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>社区团购活动佣金区域关联表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:40:22
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCommissionAreaRelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域Id
	 */
	@NotEmpty
	@Schema(description = "区域Id")
	private List<String> areaId;

	/**
	 * 区域Id
	 */
	@NotEmpty
	@Schema(description = "区域Id")
	private List<String> areaName;

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