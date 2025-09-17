package com.wanmi.sbc.elastic.api.request.settlement;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className EsSettlementDelByIdRequest
 * @description TODO
 * @date 2022/8/16 16:24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsLakalaSettlementDelRequest implements Serializable {

    @Schema(description = "结算单id")
    @NotNull
    private Long settleId;
}
