package com.wanmi.sbc.marketing.api.request.marketingsuitssku;

import java.math.BigDecimal;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>组合活动关联商品sku表列表查询请求参数</p>
 * @author zhk
 * @date 2020-04-02 10:51:12
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsSkuListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Long> idList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 组合id
	 */
	@Schema(description = "组合id")
	private Long suitsId;

	/**
	 * 促销活动id
	 */
	@Schema(description = "促销活动id")
	private Long marketingId;

	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	private String skuId;

	/**
	 * 单个优惠价格（优惠多少）
	 */
	@Schema(description = "单个优惠价格（优惠多少）")
	private BigDecimal discountPrice;

	/**
	 * sku数量
	 */
	@Schema(description = "sku数量")
	private Long num;

}