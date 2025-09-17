package com.wanmi.sbc.account.api.request.finance.record;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * 最近一个结算信息请求
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementLastByStoreIdRequest extends BasePageRequest {

    private static final long serialVersionUID = 6969376600313996853L;
    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
