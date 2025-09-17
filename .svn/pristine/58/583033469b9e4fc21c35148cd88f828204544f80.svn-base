package com.wanmi.sbc.vas.api.request.iep.iepsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>企业购设置修改参数</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 *  id 
	 */
	@Schema(description = " id ")
	@Length(max=32)
	private String id;

	/**
	 *  企业会员名称 
	 */
	@Schema(description = " 企业会员名称 ")
	@NotBlank
	@Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{2,8}$")
	@Length(min = 2,max = 9)
	private String enterpriseCustomerName;

	/**
	 *  企业价名称 
	 */
	@Schema(description = " 企业价名称 ")
	@NotBlank
	@Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{2,10}$")
	private String enterprisePriceName;

	/**
	 *  企业会员logo 
	 */
	@Schema(description = " 企业会员logo ")
	private String enterpriseCustomerLogo;

	/**
	 *  企业会员审核 0: 不需要审核 1: 需要审核 
	 */
	@Schema(description = " 企业会员审核 0: 不需要审核 1: 需要审核 ")
	@NotNull
	private DefaultFlag enterpriseCustomerAuditFlag;

	/**
	 *  企业商品审核 0: 不需要审核 1: 需要审核 
	 */
	@Schema(description = " 企业商品审核 0: 不需要审核 1: 需要审核 ")
	@NotNull
	private DefaultFlag enterpriseGoodsAuditFlag;

	/**
	 *  企业会员注册协议 
	 */
	@Schema(description = " 企业会员注册协议 ")
	@NotBlank
	private String enterpriseCustomerRegisterContent;

	/**
	 *  创建时间 
	 */
	@Schema(description = " 创建时间 ", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 *  修改人 
	 */
	@Schema(description = " 修改人 ", hidden = true)
	private String updatePerson;

}