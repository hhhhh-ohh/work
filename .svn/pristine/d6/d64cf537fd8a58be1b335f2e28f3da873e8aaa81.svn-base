package com.wanmi.sbc.message.api.request.smssenddetail;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>短信发送修改参数</p>
 * @author zgl
 * @date 2019-12-03 15:43:37
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendDetailModifyRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 发送任务id
	 */
	@Schema(description = "发送任务id")
	@Max(9223372036854775807L)
	private Long sendId;

	/**
	 * 接收短信的号码
	 */
	@Schema(description = "接收短信的号码")
	private String phoneNumbers;

	/**
	 * 回执id
	 */
	@Schema(description = "回执id")
	@Length(max=64)
	private String bizId;

	/**
	 * 状态（0-失败，1-成功）
	 */
	@Schema(description = "状态（0-失败，1-成功）")
	@Max(127)
	private Integer status;

	/**
	 * 请求状态码。
	 */
	@Schema(description = "请求状态码。")
	@Length(max=255)
	private String code;

	/**
	 * 任务执行信息
	 */
	@Schema(description = "任务执行信息")
	@Length(max=2000)
	private String message;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * sendTime
	 */
	@Schema(description = "sendTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

}