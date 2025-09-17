package com.wanmi.sbc.dw.api.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPreferenceTagRequest extends BaseQueryRequest {

    /**
     * 会员id
     */
    @Schema(description = "会员id", required = true)
    @NotBlank
    private String customerId;



}
