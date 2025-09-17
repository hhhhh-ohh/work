package com.wanmi.sbc.account.api.request.finance.record;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>获取单条结算单信息request</p>
 * Created by daiyitian on 2018-10-13-下午6:29.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementGetByIdRequest extends AccountBaseRequest{

    private static final long serialVersionUID = 6048352447026298381L;
    /**
     * 结算单编号
     */
    @Schema(description = "结算单编号")
    @NotNull
    private Long settleId;

}
