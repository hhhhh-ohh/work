package com.wanmi.sbc.setting.api.request.recommend;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>种草信息表导出查询请求参数</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendExportRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	private String title;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long cateId;

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
	 * 视频封面类型 1;原视频封面 2:自定义封面
	 */
	@Schema(description = "视频封面类型 1;原视频封面 2:自定义封面")
	private Integer videoCoverType;

	/**
	 * 视频封面图片
	 */
	@Schema(description = "视频封面图片")
	private String videoCoverImg;

	/**
	 * 发布时间是否展示 1:是 0:否
	 */
	@Schema(description = "发布时间是否展示 1:是 0:否")
	private Integer createTimeType;

	/**
	 * 阅读数是否展示 1:是 0:否
	 */
	@Schema(description = "阅读数是否展示 1:是 0:否")
	private Integer readType;

	/**
	 * 阅读数
	 */
	@Schema(description = "阅读数")
	private Long readNum;

	/**
	 * 点赞数是否展示 1:是 0:否
	 */
	@Schema(description = "点赞数是否展示 1:是 0:否")
	private Integer fabulousType;

	/**
	 * 点赞数
	 */
	@Schema(description = "点赞数")
	private Long fabulousNum;

	/**
	 * 转发数是否展示 1:是 0:否
	 */
	@Schema(description = "转发数是否展示 1:是 0:否")
	private Integer forwardType;

	/**
	 * 转发数
	 */
	@Schema(description = "转发数")
	private Long forwardNum;

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
	 * 搜索条件:置顶时间开始
	 */
	@Schema(description = "搜索条件:置顶时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime topTimeBegin;
	/**
	 * 搜索条件:置顶时间截止
	 */
	@Schema(description = "搜索条件:置顶时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime topTimeEnd;

	/**
	 * 内容状态 0:隐藏 1:公开
	 */
	@Schema(description = "内容状态 0:隐藏 1:公开")
	private Integer status;

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
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 搜索条件:删除时间开始
	 */
	@Schema(description = "搜索条件:删除时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeBegin;
	/**
	 * 搜索条件:删除时间截止
	 */
	@Schema(description = "搜索条件:删除时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeEnd;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

}
