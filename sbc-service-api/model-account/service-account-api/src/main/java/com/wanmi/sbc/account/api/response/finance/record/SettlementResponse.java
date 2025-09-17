package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.account.bean.vo.SettlementVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/** 结算新增响应请求 */
@Schema
@Data
public class SettlementResponse implements Serializable {

    @Schema(description = "普通结算单")
    private List<SettlementAddResponse> settlementAddResponses;

    @Schema(description = "拉卡拉结算单")
    private List<LakalaSettlementAddResponse> lakalaSettlementAddResponses;
}
