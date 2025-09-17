package com.wanmi.sbc.setting.pickupemployeerela.model.root;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <p>自提员工关系实体类</p>
 * @author xufeng
 * @date 2021-09-06 14:23:11
 */
@Data
@Entity
@Table(name = "pickup_employee_rela")
public class PickupEmployeeRela {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 员工id
	 */
	@Column(name = "employee_id")
	private String employeeId;

	/**
	 * 自提点id
	 */
	@Column(name = "pickup_id")
	private Long pickupId;

}
