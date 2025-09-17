package com.wanmi.sbc.elastic.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeProvider;
import com.wanmi.sbc.elastic.api.request.coupon.*;
import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import com.wanmi.sbc.elastic.activitycoupon.service.EsCouponScopeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@Validated
public class EsCouponScopeController implements EsCouponScopeProvider {

    @Autowired
    private EsCouponScopeService esCouponScopeService;

    @Override
    public BaseResponse init(@RequestBody @Valid EsActivityCouponInitRequest request) {
        esCouponScopeService.init(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchSave(@RequestBody @Valid  EsCouponScopeBatchAddRequest request) {
        if (CollectionUtils.isNotEmpty(request.getCouponScopeDTOList())) {
            esCouponScopeService.saveAll(request.getCouponScopeDTOList());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse update(@RequestBody @Valid EsActivityCouponModifyRequest request) {
        esCouponScopeService.modifyScope(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByActivityId(@RequestBody @Valid  EsCouponScopeDeleteByActivityIdRequest request) {
        esCouponScopeService.deleteByActivityId(request.getActivityId());
        return BaseResponse.SUCCESSFUL();
    }
}
