package com.wanmi.sbc.empower.api.request.wechatshareset;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询微信分享配置请求参数</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatShareSetByIdRequest extends EmpowerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信分享参数配置主键
	 */
	@Schema(description = "微信分享参数配置主键")
	@NotNull
	private String shareSetId;
}