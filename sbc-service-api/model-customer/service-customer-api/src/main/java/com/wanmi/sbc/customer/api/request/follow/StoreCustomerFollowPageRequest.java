package com.wanmi.sbc.customer.api.request.follow;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 店铺收藏分页条件
 * Created by daiyitian on 2017/11/6.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCustomerFollowPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    @NotBlank
    private String customerId;

    @Schema(description = "门店id")
    private Long storeId;

}
