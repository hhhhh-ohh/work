package com.wanmi.sbc.setting.api.request.weibologinset;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>微信登录配置通用查询请求参数</p>
 * @author lq
 * @date 2019-11-05 16:17:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeiboLoginSetQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-weiboSetIdList
	 */
	@Schema(description = "批量查询-weiboSetIdList")
	private List<String> weiboSetIdList;

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
	 * 搜索条件:createTime开始
	 */
	@Schema(description = "搜索条件:createTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:createTime截止
	 */
	@Schema(description = "搜索条件:createTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:updateTime开始
	 */
	@Schema(description = "搜索条件:updateTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:updateTime截止
	 */
	@Schema(description = "搜索条件:updateTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * operatePerson
	 */
	@Schema(description = "operatePerson")
	private String operatePerson;

}