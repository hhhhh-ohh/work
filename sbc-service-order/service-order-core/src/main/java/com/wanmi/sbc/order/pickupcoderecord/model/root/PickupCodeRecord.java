package com.wanmi.sbc.order.pickupcoderecord.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>提货码记录实体类</p>
 * @author 吕振伟
 * @date 2023-04-19 14:03:52
 */
@Data
@Entity
@Table(name = "pickup_code_record")
public class PickupCodeRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 自提码
	 */
	@Column(name = "pickup_code")
	private String pickupCode;

	/**
	 * 订单id
	 */
	@Column(name = "trade_id")
	private String tradeId;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
