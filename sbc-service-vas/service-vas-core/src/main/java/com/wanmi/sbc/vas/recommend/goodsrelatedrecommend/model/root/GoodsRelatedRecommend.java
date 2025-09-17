package com.wanmi.sbc.vas.recommend.goodsrelatedrecommend.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * <p>商品相关性推荐实体类</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Data
@Entity
@Table(name = "goods_related_recommend")
public class GoodsRelatedRecommend extends BaseEntity {
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
	 * 关联商品id
	 */
	@Column(name = "related_goods_id")
	private String relatedGoodsId;

	/**
	 * 提升度
	 */
	@Column(name = "lift")
	private Integer lift;

	/**
	 * 权重
	 */
	@Column(name = "weight")
	private BigDecimal weight;

	/**
	 * 类型，0：关联分析，1：手动关联
	 */
	@Column(name = "type")
	private Integer type;

}