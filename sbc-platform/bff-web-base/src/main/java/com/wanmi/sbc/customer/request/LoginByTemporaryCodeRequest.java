package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginByTemporaryCodeRequest extends BaseRequest {


    @Schema(description = "临时码")
    @NotBlank
    private String temporaryCode;

}
