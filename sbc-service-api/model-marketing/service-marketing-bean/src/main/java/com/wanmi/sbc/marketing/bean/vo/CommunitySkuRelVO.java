package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>社区团购活动商品表VO</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
@Schema
@Data
public class CommunitySkuRelVO implements Serializable {
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
	 * 商品spuId
	 */
	@Schema(description = "商品spuId")
	private String spuId;

	/**
	 * 商品skuId
	 */
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
	private BigDecimal pickupCommission;

	/**
	 * 帮卖佣金
	 */
	@Schema(description = "帮卖佣金")
	private BigDecimal assistCommission;

	/**
	 * 活动库存
	 */
	@Schema(description = "活动库存")
	private Long activityStock;

	/**
	 * 已买库存
	 */
	@Schema(description = "已买库存")
	private Long sales;

	/**
	 * 预估佣金
	 */
	@Schema(description = "预估佣金")
	private BigDecimal estimateCommission;
}