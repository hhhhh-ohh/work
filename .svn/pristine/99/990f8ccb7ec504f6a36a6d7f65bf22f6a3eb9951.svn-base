package com.wanmi.sbc.setting.api.request.appexternalconfig;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询AppExternalConfig请求参数</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalConfigByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private Long id;

}