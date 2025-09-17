package com.wanmi.sbc.elastic.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.activitycoupon.service.EsActivityCouponService;
import com.wanmi.sbc.elastic.api.provider.coupon.EsActivityCouponProvider;
import com.wanmi.sbc.elastic.api.request.coupon.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@Validated
public class EsActivityCouponController implements EsActivityCouponProvider {

    @Autowired
    private EsActivityCouponService esActivityCouponService;

    @Override
    public BaseResponse batchSave(@RequestBody @Valid EsActivityCouponBatchAddRequest request) {
        esActivityCouponService.saveAll(request.getEsActivityCoupons());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse update(@RequestBody @Valid EsActivityCouponModifyRequest request) {
        esActivityCouponService.modifyScope(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByActivityId(@RequestBody @Valid EsActivityCouponDeleteByActivityIdRequest request) {
        esActivityCouponService.deleteByActivityId(request.getActivityId());
        return BaseResponse.SUCCESSFUL();
    }
}
