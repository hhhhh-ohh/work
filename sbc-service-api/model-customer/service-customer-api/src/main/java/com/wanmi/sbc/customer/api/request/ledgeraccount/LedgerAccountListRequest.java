package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>清分账户列表查询请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

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

	@Schema(description = "商户或接收方id")
	private List<String> businessIds;

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
	 * 搜索条件:营业执照开始时间开始
	 */
	@Schema(description = "搜索条件:营业执照开始时间开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate merBlisStDtBegin;
	/**
	 * 搜索条件:营业执照开始时间截止
	 */
	@Schema(description = "搜索条件:营业执照开始时间截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate merBlisStDtEnd;

	/**
	 * 搜索条件:营业执照有效期结束时间开始
	 */
	@Schema(description = "搜索条件:营业执照有效期结束时间开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate merBlisExpDtBegin;
	/**
	 * 搜索条件:营业执照有效期结束时间截止
	 */
	@Schema(description = "搜索条件:营业执照有效期结束时间截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate merBlisExpDtEnd;

	/**
	 * 商户经营内容
	 */
	@Schema(description = "商户经营内容")
	private String merBusiContent;

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
	 * 搜索条件:身份证开始日期开始
	 */
	@Schema(description = "搜索条件:身份证开始日期开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate larIdCardStDtBegin;
	/**
	 * 搜索条件:身份证开始日期截止
	 */
	@Schema(description = "搜索条件:身份证开始日期截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate larIdCardStDtEnd;

	/**
	 * 搜索条件:身份证有效期结束时间开始
	 */
	@Schema(description = "搜索条件:身份证有效期结束时间开始")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate larIdCardExpDtBegin;
	/**
	 * 搜索条件:身份证有效期结束时间截止
	 */
	@Schema(description = "搜索条件:身份证有效期结束时间截止")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate larIdCardExpDtEnd;

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
	 * 法人身份证正面
	 */
	@Schema(description = "法人身份证正面")
	private String idCardFrontPic;

	/**
	 * 法人身份证背面
	 */
	@Schema(description = "法人身份证背面")
	private String idCardBackPic;

	/**
	 * 银行卡
	 */
	@Schema(description = "银行卡")
	private String bankCardPic;

	/**
	 * 营业执照
	 */
	@Schema(description = "营业执照")
	private String businessPic;

	/**
	 * 商户门头照
	 */
	@Schema(description = "商户门头照")
	private String merchantPic;

	/**
	 * 商户内部照
	 */
	@Schema(description = "商户内部照")
	private String shopinnerPic;

	/**
	 * 开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败
	 */
	@Schema(description = "开户审核状态 0、未进件 1、审核中 2、审核成功 、3、审核失败")
	private Integer accountState;

	/**
	 * 分账审核状态 0、未开通 1、审核中 2、审核成功 3、审核失败
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
	 */
	@Schema(description = "账户类型 0、商户 1、接收方")
	private Integer accountType;

	/**
	 * 接收方类型 0、平台 1、供应商 2、分销员
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
	 * 电子合同文件
	 */
	@Schema(description = "电子合同文件")
	private String ecContent;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 是否删除 0、未删除 1、已删除
	 */
	@Schema(description = "是否删除 0、未删除 1、已删除")
	private DeleteFlag delFlag;

	/**
	 * 分账申请编号
	 */
	@Schema(description = "分账申请编号")
	private String ledgerApplyId;

	/**
	 * B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败
	 */
	@Schema(description = "B2b网银新增状态：0、未新增 1、审核中 2、审核成功 3、审核失败")
	private Integer b2bAddState;

	@Schema(description = "B2b网银新增申请编号")
	private String b2bAddApplyId;

	@Schema(description = "注册地址省编号")
	private String merRegDistProvinceCode;

	@Schema(description = "注册地址市编号")
	private String merRegDistCityCode;
}