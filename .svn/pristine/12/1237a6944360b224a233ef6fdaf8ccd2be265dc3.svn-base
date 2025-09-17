package com.wanmi.sbc.marketing.api.provider.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsAddRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsModifyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动统计信息表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityStatisticsProvider")
public interface CommunityStatisticsProvider {

	/**
	 * 新增社区团购活动统计信息表API
	 *
	 * @author dyt
	 * @param communityStatisticsAddRequest 社区团购活动统计信息表新增参数结构 {@link CommunityStatisticsAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/add")
	BaseResponse add(@RequestBody @Valid CommunityStatisticsAddRequest communityStatisticsAddRequest);

	/**
	 * 修改社区团购活动统计信息表API
	 *
	 * @author dyt
	 * @param communityStatisticsModifyRequest 社区团购活动统计信息表修改参数结构 {@link CommunityStatisticsModifyRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/modify")
	BaseResponse modify(@RequestBody @Valid CommunityStatisticsModifyRequest communityStatisticsModifyRequest);

	/**
	 * @description 商家退单退款时数据更新
	 * @author  edz
	 * @date: 2023/8/7 10:37
	 * @param communityStatisticsModifyRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/return/trade/update")
	BaseResponse returnTradeUpdate(@RequestBody @Valid CommunityStatisticsModifyRequest communityStatisticsModifyRequest);

	/**
	 * @description 入账佣金更新
	 * @author  edz
	 * @date: 2023/8/7 11:17
	 * @param communityStatisticsModifyRequest
	 * @return com.wanmi.sbc.common.base.BaseResponse
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/commission/update")
	BaseResponse commissionUpdate(@RequestBody @Valid CommunityStatisticsModifyRequest communityStatisticsModifyRequest);
}

