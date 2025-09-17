package com.wanmi.sbc.customer.api.request.storereturnaddress;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询店铺退货地址表请求参数</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReturnAddressByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址ID
	 */
	@Schema(description = "收货地址ID")
	@NotNull
	private String addressId;

	/**
	 * 店铺信息ID
	 */
	@Schema(description = "店铺信息ID", hidden = true)
	private Long storeId;

	/**
	 * 是否返回省市区名称
	 */
	@Schema(description = "是否返回省市区名称")
	private Boolean showAreaName;

}