package com.wanmi.sbc.order.api.request.trade;/**
 * @author 黄昭
 * @create 2021/10/18 14:26
 */

import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 代销平台订单保存入参
 * @author malianfeng
 * @date 2022/4/25 16:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformOrderSaveRequest implements Serializable {

    private static final long serialVersionUID = 1802963311323004365L;
    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String tid;

    /**
     * 代销平台类型
     */
    @Schema(description = "代销平台类型")
    @NotNull
    private SellPlatformType sellPlatformType;
}
