package com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseDetailRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigByIdResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>新人专享设置查询服务Provider</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@FeignClient(value = "${application.marketing.name}", contextId = "NewcomerPurchaseConfigQueryProvider")
public interface NewcomerPurchaseConfigQueryProvider {

	/**
	 * 单个查询新人专享设置API
	 *
	 * @author zhanghao
	 * @param
	 * @return 新人专享设置详情 {@link NewcomerPurchaseConfigByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/get-one")
	BaseResponse<NewcomerPurchaseConfigByIdResponse> getOne();

	/**
	 * 新人专享页
	 *
	 * @author zhanghao
	 * @param request 单个查询新人专享请求参数 {@link NewcomerPurchaseDetailRequest}
	 * @return 新人专享设置详情 {@link NewcomerPurchaseDetailResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchase/detail-for-mobile")
	BaseResponse<NewcomerPurchaseDetailResponse> detailForMobile(@RequestBody @Valid NewcomerPurchaseDetailRequest request);


	/**
	 * 单个查询新人专享设置API
	 *
	 * @author zhanghao
	 * @param
	 * @return 新人专享设置详情 {@link NewcomerPurchaseConfigByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/detail")
	BaseResponse<NewcomerPurchaseConfigByIdResponse> detail();

	/**
	 * 检查活动是否有效
	 *
	 * @author xuyunpeng
	 * @param
	 * @return 有效标识 {@link Boolean}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/check-active")
	BaseResponse<Boolean> checkActive();

}

