package com.wanmi.sbc.crm.bean.dto;

import com.wanmi.sbc.crm.bean.enums.TagType;
import com.wanmi.sbc.crm.bean.vo.TagParamVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>自动标签VO</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@Data
public class AutoTagDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签组id
	 */
	@Schema(description = "id")
	private Long tagId;

	/**
	 * 自动标签名称
	 */
	@Schema(description = "自动标签名称")
	private String name;


	/**
	 * 标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签
	 */
	@Schema(description = "标签类型，0：偏好标签组，1：指标值标签，2：指标值范围标签，3、综合类标签")
	private TagType type;

	@Schema(description = "选中状态")
	private Boolean ifChecked = Boolean.TRUE;

	/**
	 * 选中的标签值
	 */
	@Schema(description = "选中的标签值")
	private List<String> chooseTags;

	/**
	 * 偏好类标签纬度名称
	 */
	@Schema(description = "偏好类标签纬度名称")
	private String columnName;

	/**
	 * 字段类型
	 */
	@Schema(description = "字段类型 0:input输入 1：范围输入 2:下拉选择")
	private Integer columnType;

	/**
	 * 偏好标签选项值
	 */
	@Schema(description = "偏好标签选项值")
	private List<?> dataSource;

}