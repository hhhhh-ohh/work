package com.wanmi.sbc.setting.api.request.appexternalconfig;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>AppExternalConfig新增参数</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalConfigAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 小程序名称
	 */
	@Schema(description = "小程序名称")
	@NotBlank
	private String appName;

	/**
	 * 小程序appId
	 */
	@Schema(description = "小程序appId")
	@NotBlank
	private String appId;

	/**
	 * 原始Id
	 */
	@Schema(description = "原始Id")
	@NotBlank
	private String originalId;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	@Override
	public void checkParam() {
		if (appId.length() != Constants.NUM_18){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (originalId.length() != Constants.NUM_15){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}