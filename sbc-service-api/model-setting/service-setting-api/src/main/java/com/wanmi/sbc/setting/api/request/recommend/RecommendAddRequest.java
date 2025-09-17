package com.wanmi.sbc.setting.api.request.recommend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>种草信息表新增参数</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * pageCode
	 */
	@Schema(description = "pageCode")
	@NotBlank
	private String pageCode;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	@Length(max=40)
	@NotBlank
	private String title;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	private Long cateId;

	/**
	 * 保存状态 1:草稿 2:已发布 3:修改已发布
	 */
	@Schema(description = "保存状态 1:草稿 2:已发布 3:修改已发布")
	private Integer saveStatus;

	/**
	 * 封面
	 */
	@NotBlank
	@Schema(description = "封面")
	private String coverImg;

	/**
	 * 视频
	 */
	@Schema(description = "视频")
	private String video;

	/**
	 * 转发数是否展示 1:是 0:否
	 */
	@Schema(description = "转发数是否展示 1:是 0:否")
	private Integer forwardType;

}