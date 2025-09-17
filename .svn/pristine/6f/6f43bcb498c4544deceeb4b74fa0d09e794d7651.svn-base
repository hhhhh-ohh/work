package com.wanmi.sbc.goods.bean.vo;

import java.math.BigDecimal;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>周期购sku表VO</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@Data
public class BuyCycleGoodsInfoVO implements Serializable {


	private static final long serialVersionUID = -2353199717378792637L;
	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	private String goodsInfoId;

	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	private String goodsId;

	/**
	 * 最低期数
	 */
	@Schema(description = "最低期数")
	private Integer minCycleNum;

	/**
	 * 每期价格
	 */
	@Schema(description = "每期价格")
	private BigDecimal cyclePrice;


	/**
	 * 商品SKU信息
	 */
	@Schema(description = "商品SKU信息")
	private GoodsInfoVO goodsInfo;


	/**
	 * 状态描述
	 */
	@Schema(description = "状态描述")
	private String stateDesc;

}