package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.PushPlatform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>友盟推送设备与会员关系VO</p>
 * @author bob
 * @date 2020-01-06 11:36:26
 */
@Schema
@Data
public class UmengTokenVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 会员ID
	 */
	@Schema(description = "会员ID")
	private String customerId;

	/**
	 * 友盟推送会员设备token
	 */
	@Schema(description = "友盟推送会员设备token")
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