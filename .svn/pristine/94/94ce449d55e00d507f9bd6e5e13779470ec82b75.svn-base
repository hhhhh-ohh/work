package com.wanmi.sbc.marketing.api.request.bargain;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>砍价新增参数</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTradeRequest {
    private static final long serialVersionUID = 1L;
    @Schema(description = "砍价id")
    @NotNull
    @Max(9223372036854775807L)
    private Long bargainId;

    private String orderId;


}