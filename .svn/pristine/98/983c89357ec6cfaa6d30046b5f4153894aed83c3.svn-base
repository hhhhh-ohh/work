package com.wanmi.sbc.order.api.request.settlement;

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
public class SettlementDetailListByIdsRequest extends BaseQueryRequest {

    /**
     * 结算单id
     */
    @Schema(description = "结算单id")
    private List<String> ids;
}
