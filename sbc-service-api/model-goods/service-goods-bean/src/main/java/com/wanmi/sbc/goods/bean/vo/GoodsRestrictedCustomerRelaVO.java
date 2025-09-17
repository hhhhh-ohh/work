package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.AssignPersonRestrictedType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>限售配置VO</p>
 * @author baijz
 * @date 2020-04-08 11:32:28
 */
@Schema
@Data
public class GoodsRestrictedCustomerRelaVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 限售会员的关系主键
	 */
	@Schema(description = "限售会员的关系主键")
	private Long relaId;

	/**
	 * 限售主键
	 */
	@Schema(description = "限售主键")
	private Long restrictedSaleId;

	/**
	 * 特定会员的限售类型 0: 会员等级  1：指定会员
	 */
	@Schema(description = "特定会员的限售类型 0: 会员等级  1：指定会员")
	private AssignPersonRestrictedType assignPersonRestrictedType;

	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	private String customerId;

	/**
	 * 会员等级ID
	 */
	@Schema(description = "会员等级ID")
	private Long customerLevelId;

	/**
	 * 地域编码-多级中间用|分割
	 */
	@Schema(description = "地域编码-多级中间用|分割")
	private String addressId;

}