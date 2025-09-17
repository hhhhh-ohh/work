package com.wanmi.sbc.setting.api.request.baseconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>单个删除基本设置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 基本设置ID
	 */
	@Schema(description = "基本设置ID")
	@NotNull
	private Integer baseConfigId;
}