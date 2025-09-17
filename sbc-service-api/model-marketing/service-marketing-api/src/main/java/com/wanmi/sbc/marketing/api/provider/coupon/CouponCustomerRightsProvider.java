package com.wanmi.sbc.marketing.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.coupon.RightsCouponRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 会员等级权益发放优惠券
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CouponCustomerRightsProvider")
public interface CouponCustomerRightsProvider {

    /**
     * 会员等级权益发放优惠券
     *
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/customer-rights/issue-coupons")
    BaseResponse customerRightsIssueCoupons();

    /**
     * 会员等级权益发放优惠券
     *
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/customer-rights/repeat-coupons")
    BaseResponse customerRightsRepeatCoupons(@Valid @RequestBody RightsCouponRequest rightsCouponRequest);
}
