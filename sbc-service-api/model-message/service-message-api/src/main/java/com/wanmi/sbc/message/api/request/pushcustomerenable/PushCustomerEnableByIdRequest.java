package com.wanmi.sbc.message.api.request.pushcustomerenable;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询会员推送通知开关请求参数</p>
 * @author Bob
 * @date 2020-01-07 15:31:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushCustomerEnableByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	@NotNull
	private String customerId;

}