package com.wanmi.sbc.customer.api.request.payingmemberrightsrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>权益与付费会员等级关联表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRightsRelModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

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
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;


}
