package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaVerifyContractInfoRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进件校验request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class VerifyContractInfoRequest {

    /**
     * 拉卡拉进件校验参数
     */
    @Schema(description = "拉卡拉进件校验参数")
    private LakalaVerifyContractInfoRequest lakalaVerifyContractInfoRequest;


    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
