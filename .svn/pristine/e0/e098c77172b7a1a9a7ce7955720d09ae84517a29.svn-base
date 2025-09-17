package com.wanmi.sbc.empower.ledgermcc.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>拉卡拉mcc表实体类</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@Data
@Entity
@Table(name = "ledger_mcc")
public class LedgerMcc extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * mcc编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mcc_id")
	private Long mccId;

	/**
	 * mcc类别
	 */
	@Column(name = "mcc_cate")
	private String mccCate;

	/**
	 * 商户类别名
	 */
	@Column(name = "supplier_cate_name")
	private String supplierCateName;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
