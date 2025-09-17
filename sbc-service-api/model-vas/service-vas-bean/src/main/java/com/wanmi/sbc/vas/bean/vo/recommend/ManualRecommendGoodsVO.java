package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>手动推荐商品管理VO</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@Data
public class ManualRecommendGoodsVO extends BasicResponse {
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
	 * 权重
	 */
	@Schema(description = "权重")
	private BigDecimal weight;

}