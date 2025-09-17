package com.wanmi.sbc.account.api.request.finance.record;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 统计结算查询请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementTotalByStoreIdRequest extends BasePageRequest {

    private static final long serialVersionUID = 2096728915215332619L;
    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
