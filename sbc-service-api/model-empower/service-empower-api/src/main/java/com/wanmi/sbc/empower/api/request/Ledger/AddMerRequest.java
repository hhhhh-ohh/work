package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaAddMerRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商户进件参数request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AddMerRequest {


    /**
     * 拉卡拉商户进件申请参数
     */
    @Schema(description = "拉卡拉商户进件申请参数")
    private LakalaAddMerRequest lakalaAddMerRequest;

    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
