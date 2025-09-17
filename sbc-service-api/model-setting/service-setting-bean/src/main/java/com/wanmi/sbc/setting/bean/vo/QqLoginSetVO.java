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
 * <p>qq登录信息VO</p>
 * @author lq
 * @date 2019-11-05 16:11:28
 */
@Schema
@Data
public class QqLoginSetVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * qqSetId
	 */
	@Schema(description = "qqSetId")
	private String qqSetId;

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