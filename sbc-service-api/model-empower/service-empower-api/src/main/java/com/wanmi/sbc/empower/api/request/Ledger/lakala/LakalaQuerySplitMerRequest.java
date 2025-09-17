package com.wanmi.sbc.empower.api.request.Ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉分账商户信息查询request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaQuerySplitMerRequest extends LakalaBaseRequest{

    /**
     * 拉卡拉内部商户号
     */
    @Schema(description = "拉卡拉内部商户号")
    @NotBlank
    @Max(32)
    private String merInnerNo;

}
