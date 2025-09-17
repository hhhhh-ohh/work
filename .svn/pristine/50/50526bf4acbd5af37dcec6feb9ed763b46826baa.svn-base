package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaEcApplyRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;


/**
 * 电子合同参数request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EcApplyRequest {


    /**
     * 拉卡拉电子合同申请参数
     */
    @Schema(description = "拉卡拉电子合同申请参数")
    private LakalaEcApplyRequest lakalaEcApplyRequest;


    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
