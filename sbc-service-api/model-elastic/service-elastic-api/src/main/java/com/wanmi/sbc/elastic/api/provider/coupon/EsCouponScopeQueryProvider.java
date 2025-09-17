package com.wanmi.sbc.elastic.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponScopePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对优惠券活动范围查询接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsCouponScopeQueryProvider")
public interface EsCouponScopeQueryProvider {

    /**
     * 根据条件分页查询优惠券活动范围分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsCouponScopePageRequest}
     * @return 优惠券活动范围分页列表 {@link EsCouponScopePageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/coupon/scope/page")
    BaseResponse<EsCouponScopePageResponse> page(@RequestBody @Valid EsCouponScopePageRequest request);

}
