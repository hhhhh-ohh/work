package com.wanmi.sbc.setting.api.request.thirdaddress;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>第三方地址映射表修改参数</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAddressModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 第三方地址省份id
	 */
	@Schema(description = "第三方地址省份id")
	@NotBlank
	private String thirdProvId;

	/**
	 * 第三方地址城市id
	 */
	@Schema(description = "第三方地址城市id")
	@NotBlank
	private String thirdCityId;

	/**
	 * 第三方地址区县id
	 */
	@Schema(description = "第三方地址区县id")
	@NotBlank
	private String thirdDistrictId;

	/**
	 * 第三方地址街道id
	 */
	@Schema(description = "第三方地址街道id")
	private String thirdStreetId;


	/**
	 * 平台地址省份id
	 */
	@Schema(description = "平台地址省份id")
	@NotBlank
	private String platformProvId;

	/**
	 * 平台地址城市id
	 */
	@Schema(description = "平台地址城市id")
	@NotBlank
	private String platformCityId;

	/**
	 * 平台地址区县id
	 */
	@Schema(description = "平台地址区县id")
	@NotBlank
	private String platformDistrictId;

	/**
	 * 平台地址街道id
	 */
	@Schema(description = "平台地址街道id")
	private String platformStreetId;

	/**
	 * 第三方标志 0:likedMall 1:京东
	 */
	@Schema(description = "第三方标志 0:likedMall 1:京东")
	@NotNull
	private ThirdPlatformType thirdFlag;

}