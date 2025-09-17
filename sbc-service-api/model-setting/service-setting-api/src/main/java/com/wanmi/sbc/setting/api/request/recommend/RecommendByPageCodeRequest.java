package com.wanmi.sbc.setting.api.request.recommend;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>单个查询种草信息表请求参数</p>
 * @author xufeng
 * @date 2022-05-21 16:24:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendByPageCodeRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private String pageCode;

	/**
	 * 是否为Boss调用
	 */
	@Schema(description = "是否为Boss调用")
	private Boolean isBoss = false;

}