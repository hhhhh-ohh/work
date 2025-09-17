package com.wanmi.sbc.empower.api.response.wechatloginset;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>微信授权登录配置VO</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginSetResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * h5-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "h5-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag mobileServerStatus;

	/**
	 * h5-AppID(应用ID)
	 */
	@Schema(description = "h5-AppID(应用ID)")
	private String mobileAppId;

	/**
	 * h5-AppSecret(应用密钥)
	 */
	@Schema(description = "h5-AppSecret(应用密钥)")
	private String mobileAppSecret;

	/**
	 * pc-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "pc-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag pcServerStatus;

	/**
	 * pc-AppID(应用ID)
	 */
	@Schema(description = "pc-AppID(应用ID)")
	private String pcAppId;

	/**
	 * pc-AppSecret(应用密钥)
	 */
	@Schema(description = "pc-AppSecret(应用密钥)")
	private String pcAppSecret;

	/**
	 * app-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "app-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag appServerStatus;

}