package com.wanmi.sbc.setting.api.request.operatedatalog;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除系统日志请求参数</p>
 * @author guanfl
 * @date 2020-04-21 14:57:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateDataLogDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@Schema(description = "自增主键")
	@NotNull
	private Long id;
}