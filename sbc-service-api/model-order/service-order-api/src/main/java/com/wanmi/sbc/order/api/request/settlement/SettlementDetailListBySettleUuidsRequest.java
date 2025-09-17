package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementDetailListBySettleUuidsRequest extends BaseQueryRequest {

    @Schema(description = "详情结算单id")
    private List<String> ids;

    @Schema(description = "结算单id")
    private List<String> settleUuids;

    @Schema(description = "结算状态")
    private List<LakalaLedgerStatus> lakalaLedgerStatus;
}
