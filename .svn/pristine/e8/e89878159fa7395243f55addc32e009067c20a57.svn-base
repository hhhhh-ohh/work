package com.wanmi.sbc.setting.api.request.helpcenterarticle;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>帮助中心文章信息导出查询请求参数</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleExportRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键IdList
	 */
	@Schema(description = "批量查询-主键IdList")
	private List<Long> idList;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	private Long id;

	/**
	 * 文章标题
	 */
	@Schema(description = "文章标题")
	private String articleTitle;

	/**
	 * 文章分类id
	 */
	@Schema(description = "文章分类id")
	private Long articleCateId;

	/**
	 * 文章内容
	 */
	@Schema(description = "文章内容")
	private String articleContent;

	/**
	 * 文章状态，0:展示，1:隐藏
	 */
	@Schema(description = "文章状态，0:展示，1:隐藏")
	private Integer articleType;

	/**
	 * 查看次数
	 */
	@Schema(description = "查看次数")
	private Long viewNum;

	/**
	 * 解决次数
	 */
	@Schema(description = "解决次数")
	private Long solveNum;

	/**
	 * 未解决次数
	 */
	@Schema(description = "未解决次数")
	private Long unresolvedNum;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Schema(description = "删除标记  0：正常，1：删除")
	private DeleteFlag delFlag;

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

}
