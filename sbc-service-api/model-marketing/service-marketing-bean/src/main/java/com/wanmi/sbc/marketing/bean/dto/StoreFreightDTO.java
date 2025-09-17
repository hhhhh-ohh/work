package com.wanmi.sbc.marketing.bean.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description 店铺运费信息入参
 * @author malianfeng
 * @date 2022/9/30 9:52
 */
@Data
public class StoreFreightDTO {

    @NotNull
    @Schema(description = "店铺id")
    private Long storeId;

    @NotNull
    @Schema(description = "店铺运费")
    private BigDecimal freight;
}

