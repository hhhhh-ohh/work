package com.wanmi.sbc.elastic.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.activitycoupon.root.EsActivityCoupon;
import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import com.wanmi.sbc.elastic.activitycoupon.service.EsActivityCouponService;
import com.wanmi.sbc.elastic.activitycoupon.service.EsCouponScopeService;
import com.wanmi.sbc.elastic.api.provider.coupon.EsActivityCouponQueryProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponScopeQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponPageRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsActivityCouponPageResponse;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponScopePageResponse;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsActivityCouponVO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponScopeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Validated
public class EsActivityCouponQueryController implements EsActivityCouponQueryProvider {

    @Autowired
    private EsActivityCouponService esActivityCouponService;

    @Override
    public BaseResponse<EsActivityCouponPageResponse> page(@RequestBody @Valid EsActivityCouponPageRequest request) {
        Page<EsActivityCoupon> page = esActivityCouponService.page(request);
        MicroServicePage<EsActivityCouponVO> esCouponScopeVOS = KsBeanUtil.convertPage(page, EsActivityCouponVO.class);
        return BaseResponse.success(EsActivityCouponPageResponse.builder().activityCouponPage(esCouponScopeVOS).build());
    }
}
