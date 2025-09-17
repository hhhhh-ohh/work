package com.wanmi.sbc.empower.api.request.wechatloginset;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 查询微信授权登录配置请求参数
 * @author zhanghao
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatServiceStatusByStoreIdRequest extends EmpowerBaseRequest {

	private static final long serialVersionUID = -2319283427338504573L;
	@Schema(description = "门店id")
	@NotNull
	private Long storeId;
}