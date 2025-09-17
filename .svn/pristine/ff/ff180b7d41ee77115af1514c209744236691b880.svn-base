package com.wanmi.sbc.goods.api.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListByCustomerIdAndMarketingIdReq extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    @NotBlank
    private String customerId;


    /**
     * 营销ID
     */
    @Schema(description = "营销ID")
    @NotNull
    private Long marketingId;
}
