package com.wanmi.sbc.empower.api.request.channel.linkedmall;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanwei
 * @className LinkedMallInitApplyRefundRequest
 * @description TODO
 * @date 2021/5/29 15:10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LinkedMallInitApplyRefundRequest extends BaseRequest {

    @Schema(description = "子订单号")
    @NotBlank
    private String subLmOrderId;

    @Schema(description = "用户id")
    @NotBlank
    private String bizUid;


    /**
     * 1.仅退款  3.退货退款
     */
    @Schema(description = "退款类型")
    @NotNull
    private Integer bizClaimType;


    /**
     * 1.未收到货 2.已收到货 3.已寄回 4.未发货 5.卖家确认收货  6.已发货
     */
    @Schema(description = "货物状态")
    @NotNull
    private Integer goodsStatus;
}