package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author edz
 * @className LakalaSettlementBatchUpdateStatusRequest
 * @description TODO
 * @date 2022/8/8 15:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class LakalaSettlementBatchUpdateStatusRequest implements Serializable {
    @Schema(description = "<分账状态，结算单uuid>")
    private Map<LakalaLedgerStatus, List<String>> LakalaLedgerStatusToSettlementUuidMap;
}
