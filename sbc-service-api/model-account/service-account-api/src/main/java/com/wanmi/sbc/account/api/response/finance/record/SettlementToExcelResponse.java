package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema
@Data
public class SettlementToExcelResponse extends BasicResponse {

    /**
     * 财务结算未结算集合
     */
    @Schema(description = "财务结算未结算集合")
    private List<SettlementViewVO> notSettledSettlements;

    /**
     * 财务结算已结算集合
     */
    @Schema(description = "财务结算已结算集合")
    private List<SettlementViewVO> settledSettlements;

    /**
     * 财务结算暂不处理集合
     */
    @Schema(description = "财务结算暂不处理集合")
    private List<SettlementViewVO> settleLaterSettlements;
}
