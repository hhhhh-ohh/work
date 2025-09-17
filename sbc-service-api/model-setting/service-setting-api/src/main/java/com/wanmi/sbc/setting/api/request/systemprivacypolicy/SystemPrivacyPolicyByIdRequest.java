package com.wanmi.sbc.setting.api.request.systemprivacypolicy;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询隐私政策请求参数</p>
 * @author yangzhen
 * @date 2020-09-23 14:52:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemPrivacyPolicyByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 隐私政策id
	 */
	@Schema(description = "隐私政策id")
	@NotNull
	private String privacyPolicyId;
}