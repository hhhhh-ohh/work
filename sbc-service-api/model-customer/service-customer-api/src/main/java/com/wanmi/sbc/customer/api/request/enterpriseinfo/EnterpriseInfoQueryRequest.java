package com.wanmi.sbc.customer.api.request.enterpriseinfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>企业信息表通用查询请求参数</p>
 * @author TangLian
 * @date 2020-03-03 14:11:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseInfoQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-企业IdList
	 */
	@Schema(description = "批量查询-企业IdList")
	private List<String> enterpriseIdList;

	/**
	 * 企业Id
	 */
	@Schema(description = "企业Id")
	private String enterpriseId;

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

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:参加时间开始
	 */
	@Schema(description = "搜索条件:参加时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:参加时间截止
	 */
	@Schema(description = "搜索条件:参加时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 删除标志
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;

}