package com.wanmi.sbc.empower.api.request.channel.linkedmall;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

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
public class LinkedMallGoodsStockGetRequest {

    @Schema(description = "spu集合")
    @NotNull
    private List<Long> providerGoodsIds;

    @Schema(description = "配送区域编码")
    private String divisionCode;

    @Schema(description = "客户ip")
    private String ip;
}
