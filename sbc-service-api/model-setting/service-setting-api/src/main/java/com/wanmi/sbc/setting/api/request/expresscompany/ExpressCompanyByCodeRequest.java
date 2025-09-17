package com.wanmi.sbc.setting.api.request.expresscompany;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询物流公司请求参数</p>
 * @author wur
 * @date 2021/4/15 13:46
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyByCodeRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流公司代码
	 */
	@Schema(description = "物流公司代码")
	@NotNull
	private String expressCode;
}