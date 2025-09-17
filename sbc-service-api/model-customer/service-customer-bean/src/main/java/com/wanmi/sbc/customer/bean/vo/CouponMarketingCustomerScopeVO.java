package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>优惠券活动目标客户作用范围VO</p>
 * @author lq
 * @date 2019-08-02 14:50:57
 */
@Schema
@Data
public class CouponMarketingCustomerScopeVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private String marketingCustomerScopeId;

	/**
	 * 优惠券活动id
	 */
	@Schema(description = "优惠券活动id")
	private String activityId;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

}