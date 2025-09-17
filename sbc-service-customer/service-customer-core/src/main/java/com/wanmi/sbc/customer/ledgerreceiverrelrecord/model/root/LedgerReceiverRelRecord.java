package com.wanmi.sbc.customer.ledgerreceiverrelrecord.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>分账绑定关系补偿记录实体类</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@Data
@Entity
@Table(name = "ledger_receiver_rel_record")
public class LedgerReceiverRelRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 清分账户id
	 */
	@Column(name = "account_id")
	private String accountId;

	/**
	 * 账户类型 0、商户 1、接收方
	 */
	@Column(name = "business_type")
	private Integer businessType;

}
