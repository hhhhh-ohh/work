package com.wanmi.sbc.customer.api.request.department;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>部门管理通用查询请求参数</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<String> departmentIdList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String departmentId;

	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
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
	private String employeeId;

	/**
	 * 员工名称
	 */
	@Schema(description = "员工名称")
	private String employeeName;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer departmentSort;

	/**
	 * 父部门id（上一级）
	 */
	@Schema(description = "父部门id（上一级）")
	private String parentDepartmentId;

	/**
	 * 员工数
	 */
	@Schema(description = "员工数")
	private Integer employeeNum;

	/**
	 * 删除标记
	 */
	@Schema(description = "删除标记", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
	private Integer delFlag;


	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 父部门id集合（多级）
	 */
	@Schema(description = "父部门id集合（多级）")
	private String parentDepartmentIds;

	/**
	 * 归属部门/管理部门集合
	 */
	private List<String> departmentIsolationIdList;


}