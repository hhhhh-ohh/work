package com.wanmi.sbc.vas.recommend.caterelatedrecommend.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * <p>分类相关性推荐实体类</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
@Data
@Entity
@Table(name = "cate_related_recommend")
public class CateRelatedRecommend extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 分类id
	 */
	@Column(name = "cate_id")
	private String cateId;

	/**
	 * 关联分类id
	 */
	@Column(name = "related_cate_id")
	private String relatedCateId;

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