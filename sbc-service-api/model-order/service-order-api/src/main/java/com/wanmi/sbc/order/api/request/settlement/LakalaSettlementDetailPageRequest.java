package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LakalaSettlementDetailPageRequest extends BaseQueryRequest {


    @Schema(description = "订单编号")
    private String tradeCode;

    @Schema(description = "供应商名称")
    private String providerName;

    @Schema(description = "分销员名称")
    private String distributorName;

    @Schema(description = "分账状态")
    private LakalaLedgerStatus status;

    private Long settleId;

    @Schema(description = "结算单id")
    private String settleUuid;


}
