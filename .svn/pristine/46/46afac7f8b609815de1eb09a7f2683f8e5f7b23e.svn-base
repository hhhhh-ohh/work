package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>商品相关性推荐VO</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Schema
@Data
public class GoodsRelatedRecommendVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 商品id
	 */
	@Schema(description = "商品id")
	private String goodsId;

	/**
	 * 关联商品id
	 */
	@Schema(description = "关联商品id")
	private String relatedGoodsId;

	/**
	 * 提升度
	 */
	@Schema(description = "提升度")
	private Integer lift;

	/**
	 * 权重
	 */
	@Schema(description = "权重")
	private BigDecimal weight;

	/**
	 * 类型，0：关联分析，1：手动关联
	 */
	@Schema(description = "类型，0：关联分析，1：手动关联")
	private Integer type;

}