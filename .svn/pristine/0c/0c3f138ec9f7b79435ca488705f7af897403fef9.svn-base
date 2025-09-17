package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>运营计划与优惠券关联VO</p>
 * @author dyt
 * @date 2020-01-08 14:11:18
 */
@Schema
@Data
public class CustomerPlanCouponVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 计划id
	 */
	@Schema(description = "计划id")
	private Long planId;

	/**
	 * 优惠券Id
	 */
	@Schema(description = "优惠券Id")
	private String couponId;

	/**
	 * 赠送数量
	 */
	@Schema(description = "赠送数量")
	private Integer giftCount;

}