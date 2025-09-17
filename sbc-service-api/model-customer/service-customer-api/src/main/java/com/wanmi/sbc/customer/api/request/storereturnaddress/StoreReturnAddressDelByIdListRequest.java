package com.wanmi.sbc.customer.api.request.storereturnaddress;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>批量删除店铺退货地址表请求参数</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReturnAddressDelByIdListRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-收货地址IDList
	 */
	@Schema(description = "批量删除-收货地址IDList")
	@NotEmpty
	private List<String> addressIdList;
}
