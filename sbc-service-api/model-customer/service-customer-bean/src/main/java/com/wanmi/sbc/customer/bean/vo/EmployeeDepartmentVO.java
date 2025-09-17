package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>员工与部门关联VO</p>
 * @author wanggang
 * @date 2020-02-26 19:33:10
 */
@Schema
@Data
public class EmployeeDepartmentVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 员工id
	 */
	@Schema(description = "员工id")
	private String employeeId;

	/**
	 * 部门id
	 */
	@Schema(description = "部门id")
	private String departmentId;

	/**
	 * 是否主管，0：否，1：是
	 */
	@Schema(description = "是否主管，0：否，1：是")
	private Integer isLeader;

}