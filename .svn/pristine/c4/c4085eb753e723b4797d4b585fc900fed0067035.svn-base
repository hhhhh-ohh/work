package com.wanmi.sbc.customer.api.request.enterpriseinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>企业信息表新增参数</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业名称
	 */
	@Schema(description = "企业名称")
	@NotBlank
	@Length(max=100)
	private String enterpriseName;

	/**
	 * 统一社会信用代码
	 */
	@Schema(description = "统一社会信用代码")
	@NotBlank
	@Length(max=50)
	private String socialCreditCode;

	/**
	 * 企业性质
	 */
	@Schema(description = "企业性质")
	@NotNull
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
	@Length(max=1024)
	private String businessLicenseUrl;

	/**
	 * 企业会员id
	 */
	@Schema(description = "企业会员id")
	@NotBlank
	@Length(max=32)
	private String customerId;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 删除标志
	 */
	@Schema(description = "删除标志", hidden = true)
	private DeleteFlag delFlag;

}