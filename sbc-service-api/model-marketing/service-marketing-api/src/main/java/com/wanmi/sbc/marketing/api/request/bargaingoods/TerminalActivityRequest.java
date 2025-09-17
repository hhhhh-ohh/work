package com.wanmi.sbc.marketing.api.request.bargaingoods;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerminalActivityRequest implements Serializable {

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    @NotNull
    private Long bargainGoodsId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
