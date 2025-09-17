package com.wanmi.sbc.setting.api.request.companyinfo;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

/**
 * <p>公司信息新增参数</p>
 * @author lq
 * @date 2019-11-05 16:09:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoAddRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	@Schema(description = "公司ID")
	@Max(9223372036854775807L)
	private Long companyInfoId;
}