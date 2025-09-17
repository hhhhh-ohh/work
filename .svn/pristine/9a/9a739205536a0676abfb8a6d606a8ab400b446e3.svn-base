package com.wanmi.sbc.order.api.request.payorder;


import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

/**
 * 根据父订单id查询支付单列表入参
 * Created by zhangjin on 2017/4/20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class FindPayOrderListRequest extends BaseQueryRequest {


    private static final long serialVersionUID = -7616911204997715950L;

    /**
     * 父订单号，用于合并支付
     */
    @Schema(description = "父订单号，用于合并支付")
    @NonNull
    private String parentTid;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    @NotBlank
    private String customerId;
}
