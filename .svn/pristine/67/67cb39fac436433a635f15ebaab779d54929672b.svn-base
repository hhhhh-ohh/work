package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信发送VO</p>
 * @author zgl
 * @date 2019-12-03 15:43:37
 */
@Schema
@Data
public class SmsSendDetailVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 发送任务id
	 */
	@Schema(description = "发送任务id")
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
	private String bizId;

	/**
	 * 状态（0-失败，1-成功）
	 */
	@Schema(description = "状态（0-失败，1-成功）")
	private Integer status;

	/**
	 * 请求状态码。
	 */
	@Schema(description = "请求状态码。")
	private String code;

	/**
	 * 任务执行信息
	 */
	@Schema(description = "任务执行信息")
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