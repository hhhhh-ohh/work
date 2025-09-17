package com.wanmi.sbc.marketing.api.request.communitystatistics;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>社区团购活动统计信息表修改参数</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Length(max=32)
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	@Length(max=32)
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	@Length(max=32)
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
	@Max(9223372036854775807L)
	private Long payNum;

	/**
	 * 支付总额
	 */
	@Schema(description = "支付总额")
	private BigDecimal payTotal;

	/**
	 * 帮卖团长数
	 */
	@Schema(description = "帮卖团长数")
	@Max(9223372036854775807L)
	private Long assistNum;

	/**
	 * 成团团长数
	 */
	@Schema(description = "成团团长数")
	@Max(9223372036854775807L)
	private Long assistPayNum;

	/**
	 * 帮卖订单数
	 */
	@Schema(description = "帮卖订单数")
	@Max(9223372036854775807L)
	private Long assistOrderNum;

	/**
	 * 帮卖总额
	 */
	@Schema(description = "帮卖总额")
	private BigDecimal assistOrderTotal;

	/**
	 * 帮卖占比
	 */
	@Schema(description = "帮卖占比")
	private BigDecimal assistOrderRatio;

	/**
	 * 自提服务订单数
	 */
	@Schema(description = "自提服务订单数")
	private Long pickupServiceOrderNum;

	/**
	 * 服务订单金额
	 */
	@Schema(description = "服务订单金额")
	private BigDecimal pickupServiceOrderTotal;

	/**
	 * 退单数
	 */
	@Schema(description = "退单数")
	private Long returnNum;

	/**
	 * 退单总数
	 */
	@Schema(description = "退单总数")
	private BigDecimal returnTotal;

	/**
	 * 帮卖退单数
	 */
	@Schema(description = "帮卖退单数")
	private Long assistReturnNum;

	/**
	 * 帮卖退单总额
	 */
	@Schema(description = "帮卖退单总额")
	private BigDecimal assistReturnTotal;

	/**
	 * 已入账佣金
	 */
	@Schema(description = "已入账佣金")
	private BigDecimal commissionReceived;

	/**
	 * 已入账自提佣金
	 */
	@Schema(description = "已入账自提佣金")
	private BigDecimal commissionReceivedPickup;

	/**
	 * 已入账帮卖佣金
	 */
	@Schema(description = "已入账帮卖佣金")
	private BigDecimal commissionReceivedAssist;

	/**
	 * 未入账佣金
	 */
	@Schema(description = "未入账佣金")
	private BigDecimal commissionPending;

	/**
	 * 未入账自提佣金
	 */
	@Schema(description = "未入账自提佣金")
	private BigDecimal commissionPendingPickup;

	/**
	 * 未入账帮卖佣金
	 */
	@Schema(description = "未入账帮卖佣金")
	private BigDecimal commissionPendingAssist;

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

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate createDate;

}
