package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>电子卡券表新增参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponAddRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 电子卡券名称
	 */
	@Schema(description = "电子卡券名称")
	@NotBlank
	@Length(max=20)
	private String couponName;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

}