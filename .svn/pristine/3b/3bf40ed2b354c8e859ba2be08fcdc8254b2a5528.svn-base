package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>种草信息表VO</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@Data
public class RecommendVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * pageCode
	 */
	@Schema(description = "pageCode")
	private String pageCode;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	private String title;

	/**
	 * 新标题
	 */
	@Schema(description = "新标题")
	private String newTitle;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long cateId;

	/**
	 * 新分类id
	 */
	@Schema(description = "新分类id")
	private Long newCateId;


	/**
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String cateName;

	/**
	 * 新分类名称
	 */
	@Schema(description = "新分类名称")
	private String newCateName;

	/**
	 * 封面
	 */
	@Schema(description = "封面")
	private String coverImg;

	/**
	 * 视频
	 */
	@Schema(description = "视频")
	private String video;

	/**
	 * 阅读数
	 */
	@Schema(description = "阅读数")
	private Long readNum;

	/**
	 * 点赞数
	 */
	@Schema(description = "点赞数")
	private Long fabulousNum;

	/**
	 * 转发数
	 */
	@Schema(description = "转发数")
	private Long forwardNum;

	/**
	 * 转发数是否展示 1:是 0:否
	 */
	@Schema(description = "转发数是否展示 1:是 0:否")
	private Integer forwardType;

	/**
	 * 访客数
	 */
	@Schema(description = "访客数")
	private Long visitorNum;

	/**
	 * 是否置顶 0:否 1:是
	 */
	@Schema(description = "是否置顶 0:否 1:是")
	private Integer isTop;

	/**
	 * 内容状态 0:隐藏 1:公开
	 */
	@Schema(description = "内容状态 0:隐藏 1:公开")
	private Integer status;

	/**
	 * 保存状态 1:草稿 2:已发布 3:修改已发布
	 */
	@Schema(description = "保存状态 1:草稿 2:已发布 3:修改已发布")
	private Integer saveStatus;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;
	/**
	 * 用户是否点赞
	 */
	@Schema(description = "用户是否点赞")
	private Boolean fabulousFlag;

}