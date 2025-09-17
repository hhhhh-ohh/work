package com.wanmi.sbc.empower.api.request.wechatloginset;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询微信授权登录配置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginSetByIdRequest extends EmpowerBaseRequest {

	private static final long serialVersionUID = -7744197866488126420L;
	/**
	 * 微信授权登录配置主键
	 */
	@Schema(description = "微信授权登录配置主键")
	@NotNull
	private String wechatSetId;
}