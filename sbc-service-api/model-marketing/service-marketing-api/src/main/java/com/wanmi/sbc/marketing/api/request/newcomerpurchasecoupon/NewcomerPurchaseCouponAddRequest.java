package com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>新人购优惠券新增参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseCouponAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券id
	 */
	@Schema(description = "优惠券id")
	@Length(max=32)
	private String couponId;

	/**
	 * 券组库存
	 */
	@Schema(description = "券组库存")
	@Max(9223372036854775807L)
	private Long couponStock;

	/**
	 * 每组赠送数量
	 */
	@Schema(description = "每组赠送数量")
	@Max(9999999999L)
	private Integer groupOfNum;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}