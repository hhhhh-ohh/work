package com.wanmi.sbc.marketing.api.provider.communityactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityPageResponse;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityListRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityListResponse;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区团购活动表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityActivityQueryProvider")
public interface CommunityActivityQueryProvider {

	/**
	 * 分页查询社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityPageReq 分页请求参数和筛选对象 {@link CommunityActivityPageRequest}
	 * @return 社区团购活动表分页列表信息 {@link CommunityActivityPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/page")
	BaseResponse<CommunityActivityPageResponse> page(@RequestBody @Valid CommunityActivityPageRequest communityActivityPageReq);

	/**
	 * 列表查询社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityListReq 列表请求参数和筛选对象 {@link CommunityActivityListRequest}
	 * @return 社区团购活动表的列表信息 {@link CommunityActivityListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/list")
	BaseResponse<CommunityActivityListResponse> list(@RequestBody @Valid CommunityActivityListRequest communityActivityListReq);

	/**
	 * 单个查询社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityByIdRequest 单个查询社区团购活动表请求参数 {@link CommunityActivityByIdRequest}
	 * @return 社区团购活动表详情 {@link CommunityActivityByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/get-by-id")
	BaseResponse<CommunityActivityByIdResponse> getById(@RequestBody @Valid CommunityActivityByIdRequest communityActivityByIdRequest);

}

