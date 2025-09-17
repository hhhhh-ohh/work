package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaAddB2bBusiRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaApplyBindRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增线上业务类型参数request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AddB2bBusiRequest {



    /**
     * 新增线上业务类型绑定参数
     */
    @Schema(description = "新增线上业务类型参数")
    private LakalaAddB2bBusiRequest lakalaAddB2bBusiRequest;

    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
