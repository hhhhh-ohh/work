package com.wanmi.sbc.vas.recommend.recommendgoodsmanage.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * <p>商品推荐管理实体类</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@Data
@Entity
@Table(name = "recommend_goods_manage")
public class RecommendGoodsManage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
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

	/**
	 * 禁推标识 0：可推送；1:禁推
	 */
	@Column(name = "no_push_type")
	private NoPushType noPushType;

}