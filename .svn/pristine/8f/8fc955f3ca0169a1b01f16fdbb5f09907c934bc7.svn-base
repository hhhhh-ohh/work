package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

/**
 * <p>清分账户新增参数</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountSaveRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商户或接收方id
	 */
	@Schema(description = "商户或接收方id")
	private String businessId;

	/**
	 * 营业执照名称
	 */
	@Schema(description = "营业执照名称")
	private String merBlisName;

	/**
	 * 商户mcc编码
	 */
	@Schema(description = "商户mcc编码")
	private String mccCode;

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
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

	@Schema(description = "注册地址省编号")
	private String merRegDistProvinceCode;

	@Schema(description = "注册地址市编号")
	private String merRegDistCityCode;
}