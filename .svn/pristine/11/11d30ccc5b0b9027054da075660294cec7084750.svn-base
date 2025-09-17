package com.wanmi.sbc.message.api.request.smssign;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询短信签名请求参数</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:49:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignByIdRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;
}