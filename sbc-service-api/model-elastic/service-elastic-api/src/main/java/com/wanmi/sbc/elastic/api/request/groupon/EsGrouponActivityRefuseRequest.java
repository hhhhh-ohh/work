package com.wanmi.sbc.elastic.api.request.groupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>根据活动ID批量更新审核状态</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsGrouponActivityRefuseRequest extends BaseRequest {


	/**
	 * 活动ID
	 */
	@Schema(description = "审核拼团活动，grouponActivityId")
	@NotNull
	private String grouponActivityId;

	/**
	 * 分销拼团活动不通过原因
	 */
	@NotBlank
	@Schema(description = "拼团活动不通过原因")
	private String auditReason;
}