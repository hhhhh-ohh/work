package com.wanmi.sbc.order.api.request.pickupcoderecord;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>提货码记录新增参数</p>
 * @author 吕振伟
 * @date 2023-04-19 14:03:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupCodeRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 自提码
	 */
	@Schema(description = "自提码")
	@NotBlank
	@Length(max=10)
	private String pickupCode;

	/**
	 * 订单id
	 */
	@Schema(description = "订单id")
	@NotBlank
	@Length(max=32)
	private String tradeId;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Schema(description = "删除标记  0：正常，1：删除", hidden = true)
	private DeleteFlag delFlag;

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

}