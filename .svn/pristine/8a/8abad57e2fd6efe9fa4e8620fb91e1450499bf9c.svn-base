package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信授权登录配置VO</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@Schema
@Data
public class WechatLoginSetVO implements Serializable {
	private static final long serialVersionUID = 1313204583256295449L;
	/**
	 * 微信授权登录配置主键
	 */
	@Schema(description = "微信授权登录配置主键")
	private String wechatSetId;

	/**
	 * h5-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "h5-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag mobileServerStatus;

	/**
	 * h5-AppID(应用ID)
	 */
	@Schema(description = "h5-AppID(应用ID)")
	private String mobileAppId;

	/**
	 * h5-AppSecret(应用密钥)
	 */
	@Schema(description = "h5-AppSecret(应用密钥)")
	private String mobileAppSecret;

	/**
	 * pc-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "pc-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag pcServerStatus;

	/**
	 * pc-AppID(应用ID)
	 */
	@Schema(description = "pc-AppID(应用ID)")
	private String pcAppId;

	/**
	 * pc-AppSecret(应用密钥)
	 */
	@Schema(description = "pc-AppSecret(应用密钥)")
	private String pcAppSecret;

	/**
	 * app-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "app-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag appServerStatus;

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