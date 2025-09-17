package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>电子卡密表修改参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 卡密Id
	 */
	@Schema(description = "卡密Id")
	@NotNull
	private String id;

	/**
	 * 卡号
	 */
	@Schema(description = "卡号")
	private String cardNumber;

	/**
	 * 卡密
	 */
	@Schema(description = "卡密")
	private String cardPassword;

	/**
	 * 优惠码
	 */
	@Schema(description = "优惠码")
	private String cardPromoCode;

}
