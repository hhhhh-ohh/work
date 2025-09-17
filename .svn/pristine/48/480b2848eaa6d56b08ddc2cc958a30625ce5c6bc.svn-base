package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import com.wanmi.sbc.common.annotation.CanEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * 拉卡拉新增线上业务类型request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaAddB2bBusiRequest extends LakalaBaseRequest{


    /**
     * 拉卡拉内部商户号
     */
    @Schema(description = "拉卡拉内部商户号")
    @NotBlank
    @Max(32)
    private String merInnerNo;

    /**
     * 银联商户号
     */
    @Schema(description = "银联商户号")
    @NotBlank
    @Max(32)
    private String merCupNo;

    /**
     * 终端号
     */
    @Schema(description = "终端号")
    @NotBlank
    @Max(32)
    private String termNo;

    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private String busiTypeCode;

    @Schema(description = "费率信息集合")
    private Set<FeeData> feeData;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    @Max(64)
    @CanEmpty
    private String retUrl;

}
