package com.wanmi.sbc.setting.api.request.weibologinset;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除微信登录配置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:17:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeiboLoginSetDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * weiboSetId
	 */
	@Schema(description = "weiboSetId")
	@NotNull
	private String weiboSetId;
}