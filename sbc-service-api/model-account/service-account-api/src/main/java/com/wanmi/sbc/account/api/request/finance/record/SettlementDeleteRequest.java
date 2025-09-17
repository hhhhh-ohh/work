package com.wanmi.sbc.account.api.request.finance.record;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>删除结算和结算明细request</p>
 * Created by daiyitian on 2018-10-13-下午6:29.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementDeleteRequest extends AccountBaseRequest{

    private static final long serialVersionUID = 4217016231787465048L;
    /**
     * 店铺编号
     */
    @Schema(description = "店铺编号")
    @NotNull
    private Long storeId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @NotBlank
    private String startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @NotBlank
    private String endTime;

    /**
     * 代销商家店铺id
     */
    @Schema(description = "代销商家店铺ID")
    private Long supplierStoreId;
}
