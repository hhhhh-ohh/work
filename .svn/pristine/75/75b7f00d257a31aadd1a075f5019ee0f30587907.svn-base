package com.wanmi.sbc.elastic.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityPageRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsMagicCouponActivityPageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponActivityPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对优惠券活动查询接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsCouponActivityQueryProvider")
public interface EsCouponActivityQueryProvider {

    /**
     * 根据条件分页查询优惠券活动分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsCouponActivityPageRequest}
     * @return 优惠券活动分页列表 {@link EsCouponActivityPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/coupon/activity/page")
    BaseResponse<EsCouponActivityPageResponse> page(@RequestBody @Valid EsCouponActivityPageRequest request);

    /**
     * @description 根据条件分页查询魔方优惠券活动分页列表
     * @author  EDZ
     * @date 2021/6/11 11:36
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.elastic.api.response.coupon.EsCouponActivityPageResponse>
     **/
    @PostMapping("/elastic/${application.elastic.version}/magic/coupon/activity/page")
    BaseResponse<EsCouponActivityPageResponse> magicPage(@RequestBody EsMagicCouponActivityPageRequest request);

}
