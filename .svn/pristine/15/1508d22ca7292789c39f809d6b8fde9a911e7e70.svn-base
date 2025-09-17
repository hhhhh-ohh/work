package com.wanmi.sbc.setting.api.request.systemresource;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除平台素材资源请求参数</p>
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 素材资源ID
	 */
	@Schema(description = "素材资源ID")
	@NotNull
	private Long resourceId;
}