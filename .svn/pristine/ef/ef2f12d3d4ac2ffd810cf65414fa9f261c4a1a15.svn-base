package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsDistributionCustomerModifyRequest extends BaseRequest {

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    @NotBlank
    private String customerId;


    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    @NotBlank
    private String customerName;
}
