package com.wanmi.sbc.customer.api.provider.communitypickup;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointDelByIdListRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointDelByIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>团长自提点表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@FeignClient(value = "${application.customer.name}", contextId = "CommunityLeaderPickupPointProvider")
public interface CommunityLeaderPickupPointProvider {

	/**
	 * 单个删除团长自提点表API
	 *
	 * @author dyt
	 * @param communityLeaderPickupPointDelByIdRequest 单个删除参数结构 {@link CommunityLeaderPickupPointDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleaderpickuppoint/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid CommunityLeaderPickupPointDelByIdRequest communityLeaderPickupPointDelByIdRequest);

	/**
	 * 批量删除团长自提点表API
	 *
	 * @author dyt
	 * @param communityLeaderPickupPointDelByIdListRequest 批量删除参数结构 {@link CommunityLeaderPickupPointDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleaderpickuppoint/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid CommunityLeaderPickupPointDelByIdListRequest communityLeaderPickupPointDelByIdListRequest);

}

