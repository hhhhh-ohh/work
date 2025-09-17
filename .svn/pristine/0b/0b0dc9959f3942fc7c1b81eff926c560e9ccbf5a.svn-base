package com.wanmi.sbc.customer.payingmemberprice.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>付费设置表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Data
@Entity
@Table(name = "paying_member_price")
public class PayingMemberPrice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费设置id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "price_id")
	private Integer priceId;

	/**
	 * 付费设置id
	 */
	@Column(name = "level_id")
	private Integer levelId;

	/**
	 * 付费设置数量 ，例如3个月
	 */
	@Column(name = "price_num")
	private Integer priceNum;

	/**
	 * 付费设置总金额，例如上述3个月90元
	 */
	@Column(name = "price_total")
	private BigDecimal priceTotal;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
