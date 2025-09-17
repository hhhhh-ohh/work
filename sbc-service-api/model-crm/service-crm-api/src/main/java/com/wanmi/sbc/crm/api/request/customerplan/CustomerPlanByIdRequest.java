package com.wanmi.sbc.crm.api.request.customerplan;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询 人群运营计划请求参数</p>
 * @author dyt
 * @date 2020-01-07 17:07:02
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanByIdRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 标识
	 */
	@Schema(description = "标识")
	@NotNull
	private Long id;
}