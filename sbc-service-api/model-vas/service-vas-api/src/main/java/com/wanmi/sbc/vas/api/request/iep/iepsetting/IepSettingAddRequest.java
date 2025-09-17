package com.wanmi.sbc.vas.api.request.iep.iepsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>企业购设置新增参数</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 *  企业会员名称 
	 */
	@Schema(description = " 企业会员名称 ")
	@NotBlank
	@Length(max=8)
	private String enterpriseCustomerName;

	/**
	 *  企业价名称 
	 */
	@Schema(description = " 企业价名称 ")
	@NotBlank
	@Length(max=10)
	private String enterprisePriceName;

	/**
	 *  企业会员logo 
	 */
	@Schema(description = " 企业会员logo ")
	@Length(max=100)
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
	private String enterpriseCustomerRegisterContent;

	/**
	 *  创建人 
	 */
	@Schema(description = " 创建人 ", hidden = true)
	private String createPerson;

	/**
	 *  修改人 
	 */
	@Schema(description = " 修改人 ", hidden = true)
	private String updatePerson;

	/**
	 *  是否删除标志 0：否，1：是 
	 */
	@Schema(description = " 是否删除标志 0：否，1：是 ", hidden = true)
	private DeleteFlag delFlag;

}