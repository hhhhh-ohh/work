package com.wanmi.sbc.customer.payingmemberrightsrel.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import lombok.Data;

/**
 * <p>权益与付费会员等级关联表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Data
@Entity
@Table(name = "paying_member_rights_rel")
public class PayingMemberRightsRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 付费设置id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * 付费设置id
	 */
	@Column(name = "price_id")
	private Integer priceId;

	/**
	 * 权益id
	 */
	@Column(name = "rights_id")
	private Integer rightsId;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
