package com.wanmi.sbc.customer.bean.vo;

import java.math.BigDecimal;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>折扣商品与付费会员等级关联表VO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@Schema
@Data
public class PayingMemberDiscountRelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	private String goodsInfoId;

	/**
	 * 商品SKU编码
	 */
	@Schema(description = "商品SKU编码")
	private String goodsInfoNo;

	/**
	 * 折扣
	 */
	@Schema(description = "折扣")
	private BigDecimal discount;

	/**
	 * 商品信息
	 */
	@Schema(description = "商品信息")
	private BasicResponse goodsInfoVO;

}