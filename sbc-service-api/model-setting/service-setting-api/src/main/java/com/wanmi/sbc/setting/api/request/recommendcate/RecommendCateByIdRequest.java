package com.wanmi.sbc.setting.api.request.recommendcate;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>单个查询笔记分类表请求参数</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateByIdRequest extends BaseRequest {

	private static final long serialVersionUID = 3501932533361744463L;

	/**
	 * 分类id
	 */
	@Schema(description = "分类id")
	@NotNull
	private Long cateId;

}