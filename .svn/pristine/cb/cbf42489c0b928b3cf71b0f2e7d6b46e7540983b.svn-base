package com.wanmi.sbc.elastic.bean.dto.customer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>企业信息表VO</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsEnterpriseInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业名称
	 */
	@Schema(description = "企业名称")
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
	@Schema(description = "企业人数")
	private Integer businessEmployeeNum;

	/**
	 * 企业会员id
	 */
	@Schema(description = "会员ID")
	private String customerId;

}