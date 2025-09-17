package com.wanmi.sbc.marketing.communityregionsetting.model.root;


import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>社区拼团区域设置表实体类</p>
 * @author dyt
 * @date 2023-07-20 14:56:35
 */
@Data
@Entity
@Table(name = "community_region_area_setting")
public class CommunityRegionAreaSetting implements Serializable {
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
	 * 省市区id
	 */
	@Column(name = "area_id")
	private String areaId;

	/**
	 * 省市区名称
	 */
	@Column(name = "area_name")
	private String areaName;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;
}
