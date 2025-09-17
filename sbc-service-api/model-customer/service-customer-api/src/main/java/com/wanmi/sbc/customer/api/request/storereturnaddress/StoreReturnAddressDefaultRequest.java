package com.wanmi.sbc.customer.api.request.storereturnaddress;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>店铺退货地址表设为默认参数</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReturnAddressDefaultRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址ID
	 */
	@Schema(description = "收货地址ID")
	@NotBlank
	@Length(max=32)
	private String addressId;

	/**
	 * 店铺信息ID
	 */
	@Schema(description = "店铺信息ID", hidden = true)
	private Long storeId;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人", hidden = true)
	private String updatePerson;
}