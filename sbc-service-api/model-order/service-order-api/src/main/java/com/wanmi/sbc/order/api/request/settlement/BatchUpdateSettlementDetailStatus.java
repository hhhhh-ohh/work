package com.wanmi.sbc.order.api.request.settlement;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Map;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchUpdateSettlementDetailStatus extends BaseQueryRequest {

    /**
     * 结算单id
     */
    @Schema(description = "成功结算单id")
    private List<String> success;

    @Schema(description = "结算中结算单id")
    private List<String> processing;

    @Schema(description = "失败结算单id")
    private List<String> fail;

    @Schema(description = "分账余额不足结算单id")
    private List<String> insufficientAmount;

    @Schema(description = "线下分账结算单id")
    private List<String> Offline;

    @Schema(description = "结算单详情ID对应拉卡拉分账流水号")
    private Map<String, String> lakalaSettlementDetailIdTosepTranSidMap;
}
