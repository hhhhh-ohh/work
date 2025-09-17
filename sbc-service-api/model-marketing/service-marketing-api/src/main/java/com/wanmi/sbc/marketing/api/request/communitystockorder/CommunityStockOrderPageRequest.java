package com.wanmi.sbc.marketing.api.request.communitystockorder;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购备货单分页查询请求参数</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStockOrderPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	private Long skuId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
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
	private Long num;

}