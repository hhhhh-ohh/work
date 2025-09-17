package com.wanmi.sbc.setting.api.request.weibologinset;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>微信登录配置新增参数</p>
 * @author lq
 * @date 2019-11-05 16:17:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeiboLoginSetAddRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * mobileServerStatus
	 */
	@Schema(description = "mobileServerStatus")
	@Max(127)
	private Integer mobileServerStatus;

	/**
	 * mobileAppId
	 */
	@Schema(description = "mobileAppId")
	@Length(max=60)
	private String mobileAppId;

	/**
	 * mobileAppSecret
	 */
	@Schema(description = "mobileAppSecret")
	@Length(max=60)
	private String mobileAppSecret;

	/**
	 * pcServerStatus
	 */
	@Schema(description = "pcServerStatus")
	@Max(127)
	private Integer pcServerStatus;

	/**
	 * pcAppId
	 */
	@Schema(description = "pcAppId")
	@Length(max=60)
	private String pcAppId;

	/**
	 * pcAppSecret
	 */
	@Schema(description = "pcAppSecret")
	@Length(max=60)
	private String pcAppSecret;

	/**
	 * appServerStatus
	 */
	@Schema(description = "appServerStatus")
	@Max(127)
	private Integer appServerStatus;

	/**
	 * createTime
	 */
	@Schema(description = "createTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * updateTime
	 */
	@Schema(description = "updateTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * operatePerson
	 */
	@Schema(description = "operatePerson")
	@Length(max=45)
	private String operatePerson;

}