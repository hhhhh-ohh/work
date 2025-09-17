package com.wanmi.sbc.customer.api.request.enterpriseinfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

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
public class EnterpriseInfoModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业Id
	 */
	@Schema(description = "企业Id")
	@Length(max=32)
	private String enterpriseId;

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
	 * 参加时间
	 */
	@Schema(description = "参加时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}