package com.wanmi.sbc.customer.api.provider.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.communityleader.*;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionExportRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderByIdResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderListResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购团长表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@FeignClient(value = "${application.customer.name}", contextId = "CommunityLeaderQueryProvider")
public interface CommunityLeaderQueryProvider {

	/**
	 * 分页查询社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderPageReq 分页请求参数和筛选对象 {@link CommunityLeaderPageRequest}
	 * @return 社区团购团长表分页列表信息 {@link CommunityLeaderPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/page")
	BaseResponse<CommunityLeaderPageResponse> page(@RequestBody @Valid CommunityLeaderPageRequest communityLeaderPageReq);

	/**
	 * 列表查询社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderListReq 列表请求参数和筛选对象 {@link CommunityLeaderListRequest}
	 * @return 社区团购团长表的列表信息 {@link CommunityLeaderListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/list")
	BaseResponse<CommunityLeaderListResponse> list(@RequestBody @Valid CommunityLeaderListRequest communityLeaderListReq);

	/**
	 * 单个查询社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderByIdRequest 单个查询社区团购团长表请求参数 {@link CommunityLeaderByIdRequest}
	 * @return 社区团购团长表详情 {@link CommunityLeaderByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/get-by-id")
	BaseResponse<CommunityLeaderByIdResponse> getById(@RequestBody @Valid CommunityLeaderByIdRequest communityLeaderByIdRequest);

	/**
	 * 单个查询社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderByIdRequest 单个查询社区团购团长表请求参数 {@link CommunityLeaderByIdRequest}
	 * @return 社区团购团长表详情 {@link CommunityLeaderByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/get-by-customer-id")
	BaseResponse<CommunityLeaderByIdResponse> getByCustomerId(@RequestBody @Valid CommunityLeaderByIdRequest communityLeaderByIdRequest);

	/**
	 * 根据条件查询导出查询团长佣金数量
	 * @param request
	 * @return
	 */
	@PostMapping("/customer/${application.marketing.version}/communityleader/commission/export/count")
	BaseResponse<Long> countForExport(@RequestBody @Valid CommunityLeaderQueryRequest request);
}

