package com.wanmi.sbc.marketing.api.request.marketingsuits;

import java.math.BigDecimal;
import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>组合商品主表列表查询请求参数</p>
 * @author zhk
 * @date 2020-04-01 20:54:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsListRequest extends BaseRequest {
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
	 * 促销id
	 */
	@Schema(description = "促销id")
	private Long marketingId;

	/**
	 * 套餐主图（图片url全路径）
	 */
	@Schema(description = "套餐主图（图片url全路径）")
	private String mainImage;

	/**
	 * 套餐价格
	 */
	@Schema(description = "套餐价格")
	private BigDecimal suitsPrice;

}