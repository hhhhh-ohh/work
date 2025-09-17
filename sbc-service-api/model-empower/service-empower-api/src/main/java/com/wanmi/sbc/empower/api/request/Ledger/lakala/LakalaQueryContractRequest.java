package com.wanmi.sbc.empower.api.request.Ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉商户进件复议request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaQueryContractRequest extends LakalaBaseRequest{


    /**
     * 进件ID
     */
    @Schema(description = "进件ID")
    @NotBlank
    @Max(32)
    private String contractId;
}
