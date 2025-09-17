package com.wanmi.sbc.marketing.api.provider.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerCountCustomerRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerListRequest;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsCustomerCountCoustomerResponse;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsCustomerListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动统计信息表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityStatisticsCustomerQueryProvider")
public interface CommunityStatisticsCustomerQueryProvider {

	/**
	 * 列表查询社区团购活动统计信息表API
	 *
	 * @author dyt
	 * @param communityStatisticsCustomerListRequest 列表请求参数和筛选对象 {@link CommunityStatisticsCustomerListRequest}
	 * @return 社区团购活动统计信息表的列表信息 {@link CommunityStatisticsCustomerListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/customer/list")
	BaseResponse<CommunityStatisticsCustomerListResponse> list(@RequestBody @Valid CommunityStatisticsCustomerListRequest communityStatisticsCustomerListRequest);

	/**
	 * 拓客数量查询
	 *
	 * @author dyt
	 * @param communityStatisticsCustomerListRequest 列表请求参数和筛选对象 {@link CommunityStatisticsCustomerListRequest}
	 * @return 社区团购活动统计信息表的列表信息
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/customer/count")
	BaseResponse<Long> count(@RequestBody @Valid CommunityStatisticsCustomerListRequest communityStatisticsCustomerListRequest);

	/**
	 * 批量统计取会员人数查询
	 *
	 * @author dyt
	 * @param request 列表请求参数和筛选对象 {@link CommunityStatisticsCustomerCountCustomerRequest}
	 * @return 批量统计取会员人数信息 {@link CommunityStatisticsCustomerCountCoustomerResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/customer/count-customer")
	BaseResponse<CommunityStatisticsCustomerCountCoustomerResponse> countCustomer(@RequestBody @Valid CommunityStatisticsCustomerCountCustomerRequest request);
}

