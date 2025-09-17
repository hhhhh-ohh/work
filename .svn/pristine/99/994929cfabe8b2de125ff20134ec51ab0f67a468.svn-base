package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.enums.EnableStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>系统成长值开关修改参数</p>
 * @author yxz
 * @date 2019-04-03 11:43:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemLiveStatusModifyRequest extends SettingBaseRequest {


	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotBlank
	private String SystemLiveConfigId;

	/**
	 * 是否启用标志 0：停用，1：启用
	 */
	@Schema(description = "是否启用标志 0：停用，1：启用")
	private EnableStatus status;
}