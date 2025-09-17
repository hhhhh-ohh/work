package com.wanmi.sbc.marketing.communitysku.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>社区团购活动商品表实体类</p>
 * @author dyt
 * @date 2023-07-24 14:47:53
 */
@Data
@Entity
@Table(name = "community_sku_rel")
public class CommunitySkuRel implements Serializable {
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
	 * 商品spuId
	 */
	@Column(name = "spu_id")
	private String spuId;

	/**
	 * 商品skuId
	 */
	@Column(name = "sku_id")
	private String skuId;

	/**
	 * 活动价
	 */
	@Column(name = "price")
	private BigDecimal price;

	/**
	 * 自提服务佣金
	 */
	@Column(name = "pickup_commission")
	private BigDecimal pickupCommission;

	/**
	 * 帮卖佣金
	 */
	@Column(name = "assist_commission")
	private BigDecimal assistCommission;

	/**
	 * 活动库存
	 */
	@Column(name = "activity_stock")
	private Long activityStock;

	/**
	 * 已买库存
	 */
	@Column(name = "sales")
	private Long sales = 0L;

}
