package com.wanmi.sbc.customer.api.request.enterpriseinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>企业信息表根据会员id批量查询请求参数</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoListByCustomerIdsRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-会员IdList
	 */
	@NotNull
	@Schema(description = "批量查询-会员IdList")
	private List<String> customerIdList;

}