package com.wanmi.sbc.vas.recommend.manualrecommendgoods.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * <p>手动推荐商品管理实体类</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Data
@Entity
@Table(name = "manual_recommend_goods")
public class ManualRecommendGoods extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 商品id
	 */
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * 权重
	 */
	@Column(name = "weight")
	private BigDecimal weight;

}