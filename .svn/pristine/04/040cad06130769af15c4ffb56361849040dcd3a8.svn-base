package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>清分账户VO</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@Data
public class LedgerAccountVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 商户或接收方id
	 */
	@Schema(description = "商户或接收方id")
	private String businessId;

	/**
	 * 外部系统商户标识
	 */
	@Schema(description = "外部系统商户标识")
	private String thirdMemNo;

	/**
	 * 营业执照名称
	 */
	@Schema(description = "营业执照名称")
	private String merBlisName;

	/**
	 * 注册地址
	 */
	@Schema(description = "注册地址")
	private String merRegDistCode;

	/**
	 * 详情地址
	 */
	@Schema(description = "详情地址")
	private String merRegAddr;

	/**
	 * 营业执照号
	 */
	@Schema(description = "营业执照号")
	private String merBlis;

	/**
	 * 营业执照开始时间
	 */
	@Schema(description = "营业执照开始时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate merBlisStDt;

	/**
	 * 营业执照有效期结束时间
	 */
	@Schema(description = "营业执照有效期结束时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate merBlisExpDt;

	/**
	 * 商户经营内容code
	 */
	@Schema(description = "商户经营内容code")
	private String merBusiContent;

	/**
	 * 商户经营内容名称
	 */
	@Schema(description = "商户经营内容名称")
	private String merBusiContentName;

	/**
	 * 法人姓名
	 */
	@Schema(description = "法人姓名")
	private String larName;

	/**
	 * 法人身份证号
	 */
	@Schema(description = "法人身份证号")
	private String larIdCard;

	/**
	 * 身份证开始日期
	 */
	@Schema(description = "身份证开始日期")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate larIdCardStDt;

	/**
	 * 身份证有效期结束时间
	 */
	@Schema(description = "身份证有效期结束时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate larIdCardExpDt;

	/**
	 * 商户联系人
	 */
	@Schema(description = "商户联系人")
	private String merContactName;

	/**
	 * 商户联系人手机号
	 */
	@Schema(description = "商户联系人手机号")
	private String merContactMobile;

	/**
	 * 账户名称
	 */
	@Schema(description = "账户名称")
	private String acctName;

	/**
	 * 账户卡号
	 */
	@Schema(description = "账户卡号")
	private String acctNo;

	/**
	 * 账户证件号
	 */
	@Schema(description = "账户证件号")
	private String acctCertificateNo;

	/**
	 * 账户开户行号
	 */
	@Schema(description = "账户开户行号")
	private String openningBankCode;

	/**
	 * 账户开户行名称
	 */
	@Schema(description = "账户开户行名称")
	private String openningBankName;

	/**
	 * 账户清算行号
	 */
	@Schema(description = "账户清算行号")
	private String clearingBankCode;

	/**
	 * 法人身份证正面id
	 */
	@Schema(description = "法人身份证正面id")
	private String idCardFrontPic;

	/**
	 * 法人身份证背面id
	 */
	@Schema(description = "法人身份证背面id")
	private String idCardBackPic;

	/**
	 * 银行卡id
	 */
	@Schema(description = "银行卡id")
	private String bankCardPic;

	/**
	 * 营业执照id
	 */
	@Schema(description = "营业执照id")
	private String businessPic;

	/**
	 * 商户门头照id
	 */
	@Schema(description = "商户门头照id")
	private String merchantPic;

	/**
	 * 商户内部照id
	 */
	@Schema(description = "商户内部照id")
	private String shopinnerPic;

	/**
	 * 法人身份证正面 图片内容
	 */
	@Schema(description = "法人身份证正面 图片内容")
	private byte[] idCardFrontPicContent;

	/**
	 * 法人身份证背面 图片内容
	 */
	@Schema(description = "法人身份证背面 图片内容")
	private byte[] idCardBackPicContent;

	/**
	 * 银行卡 图片内容
	 */
	@Schema(description = "银行卡 图片内容")
	private byte[] bankCardPicContent;

	/**
	 * 营业执照 图片内容
	 */
	@Schema(description = "营业执照 图片内容")
	private byte[] businessPicContent;

	/**
	 * 商户门头照 图片内容
	 */
	@Schema(description = "商户门头照 图片内容")
	private byte[] merchantPicContent;

	/**
	 * 商户内部照 图片内容
	 */
	@Schema(description = "商户内部照 图片内容")
	private byte[] shopinnerPicContent;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败
	 *
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerAccountState
	 */
	@Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败")
	private Integer accountState;

	/**
	 * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
	 *
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerState
	 */
	@Schema(description = "分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败")
	private Integer ledgerState;

	/**
	 * 开户驳回原因
	 */
	@Schema(description = "开户驳回原因")
	private String accountRejectReason;

	/**
	 * 分账驳回原因
	 */
	@Schema(description = "分账驳回原因")
	private String ledgerRejectReason;

	/**
	 * 账户类型 0、商户 1、接收方
	 *
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerAccountType
	 */
	@Schema(description = "账户类型 0、商户 1、接收方")
	private Integer accountType;

	/**
	 * 接收方类型 0、平台 1、供应商 2、分销员
	 *
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerReceiverType
	 */
	@Schema(description = "接收方类型 0、平台 1、供应商 2、分销员")
	private Integer receiverType;

	/**
	 * 进件id
	 */
	@Schema(description = "进件id")
	private String contractId;

	/**
	 * 电子合同受理号
	 */
	@Schema(description = "电子合同受理号")
	private String ecApplyId;

	/**
	 * 待签约的电子合同链接
	 */
	@Schema(description = "待签约的电子合同链接")
	private String ecUrl;

	/**
	 * 电子合同文件id
	 */
	@Schema(description = "电子合同文件id")
	private String ecContent;

	/**
	 * 平台分账绑定状态
	 * @see com.wanmi.sbc.customer.bean.enums.LedgerBindState
	 */
	@Schema(description = "平台分账绑定状态")
	private Integer bossBindState;

	/**
	 * 平台分账绑定id
	 */
	@Schema(description = "平台分账绑定id")
	private String relId;

	/**
	 * 分账绑定驳回原因
	 */
	@Schema(description = "分账绑定驳回原因")
	private String bindRejectReason;

	/**
	 * 商户mcc编码
	 */
	@Schema(description = "商户mcc编码")
	private String mccCode;

	/**
	 * 商户mcc编码名称
	 */
	@Schema(description = "商户mcc编码名称")
	private String mccCodeName;

	/**
	 * 终端号
	 */
	@Schema(description = "终端号")
	private String termNo;

	/**
	 * 银联商户号
	 */
	@Schema(description = "银联商户号")
	private String merCupNo;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

	/**
	 * 分账申请id
	 */
	@Schema(description = "分账申请id")
	private String ledgerApplyId;

	/**
	 * 电子合同编号
	 */
	@Schema(description = "电子合同编号")
	private String ecNo;

	/**
	 * 平台分账合作协议文件id
	 */
	@Schema(description = "平台分账合作协议文件id")
	private String bindContractId;

	/**
	 * B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败")
	private Integer b2bAddState;

	/**
	 * B2b网银新增申请id
	 */
	@Schema(description = "B2b网银新增申请id")
	private String b2bAddApplyId;

	@Schema(description = "商户注册名称")
	private String merRegName;

	@Schema(description = "注册地址省编号")
	private String merRegDistProvinceCode;

	@Schema(description = "注册地址市编号")
	private String merRegDistCityCode;

	/**
	 * 银行卡终端号
	 */
	@Schema(description = "银行卡终端号")
	private String bankTermNo;

	/**
	 * 快捷终端号
	 */
	@Schema(description = "快捷终端号")
	private String quickPayTermNo;

	/**
	 * 网银终端号
	 */
	@Schema(description = "网银终端号")
	private String unionTermNo;
}