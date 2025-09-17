package com.wanmi.sbc.elastic.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponPageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsActivityCouponPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对优惠券活动范围查询接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsActivityCouponQueryProvider")
public interface EsActivityCouponQueryProvider {

    /**
     * 根据条件分页查询优惠券活动范围分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsActivityCouponPageRequest}
     * @return 优惠券活动范围分页列表 {@link EsActivityCouponPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/activity/coupon/page")
    BaseResponse<EsActivityCouponPageResponse> page(@RequestBody @Valid EsActivityCouponPageRequest request);

}
