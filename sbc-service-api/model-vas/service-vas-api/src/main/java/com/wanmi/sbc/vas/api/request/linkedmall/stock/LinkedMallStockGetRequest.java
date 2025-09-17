package com.wanmi.sbc.vas.api.request.linkedmall.stock;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class LinkedMallStockGetRequest {

    @Schema(description = "spu集合")
    private List<Long> providerGoodsIds;

    @Schema(description = "配送区域编码")
    private String divisionCode;

    @Schema(description = "客户ip")
    private String ip;
}
