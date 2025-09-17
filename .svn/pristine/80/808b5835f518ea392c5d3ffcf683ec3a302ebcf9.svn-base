package com.wanmi.sbc.customer.ledgerreceiverrel.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>分账绑定关系实体类</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Data
@Entity
@Table(name = "ledger_receiver_rel")
public class LedgerReceiverRel {
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
	 * 商户分账数据id
	 */
	@Column(name = "ledger_supplier_id")
	private String ledgerSupplierId;

	/**
	 * 商户id
	 */
	@Column(name = "supplier_id")
	private Long supplierId;

	/**
	 * 接收方id
	 */
	@Column(name = "receiver_id")
	private String receiverId;

	/**
	 * 接收方名称
	 */
	@Column(name = "receiver_name")
	private String receiverName;

	/**
	 * 接收方编码
	 */
	@Column(name = "receiver_code")
	private String receiverCode;

	/**
	 * 接收方账户
	 */
	@Column(name = "receiver_account")
	private String receiverAccount;

	/**
	 * 接收方类型 0、平台 1、供应商 2、分销员
	 */
	@Column(name = "receiver_type")
	private Integer receiverType;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 3、审核失败
	 */
	@Column(name = "account_state")
	private Integer accountState;

	/**
	 * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
	 */
	@Column(name = "bind_state")
	private Integer bindState;

	/**
	 * 绑定时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "bind_time")
	private LocalDateTime bindTime;

	/**
	 * 外部绑定受理编号
	 */
	@Column(name = "apply_id")
	private String applyId;

	/**
	 * 删除标识 0、未删除 1、已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 审核状态 0、待审核 1、已审核 2、已驳回
	 */
	@Column(name = "check_state")
	private Integer checkState;

	/**
	 * 驳回原因
	 */
	@Column(name = "reject_reason")
	private String rejectReason;

	/**
	 * 电子合同编号
	 */
	@Column(name = "ec_no")
	private String ecNo;

	/**
	 * 电子合同文件
	 */
	@Column(name = "ec_content_id")
	private String ecContentId;

	/**
	 * 待签约的电子合同链接
	 */
	@Column(name = "ec_url")
	private String ecUrl;

	/**
	 * 电子合同受理号
	 */
	@Column(name = "ec_apply_id")
	private String ecApplyId;

	/**
	 * 分账合作协议文件id
	 */
	@Column(name = "bind_contract_id")
	private String bindContractId;
}
