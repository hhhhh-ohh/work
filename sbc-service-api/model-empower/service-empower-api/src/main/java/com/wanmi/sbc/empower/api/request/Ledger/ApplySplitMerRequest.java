package com.wanmi.sbc.empower.api.request.Ledger;


import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaApplySplitMerRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建分账商户request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ApplySplitMerRequest {


    /**
     * 拉卡拉创建分账商户参数
     */
    @Schema(description = "拉卡拉创建分账商户参数")
    private LakalaApplySplitMerRequest lakalaApplySplitMerRequest;

    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
