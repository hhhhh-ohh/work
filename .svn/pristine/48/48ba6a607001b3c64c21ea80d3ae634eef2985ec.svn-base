package com.wanmi.sbc.setting.api.request.companyinfo;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除公司信息请求参数</p>
 * @author lq
 * @date 2019-11-05 16:09:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoDelByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	@NotNull
	private Long companyInfoId;
}