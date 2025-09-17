package com.wanmi.sbc.setting.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 平台和第三方代销平台物流公司映射DTO
 * @author malianfeng
 * @date 2022/4/26 20:35
 */
@Data
public class ExpressCompanyThirdRelDTO implements Serializable {

	private static final long serialVersionUID = 4326855945437375997L;

	/**
	 * 平台物流ID
	 */
	@Schema(description = "平台物流ID")
	@NotNull
	private Long expressCompanyId;

	/**
	 * 第三方平台物流ID
	 */
	@Schema(description = "第三方平台物流ID")
	@NotNull
	private Long thirdExpressCompanyId;
}