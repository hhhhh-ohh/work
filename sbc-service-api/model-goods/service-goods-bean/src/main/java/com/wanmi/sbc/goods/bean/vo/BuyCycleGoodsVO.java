package com.wanmi.sbc.goods.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>周期购spu表VO</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@Data
public class BuyCycleGoodsVO implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	private String goodsId;

	/**
	 * 配送周期
	 */
	@Schema(description = "配送周期")
	private Integer deliveryCycle;

	/**
	 * 自选期数，当deliveryCycle = 6、7 时有值
	 */
	@Schema(description = "自选期数")
	private Integer optionalNum;

	/**
	 * 用户可选送达日期
	 */
	@Schema(description = "用户可选送达日期")
	private String deliveryDate;

	/**
	 * 预留时间 日期
	 */
	@Schema(description = "预留时间 日期")
	private Integer reserveDay;

	/**
	 * 预留时间 时间点
	 */
	@Schema(description = "预留时间 时间点")
	private Integer reserveTime;



	/**
	 * 周期购商品状态 0：生效；1：失效
	 */
	@Schema(description = "周期购商品状态 0：生效；1：失效")
	private Integer cycleState;

	/**
	 * 周期购商品sku集合
	 */
	@Schema(description = "周期购商品sku集合")
	private List<BuyCycleGoodsInfoVO> buyCycleGoodsInfos;


	/**
	 * 商品主图
	 */
	@Schema(description = "商品主图")
	private String goodsImg;


	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsName;


	/**
	 * 状态描述
	 */
	@Schema(description = "状态描述")
	private String stateDesc;

	/**
	 * 店铺Id
	 */
	private Long storeId;


	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String supplierName;



}