package com.wanmi.sbc.setting.api.request.platformaddress;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>平台地址信息校验是否需要完善参数</p>
 * @author yhy
 * @date 2020-03-30 14:39:57
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressVerifyRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;
	/**
	 * 省级id
	 */
	@Schema(description = "省级id")
	@NotBlank
	private String provinceId;

	/**
	 * 城市id
	 */
	@Schema(description = "城市id")
	@NotBlank
	private String cityId;

	/**
	 * 区县id
	 */
	@Schema(description = "区县id")
	@NotBlank
	private String areaId;

	/**
	 * 街道id
	 */
	@Schema(description = "街道id")
	private String streetId;

}