package com.wanmi.sbc.customer.api.request.department;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>部门管理列表查询请求参数</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司ID
	 */
	@Schema(description = "公司ID")
	private Long companyInfoId;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer departmentSort;

	/**
	 * 删除标记
	 */
	@Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
	private Integer delFlag;

	/**
	 * 归属部门/管理部门集合
	 */
	private List<String> departmentIsolationIdList;

	/**
	 * 管理部门id列表(数据隔离)
	 */
	@Schema(description = "管理部门列表")
	private List<String> manageDepartmentIdList;

	/**
	 * 所属部门id列表(数据隔离)
	 */
	@Schema(description = "所属部门列表")
	private List<String> belongToDepartmentIdList;

	/**
	 * 是否有归属部门
	 */
	private Boolean belongToDepartment = Boolean.TRUE;

	private String employeeId;

	/**
	 * 是否是主账号 0:否，1：是
	 */
	private Integer isMaster = 0;

	/**
	 * 管理部门集合
	 */
	private String manageDepartmentIds;

	/**
	 * 门店公司ID
	 */
	@Schema(description = "门店公司ID")
	private Long o2oCompanyInfoId;
}