package com.wanmi.ares.provider.impl;

import com.github.pagehelper.PageInfo;
import com.wanmi.ares.marketing.coupon.service.CouponEffectService;
import com.wanmi.ares.marketing.coupon.service.CouponOverviewService;
import com.wanmi.ares.provider.CouponProvider;
import com.wanmi.ares.request.coupon.CouponActivityEffectRequest;
import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.request.coupon.CouponQueryRequest;
import com.wanmi.ares.view.coupon.CouponActivityEffectView;
import com.wanmi.ares.view.coupon.CouponInfoEffectView;
import com.wanmi.ares.view.coupon.CouponOverviewView;
import com.wanmi.ares.view.coupon.CouponStoreEffectView;
import com.wanmi.ares.view.coupon.DataPeriodView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @ClassName CouponProviderController
 * @description
 * @Author zhanggaolei
 * @Date 2021/1/14 17:12
 * @Version 1.0
 **/
@RestController
public class CouponProviderController implements CouponProvider {
    @Autowired
    private CouponOverviewService couponOverviewService;
    @Autowired
    private CouponEffectService couponEffectService;

    @Override
    public CouponOverviewView overview(@Valid CouponQueryRequest request) {
        return couponOverviewService.overview(request);
    }

    @Override
    public List<CouponOverviewView> overviewList(@Valid CouponQueryRequest request) {
        return couponOverviewService.overviewList(request);
    }

    @Override
    public DataPeriodView dataPeriod() {
        return couponOverviewService.getDataPeriod();
    }

    @Override
    public PageInfo<CouponInfoEffectView> pageCouponInfoEffect(@Valid CouponEffectRequest request) {
        return couponEffectService.pageCouponInfoEffect(request);
    }

    @Override
    public PageInfo<CouponInfoEffectView> pageCouponInfoEffectByActivityId(@Valid CouponEffectRequest request) {
        return couponEffectService.pageCouponInfoEffectByActivityId(request);
    }

    @Override
    public PageInfo<CouponActivityEffectView> pageCouponActivityEffect(@Valid CouponActivityEffectRequest request) {
        return couponEffectService.pageCouponActivityEffect(request);
    }

    @Override
    public PageInfo<CouponStoreEffectView> pageCouponStoreEffect(@Valid CouponEffectRequest request) {
        return couponEffectService.pageCouponStoreEffect(request);
    }


}
