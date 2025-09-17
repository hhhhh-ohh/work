package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询电子卡券表请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 电子卡券id
	 */
	@Schema(description = "电子卡券id")
	@NotNull
	private Long id;

}