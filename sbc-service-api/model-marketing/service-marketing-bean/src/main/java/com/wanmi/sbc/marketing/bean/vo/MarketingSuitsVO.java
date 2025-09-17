package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>组合商品主表VO</p>
 * @author zhk
 * @date 2020-04-01 20:54:00
 */
@Schema
@Data
public class MarketingSuitsVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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