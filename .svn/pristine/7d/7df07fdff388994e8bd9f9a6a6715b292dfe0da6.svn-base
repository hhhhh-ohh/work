package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>电子卡券表修改参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 电子卡券id
	 */
	@Schema(description = "电子卡券id")
	@NotNull
	private Long id;

	/**
	 * 电子卡券名称
	 */
	@Schema(description = "电子卡券名称")
	@Length(max=20)
	@NotBlank
	private String couponName;

}
