package com.wanmi.sbc.customer.api.request.payingmemberrightsrel;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>权益与付费会员等级关联表新增参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRightsRelAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 付费设置id
	 */
	@Schema(description = "付费设置id")
	@Max(9999999999L)
	private Integer priceId;

	/**
	 * 权益id
	 */
	@Schema(description = "权益id")
	@Max(9999999999L)
	@NotNull
	private Integer rightsId;

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