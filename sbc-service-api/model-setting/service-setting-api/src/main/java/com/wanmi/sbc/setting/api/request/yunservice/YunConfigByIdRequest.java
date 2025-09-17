package com.wanmi.sbc.setting.api.request.yunservice;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询系统配置表请求参数</p>
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YunConfigByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 *  编号
	 */
	@Schema(description = " 编号")
	@NotNull
	private Long id;
}