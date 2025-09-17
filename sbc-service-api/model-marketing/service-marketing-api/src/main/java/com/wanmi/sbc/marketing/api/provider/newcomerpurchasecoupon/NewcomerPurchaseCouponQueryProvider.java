package com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.*;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponByIdResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponFetchResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponListResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>新人购优惠券查询服务Provider</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@FeignClient(value = "${application.marketing.name}", contextId = "NewcomerPurchaseCouponQueryProvider")
public interface NewcomerPurchaseCouponQueryProvider {

	/**
	 * 分页查询新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponPageReq 分页请求参数和筛选对象 {@link NewcomerPurchaseCouponPageRequest}
	 * @return 新人购优惠券分页列表信息 {@link NewcomerPurchaseCouponPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/page")
	BaseResponse<NewcomerPurchaseCouponPageResponse> page(@RequestBody @Valid NewcomerPurchaseCouponPageRequest newcomerPurchaseCouponPageReq);

	/**
	 * 列表查询新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponListReq 列表请求参数和筛选对象 {@link NewcomerPurchaseCouponListRequest}
	 * @return 新人购优惠券的列表信息 {@link NewcomerPurchaseCouponListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/list")
	BaseResponse<NewcomerPurchaseCouponListResponse> list(@RequestBody @Valid NewcomerPurchaseCouponListRequest newcomerPurchaseCouponListReq);

	/**
	 * 单个查询新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponByIdRequest 单个查询新人购优惠券请求参数 {@link NewcomerPurchaseCouponByIdRequest}
	 * @return 新人购优惠券详情 {@link NewcomerPurchaseCouponByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/get-by-id")
	BaseResponse<NewcomerPurchaseCouponByIdResponse> getById(@RequestBody @Valid NewcomerPurchaseCouponByIdRequest newcomerPurchaseCouponByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author zhanghao
	 * @param request {tableDesc}导出数量查询请求 {@link NewcomerPurchaseCouponExportRequest}
	 * @return 新人购优惠券数量 {@link Long}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid NewcomerPurchaseCouponExportRequest request);

	/**
	 * 查询可使用的新人购优惠券API
	 *
	 * @author zhanghao
	 * @param request 请求参数和筛选对象 {@link NewcomerPurchaseCouponGetFetchRequest}
	 * @return 新人购优惠券的列表信息 {@link NewcomerPurchaseCouponFetchResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/get-fetch-coupons")
	BaseResponse<NewcomerPurchaseCouponFetchResponse> getFetchCoupons(@RequestBody @Valid NewcomerPurchaseCouponGetFetchRequest request);


}

