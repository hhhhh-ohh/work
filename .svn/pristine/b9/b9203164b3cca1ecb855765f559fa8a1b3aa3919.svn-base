package com.wanmi.sbc.marketing.communityrel.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>社区团购活动佣金区域关联表实体类</p>
 * @author dyt
 * @date 2023-07-24 14:40:22
 */
@Data
@Entity
@Table(name = "community_commission_area_rel")
public class CommunityCommissionAreaRel  implements Serializable {
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
	 * 分组号
	 */
	@Column(name = "group_no")
	private Integer groupNo;

	/**
	 * 区域Id
	 */
	@Column(name = "area_id")
	private String areaId;

	/**
	 * 区域名称
	 */
	@Column(name = "area_name")
	private String areaName;

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
