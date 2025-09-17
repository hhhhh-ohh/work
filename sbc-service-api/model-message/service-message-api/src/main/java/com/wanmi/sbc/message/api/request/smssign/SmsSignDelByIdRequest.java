package com.wanmi.sbc.message.api.request.smssign;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>单个删除短信签名请求参数</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:49:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignDelByIdRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;
}