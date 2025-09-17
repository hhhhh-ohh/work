package com.wanmi.sbc.setting.api.request.baseconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询基本设置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 基本设置ID
	 */
	@Schema(description = "基本设置ID")
	@NotNull
	private Integer baseConfigId;
}