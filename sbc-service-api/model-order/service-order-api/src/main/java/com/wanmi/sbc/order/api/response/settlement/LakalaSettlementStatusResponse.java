package com.wanmi.sbc.order.api.response.settlement;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className LakalaSettlementStatusResponse
 * @description TODO
 * @date 2022/8/12 15:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LakalaSettlementStatusResponse implements Serializable {
    @Schema(description = "分账成功的settleUuid")
    private List<String> successList;
    @Schema(description = "分账中的settleUuid")
    private List<String> processingList;
    @Schema(description = "分账失败的settleUuid")
    private List<String> filList;
    @Schema(description = "分账余额不足的settleUuid")
    private List<String> insufficientAmountList;
    @Schema(description = "线下分账成功的settleUuid")
    private List<String> offlineList;
    @Schema(description = "部分分账成功的settleUuid")
    private List<String> partialSuccessList;
}
