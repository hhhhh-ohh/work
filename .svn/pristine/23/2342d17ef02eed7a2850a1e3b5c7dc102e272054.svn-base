package com.wanmi.sbc.setting.api.request.helpcenterarticlecate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>帮助中心文章信息修改参数</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleCateSortRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	@NotNull
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 分类排序
	 */
	@Schema(description = "分类排序")
	private Integer cateSort;


}
