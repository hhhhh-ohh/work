package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaCardBinRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 卡bin参数request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class CardBinRequest {



    /**
     * 拉卡拉卡bin查询参数
     */
    @Schema(description = "拉卡拉卡bin查询参数")
    private LakalaCardBinRequest lakalaCardBinRequest;

    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
