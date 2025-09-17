package com.wanmi.sbc.order.api.request.trade;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className BuyCycleInfoRequest
 * @description
 * @date 2022/10/18 3:05 PM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyCycleInfoRequest implements Serializable {
    private static final long serialVersionUID = -8097096577172400148L;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    @NotBlank
    private String skuId;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotBlank
    private String customerId;

    /**
     * 用户终端
     */
    @Schema(description = "用户终端")
    @NotBlank
    private String terminalToken;
}
