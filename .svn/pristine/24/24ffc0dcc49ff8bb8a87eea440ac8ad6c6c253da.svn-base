package com.wanmi.sbc.marketing.provider.impl.communitystatistics;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsCustomerQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerCountCustomerRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerListRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerQueryRequest;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsCustomerCountCoustomerResponse;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsCustomerListResponse;
import com.wanmi.sbc.marketing.api.response.communitystatistics.CommunityStatisticsListResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsCustomerVO;
import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatisticsCustomer;
import com.wanmi.sbc.marketing.communitystatistics.service.CommunityStatisticsCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动统计会员信息表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@RestController
@Validated
public class CommunityStatisticsCustomerQueryController implements CommunityStatisticsCustomerQueryProvider {
	@Autowired
	private CommunityStatisticsCustomerService communityStatisticsCustomerService;

	@Override
	public BaseResponse<CommunityStatisticsCustomerListResponse> list(@RequestBody @Valid CommunityStatisticsCustomerListRequest communityStatisticsListReq) {
		CommunityStatisticsCustomerQueryRequest queryReq = KsBeanUtil.convert(communityStatisticsListReq, CommunityStatisticsCustomerQueryRequest.class);
		List<CommunityStatisticsCustomer> communityStatisticsList
				= communityStatisticsCustomerService.list(queryReq);
		List<CommunityStatisticsCustomerVO> newList
				= communityStatisticsList.stream().map(
				entity -> communityStatisticsCustomerService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(
				CommunityStatisticsCustomerListResponse.builder().communityStatisticsCustomerVOList(newList).build());
	}

	@Override
	public BaseResponse<Long> count(@RequestBody @Valid CommunityStatisticsCustomerListRequest request) {
		CommunityStatisticsCustomerQueryRequest queryReq = KsBeanUtil.convert(request, CommunityStatisticsCustomerQueryRequest.class);
		return BaseResponse.success(communityStatisticsCustomerService.count(queryReq));
	}

	@Override
	public BaseResponse<CommunityStatisticsCustomerCountCoustomerResponse> countCustomer(@RequestBody @Valid CommunityStatisticsCustomerCountCustomerRequest request) {
		Map<String, Long> result = communityStatisticsCustomerService.countCustomerGroupLeader(request);
		return BaseResponse.success(CommunityStatisticsCustomerCountCoustomerResponse.builder().result(result).build());
	}
}

