package com.wanmi.sbc.message.api.request.messagesend;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * <p>站内信任务表分页查询请求参数</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:12:11
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	@Schema(description = "任务名称")
	private String name;

	/**
	 * 搜索条件:发送时间截止
	 */
	@Schema(description = "发送时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTimeEnd;

	@Schema(description = "是否是发送消息")
	private Boolean isAppMessageSendFlag;

}