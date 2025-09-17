package com.wanmi.sbc.setting.api.request.push;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询消息推送请求参数</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushConfigByIdRequest extends SettingBaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息推送配置编号
	 */
	@Schema(description = "消息推送配置编号")
	@NotNull
	private Long appPushId;
}