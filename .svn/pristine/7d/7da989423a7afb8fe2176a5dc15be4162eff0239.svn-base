package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaEcQStatusRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电子合同查询request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EcQStatusRequest {

    /**
     * 拉卡拉电子合同下载参数
     */
    @Schema(description = "拉卡拉电子合同下载参数")
    private LakalaEcQStatusRequest lakalaEcQStatusRequest;


    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
