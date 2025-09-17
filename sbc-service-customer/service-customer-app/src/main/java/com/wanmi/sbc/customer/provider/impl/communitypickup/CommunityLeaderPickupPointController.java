package com.wanmi.sbc.customer.provider.impl.communitypickup;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointDelByIdListRequest;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointDelByIdRequest;
import com.wanmi.sbc.customer.communitypickup.model.root.CommunityLeaderPickupPoint;
import com.wanmi.sbc.customer.communitypickup.service.CommunityLeaderPickupPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>团长自提点表保存服务接口实现</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@RestController
@Validated
public class CommunityLeaderPickupPointController implements CommunityLeaderPickupPointProvider {
	@Autowired
	private CommunityLeaderPickupPointService communityLeaderPickupPointService;

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CommunityLeaderPickupPointDelByIdRequest communityLeaderPickupPointDelByIdRequest) {
		CommunityLeaderPickupPoint communityLeaderPickupPoint = KsBeanUtil.convert(communityLeaderPickupPointDelByIdRequest, CommunityLeaderPickupPoint.class);
		communityLeaderPickupPoint.setDelFlag(DeleteFlag.YES);
		communityLeaderPickupPointService.deleteById(communityLeaderPickupPoint);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid CommunityLeaderPickupPointDelByIdListRequest communityLeaderPickupPointDelByIdListRequest) {
		List<CommunityLeaderPickupPoint> communityLeaderPickupPointList = communityLeaderPickupPointDelByIdListRequest.getPickupPointIdList().stream()
			.map(PickupPointId -> {
				CommunityLeaderPickupPoint communityLeaderPickupPoint = KsBeanUtil.convert(PickupPointId, CommunityLeaderPickupPoint.class);
				communityLeaderPickupPoint.setDelFlag(DeleteFlag.YES);
				return communityLeaderPickupPoint;
			}).collect(Collectors.toList());
		communityLeaderPickupPointService.deleteByIdList(communityLeaderPickupPointList);
		return BaseResponse.SUCCESSFUL();
	}

}

