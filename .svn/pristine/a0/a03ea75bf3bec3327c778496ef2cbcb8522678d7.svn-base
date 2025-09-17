package com.wanmi.sbc.setting.api.response.wechat;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>微信设置响应结果</p>
 * @author dyt
 * @date 2020-11-05 16:15:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatSetResponse extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * h5-是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "h5-是否启用 0 不启用， 1 启用")
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
	 * pc-是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "pc-是否启用 0 不启用， 1 启用")
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
	 * 小程序-是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "小程序-是否启用 0 不启用， 1 启用")
	private DefaultFlag miniProgramServerStatus;

	/**
	 * 小程序-AppID(应用ID)
	 */
	@Schema(description = "小程序-AppID(应用ID)")
	private String miniProgramAppAppId;

	/**
	 * 小程序-AppSecret(应用密钥)
	 */
	@Schema(description = "小程序-AppSecret(应用密钥)")
	private String miniProgramAppSecret;

	/**
	 * app-是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "app-是否启用 0 不启用， 1 启用")
	private DefaultFlag appServerStatus;

	/**
	 * app-AppID(应用ID)
	 */
	@Schema(description = "app-AppID(应用ID)")
	private String appAppId;

	/**
	 * app-AppSecret(应用密钥)
	 */
	@Schema(description = "app-AppSecret(应用密钥)")
	private String appAppSecret;

}