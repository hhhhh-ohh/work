package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>组合活动关联商品sku表VO</p>
 * @author zhk
 * @date 2020-04-02 10:51:12
 */
@Schema
@Data
public class MarketingSuitsSkuVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 单个优惠价
	 */
	@Schema(description = "单个优惠价格")
	private BigDecimal discountPrice;

	/**
	 * sku数量
	 */
	@Schema(description = "sku数量")
	private Long num;

	/**
	 * sku名称
	 */
	@Schema(description = "sku名称")
	private String goodsInfoName;

	/**
	 * 规格
	 */
	@Schema(description = "规格")
	private String specText;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String cateName;

	/**
	 * 品牌名称
	 */
	@Schema(description = "品牌名称")
	private String brandName;

	/**
	 * 市场价
	 */
	@Schema(description = "市场价")
	private BigDecimal marketPrice;

	/**
	 * sku编码
	 */
	@Schema(description = "sku编码")
	private String goodsInfoNo;

	/**
	 * 积分价
	 */
	@Schema(description = "积分价")
	private Long buyPoint;

	/**
	 * 商品类型 0:实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型 0:实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

	/**
	 * 供货价
	 */
	@Schema(description = "供货价")
	private BigDecimal supplyPrice;


	/**
	 * 供应商
	 */
	@Schema(description = "供应商")
	private String providerName;

	/**
	 * 营销商品状态
	 */
	@Schema(description = "营销商品状态")
	private MarketingGoodsStatus marketingGoodsStatus;


}