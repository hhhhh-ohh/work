package com.wanmi.sbc.crm.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>运营计划与优惠券关联新增参数</p>
 * @author dyt
 * @date 2020-01-08 14:11:18
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanCouponDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券Id
	 */
	@Schema(description = "优惠券Id")
	@NotBlank
	private String couponId;

	/**
	 * 赠送数量
	 */
	@Schema(description = "赠送数量")
	@NotNull
	@Max(9999999999L)
	private Integer giftCount;

}