package com.wanmi.sbc.marketing.bean.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>新人购优惠券VO</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Schema
@Data
public class NewcomerPurchaseCouponVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 优惠券id
	 */
	@Schema(description = "优惠券id")
	private String couponId;

	/**
	 * 优惠券名称
	 */
	@Schema(description = "优惠券名称")
	private String couponName;


	/**
	 * 券组库存
	 */
	@Schema(description = "券组库存")
	private Long couponStock;

	/**
	 * 原始活动库存
	 */
	@Schema(description = "原始库存")
	private Long activityStock;

	/**
	 * 每组赠送数量
	 */
	@Schema(description = "每组赠送数量")
	private Integer groupOfNum;

	/**
	 * 优惠券信息
	 */
	@Schema(description = "优惠券信息")
	private CouponInfoVO couponInfoVO;

}