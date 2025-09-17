package com.wanmi.sbc.customer.api.request.follow;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 店铺收藏已被关注条件
 * Created by daiyitian on 2017/11/6.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCustomerFollowExistsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    @NotBlank
    private String customerId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    @NotNull
    private Long storeId;

}
