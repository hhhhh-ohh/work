package com.wanmi.sbc.customer.api.request.payingmemberstorerel;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>商家与付费会员等级关联表新增参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberStoreRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商家与付费会员等级关联id
	 */
	@Schema(description = "商家与付费会员等级关联id")
	@Max(9999999999L)
	private Integer levelId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	@Max(9223372036854775807L)
	@NotNull
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	@Length(max=150)
	private String storeName;

	/**
	 * 公司编码
	 */
	@Schema(description = "公司编码")
	@Length(max=32)
	@NotNull
	private String companyCode;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}