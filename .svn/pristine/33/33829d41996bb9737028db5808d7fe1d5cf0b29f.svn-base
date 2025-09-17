package com.wanmi.sbc.marketing.api.request.drawactivity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.DrawTimesType;
import com.wanmi.sbc.marketing.bean.enums.DrawWinTimesType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>抽奖活动表列表查询请求参数</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 搜索条件:开始时间开始
	 */
	@Schema(description = "搜索条件:开始时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeBegin;
	/**
	 * 搜索条件:开始时间截止
	 */
	@Schema(description = "搜索条件:开始时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTimeEnd;

	/**
	 * 搜索条件:结束时间开始
	 */
	@Schema(description = "搜索条件:结束时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeBegin;
	/**
	 * 搜索条件:结束时间截止
	 */
	@Schema(description = "搜索条件:结束时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTimeEnd;

	/**
	 * 抽奖次数限制类型（0：每日，1：每人）
	 */
	@Schema(description = "抽奖次数限制类型（0：每日，1：每人）")
	private DrawTimesType drawTimesType;

	/**
	 * 抽奖次数，默认为0
	 */
	@Schema(description = "抽奖次数，默认为0")
	private Integer drawTimes;

	/**
	 * 中奖次数限制类型 （0：无限制，1：每人每天）
	 */
	@Schema(description = "中奖次数限制类型 （0：无限制，1：每人每天）")
	private DrawWinTimesType winTimesType;

	/**
	 * 每人每天最多中奖次数，默认为0
	 */
	@Schema(description = "每人每天最多中奖次数，默认为0")
	private Integer winTimes;

	/**
	 * 未中奖提示
	 */
	@Schema(description = "未中奖提示")
	private String notAwardTip;

	/**
	 * 抽奖次数上限提示
	 */
	@Schema(description = "抽奖次数上限提示")
	private String maxAwardTip;

	/**
	 * 活动规则说明
	 */
	@Schema(description = "活动规则说明")
	private String activityContent;

	/**
	 * 实际抽奖人/次
	 */
	@Schema(description = "实际抽奖人/次")
	private Long drawCount;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 实际中奖人/次
	 */
	@Schema(description = "实际中奖人/次")
	private Long awardCount;

	/**
	 * 是否暂停 0进行 1暂停
	 */
	@Schema(description = "是否暂停 0进行 1暂停")
	private Integer pauseFlag;

}