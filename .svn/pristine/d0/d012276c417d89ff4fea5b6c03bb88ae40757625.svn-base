package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>社区团购备货单新增参数</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStockOrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	@NotBlank
	private String activityId;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	@NotNull
	private String skuId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	@NotBlank
	private String goodsName;

	/**
	 * 规格
	 */
	@Schema(description = "规格")
	private String specName;

	/**
	 * 购买数量
	 */
	@Schema(description = "购买数量")
	@NotNull
	private Long num;

	/**
	 * 规格
	 */
	@Schema(description = "商品图片")
	private String goodsImg;
}