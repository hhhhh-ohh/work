package com.wanmi.ares.request.coupon;

import com.wanmi.ares.enums.CouponActivityType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @ClassName CouponActivityEffectRequest
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/27 15:15
 * @Version 1.0
 **/
@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponActivityEffectRequest  extends CouponEffectRequest{

    @Schema(description = "活动类型")
    private CouponActivityType activityType;
}
