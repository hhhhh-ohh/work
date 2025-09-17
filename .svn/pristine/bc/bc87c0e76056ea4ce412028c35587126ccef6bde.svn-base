package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSettlementDetailStatusRequest extends BaseQueryRequest {

    /**
     * 结算单id
     */
    @Schema(description = "拉卡拉分账流水号")
    private String payInstId;

    @Schema(description = "分账状态")
    private LakalaLedgerStatus lakalaLedgerStatus;

}
