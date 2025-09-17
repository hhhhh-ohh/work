package com.wanmi.sbc.customer.api.request.storereturnaddress;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除店铺退货地址表请求参数</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReturnAddressDelByIdRequest extends CustomerBaseRequest {
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
     * 删除人
     */
    @Schema(description = "删除人", hidden = true)
    private String deletePerson;
}
