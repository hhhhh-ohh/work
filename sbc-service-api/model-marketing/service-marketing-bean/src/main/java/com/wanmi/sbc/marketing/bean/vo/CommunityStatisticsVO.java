package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>社区团购活动统计信息表VO</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@Data
public class CommunityStatisticsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 团长会员id
	 */
	@Schema(description = "团长会员id")
	private String leaderCustomerId;

	/**
	 * 支付订单个数
	 */
	@Schema(description = "支付订单个数")
	private Long payNum = 0L;

	/**
	 * 支付总额
	 */
	@Schema(description = "支付总额")
	private BigDecimal payTotal = BigDecimal.ZERO;

	/**
	 * 帮卖团长数
	 */
	@Schema(description = "帮卖团长数")
	private Long assistNum = 0L;

	/**
	 * 成团团长数
	 */
	@Schema(description = "成团团长数")
	private Long assistPayNum = 0L;

	/**
	 * 帮卖订单数
	 */
	@Schema(description = "帮卖订单数")
	private Long assistOrderNum = 0L;

	/**
	 * 帮卖总额
	 */
	@Schema(description = "帮卖总额")
	private BigDecimal assistOrderTotal = BigDecimal.ZERO;

	/**
	 * 自提服务订单数
	 */
	@Schema(description = "自提服务订单数")
	private Long pickupServiceOrderNum= 0L;

	/**
	 * 服务订单金额
	 */
	@Schema(description = "服务订单金额")
	private BigDecimal pickupServiceOrderTotal = BigDecimal.ZERO;

	/**
	 * 帮卖占比
	 */
	@Schema(description = "帮卖占比")
	private BigDecimal assistOrderRatio = BigDecimal.ZERO;

	/**
	 * 退单数
	 */
	@Schema(description = "退单数")
	private Long returnNum = 0L;

	/**
	 * 退单总数
	 */
	@Schema(description = "退单总数")
	private BigDecimal returnTotal = BigDecimal.ZERO;

	/**
	 * 帮卖退单数
	 */
	@Schema(description = "帮卖退单数")
	private Long assistReturnNum = 0L;

	/**
	 * 帮卖退单总额
	 */
	@Schema(description = "帮卖退单总额")
	private BigDecimal assistReturnTotal = BigDecimal.ZERO;

	/**
	 * 已入账佣金
	 */
	@Schema(description = "已入账佣金")
	private BigDecimal commissionReceived = BigDecimal.ZERO;

	/**
	 * 已入账自提佣金
	 */
	@Schema(description = "已入账自提佣金")
	private BigDecimal commissionReceivedPickup = BigDecimal.ZERO;

	/**
	 * 已入账帮卖佣金
	 */
	@Schema(description = "已入账帮卖佣金")
	private BigDecimal commissionReceivedAssist = BigDecimal.ZERO;

	/**
	 * 未入账佣金
	 */
	@Schema(description = "未入账佣金")
	private BigDecimal commissionPending = BigDecimal.ZERO;

	/**
	 * 未入账自提佣金
	 */
	@Schema(description = "未入账自提佣金")
	private BigDecimal commissionPendingPickup = BigDecimal.ZERO;

	/**
	 * 未入账帮卖佣金
	 */
	@Schema(description = "未入账帮卖佣金")
	private BigDecimal commissionPendingAssist = BigDecimal.ZERO;

	/**
	 * 参团人数
	 */
	@Schema(description = "参团人数")
	private Long customerNum = 0L;

	/**
	 * 发展人数
	 */
	@Schema(description = "发展人数")
	private Long customerAddNum = 0L;

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
	 * 退款佣金
	 */
	@Schema(description = "退款佣金")
	private BigDecimal returnTradeCommission;

	/**
	 * 退款佣金帮卖
	 */
	@Schema(description = "退款佣金帮卖")
	private BigDecimal returnTradeCommissionAssist;

	/**
	 * 退款佣金自提
	 */
	@Schema(description = "退款佣金自提")
	private BigDecimal returnTradeCommissionPickup;
}