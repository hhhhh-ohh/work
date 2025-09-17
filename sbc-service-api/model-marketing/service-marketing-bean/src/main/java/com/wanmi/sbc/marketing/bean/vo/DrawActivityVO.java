package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>抽奖活动表VO</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
public class DrawActivityVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 抽奖形式（0：九宫格，1：大转盘）
	 */
	@Schema(description = "抽奖形式（0：九宫格，1：大转盘）")
	@NotNull
	private DrawFromType formType;

	/**
	 * 抽奖类型（0：无限制，1：积分）
	 */
	@Schema(description = "抽奖类型（0：无限制，1：积分）")
	@NotNull
	private DrawType drawType;

	/**
	 * 消耗积分
	 */
	@Schema(description = "消耗积分 当drawType为1时有值")
	@NotNull
	private Long consumePoints;

	/**
	 * 抽奖次数限制类型（0：每日，1：每周 2：每月 3：每人）
	 */
	@Schema(description = "抽奖次数限制类型（0：每日，1：每周 2：每月 3：每人）")
	private DrawTimesType drawTimesType;

	/**
	 * 抽奖次数，默认为0
	 */
	@Schema(description = "抽奖次数，默认为0")
	private Integer drawTimes;

	/**
	 * 中奖次数限制类型 （0：无限制，1：每人每天 2：每人每次）
	 */
	@Schema(description = "中奖次数限制类型 （0：无限制，1：每人每天 2：每人每次）")
	private DrawWinTimesType winTimesType;

	/**
	 * 每人每天最多中奖次数，默认为0
	 */
	@Schema(description = "每人每天最多中奖次数，默认为0")
	private Integer winTimes;

	/**
	 * 客户等级
	 */
	@Schema(description = "客户等级")
	private String joinLevel;

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
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

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

	/**
	 * 查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束
	 */
	@Schema(description = "查询类型，0：全部，1：进行中，2：暂停中，3：未开始，4：已结束")
	private DrawActivityStatus drawActivityStatus;

	/**
	 * 获取抽奖活动状态
	 *
	 * @return
	 */
	public DrawActivityStatus getDrawActivityStatus() {
		if (startTime != null && endTime != null && null != pauseFlag) {
			if (LocalDateTime.now().isBefore(startTime)) {
				return DrawActivityStatus.NOT_START;
			} else if (LocalDateTime.now().isAfter(endTime)) {
				return DrawActivityStatus.ENDED;
			} else if (pauseFlag == 1) {
				return DrawActivityStatus.PAUSED;
			} else {
				return DrawActivityStatus.STARTED;
			}
		}
		return null;
	}

}