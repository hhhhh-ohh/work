package com.wanmi.sbc.empower.api.response.wechatshareset;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>微信分享配置VO</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatShareSetInfoResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信公众号App ID
	 */
	@Schema(description = "微信公众号App ID")
	private String shareAppId;

	/**
	 * 微信公众号 App Secret
	 */
	@Schema(description = "微信公众号 App Secret")
	private String shareAppSecret;

}