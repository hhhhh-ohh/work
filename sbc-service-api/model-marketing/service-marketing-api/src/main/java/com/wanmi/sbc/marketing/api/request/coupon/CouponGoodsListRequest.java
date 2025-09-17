package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 16:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponGoodsListRequest extends BaseRequest {

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券Id")
    private String couponId;

    /**
     *活动id
     */
    @Schema(description = "优惠券活动Id")
    private String activityId;

    /**
     * 用户id
     */
    @Schema(description = "用户Id")
    private String customerId;

    @Schema(description = "店铺Id")
    private Long storeId;

}
