package com.wanmi.sbc.customer.api.request.department;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * <p>批量删除部门管理请求参数</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentModifyLeaderRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	@NotBlank
	private String departmentId;

	/**
	 * 主管ID
	 */
	@Schema(description = "主管ID")
	private String oldEmployeeId;

	/**
	 * 新主管ID
	 */
	@Schema(description = "新主管ID")
	@NotBlank
	private String newEmployeeId;

	@Schema(description = "公司id", hidden = true)
	private Long companyInfoId;
}
