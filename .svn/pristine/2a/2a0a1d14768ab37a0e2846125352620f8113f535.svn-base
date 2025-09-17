package com.wanmi.sbc.customer.ledgersupplier.model.root;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <p>商户分账绑定数据实体类</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@Data
@Entity
@Table(name = "ledger_supplier")
@EntityListeners(AuditingEntityListener.class)
public class LedgerSupplier{
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
	@Column(name = "ledger_account_id")
	private String ledgerAccountId;

	/**
	 * 商家id
	 */
	@Column(name = "company_info_id")
	private Long companyInfoId;

	/**
	 * 商家名称
	 */
	@Column(name = "company_name")
	private String companyName;

	/**
	 * 商家编号
	 */
	@Column(name = "company_code")
	private String companyCode;

	/**
	 * 平台绑定状态 0、未绑定 1、已绑定
	 */
	@Column(name = "plat_bind_state")
	private Integer platBindState;

	/**
	 * 供应商绑定数
	 */
	@Column(name = "provider_num")
	private Long providerNum;

	/**
	 * 分销员绑定数
	 */
	@Column(name = "distribution_num")
	private Long distributionNum;

	/**
	 *
	 * 创建时间
	 */
	@CreatedDate
	@Column(name = "create_time")
	private LocalDateTime createTime;

}
