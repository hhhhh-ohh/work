package com.wanmi.sbc.message.api.request.appmessage;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>App站内信消息发送表分页查询请求参数</p>
 * @author xuyunpeng
 * @date 2020-01-06 10:53:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppMessagePageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息一级类型：0优惠促销、1服务通知
	 */
	@Schema(description = "消息一级类型：0优惠促销、1服务通知")
	private Integer messageType;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	@NotNull
	private String customerId;

}