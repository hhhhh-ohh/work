package com.wanmi.sbc.setting.api.request.businessconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除招商页设置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:09:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessConfigDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 招商页设置主键
	 */
	@Schema(description = "招商页设置主键")
	@NotNull
	private Integer businessConfigId;
}