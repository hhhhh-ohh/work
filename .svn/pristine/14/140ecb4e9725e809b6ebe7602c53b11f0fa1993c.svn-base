package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaQueryContractRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaReconsiderSubmitRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进件信息查询参数request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class QueryContractRequest {

    /**
     * 进件信息查询提交参数
     */
    @Schema(description = "进件信息查询参数")
    private LakalaQueryContractRequest lakalaQueryContractRequest;


    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
