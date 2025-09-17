package com.wanmi.sbc.customer.provider.impl.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderProvider;
import com.wanmi.sbc.customer.api.request.communityleader.*;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderAddResponse;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.communityleader.model.root.CommunityLeader;
import com.wanmi.sbc.customer.communityleader.service.CommunityLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>社区团购团长表保存服务接口实现</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@RestController
@Validated
public class CommunityLeaderController implements CommunityLeaderProvider {
	@Autowired
	private CommunityLeaderService communityLeaderService;

	@Override
	public BaseResponse<CommunityLeaderAddResponse> add(@RequestBody @Valid CommunityLeaderAddRequest communityLeaderAddRequest) {
		communityLeaderService.exists(communityLeaderAddRequest.getLeaderAccount(), null);
		CommunityLeader leader = communityLeaderService.add(communityLeaderAddRequest);
		CommunityLeaderVO vo = communityLeaderService.wrapperVo(leader);
		return BaseResponse.success(CommunityLeaderAddResponse.builder().communityLeaderVO(vo).build());
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid CommunityLeaderModifyRequest communityLeaderModifyRequest) {
		communityLeaderService.modify(communityLeaderModifyRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse checkById(@RequestBody @Valid CommunityLeaderCheckByIdRequest communityLeaderCheckByIdRequest) {
		communityLeaderService.check(communityLeaderCheckByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse assistById(@RequestBody @Valid CommunityLeaderModifyAssistByIdRequest communityLeaderModifyAssistByIdRequest) {
		communityLeaderService.assist(communityLeaderModifyAssistByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CommunityLeaderDelByIdRequest communityLeaderDelByIdRequest) {
		communityLeaderService.deleteById(communityLeaderDelByIdRequest.getLeaderId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid CommunityLeaderDelByIdListRequest communityLeaderDelByIdListRequest) {
		communityLeaderService.deleteByIdList(communityLeaderDelByIdListRequest.getLeaderIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

