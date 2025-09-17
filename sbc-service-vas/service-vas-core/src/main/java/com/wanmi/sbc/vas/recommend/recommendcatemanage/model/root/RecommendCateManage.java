package com.wanmi.sbc.vas.recommend.recommendcatemanage.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>分类推荐管理实体类</p>
 * @author lvzhenwei
 * @date 2020-11-19 14:05:07
 */
@Data
@Entity
@Table(name = "recommend_cate_manage")
public class RecommendCateManage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 类目id
	 */
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 权重
	 */
	@Column(name = "weight")
	private Integer weight;

	/**
	 * 禁推标识 0：可推送；1:禁推
	 */
	@Column(name = "no_push_type")
	private NoPushType noPushType;

}