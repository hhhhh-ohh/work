package com.wanmi.sbc.customer.api.provider.communitypickup;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointByIdRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointPageRequest;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointByIdResponse;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointListResponse;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>团长自提点表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@FeignClient(value = "${application.customer.name}", contextId = "CommunityLeaderPickupPointQueryProvider")
public interface CommunityLeaderPickupPointQueryProvider {

	/**
	 * 分页查询团长自提点表API
	 *
	 * @author dyt
	 * @param communityLeaderPickupPointPageReq 分页请求参数和筛选对象 {@link CommunityLeaderPickupPointPageRequest}
	 * @return 团长自提点表分页列表信息 {@link CommunityLeaderPickupPointPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleaderpickuppoint/page")
	BaseResponse<CommunityLeaderPickupPointPageResponse> page(@RequestBody @Valid CommunityLeaderPickupPointPageRequest communityLeaderPickupPointPageReq);

	/**
	 * 列表查询团长自提点表API
	 *
	 * @author dyt
	 * @param communityLeaderPickupPointListReq 列表请求参数和筛选对象 {@link CommunityLeaderPickupPointListRequest}
	 * @return 团长自提点表的列表信息 {@link CommunityLeaderPickupPointListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleaderpickuppoint/list")
	BaseResponse<CommunityLeaderPickupPointListResponse> list(@RequestBody @Valid CommunityLeaderPickupPointListRequest communityLeaderPickupPointListReq);

	/**
	 * 单个查询团长自提点表API
	 *
	 * @author dyt
	 * @param communityLeaderPickupPointByIdRequest 单个查询团长自提点表请求参数 {@link CommunityLeaderPickupPointByIdRequest}
	 * @return 团长自提点表详情 {@link CommunityLeaderPickupPointByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleaderpickuppoint/get-by-id")
	BaseResponse<CommunityLeaderPickupPointByIdResponse> getById(@RequestBody @Valid CommunityLeaderPickupPointByIdRequest communityLeaderPickupPointByIdRequest);

}

