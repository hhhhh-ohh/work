package com.wanmi.sbc.marketing.communitystatistics.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购活动统计信息表实体类</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Data
@Entity
@Table(name = "community_statistics")
public class CommunityStatistics implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Column(name = "activity_id")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 团长id
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 团长会员id
	 */
	@Column(name = "leader_customer_id")
	private String leaderCustomerId;

	/**
	 * 支付订单个数
	 */
	@Column(name = "pay_num")
	private Long payNum;

	/**
	 * 支付总额
	 */
	@Column(name = "pay_total")
	private BigDecimal payTotal;

	/**
	 * 帮卖团长数
	 */
	@Column(name = "assist_num")
	private Long assistNum;

	/**
	 * 成团团长数
	 */
	@Column(name = "assist_pay_num")
	private Long assistPayNum;

	/**
	 * 帮卖订单数
	 */
	@Column(name = "assist_order_num")
	private Long assistOrderNum;

	/**
	 * 帮卖总额
	 */
	@Column(name = "assist_order_total")
	private BigDecimal assistOrderTotal;

	/**
	 * 帮卖占比
	 */
	@Column(name = "assist_order_ratio")
	private BigDecimal assistOrderRatio;

	/**
	 * 自提服务订单数
	 */
	@Column(name = "pickup_service_order_num")
	private Long pickupServiceOrderNum;

	/**
	 * 服务订单金额
	 */
	@Column(name = "pickup_service_order_total")
	private BigDecimal pickupServiceOrderTotal;

	/**
	 * 退单数
	 */
	@Column(name = "return_num")
	private Long returnNum;

	/**
	 * 退单总数
	 */
	@Column(name = "return_total")
	private BigDecimal returnTotal;

	/**
	 * 帮卖退单数
	 */
	@Column(name = "assist_return_num")
	private Long assistReturnNum;

	/**
	 * 帮卖退单总额
	 */
	@Column(name = "assist_return_total")
	private BigDecimal assistReturnTotal;

	/**
	 * 已入账佣金
	 */
	@Column(name = "commission_received")
	private BigDecimal commissionReceived;

	/**
	 * 已入账自提佣金
	 */
	@Column(name = "commission_received_pickup")
	private BigDecimal commissionReceivedPickup;

	/**
	 * 已入账帮卖佣金
	 */
	@Column(name = "commission_received_assist")
	private BigDecimal commissionReceivedAssist;

	/**
	 * 未入账佣金
	 */
	@Column(name = "commission_pending")
	private BigDecimal commissionPending;

	/**
	 * 未入账自提佣金
	 */
	@Column(name = "commission_pending_pickup")
	private BigDecimal commissionPendingPickup;

	/**
	 * 未入账帮卖佣金
	 */
	@Column(name = "commission_pending_assist")
	private BigDecimal commissionPendingAssist;

	/**
	 * 退款佣金
	 */
	@Column(name = "return_trade_commission")
	private BigDecimal returnTradeCommission;

	/**
	 * 退款佣金帮卖
	 */
	@Column(name = "return_trade_commission_assist")
	private BigDecimal returnTradeCommissionAssist;

	/**
	 * 退款佣金自提
	 */
	@Column(name = "return_trade_commission_pickup")
	private BigDecimal returnTradeCommissionPickup;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate createDate;

}
