package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * @description 根据SKU查询并领取登录人待领取的优惠券券
 * @author malianfeng
 * @date 2022/5/25 17:44
 */
@Schema
@Data
public class CouponAutoFetchRequest extends BaseRequest {

    private static final long serialVersionUID = 1216066410782081162L;

    /**
     * SKU列表
     */
    @Schema(description = "SKU列表")
    @NotNull
    private List<String> goodsInfoIds;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;
}
