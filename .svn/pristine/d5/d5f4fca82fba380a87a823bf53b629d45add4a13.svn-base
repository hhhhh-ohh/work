package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>企业信息表VO</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@Data
public class EnterpriseInfoVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业Id
	 */
	@Schema(description = "企业Id")
	private String enterpriseId;

	/**
	 * 企业名称
	 */
	@Schema(description = "企业名称")
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String enterpriseName;

	/**
	 * 统一社会信用代码
	 */
	@Schema(description = "统一社会信用代码")
	private String socialCreditCode;

	/**
	 * 企业性质
	 */
	@Schema(description = "企业性质")
	private Integer businessNatureType;

	/**
	 * 企业行业
	 */
	@Schema(description = "企业行业")
	private Integer businessIndustryType;

	/**
	 * 企业人数 0：1-49，1：50-99，2：100-499，3：500-999，4：1000以上
	 */
	@Schema(description = "企业人数 0：1-49，1：50-99，2：100-499，3：500-999，4：1000以上")
	private Integer businessEmployeeNum;

	/**
	 * 营业执照地址
	 */
	@Schema(description = "营业执照地址")
	private String businessLicenseUrl;

	/**
	 * 企业会员id
	 */
	@Schema(description = "企业会员id")
	private String customerId;

}