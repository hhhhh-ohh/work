package com.wanmi.sbc.elastic.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.coupon.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对活动优惠券范围操作接口</p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsCouponScopeProvider")
public interface EsCouponScopeProvider {

    /**
     * 初始化优惠券活动范围ES数据
     *
     * @param request 初始化ES条件 {@link EsCouponScopeInitRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/coupon/scope/init")
    BaseResponse init(@RequestBody @Valid EsActivityCouponInitRequest request);

    /**
     * 新增优惠券活动范围
     *
     * @param request 优惠券活动范围新增信息结构 {@link EsCouponScopeBatchAddRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/coupon/scope/batch-save")
    BaseResponse batchSave(@RequestBody @Valid EsCouponScopeBatchAddRequest request);

    /**
     * 更新优惠券活动范围
     *
     * @param request 优惠券活动范围新增信息结构 {@link EsActivityCouponModifyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/coupon/scope/update")
    BaseResponse update(@RequestBody @Valid EsActivityCouponModifyRequest request);

    /**
     * 删除优惠券
     *
     * @param request 优惠券ID {@link EsCouponScopeDeleteByActivityIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/coupon/scope/delete-by-id")
    BaseResponse deleteByActivityId(@RequestBody @Valid EsCouponScopeDeleteByActivityIdRequest request);
}
