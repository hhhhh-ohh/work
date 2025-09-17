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
 * <p>社区团购活动商品表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySkuRelDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 商品spuId
	 */
	@NotBlank
	@Schema(description = "商品spuId")
	private String spuId;

	/**
	 * 商品skuId
	 */
	@NotBlank
	@Schema(description = "商品skuId")
	private String skuId;

	/**
	 * 活动价
	 */
	@Schema(description = "活动价")
	private BigDecimal price;

	/**
	 * 自提服务佣金
	 */
	@Schema(description = "自提服务佣金")
	@DecimalMin(value = "0")
	@DecimalMax(value = "100")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal pickupCommission;

	/**
	 * 帮卖佣金
	 */
	@Schema(description = "帮卖佣金")
	@DecimalMin(value = "0")
	@DecimalMax(value = "100")
	@Digits(integer = 10, fraction = 2)
	private BigDecimal assistCommission;

	/**
	 * 活动库存
	 */
	@Schema(description = "活动库存")
	private Long activityStock;

}