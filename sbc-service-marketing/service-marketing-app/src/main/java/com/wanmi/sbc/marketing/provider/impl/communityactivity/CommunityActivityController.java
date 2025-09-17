package com.wanmi.sbc.marketing.provider.impl.communityactivity;

import com.wanmi.sbc.marketing.api.request.communityactivity.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityProvider;
import com.wanmi.sbc.marketing.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.marketing.communityactivity.model.root.CommunityActivity;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>社区团购活动表保存服务接口实现</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@RestController
@Validated
public class CommunityActivityController implements CommunityActivityProvider {
	@Autowired
	private CommunityActivityService communityActivityService;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityActivityAddRequest communityActivityAddRequest) {
		communityActivityService.add(communityActivityAddRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid CommunityActivityModifyRequest communityActivityModifyRequest) {
		communityActivityService.modify(communityActivityModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CommunityActivityDelByIdRequest communityActivityDelByIdRequest) {
		communityActivityService.deleteById(communityActivityDelByIdRequest.getActivityId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid CommunityActivityDelByIdListRequest communityActivityDelByIdListRequest) {
		communityActivityService.deleteByIdList(communityActivityDelByIdListRequest.getActivityIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyGenerateFlagByIdList(@RequestBody @Valid CommunityActivityModifyGenerateFlagByIdListRequest request) {
		communityActivityService.modifyGenerateFlagByIds(request.getActivityIdList(), request.getGenerateFlag(),
				request.getGenerateTime());
		return BaseResponse.SUCCESSFUL();
	}
}

