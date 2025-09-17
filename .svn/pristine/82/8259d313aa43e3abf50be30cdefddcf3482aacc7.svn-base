package com.wanmi.sbc.crm.api.response.rfmsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.enums.Period;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>rfm参数配置新增参数</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RfmSettingResponse extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * R配置
	 */
	@Schema(description = "R分析配置")
	@NotEmpty
	@Valid
	private List<RfmSettingRFMElementBaseResponse> r;

	/**
	 * F配置
	 */
	@Schema(description = "F分析配置")
	@NotEmpty
	@Valid
	private List<RfmSettingRFMElementBaseResponse> f;

	/**
	 * M配置
	 */
	@Schema(description = "M分析配置")
	@NotEmpty
	@Valid
	private List<RfmSettingRFMElementBaseResponse> m;

	/**
	 * M配置
	 */
	@Schema(description = "统计范围")
	@NotNull
	private Period period;

}