package com.wanmi.sbc.message.api.request.umengtoken;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.bean.enums.PushPlatform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>友盟推送设备与会员关系列表查询请求参数</p>
 * @author bob
 * @date 2020-01-06 11:36:26
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmengTokenListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

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
	 * 搜索条件:绑定时间开始
	 */
	@Schema(description = "搜索条件:绑定时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindingTimeBegin;
	/**
	 * 搜索条件:绑定时间截止
	 */
	@Schema(description = "搜索条件:绑定时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime bindingTimeEnd;

}