package com.wanmi.sbc.marketing.api.provider.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>对优惠券查询接口</p>
 * Created by daiyitian on 2018-11-23-下午6:23.
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CouponInfoQueryProvider")
public interface CouponInfoQueryProvider {

    /**
     * 根据条件分页查询优惠券分页列表
     *
     * @param request 条件分页查询请求结构 {@link CouponInfoPageRequest}
     * @return 优惠券分页列表 {@link CouponInfoPageResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/info/page")
    BaseResponse<CouponInfoPageResponse> page(@RequestBody @Valid CouponInfoPageRequest request);

    /**
     * 根据id查询优惠券信息
     *
     * @param request 包含id的查询请求结构 {@link CouponInfoByIdRequest}
     * @return 优惠券信息 {@link CouponInfoByIdResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/info/get-by-id")
    BaseResponse<CouponInfoByIdResponse> getById(@RequestBody @Valid CouponInfoByIdRequest request);

    /**
     * 根据id查询优惠券详情信息
     *
     * @param request 包含id的查询详情请求结构 {@link CouponInfoDetailByIdRequest}
     * @return 优惠券详情信息 {@link CouponInfoDetailByIdResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/info/get-detail-by-id")
    BaseResponse<CouponInfoDetailByIdResponse> getDetailById(@RequestBody @Valid CouponInfoDetailByIdRequest request);

    /**
     * 条件查询优惠券列表
     * @param request
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/info/query-couponInfos")
    BaseResponse<CouponInfosQueryResponse> queryCouponInfos(@RequestBody @Valid CouponInfoQueryRequest request);

    /**
     * 条件查询优惠券列表
     * @param request
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/coupon/info/list-by-page")
    BaseResponse<CouponInfoListByPageResponse> listByPage(@RequestBody @Valid CouponInfoListByPageRequest request);

    /**
     * @description 魔方优惠券分页列表
     * @author  EDZ
     * @date 2021/6/11 11:24
     * @param request
     * @param storeId
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse>
     **/
    @PostMapping("/marketing/${application.marketing.version}/magic/coupon/info/page")
    BaseResponse<MagicCouponInfoPageResponse> magicCouponInfoPage(
            @RequestBody MagicCouponInfoPageRequest request,
            @RequestParam(value = "storeId", required = false) Long storeId);

    /**
     * @description h5/app优惠券魔方页面，优惠券数据状态查询
     * @author  EDZ
     * @date 2021/6/11 11:24
     * @param requestList
     * @param customerId
     * @return com.wanmi.sbc.common.base.BaseResponse<java.util.List<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoResponse>>
     **/
    @PostMapping("/marketing/${application.marketing.version}/magic/coupon/info/status")
    BaseResponse<List<MagicCouponInfoResponse>> magicCouponInfoStatus(
            @RequestBody List<MagicCouponInfoRequest> requestList,
            @RequestParam(value = "customerId", required = false) String customerId);

    @PostMapping("/marketing/${application.marketing.version}/coupon/info/queryCouponInfo")
    BaseResponse<MagicCouponInfoResponse> queryCouponInfo(
            @RequestBody MagicCouponInfoRequest requestList,
            @RequestParam(value = "customerId", required = false) String customerId);

    @PostMapping("/marketing/${application.marketing.version}/coupon/info/queryCouponInfoById")
    BaseResponse<MagicCouponInfoResponse> queryCouponInfoById(
            @RequestBody @Valid CouponInfoByIdRequest request);

    /**
     * @description 魔方新人优惠券分页列表
     * @author  EDZ
     * @date 2021/6/11 11:24
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse>
     **/
    @PostMapping("/marketing/${application.marketing.version}/magic/newcomer/coupon/info/page")
    BaseResponse<MagicCouponInfoPageResponse> magicNewcomerCouponInfoPage(@RequestBody @Valid MagicCouponInfoPageRequest request);
}
