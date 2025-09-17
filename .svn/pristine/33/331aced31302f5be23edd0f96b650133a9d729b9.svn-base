package com.wanmi.sbc.marketing.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeBatchSendResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponNotFetchCountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对优惠券码操作接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CouponCodeProvider")
public interface CouponCodeProvider {

    /**
     * 领取优惠券
     *
     * @param request 优惠券领取请求结构 {@link CouponFetchRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/fetch")
    BaseResponse fetch(@RequestBody @Valid CouponFetchRequest request);

    /**
     * 自动领取可领优惠券
     * @param request
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/auto-fetch")
    BaseResponse autoFetchCoupons(@RequestBody CouponAutoFetchRequest request);

    /**
     * 批量更新券码使用状态
     *
     * @param request 批量修改请求结构 {@link CouponCodeBatchModifyRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/batch-modify")
    BaseResponse batchModify(@RequestBody @Valid CouponCodeBatchModifyRequest request);

    /**
     * 根据id撤销优惠券使用
     *
     * @param request 包含id的撤销使用请求结构 {@link CouponCodeReturnByIdRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/return-by-id")
    BaseResponse returnById(@RequestBody @Valid CouponCodeReturnByIdRequest request);

    /**
     * 精准发券
     * @param request
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/precision-vouchers")
    BaseResponse precisionVouchers(@RequestBody @Valid CouponCodeBatchSendCouponRequest request);

    /**
     * 数据迁移：旧coupon_code按照新的分表规则进行拆分保存至新表中
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/data-migration")
    BaseResponse dataMigrationFromCouponCode();

    /**
     * 批量发券
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/send-batch")
    BaseResponse sendBatchCouponCodeByCustomerList(@RequestBody @Valid CouponCodeBatchSendCouponRequest request);

    /**
     * 批量发券
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/send-batch-by-customers")
    BaseResponse<CouponCodeBatchSendResponse> sendBatchCouponCodeByCustomers(@RequestBody @Valid CouponCodeBatchSendRequest request);

    /**
     * 异步批量发券
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/send-batch-async-by-customers")
    BaseResponse<CouponCodeBatchSendResponse> sendBatchCouponCodeAsyncByCustomers(@RequestBody @Valid CouponCodeBatchSendRequest request);

    /**
     * 满返支付精准发券
     * @param request
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/precision-coupon")
    BaseResponse precisionCoupon(@RequestBody @Valid CouponCodeBatchSendFullReturnRequest request);

    /**
     * 注销后更新优惠券过期时间
     * @param request
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/modify-by-customerId")
    BaseResponse modifyByCustomerId(@RequestBody CouponCodeReturnByIdRequest request);

    /**
     * 修改优惠券发送状态
     * @param request
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/modify-coupon-expired-sendflag-by-customerid")
    BaseResponse updateCouponExpiredSendFlagById(@RequestBody CouponCodeReturnByIdRequest request);

    /**
     * 回收券
     * @param request
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/recycle")
    BaseResponse recycleCoupon(@RequestBody @Valid CouponCodeRecycleByIdRequest request);

    /**
     * 统计用户可领取但未领取的优惠券数量
     * @param request
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/code/not-fetch-count")
    BaseResponse notFetchCount(@RequestBody CouponCacheCenterPageRequest request);
}
