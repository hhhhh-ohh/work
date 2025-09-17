package com.wanmi.sbc.marketing.provider.impl.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsCustomerProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerAddRequest;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatisticsCustomer;
import com.wanmi.sbc.marketing.communitystatistics.service.CommunityStatisticsCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动统计会员信息表保存服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@RestController
@Validated
public class CommunityStatisticsCustomerController implements CommunityStatisticsCustomerProvider {
	@Autowired
	private CommunityStatisticsCustomerService communityStatisticsCustomerService;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityStatisticsCustomerAddRequest communityStatisticsCustomerAddRequest) {
		CommunityStatisticsCustomer communityStatistics
				= KsBeanUtil.convert(communityStatisticsCustomerAddRequest, CommunityStatisticsCustomer.class);
		communityStatisticsCustomerService.add(communityStatistics);
		return BaseResponse.SUCCESSFUL();
	}
}

