package com.wanmi.sbc.message.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.SendDetailStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>短信发送实体类</p>
 * @author zgl
 * @date 2019-12-03 15:43:37
 */
@Data
public class SmsSendDetailDTO implements Serializable {
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
	private SendDetailStatus status;

	/**
	 * 请求状态码。
	 */
    @Schema(description = "请求状态码")
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
    @Schema(description = "发送时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime sendTime;

    @Schema(description = "签名名称")
	private String signName;

    @Schema(description = "模板code")
	private String templateCode;

    /**
     * 模板内容JSON
     */
    @Schema(description = "模板内容JSON")
	private String templateParam;

    /**
     * 业务类型  参照com.wanmi.sbc.customer.bean.enums.SmsTemplate
     */
    @Schema(description = "业务类型")
	private String businessType;

    @Schema(description = "签名id")
	private Long signId;

}