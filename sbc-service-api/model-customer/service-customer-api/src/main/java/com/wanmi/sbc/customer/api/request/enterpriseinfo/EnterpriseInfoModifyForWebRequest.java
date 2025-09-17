package com.wanmi.sbc.customer.api.request.enterpriseinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

/**
 * <p>企业信息表修改参数</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoModifyForWebRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 企业会员id
	 */
	@Schema(description = "企业会员id")
	private String customerId;

}