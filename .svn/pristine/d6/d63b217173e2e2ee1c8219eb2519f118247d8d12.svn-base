package com.wanmi.sbc.marketing.provider.impl.communityactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityListRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityQueryRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityListResponse;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityPageResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import com.wanmi.sbc.marketing.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.marketing.communityrel.service.CommunityAreaRelService;
import com.wanmi.sbc.marketing.communityrel.service.CommunityLeaderRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@RestController
@Validated
public class CommunityActivityQueryController implements CommunityActivityQueryProvider {
	@Autowired
	private CommunityActivityService communityActivityService;

	@Autowired
	private CommunityAreaRelService communityAreaRelService;

	@Autowired
	private CommunityLeaderRelService communityLeaderRelService;

	@Override
	public BaseResponse<CommunityActivityPageResponse> page(@RequestBody @Valid CommunityActivityPageRequest communityActivityPageReq) {
		CommunityActivityQueryRequest queryReq = KsBeanUtil.convert(communityActivityPageReq, CommunityActivityQueryRequest.class);
		//查询仅支持帮卖团长的活动
		if(Boolean.TRUE.equals(communityActivityPageReq.getAssistLeaderFlag())) {
			List<String>  assistActivityIds = new ArrayList<>();
			assistActivityIds.addAll(communityAreaRelService.getActivityByAssistLeader(communityActivityPageReq));
			assistActivityIds.addAll(communityLeaderRelService.getActivityByAssistLeader(communityActivityPageReq));
			queryReq.setAssistActivityIds(assistActivityIds);
		}
		Page<CommunityActivity> communityActivityPage = communityActivityService.page(queryReq);
		Page<CommunityActivityVO> newPage = communityActivityPage.map(entity -> communityActivityService.wrapperVo(entity));
		MicroServicePage<CommunityActivityVO> microPage = new MicroServicePage<>(newPage, communityActivityPageReq.getPageable());
		communityActivityService.fillCommunityActivityVO(queryReq,microPage.getContent());
		CommunityActivityPageResponse finalRes = new CommunityActivityPageResponse(microPage, null);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<CommunityActivityListResponse> list(@RequestBody @Valid CommunityActivityListRequest communityActivityListReq) {
		CommunityActivityQueryRequest queryReq = KsBeanUtil.convert(communityActivityListReq, CommunityActivityQueryRequest.class);
		List<CommunityActivity> communityActivityList = communityActivityService.list(queryReq);
		List<CommunityActivityVO> newList = communityActivityList.stream().map(entity -> communityActivityService.wrapperVo(entity)).collect(Collectors.toList());
		communityActivityService.fillCommunityActivityVO(queryReq, newList);
		return BaseResponse.success(new CommunityActivityListResponse(newList));
	}

	@Override
	public BaseResponse<CommunityActivityByIdResponse> getById(@RequestBody @Valid CommunityActivityByIdRequest communityActivityByIdRequest) {
		CommunityActivity communityActivity =
				communityActivityService.getOne(communityActivityByIdRequest.getActivityId(), communityActivityByIdRequest.getStoreId());
		List<CommunityActivityVO> voList = Collections.singletonList(communityActivityService.wrapperVo(communityActivity));
		CommunityActivityQueryRequest queryRequest = CommunityActivityQueryRequest.builder()
				.commissionRelFlag(communityActivityByIdRequest.getCommissionRelFlag())
				.saleRelFlag(communityActivityByIdRequest.getSaleRelFlag())
				.leaderId(communityActivityByIdRequest.getLeaderId())
				.commissionRelFlag(communityActivityByIdRequest.getCommissionRelFlag())
				.skuRelFlag(communityActivityByIdRequest.getSkuRelFlag())
				.build();
		communityActivityService.fillCommunityActivityVO(queryRequest, voList);
		return BaseResponse.success(new CommunityActivityByIdResponse(voList.get(0)));
	}
}

