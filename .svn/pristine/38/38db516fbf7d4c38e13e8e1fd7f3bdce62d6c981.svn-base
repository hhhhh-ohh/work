package com.wanmi.sbc.empower.api.request.pay.unionb2b;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chenli
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class UnionDirectRefundRequest extends PayBaseRequest {

    @Schema(description = "店铺id")
    @NotBlank
    private Long storeId;

    @Schema(description = "业务编号")
    @NotBlank
    private String businessId;
}
