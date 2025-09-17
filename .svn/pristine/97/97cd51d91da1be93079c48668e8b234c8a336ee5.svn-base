package com.wanmi.sbc.setting.api.request.expresscompany;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询物流公司请求参数</p>
 * @author lq
 * @date 2019-11-05 16:10:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID,自增
	 */
	@Schema(description = "主键ID,自增")
	@NotNull
	private Long expressCompanyId;
}