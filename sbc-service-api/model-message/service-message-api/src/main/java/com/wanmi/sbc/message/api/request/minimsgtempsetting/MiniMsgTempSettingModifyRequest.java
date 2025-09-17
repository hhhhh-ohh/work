package com.wanmi.sbc.message.api.request.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * <p>小程序订阅消息模版配置表修改参数</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 温馨提示-提供修改
	 */
	@Schema(description = "温馨提示-提供修改")
	@Length(max=20)
	@NotBlank
	private String newTips;

}
