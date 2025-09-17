package com.wanmi.sbc.customer.payingmemberrecommendrel.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import lombok.Data;

/**
 * <p>推荐商品与付费会员等级关联表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Data
@Entity
@Table(name = "paying_member_recommend_rel")
public class PayingMemberRecommendRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 推荐商品与付费会员等级关联id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * skuId
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
