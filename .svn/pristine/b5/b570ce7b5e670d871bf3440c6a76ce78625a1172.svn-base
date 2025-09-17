package com.wanmi.sbc.customer.ledgeraccount.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>清分账户实体类</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Data
@Entity
@Table(name = "ledger_account")
public class LedgerAccount extends BaseEntity {
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
	 * 商户或接收方id
	 */
	@Column(name = "business_id")
	private String businessId;

	/**
	 * 外部系统商户标识
	 */
	@Column(name = "third_mem_no")
	private String thirdMemNo;

	/**
	 * 营业执照名称
	 */
	@Column(name = "mer_blis_name")
	private String merBlisName;

	/**
	 * 注册地址
	 */
	@Column(name = "mer_reg_dist_code")
	private String merRegDistCode;

	/**
	 * 详情地址
	 */
	@Column(name = "mer_reg_addr")
	private String merRegAddr;

	/**
	 * 营业执照号
	 */
	@Column(name = "mer_blis")
	private String merBlis;

	/**
	 * 营业执照开始时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "mer_blis_st_dt")
	private LocalDate merBlisStDt;

	/**
	 * 营业执照有效期结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "mer_blis_exp_dt")
	private LocalDate merBlisExpDt;

	/**
	 * 商户经营内容
	 */
	@Column(name = "mer_busi_content")
	private String merBusiContent;

	/**
	 * 商户mcc编码
	 */
	@Column(name = "mcc_code")
	private String mccCode;

	/**
	 * 法人姓名
	 */
	@Column(name = "lar_name")
	private String larName;

	/**
	 * 法人身份证号
	 */
	@Column(name = "lar_id_card")
	private String larIdCard;

	/**
	 * 身份证开始日期
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "lar_id_card_st_dt")
	private LocalDate larIdCardStDt;

	/**
	 * 身份证有效期结束时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "lar_id_card_exp_dt")
	private LocalDate larIdCardExpDt;

	/**
	 * 商户联系人
	 */
	@Column(name = "mer_contact_name")
	private String merContactName;

	/**
	 * 商户联系人手机号
	 */
	@Column(name = "mer_contact_mobile")
	private String merContactMobile;

	/**
	 * 账户名称
	 */
	@Column(name = "acct_name")
	private String acctName;

	/**
	 * 账户卡号
	 */
	@Column(name = "acct_no")
	private String acctNo;

	/**
	 * 账户证件号
	 */
	@Column(name = "acct_certificate_no")
	private String acctCertificateNo;

	/**
	 * 账户开户行号
	 */
	@Column(name = "openning_bank_code")
	private String openningBankCode;

	/**
	 * 账户开户行名称
	 */
	@Column(name = "openning_bank_name")
	private String openningBankName;

	/**
	 * 账户清算行号
	 */
	@Column(name = "clearing_bank_code")
	private String clearingBankCode;

	/**
	 * 法人身份证正面
	 */
	@Column(name = "id_card_front_pic")
	private String idCardFrontPic;

	/**
	 * 法人身份证背面
	 */
	@Column(name = "id_card_back_pic")
	private String idCardBackPic;

	/**
	 * 银行卡
	 */
	@Column(name = "bank_card_pic")
	private String bankCardPic;

	/**
	 * 营业执照
	 */
	@Column(name = "business_pic")
	private String businessPic;

	/**
	 * 商户门头照
	 */
	@Column(name = "merchant_pic")
	private String merchantPic;

	/**
	 * 商户内部照
	 */
	@Column(name = "shopinner_pic")
	private String shopinnerPic;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败
	 */
	@Column(name = "account_state")
	private Integer accountState;

	/**
	 * 进件审核通过时间
	 */
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "pass_time")
	private LocalDateTime passTime;

	/**
	 * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
	 */
	@Column(name = "ledger_state")
	private Integer ledgerState;

	/**
	 * 开户驳回原因
	 */
	@Column(name = "account_reject_reason")
	private String accountRejectReason;

	/**
	 * 分账驳回原因
	 */
	@Column(name = "ledger_reject_reason")
	private String ledgerRejectReason;

	/**
	 * 账户类型 0、商户 1、接收方
	 */
	@Column(name = "account_type")
	private Integer accountType;

	/**
	 * 接收方类型 0、平台 1、供应商 2、分销员
	 */
	@Column(name = "receiver_type")
	private Integer receiverType;

	/**
	 * 进件id
	 */
	@Column(name = "contract_id")
	private String contractId;

	/**
	 * 电子合同受理号
	 */
	@Column(name = "ec_apply_id")
	private String ecApplyId;

	/**
	 * 待签约的电子合同链接
	 */
	@Column(name = "ec_url")
	private String ecUrl;

	/**
	 * 电子合同文件
	 */
	@Column(name = "ec_content")
	private String ecContent;

	/**
	 * 是否删除 0、未删除 1、已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 终端号
	 */
	@Column(name = "term_no")
	private String termNo;

	/**
	 * 银联商户号
	 */
	@Column(name = "mer_cup_no")
	private String merCupNo;


	/**
	 * 分账申请编号
	 */
	@Column(name = "ledger_apply_id")
	private String ledgerApplyId;

	/**
	 * 邮箱
	 */
	@Column(name = "e_mail")
	private String email;

	/**
	 * 电子合同编号
	 */
	@Column(name = "ec_no")
	private String ecNo;

	/**
	 * 平台分账合作协议文件id
	 */
	@Column(name = "bind_contract_id")
	private String bindContractId;

	/**
	 * B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败
	 */
	@Column(name = "b2b_add_state")
	private Integer b2bAddState;

	/**
	 * B2b网银新增申请id
	 */
	@Column(name = "b2b_add_apply_id")
	private String b2bAddApplyId;

	/**
	 * 注册地址省编号
	 */
	@Column(name = "mer_reg_dist_province_code")
	private String merRegDistProvinceCode;

	/**
	 * 注册地址市编号
	 */
	@Column(name = "mer_reg_dist_city_code")
	private String merRegDistCityCode;

	/**
	 * 银行卡终端号
	 */
	@Column(name = "bank_term_no")
	private String bankTermNo;

	/**
	 * 快捷终端号
	 */
	@Column(name = "quick_pay_term_no")
	private String quickPayTermNo;

	/**
	 * 网银终端号
	 */
	@Column(name = "union_term_no")
	private String unionTermNo;
}
