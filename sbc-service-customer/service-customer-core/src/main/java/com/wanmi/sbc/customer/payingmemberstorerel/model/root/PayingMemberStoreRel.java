package com.wanmi.sbc.customer.payingmemberstorerel.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import lombok.Data;

/**
 * <p>商家与付费会员等级关联表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Data
@Entity
@Table(name = "paying_member_store_rel")
public class PayingMemberStoreRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 商家与付费会员等级关联id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Column(name = "store_name")
	private String storeName;

	/**
	 * 公司编码
	 */
	@Column(name = "company_code")
	private String companyCode;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
