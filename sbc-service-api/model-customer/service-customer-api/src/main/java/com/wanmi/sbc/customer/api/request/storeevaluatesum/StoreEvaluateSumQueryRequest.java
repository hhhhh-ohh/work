package com.wanmi.sbc.customer.api.request.storeevaluatesum;

import java.math.BigDecimal;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

/**
 * <p>店铺评价通用查询请求参数</p>
 * @author liutao
 * @date 2019-02-23 10:59:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class StoreEvaluateSumQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-id 主键List
	 */
	@Schema(description = "批量查询-id 主键List")
	private List<Long> sumIdList;

	/**
	 * id 主键
	 */
	@Schema(description = " id 主键")
	private Long sumId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 服务综合评分
	 */
	@Schema(description = "服务综合评分")
	private BigDecimal sumServerScore;

	/**
	 * 商品质量综合评分
	 */
	@Schema(description = "商品质量综合评分")
	private BigDecimal sumGoodsScore;

	/**
	 * 物流综合评分
	 */
	@Schema(description = "物流综合评分")
	private BigDecimal sumLogisticsScoreScore;

	/**
	 * 订单数
	 */
	@Schema(description = "订单数")
	private Integer orderNum;

	/**
	 * 评分周期 0：30天，1：90天，2：180天
	 */
	@Schema(description = "评分周期 0：30天，1：90天，2：180天")
	private Integer scoreCycle;

	/**
	 * 综合评分
	 */
	@Schema(description = "综合评分")
	private BigDecimal sumCompositeScore;
}