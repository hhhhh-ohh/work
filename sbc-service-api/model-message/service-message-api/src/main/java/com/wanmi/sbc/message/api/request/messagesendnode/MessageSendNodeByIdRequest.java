package com.wanmi.sbc.message.api.request.messagesendnode;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询站内信通知节点表请求参数</p>
 * @author xuyunpeng
 * @date 2020-01-09 11:45:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendNodeByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@NotNull
	private Long id;

}