package com.wanmi.sbc.crm.api.request.customerplanconversion;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询运营计划转化效果请求参数</p>
 * @author zhangwenchang
 * @date 2020-02-12 00:16:50
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanConversionByPlanIdRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 运营计划id
	 */
	@Schema(description = "运营计划id")
	@NotNull
	private Long planId;
}