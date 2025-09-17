package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaApplyBindRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分账绑定参数request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ApplyBindRequest {



    /**
     * 拉卡拉分账绑定参数
     */
    @Schema(description = "拉卡拉分账绑定参数")
    private LakalaApplyBindRequest lakalaApplyBindRequest;

    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
