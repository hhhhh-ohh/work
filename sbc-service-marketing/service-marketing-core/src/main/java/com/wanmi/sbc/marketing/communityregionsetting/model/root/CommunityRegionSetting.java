package com.wanmi.sbc.marketing.communityregionsetting.model.root;


import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>社区拼团区域设置表实体类</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Data
@Entity
@Table(name = "community_region_setting")
public class CommunityRegionSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "region_id")
	private Long regionId;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 区域名称
	 */
	@Column(name = "region_name")
	private String regionName;

}
