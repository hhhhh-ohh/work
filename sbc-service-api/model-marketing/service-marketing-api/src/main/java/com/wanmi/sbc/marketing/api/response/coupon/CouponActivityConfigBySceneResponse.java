package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 根据scene查询优惠券活动配置
 * @author malianfeng
 * @date 2021/9/28 13:55
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponActivityConfigBySceneResponse extends BasicResponse {

    /**
     * 优惠券Id
     */
    @Schema(description = "优惠券Id")
    private String couponId;

    /**
     * 优惠券活动Id
     */
    @Schema(description = "优惠券活动Id")
    private String activityId;

}

