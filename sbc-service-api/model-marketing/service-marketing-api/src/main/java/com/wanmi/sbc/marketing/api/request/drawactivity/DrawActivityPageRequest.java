package com.wanmi.sbc.marketing.api.request.drawactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.DrawActivityStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>抽奖活动表分页查询请求参数</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 搜索条件:开始时间
	 */
	@Schema(description = "搜索条件:开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 搜索条件:结束时间
	 */
	@Schema(description = "搜索条件:结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 目标客户
	 */
	@Schema(description = "目标客户")
	private String joinLevel;

	/**
	 * 查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
	 */
	@Schema(description = "查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束")
	private DrawActivityStatus queryTab;

	/**
	 * 不查暂停状态的 true 不查
	 */
	@Schema(description = "不查暂停状态的 true 不查 ")
	private Boolean notPausedFlag;

}