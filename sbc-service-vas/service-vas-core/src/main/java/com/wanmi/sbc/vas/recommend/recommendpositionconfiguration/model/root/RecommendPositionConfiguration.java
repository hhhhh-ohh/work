package com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;
import com.wanmi.sbc.vas.bean.enums.recommen.TacticsType;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>推荐坑位设置实体类</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:14:13
 */
@Data
@Entity
@Table(name = "recommend_position_configuration")
public class RecommendPositionConfiguration extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 坑位名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类
	 */
	@Column(name = "type")
	@Enumerated
	private PositionType type;

	/**
	 * 坑位标题
	 */
	@Column(name = "title")
	private String title;

	/**
	 * 推荐内容
	 */
	@Column(name = "content")
	private String content;

	/**
	 * 推荐策略类型，0：热门推荐；1：基于商品相关性推荐
	 */
	@Column(name = "tactics_type")
	@Enumerated
	private TacticsType tacticsType;

	/**
	 * 推荐上限
	 */
	@Column(name = "upper_limit")
	private Integer upperLimit;

	/**
	 * 坑位开关，0：关闭；1：开启
	 */
	@Column(name = "is_open")
	@Enumerated
	private PositionOpenFlag isOpen;

}