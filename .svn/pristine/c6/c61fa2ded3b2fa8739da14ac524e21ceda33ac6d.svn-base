package com.wanmi.sbc.marketing.api.request.communitysku;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>社区团购活动商品表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySkuRelQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

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
	 * 批量-活动id
	 */
	@Schema(description = "批量-活动id")
	private List<String> activityIdList;

	/**
	 * 商品spuId
	 */
	@Schema(description = "商品spuId")
	private String spuId;

	/**
	 * 商品skuId-list
	 */
	@Schema(description = "商品skuId-list")
	private List<String> skuIdList;

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

}