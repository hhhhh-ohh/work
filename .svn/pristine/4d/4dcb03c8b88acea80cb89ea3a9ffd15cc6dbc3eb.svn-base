package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信分享配置VO</p>
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Schema
@Data
public class WechatShareSetVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信分享参数配置主键
	 */
	@Schema(description = "微信分享参数配置主键")
	private String shareSetId;

	/**
	 * 微信公众号App ID
	 */
	@Schema(description = "微信公众号App ID")
	private String shareAppId;

	/**
	 * 微信公众号 App Secret
	 */
	@Schema(description = "微信公众号 App Secret")
	private String shareAppSecret;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 操作人
	 */
	@Schema(description = "操作人")
	private String operatePerson;

}