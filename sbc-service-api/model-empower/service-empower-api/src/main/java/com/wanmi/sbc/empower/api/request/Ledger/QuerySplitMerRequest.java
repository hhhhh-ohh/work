package com.wanmi.sbc.empower.api.request.Ledger;


import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaQuerySplitMerRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分账商户信息查询request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class QuerySplitMerRequest {


    /**
     * 拉卡拉分账商户信息查询参数
     */
    @Schema(description = "拉卡拉分账商户信息查询参数")
    private LakalaQuerySplitMerRequest lakalaQuerySplitMerRequest;

    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
