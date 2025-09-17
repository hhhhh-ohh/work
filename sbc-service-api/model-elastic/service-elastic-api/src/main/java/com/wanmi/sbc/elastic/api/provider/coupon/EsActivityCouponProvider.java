package com.wanmi.sbc.elastic.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.coupon.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对活动优惠券操作接口</p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsActivityCouponProvider")
public interface EsActivityCouponProvider {

    /**
     * 新增活动优惠券
     *
     * @param request 活动优惠券新增信息结构 {@link EsActivityCouponBatchAddRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/activity/coupon/batch-save")
    BaseResponse batchSave(@RequestBody @Valid EsActivityCouponBatchAddRequest request);

    /**
     * 更新活动优惠券
     *
     * @param request 活动优惠券新增信息结构 {@link EsActivityCouponModifyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/activity/coupon/update")
    BaseResponse update(@RequestBody @Valid EsActivityCouponModifyRequest request);

    /**
     * 删除活动优惠券
     *
     * @param request 优惠券ID {@link EsActivityCouponDeleteByActivityIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/activity/coupon/delete-by-id")
    BaseResponse deleteByActivityId(@RequestBody @Valid EsActivityCouponDeleteByActivityIdRequest request);
}
