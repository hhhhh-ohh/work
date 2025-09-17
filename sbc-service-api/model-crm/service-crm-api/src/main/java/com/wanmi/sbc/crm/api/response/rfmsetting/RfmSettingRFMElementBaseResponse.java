package com.wanmi.sbc.crm.api.response.rfmsetting;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * <p>rfm参数配置新增参数</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@Data
public class RfmSettingRFMElementBaseResponse extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数
	 */
	@Schema(description = "参数")
	@NotNull
	@Max(9999999999L)
	private Integer param;

	/**
	 * 得分
	 */
	@Schema(description = "得分")
	@NotNull
	@Max(9999999999L)
	private Integer score;

}