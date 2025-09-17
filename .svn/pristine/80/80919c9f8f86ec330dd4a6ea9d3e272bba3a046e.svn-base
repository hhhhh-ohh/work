package com.wanmi.sbc.setting.bean.vo;

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
 * <p>微信登录配置VO</p>
 * @author lq
 * @date 2019-11-05 16:17:06
 */
@Schema
@Data
public class WeiboLoginSetVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * weiboSetId
	 */
	@Schema(description = "weiboSetId")
	private String weiboSetId;

	/**
	 * mobileServerStatus
	 */
	@Schema(description = "mobileServerStatus")
	private Integer mobileServerStatus;

	/**
	 * mobileAppId
	 */
	@Schema(description = "mobileAppId")
	private String mobileAppId;

	/**
	 * mobileAppSecret
	 */
	@Schema(description = "mobileAppSecret")
	private String mobileAppSecret;

	/**
	 * pcServerStatus
	 */
	@Schema(description = "pcServerStatus")
	private Integer pcServerStatus;

	/**
	 * pcAppId
	 */
	@Schema(description = "pcAppId")
	private String pcAppId;

	/**
	 * pcAppSecret
	 */
	@Schema(description = "pcAppSecret")
	private String pcAppSecret;

	/**
	 * appServerStatus
	 */
	@Schema(description = "appServerStatus")
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
	private String operatePerson;

}