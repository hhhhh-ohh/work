package com.wanmi.sbc.setting.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>笔记分类表DTO</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@Data
public class RecommendCateDTO implements Serializable {

	private static final long serialVersionUID = -6514381083415838508L;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long cateId;

	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String cateName;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer cateSort;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String delPerson;

}