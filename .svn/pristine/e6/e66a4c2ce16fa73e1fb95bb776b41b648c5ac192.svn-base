package com.wanmi.sbc.marketing.api.request.communitystatistics;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>社区团购活动统计信息表通用查询请求参数</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

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
	 * 批量活动id
	 */
	@Schema(description = "批量活动id")
	private List<String> activityIds;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

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
	 * 批量-团长id
	 */
	@Schema(description = "批量-团长id")
	private List<String> leaderIds;

	/**
	 * 支付订单个数
	 */
	@Schema(description = "支付订单个数")
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
	private Long assistNum;

	/**
	 * 成团团长数
	 */
	@Schema(description = "成团团长数")
	private Long assistPayNum;

	/**
	 * 帮卖订单数
	 */
	@Schema(description = "帮卖订单数")
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

}