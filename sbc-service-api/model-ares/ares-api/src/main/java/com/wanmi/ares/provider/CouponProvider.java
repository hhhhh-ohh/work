package com.wanmi.ares.provider;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.request.coupon.CouponActivityEffectRequest;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.request.coupon.CouponQueryRequest;
import com.wanmi.ares.view.coupon.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @ClassName CouponOverviewProvider
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/1/14 16:45
 * @Version 1.0
 **/
@FeignClient(name = "${application.ares.name}", contextId="CouponProvider")
public interface CouponProvider {
    @PostMapping("/ares/${application.ares.version}/marketing/coupon/overview")
    CouponOverviewView overview(@RequestBody @Valid CouponQueryRequest request);

    @PostMapping("/ares/${application.ares.version}/marketing/coupon/overview-list")
    List<CouponOverviewView> overviewList(@RequestBody @Valid CouponQueryRequest request);

    @PostMapping("/ares/${application.ares.version}/marketing/coupon/data-period")
    DataPeriodView dataPeriod();

    @PostMapping("/ares/${application.ares.version}/marketing/coupon/coupon-info-effect")
    PageInfo<CouponInfoEffectView> pageCouponInfoEffect(@RequestBody @Valid CouponEffectRequest request);

    @PostMapping("/ares/${application.ares.version}/marketing/coupon/coupon-info-effect-activity")
    PageInfo<CouponInfoEffectView> pageCouponInfoEffectByActivityId(@RequestBody @Valid CouponEffectRequest request);

    @PostMapping("/ares/${application.ares.version}/marketing/coupon/coupon-activity-effect")
    PageInfo<CouponActivityEffectView> pageCouponActivityEffect(@RequestBody @Valid CouponActivityEffectRequest request);

    @PostMapping("/ares/${application.ares.version}/marketing/coupon/coupon-store-effect")
    PageInfo<CouponStoreEffectView> pageCouponStoreEffect(@RequestBody @Valid CouponEffectRequest request);
}
