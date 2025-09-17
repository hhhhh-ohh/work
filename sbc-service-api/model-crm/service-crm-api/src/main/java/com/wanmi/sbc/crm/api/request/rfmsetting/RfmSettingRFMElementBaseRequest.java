package com.wanmi.sbc.crm.api.request.rfmsetting;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>rfm参数配置新增参数</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class RfmSettingRFMElementBaseRequest extends CrmBaseRequest {
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