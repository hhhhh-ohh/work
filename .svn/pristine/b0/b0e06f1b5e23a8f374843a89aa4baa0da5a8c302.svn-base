package com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.*;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponAddResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.NewcomerPurchaseCouponModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>新人购优惠券保存服务Provider</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@FeignClient(value = "${application.marketing.name}", contextId = "NewcomerPurchaseCouponProvider")
public interface NewcomerPurchaseCouponProvider {

	/**
	 * 新增新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponAddRequest 新人购优惠券新增参数结构 {@link NewcomerPurchaseCouponAddRequest}
	 * @return 新增的新人购优惠券信息 {@link NewcomerPurchaseCouponAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/add")
	BaseResponse<NewcomerPurchaseCouponAddResponse> add(@RequestBody @Valid NewcomerPurchaseCouponAddRequest newcomerPurchaseCouponAddRequest);

	/**
	 * 修改新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponModifyRequest 新人购优惠券修改参数结构 {@link NewcomerPurchaseCouponModifyRequest}
	 * @return 修改的新人购优惠券信息 {@link NewcomerPurchaseCouponModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/modify")
	BaseResponse<NewcomerPurchaseCouponModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseCouponModifyRequest newcomerPurchaseCouponModifyRequest);

	/**
	 * 单个删除新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponDelByIdRequest 单个删除参数结构 {@link NewcomerPurchaseCouponDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid NewcomerPurchaseCouponDelByIdRequest newcomerPurchaseCouponDelByIdRequest);

	/**
	 * 批量删除新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponDelByIdListRequest 批量删除参数结构 {@link NewcomerPurchaseCouponDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseCouponDelByIdListRequest newcomerPurchaseCouponDelByIdListRequest);



	/**
	 * 批量保存新人购优惠券API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseCouponBatchSaveRequest 新人购优惠券新增参数结构 {@link NewcomerPurchaseCouponBatchSaveRequest}
	 * @return 新增的新人购优惠券信息 {@link}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/batch-save")
	BaseResponse batchSave(@RequestBody @Valid NewcomerPurchaseCouponBatchSaveRequest newcomerPurchaseCouponBatchSaveRequest);

	/**
	 * 领取新人专享券
	 *
	 * @author zhanghao
	 * @param request  领取新人专享券参数结构 {@link NewcomerPurchaseCouponFetchRequest}
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchasecoupon/fetch-coupon")
	BaseResponse fetchCoupon(@RequestBody @Valid NewcomerPurchaseCouponFetchRequest request);

}

