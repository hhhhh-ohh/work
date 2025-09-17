package com.wanmi.sbc.message.api.request.umengtoken;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.api.request.SmsBaseRequest;
import com.wanmi.sbc.message.bean.enums.PushPlatform;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>友盟推送设备与会员关系新增参数</p>
 * @author bob
 * @date 2020-01-06 11:36:26
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmengTokenAddRequest extends SmsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	@Length(max=32)
	private String customerId;

	/**
	 * 友盟推送会员设备token
	 */
	@Schema(description = "友盟推送会员设备token")
	@Length(max=64)
	@NotBlank
	private String devlceToken;

	/**
	 * 友盟推送会员设备token平台类型
	 */
	@Schema(description = "token平台类型")
	private PushPlatform platform;

	/**
	 * 绑定时间
	 */
	@Schema(description = "绑定时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindingTime;

}