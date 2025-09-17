package com.wanmi.sbc.marketing.communityregionsetting.model.root;


import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>社区拼团区域设置表实体类</p>
 * @author dyt
 * @date 2023-07-20 14:55:16
 */
@Data
@Entity
@Table(name = "community_region_leader_setting")
public class CommunityRegionLeaderSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 区域id
	 */
	@Column(name = "region_id")
	private Long regionId;

	/**
	 * 团长id
	 */
	@Column(name = "leader_id")
	private String leaderId;

	/**
	 * 自提点id
	 */
	@Column(name = "pickup_point_id")
	private String pickupPointId;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;
}
