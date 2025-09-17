package com.wanmi.sbc.marketing.provider.impl.communityregionsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.communityregionsetting.CommunityRegionSettingProvider;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingAddRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingDelByIdListRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingModifyRequest;
import com.wanmi.sbc.marketing.communityregionsetting.service.CommunityRegionAreaSettingService;
import com.wanmi.sbc.marketing.communityregionsetting.service.CommunityRegionLeaderSettingService;
import com.wanmi.sbc.marketing.communityregionsetting.service.CommunityRegionSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>社区拼团区域设置表保存服务接口实现</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@RestController
@Validated
public class CommunityRegionSettingController implements CommunityRegionSettingProvider {
	@Autowired
	private CommunityRegionSettingService communityRegionSettingService;

	@Autowired
	private CommunityRegionLeaderSettingService communityRegionLeaderSettingService;

	@Autowired
	private CommunityRegionAreaSettingService communityRegionAreaSettingService;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityRegionSettingAddRequest communityRegionSettingAddRequest) {
		communityRegionSettingService.exists(communityRegionSettingAddRequest.getRegionName(), null, communityRegionSettingAddRequest.getStoreId());
		communityRegionLeaderSettingService.exists(communityRegionSettingAddRequest.getPickupPointIdList(), null, communityRegionSettingAddRequest.getStoreId());
		communityRegionAreaSettingService.exists(communityRegionSettingAddRequest.getAreaIdList(), null, communityRegionSettingAddRequest.getStoreId());
		communityRegionSettingService.add(communityRegionSettingAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid CommunityRegionSettingModifyRequest communityRegionSettingModifyRequest) {
		communityRegionSettingService.exists(communityRegionSettingModifyRequest.getRegionName(),
				communityRegionSettingModifyRequest.getRegionId(), communityRegionSettingModifyRequest.getStoreId());
		communityRegionLeaderSettingService.exists(communityRegionSettingModifyRequest.getPickupPointIdList(),
				communityRegionSettingModifyRequest.getRegionId(), communityRegionSettingModifyRequest.getStoreId());
		communityRegionAreaSettingService.exists(communityRegionSettingModifyRequest.getAreaIdList(), communityRegionSettingModifyRequest.getRegionId(),
				communityRegionSettingModifyRequest.getStoreId());
		communityRegionSettingService.modify(communityRegionSettingModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CommunityRegionSettingDelByIdRequest communityRegionSettingDelByIdRequest) {
		communityRegionSettingService.deleteById(communityRegionSettingDelByIdRequest.getRegionId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid CommunityRegionSettingDelByIdListRequest communityRegionSettingDelByIdListRequest) {
		communityRegionSettingService.deleteByIdList(communityRegionSettingDelByIdListRequest.getRegionIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

