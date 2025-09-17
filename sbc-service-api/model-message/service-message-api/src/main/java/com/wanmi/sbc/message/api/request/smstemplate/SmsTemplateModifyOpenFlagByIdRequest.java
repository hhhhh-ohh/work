package com.wanmi.sbc.message.api.request.smstemplate;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个短信模板开关请求参数</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:43:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsTemplateModifyOpenFlagByIdRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;

	/**
	 * 开关标识
	 */
	@Schema(description = "开关标识")
	@NotNull
	private Boolean openFlag;
}