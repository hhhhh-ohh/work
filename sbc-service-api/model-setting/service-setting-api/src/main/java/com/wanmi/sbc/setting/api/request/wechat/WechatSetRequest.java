package com.wanmi.sbc.setting.api.request.wechat;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.TerminalType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>微信设置参数</p>
 * @author dyt
 * @date 2020-11-05 16:15:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatSetRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Schema(description = "终端: 0 pc 1 h5 2 app 3 mini", required = true)
	private TerminalType terminalType;

	@NotNull
	@Schema(description = "是否启用 0 不启用， 1 启用", required = true)
	private DefaultFlag status;

	@Schema(description = "App ID")
	private String appId;

	@Schema(description = "App Secret")
	private String secret;

	/**
	 * 统一参数校验入口
	 */
	@Override
	public void checkParam(){
		if (status == DefaultFlag.NO){
			return;
		}

		if (StringUtils.isBlank(appId) || StringUtils.isBlank(secret)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}


}