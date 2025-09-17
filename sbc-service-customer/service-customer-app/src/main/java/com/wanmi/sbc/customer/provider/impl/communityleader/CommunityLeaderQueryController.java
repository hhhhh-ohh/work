package com.wanmi.sbc.customer.provider.impl.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderListRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderPageRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderQueryRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointQueryRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionExportRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionPageRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderByIdResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderListResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderPageResponse;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.communityleader.model.root.CommunityLeader;
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
 * <p>社区团购团长表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@RestController
@Validated
public class CommunityLeaderQueryController implements CommunityLeaderQueryProvider {
	@Autowired
	private CommunityLeaderService communityLeaderService;

	@Autowired
	private CommunityLeaderPickupPointService communityLeaderPickupPointService;

	@Override
	public BaseResponse<CommunityLeaderPageResponse> page(@RequestBody @Valid CommunityLeaderPageRequest communityLeaderPageReq) {
		CommunityLeaderQueryRequest queryReq = KsBeanUtil.convert(communityLeaderPageReq, CommunityLeaderQueryRequest.class);
		queryReq.setAreaLeaderIdList(communityLeaderPickupPointService.getLeaderByAreaIds(queryReq));
		//区域下没有团长
		if(CollectionUtils.isNotEmpty(queryReq.getAreaIds())
				&& CollectionUtils.isEmpty(queryReq.getAreaLeaderIdList())){
			return BaseResponse.success(new CommunityLeaderPageResponse( new MicroServicePage<>(Collections.emptyList()), Collections.emptyList()));
		}
		Page<CommunityLeader> communityLeaderPage = communityLeaderService.page(queryReq);
		Page<CommunityLeaderVO> newPage = communityLeaderPage.map(entity -> communityLeaderService.wrapperVo(entity));
		MicroServicePage<CommunityLeaderVO> microPage = new MicroServicePage<>(newPage, communityLeaderPageReq.getPageable());
		//提供自提点信息
		List<CommunityLeaderPickupPointVO> pickupPoints = Collections.emptyList();
		if(CollectionUtils.isNotEmpty(newPage.getContent())){
			List<String> ids = newPage.getContent().stream().map(CommunityLeaderVO::getLeaderId).collect(Collectors.toList());
			if(Boolean.TRUE.equals(communityLeaderPageReq.getPickupFlag())){
				CommunityLeaderPickupPointQueryRequest pointRequest = CommunityLeaderPickupPointQueryRequest.builder().leaderIds(ids).build();
				List<CommunityLeaderPickupPoint> points = communityLeaderPickupPointService.list(pointRequest);
				pickupPoints = points.stream().map(entity -> communityLeaderPickupPointService.wrapperVo(entity)).collect(Collectors.toList());
			}
		}
		CommunityLeaderPageResponse finalRes = new CommunityLeaderPageResponse(microPage, pickupPoints);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<CommunityLeaderListResponse> list(@RequestBody @Valid CommunityLeaderListRequest communityLeaderListReq) {
		CommunityLeaderQueryRequest queryReq = KsBeanUtil.convert(communityLeaderListReq, CommunityLeaderQueryRequest.class);
		queryReq.setAreaLeaderIdList(communityLeaderPickupPointService.getLeaderByAreaIds(queryReq));
		//区域下没有团长
		if(CollectionUtils.isNotEmpty(queryReq.getAreaIds())
				&& CollectionUtils.isEmpty(queryReq.getAreaLeaderIdList())){
			return BaseResponse.success(new CommunityLeaderListResponse(Collections.emptyList(), Collections.emptyList()));
		}
		List<CommunityLeader> communityLeaderList = communityLeaderService.list(queryReq);
		List<CommunityLeaderVO> newList = communityLeaderList.stream().map(entity -> communityLeaderService.wrapperVo(entity)).collect(Collectors.toList());
		//提供自提点信息
		List<CommunityLeaderPickupPointVO> pickupPoints = Collections.emptyList();
		if (CollectionUtils.isNotEmpty(newList)) {
			List<String> ids = newList.stream().map(CommunityLeaderVO::getLeaderId).collect(Collectors.toList());
			//提供自提点信息
			if (Boolean.TRUE.equals(communityLeaderListReq.getPickupFlag())) {
				CommunityLeaderPickupPointQueryRequest pointRequest = CommunityLeaderPickupPointQueryRequest.builder().leaderIds(ids).build();
				List<CommunityLeaderPickupPoint> points = communityLeaderPickupPointService.list(pointRequest);
				pickupPoints = points.stream().map(entity -> communityLeaderPickupPointService.wrapperVo(entity)).collect(Collectors.toList());
			}
		}
		return BaseResponse.success(new CommunityLeaderListResponse(newList, pickupPoints));
	}

	@Override
	public BaseResponse<CommunityLeaderByIdResponse> getById(@RequestBody @Valid CommunityLeaderByIdRequest communityLeaderByIdRequest) {
		CommunityLeader communityLeader =
				communityLeaderService.getOne(communityLeaderByIdRequest.getLeaderId());
		return BaseResponse.success(new CommunityLeaderByIdResponse(communityLeaderService.wrapperVo(communityLeader), null));
	}

	@Override
	public BaseResponse<CommunityLeaderByIdResponse> getByCustomerId(@RequestBody @Valid CommunityLeaderByIdRequest communityLeaderByIdRequest) {
		CommunityLeader communityLeader =
				communityLeaderService.getByCustomer(communityLeaderByIdRequest.getCustomerId());
		return BaseResponse.success(new CommunityLeaderByIdResponse(communityLeaderService.wrapperVo(communityLeader), null));
	}

	@Override
	public BaseResponse<Long> countForExport(@RequestBody @Valid CommunityLeaderQueryRequest request) {
		CommunityLeaderQueryRequest queryReq = new CommunityLeaderQueryRequest();
		KsBeanUtil.copyPropertiesThird(request, queryReq);
		Long total = communityLeaderService.count(queryReq);
		return BaseResponse.success(total);
	}

}

