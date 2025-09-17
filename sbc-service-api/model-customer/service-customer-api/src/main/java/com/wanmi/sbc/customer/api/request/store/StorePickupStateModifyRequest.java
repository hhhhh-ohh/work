package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Schema
@Data
public class StorePickupStateModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 自提开关 0 关 1 开
     */
    @Schema(description = "自提开关")
    @NotNull
    private BoolFlag pickupState;

}