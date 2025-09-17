package com.wanmi.sbc.customer.provider.impl.communitypickup;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointByIdRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointPageRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointQueryRequest;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointByIdResponse;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointListResponse;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointPageResponse;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.communityleader.service.CommunityLeaderService;
import com.wanmi.sbc.customer.communitypickup.model.root.CommunityLeaderPickupPoint;
import com.wanmi.sbc.customer.communitypickup.service.CommunityLeaderPickupPointService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>团长自提点表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@RestController
@Validated
public class CommunityLeaderPickupPointQueryController implements CommunityLeaderPickupPointQueryProvider {
	@Autowired
	private CommunityLeaderPickupPointService communityLeaderPickupPointService;

	@Autowired
	private CommunityLeaderService communityLeaderService;

	@Override
	public BaseResponse<CommunityLeaderPickupPointPageResponse> page(@RequestBody @Valid CommunityLeaderPickupPointPageRequest communityLeaderPickupPointPageReq) {
		CommunityLeaderPickupPointQueryRequest queryReq = KsBeanUtil.convert(communityLeaderPickupPointPageReq, CommunityLeaderPickupPointQueryRequest.class);
		queryReq.setAssistLeaderIds(communityLeaderService.getLeaderByAssistFlag(queryReq));
		//区域下没有团长
		if(queryReq.getAssistFlag() != null
				&& CollectionUtils.isEmpty(queryReq.getAssistLeaderIds())){
			return BaseResponse.success(new CommunityLeaderPickupPointPageResponse(new MicroServicePage<>(Collections.emptyList())));
		}
		Page<CommunityLeaderPickupPoint> communityLeaderPickupPointPage = communityLeaderPickupPointService.page(queryReq);
		Page<CommunityLeaderPickupPointVO> newPage = communityLeaderPickupPointPage.map(entity -> communityLeaderPickupPointService.wrapperVo(entity));
		//填充帮卖
		if(Boolean.TRUE.equals(communityLeaderPickupPointPageReq.getAssistRelFlag())){
			communityLeaderService.fillAssistFlag(newPage.getContent());
		}
		MicroServicePage<CommunityLeaderPickupPointVO> microPage = new MicroServicePage<>(newPage, communityLeaderPickupPointPageReq.getPageable());
		CommunityLeaderPickupPointPageResponse finalRes = new CommunityLeaderPickupPointPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<CommunityLeaderPickupPointListResponse> list(@RequestBody @Valid CommunityLeaderPickupPointListRequest communityLeaderPickupPointListReq) {
		CommunityLeaderPickupPointQueryRequest queryReq = KsBeanUtil.convert(communityLeaderPickupPointListReq, CommunityLeaderPickupPointQueryRequest.class);
		queryReq.setAssistLeaderIds(communityLeaderService.getLeaderByAssistFlag(queryReq));
		//区域下没有团长
		if (queryReq.getAssistFlag() != null
				&& CollectionUtils.isEmpty(queryReq.getAssistLeaderIds())) {
			return BaseResponse.success(new CommunityLeaderPickupPointListResponse(Collections.emptyList()));
		}
		List<CommunityLeaderPickupPoint> communityLeaderPickupPointList = communityLeaderPickupPointService.list(queryReq);
		List<CommunityLeaderPickupPointVO> newList = communityLeaderPickupPointList.stream().map(entity -> communityLeaderPickupPointService.wrapperVo(entity)).collect(Collectors.toList());
		//填充帮卖
		if (Boolean.TRUE.equals(communityLeaderPickupPointListReq.getAssistRelFlag())) {
			communityLeaderService.fillAssistFlag(newList);
		}
		return BaseResponse.success(new CommunityLeaderPickupPointListResponse(newList));
	}

	@Override
	public BaseResponse<CommunityLeaderPickupPointByIdResponse> getById(@RequestBody @Valid CommunityLeaderPickupPointByIdRequest communityLeaderPickupPointByIdRequest) {
		CommunityLeaderPickupPoint communityLeaderPickupPoint =
				communityLeaderPickupPointService.getOne(communityLeaderPickupPointByIdRequest.getPickupPointId());
		CommunityLeaderPickupPointVO vo = communityLeaderPickupPointService.wrapperVo(communityLeaderPickupPoint);
		return BaseResponse.success(new CommunityLeaderPickupPointByIdResponse(vo));
	}

}

