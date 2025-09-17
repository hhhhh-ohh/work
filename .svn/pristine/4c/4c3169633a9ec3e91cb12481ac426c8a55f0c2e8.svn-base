package com.wanmi.sbc.setting.api.request.helpcenterarticle;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询帮助中心文章信息请求参数</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Schema(description = "主键Id")
	@NotNull
	private Long id;

}