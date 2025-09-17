package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>帮助中心文章信息VO</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Schema
@Data
public class HelpCenterArticleCateVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	private Long id;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String cateName;

	/**
	 * 分类排序
	 */
	@Schema(description = "分类排序")
	private Integer cateSort;

	/**
	 * 是否默认分类 0:否，1：是
	 */
	@Schema(description = "是否默认分类 0:否，1：是")
	private DefaultFlag defaultCate;

}