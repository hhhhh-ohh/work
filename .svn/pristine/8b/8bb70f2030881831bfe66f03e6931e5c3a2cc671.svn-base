package com.wanmi.sbc.empower.api.request.settlement;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className SettlementRequest
 * @description TODO
 * @date 2022/7/20 15:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SettlementRequest implements Serializable {

    @Schema(description = "结算单详情id")
    private List<String> ids;

    @Schema(description = "分账方式 0：同步分账 1：异步分账")
    private int type;

    @Schema(description = "结算单详情uuid")
    private List<String> uuids;

    @Schema(description = "0:线上分账 1:线下分账")
    private int lakalaLedgerType;
}
