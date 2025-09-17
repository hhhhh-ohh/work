package com.wanmi.sbc.goods.api.provider.flashpromotionactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashPromotionActivityModifyStatusRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashPromotionActivityPageRequest;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashPromotionActivityPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抢购活动查询服务Provider</p>
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
@FeignClient(value = "${application.goods.name}", contextId = "FlashPromotionActivityQueryProvider")
public interface FlashPromotionActivityQueryProvider {

	/**
	 * 分页查询抢购活动API
	 *
	 * @author xufeng
	 * @param flashPromotionActivityPageRequest 分页请求参数和筛选对象 {@link FlashPromotionActivityPageRequest}
	 * @return 抢购活动分页列表信息 {@link FlashPromotionActivityPageRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/flashpromotionactivity/page")
	BaseResponse<FlashPromotionActivityPageResponse> page(@RequestBody @Valid FlashPromotionActivityPageRequest
															flashPromotionActivityPageRequest);

	/**
	 * 限时购启动暂停活动
	 *
	 * @author xufeng
	 * @param flashPromotionActivityModifyStatusRequest 限时购启动暂停活动 {@link FlashPromotionActivityModifyStatusRequest}
	 * @return 限时购启动暂停活动 {@link FlashPromotionActivityModifyStatusRequest}
	 */
	@PostMapping("/goods/${application.goods.version}/flashpromotionactivity/modify-by-activityid")
	BaseResponse modifyByActivityId(@RequestBody @Valid FlashPromotionActivityModifyStatusRequest flashPromotionActivityModifyStatusRequest);

}

