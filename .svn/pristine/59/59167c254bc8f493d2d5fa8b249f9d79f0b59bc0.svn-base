package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据skuid查storeid
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreQueryBySkuIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;


    /**
     * skuId
     */
    @NotBlank
    @Schema(description = "skuId")
    private String skuId;



}
