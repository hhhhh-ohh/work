package com.wanmi.sbc.empower.api.request.Ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉卡Bin查询request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaCardBinRequest extends LakalaBaseRequest{

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    @NotBlank
    @Max(32)
    private String cardNo;
}
