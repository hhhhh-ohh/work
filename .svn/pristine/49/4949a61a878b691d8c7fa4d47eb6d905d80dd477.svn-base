package com.wanmi.sbc.marketing.api.provider.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerAddRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动统计会员信息表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityStatisticsCustomerProvider")
public interface CommunityStatisticsCustomerProvider {

	/**
	 * 新增社区团购活动统计会员信息表API
	 *
	 * @author dyt
	 * @param communityStatisticsCustomerAddRequest 社区团购活动统计会员信息表新增参数结构 {@link CommunityStatisticsCustomerAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitystatistics/customer/add")
	BaseResponse add(@RequestBody @Valid CommunityStatisticsCustomerAddRequest communityStatisticsCustomerAddRequest);
}

