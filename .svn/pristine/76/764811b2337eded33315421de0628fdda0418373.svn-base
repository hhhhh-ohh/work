package com.wanmi.sbc.marketing.api.provider.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsListRequest;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动统计信息表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityStatisticsQueryProvider")
public interface CommunityStatisticsQueryProvider {

	/**
	 * 列表查询社区团购活动统计信息表API
	 *
	 * @author dyt
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return 社区团购活动统计信息表的列表信息 {@link CommunityStatisticsListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/list")
	BaseResponse<CommunityStatisticsListResponse> list(@RequestBody @Valid CommunityStatisticsListRequest communityStatisticsListReq);

	/**
	 * 汇总单个团长的统计数据
	 *
	 * @author dyt
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return 社区团购活动统计信息表的列表信息 {@link CommunityStatisticsListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/leader")
	BaseResponse<CommunityStatisticsListResponse> findByLeaderIdGroup(@RequestBody @Valid CommunityStatisticsListRequest communityStatisticsListReq);

	/**
	 * 多个团长统计数据
	 *
	 * @author dyt
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return 社区团购活动统计信息表的列表信息 {@link CommunityStatisticsListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/leader-list")
	BaseResponse<CommunityStatisticsListResponse> findByLeaderIdsGroup(@RequestBody @Valid CommunityStatisticsListRequest communityStatisticsListReq);

	/**
	 * 多个活动统计数据
	 *
	 * @author dyt
	 * @param communityStatisticsListReq 列表请求参数和筛选对象 {@link CommunityStatisticsListRequest}
	 * @return 社区团购活动统计信息表的列表信息 {@link CommunityStatisticsListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/activity-list")
	BaseResponse<CommunityStatisticsListResponse> findByActivityIdsGroup(@RequestBody @Valid CommunityStatisticsListRequest communityStatisticsListReq);
}

