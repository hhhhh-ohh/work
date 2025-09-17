package com.wanmi.sbc.marketing.communityrel.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>社区团购活动佣金团长关联表实体类</p>
 * @author dyt
 * @date 2023-07-24 14:43:24
 */
@Data
@Entity
@Table(name = "community_commission_leader_rel")
public class CommunityCommissionLeaderRel  implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 活动id
	 */
	@Column(name = "activity_id")
	private String activityId;

	/**
	 * 团长Id
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 团长自提点Id
	 */
	@Column(name = "pickup_point_id")
	private String pickupPointId;

	/**
	 * 自提服务佣金
	 */
	@Column(name = "pickup_commission")
	private BigDecimal pickupCommission;

	/**
	 * 帮卖团长佣金
	 */
	@Column(name = "assist_commission")
	private BigDecimal assistCommission;

}
