package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.api.response.coupon.GetCouponGroupResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema
@Data
public class SendCouponGroupRequest extends BaseRequest {

    @Schema(description = "客户id")
    @NotNull
    private String customerId;

    @Schema(description = "活动id")
    @NotNull
    private String activityId;

    @Schema(description = "优惠券ids")
    private List<GetCouponGroupResponse> couponInfos = new ArrayList<>();


}
