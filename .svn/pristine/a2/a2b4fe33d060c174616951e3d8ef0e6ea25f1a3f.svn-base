package com.wanmi.sbc.customer.api.request.department;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>部门管理新增参数</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	@NotBlank
	@Length(max=20)
	private String departmentName;

	/**
	 * 公司ID
	 */
	@Schema(description = "公司ID")
	private Long companyInfoId;

	/**
	 * 层级
	 */
	@Schema(description = "层级")
	private Integer departmentGrade;

	/**
	 * 主管员工ID
	 */
	@Schema(description = "主管员工ID")
	@Length(max=32)
	private String employeeId;

	/**
	 * 员工名称
	 */
	@Schema(description = "员工名称")
	@Length(max=45)
	private String employeeName;

	/**
	 * 父部门id（上一级）
	 */
	@Schema(description = "父部门id（上一级）")
	private String parentDepartmentId;



	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}