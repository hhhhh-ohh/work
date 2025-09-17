package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.DrawFromType;
import com.wanmi.sbc.marketing.bean.enums.DrawTimesType;
import com.wanmi.sbc.marketing.bean.enums.DrawType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>C端抽奖活动详情</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
public class DrawDetail implements Serializable {
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
	 * 活动规则说明
	 */
	@Schema(description = "活动规则说明")
	private String activityContent;

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
	 * 当前用户剩余抽奖次数
	 */
	@Schema(description = "当前用户剩余抽奖次数")
	private Long leftDrawCount = 0L;

	/**
	 * 当前用户可用积分
	 */
	@Schema(description = "当前用户可用积分")
	private Long leftPointsAvailable;

	/**
	 * 客户等级
	 */
	@Schema(description = "客户等级")
	private String joinLevel;

	/**
	 * 活动奖品集合
	 */
	@Schema(description = "活动奖品集合")
	private List<WebPrizeVO> prizeVOList;


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
	 * 是否暂停 0进行 1暂停
	 */
	@Schema(description = "是否暂停")
	private Integer pauseFlag;


}