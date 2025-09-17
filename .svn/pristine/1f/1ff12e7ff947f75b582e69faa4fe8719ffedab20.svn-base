package com.wanmi.sbc.marketing.communitysetting.model.root;


import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderAreaSummaryType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>社区拼团商家设置表实体类</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Data
@Entity
@Table(name = "community_setting")
public class CommunitySetting implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id
	 */
	@Id
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 汇总类型 0:按团长 1:按区域 以逗号拼凑
	 */
	@Column(name = "delivery_order_type")
	private String deliveryOrderType;

	/**
	 * 区域汇总类型 0：省份1：城市2：自定义
	 */
	@Column(name = "delivery_area_type")
	@Enumerated
	private DeliveryOrderAreaSummaryType deliveryAreaType;

}
