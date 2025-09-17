package com.wanmi.sbc.empower.api.request.miniprogramset;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询小程序配置请求参数</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetByTypeRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 小程序类型 0 微信小程序
	 */
	@Schema(description = "小程序类型 0 微信小程序")
	@NotNull
	private Integer type = 0;

}