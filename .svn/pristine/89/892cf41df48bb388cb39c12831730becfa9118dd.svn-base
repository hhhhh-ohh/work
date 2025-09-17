package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoDeleteStoreGetByStoreNameRequest extends CustomerBaseRequest {
    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    @NotNull
    private String storeName;
}
