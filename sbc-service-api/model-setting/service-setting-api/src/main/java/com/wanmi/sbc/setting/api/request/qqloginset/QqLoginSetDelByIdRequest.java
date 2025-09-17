package com.wanmi.sbc.setting.api.request.qqloginset;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除qq登录信息请求参数</p>
 * @author lq
 * @date 2019-11-05 16:11:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QqLoginSetDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * qqSetId
	 */
	@Schema(description = "qqSetId")
	@NotNull
	private String qqSetId;
}