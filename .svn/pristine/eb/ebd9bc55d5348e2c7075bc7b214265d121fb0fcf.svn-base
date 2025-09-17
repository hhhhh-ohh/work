package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>部门管理VO</p>
 * @author wanggang
 * @date 2020-02-26 19:02:40
 */
@Schema
@Data
public class DepartmentVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 父部门id集合（多级）
	 */
	@Schema(description = "父部门id集合（多级）")
	private String parentDepartmentIds;

	/**
	 * 删除标志 0未删除 1已删除
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	private String createPerson;


	/**
	 * 创建时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 部门所包含员工Id集合
	 */
	@Schema(description = "部门所包含员工Id集合")
	private List<String> allEmployeeIds;

	private String updatePerson;

}