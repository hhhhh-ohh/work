package com.wanmi.sbc.marketing.provider.impl.communityassist;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communityassist.CommunityAssistRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordLeaderCountRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordListRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordQueryRequest;
import com.wanmi.sbc.marketing.api.response.communityassist.CommunityAssistRecordByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityassist.CommunityAssistRecordCountLeaderResponse;
import com.wanmi.sbc.marketing.api.response.communityassist.CommunityAssistRecordListResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityAssistRecordVO;
import com.wanmi.sbc.marketing.communityassist.model.root.CommunityAssistRecord;
import com.wanmi.sbc.marketing.communityassist.service.CommunityAssistRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>社区团购活动帮卖转发记录查询服务接口实现</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@RestController
@Validated
public class CommunityAssistRecordQueryController implements CommunityAssistRecordQueryProvider {
	@Autowired
	private CommunityAssistRecordService communityAssistRecordService;

	@Override
	public BaseResponse<CommunityAssistRecordListResponse> list(@RequestBody @Valid CommunityAssistRecordListRequest communityAssistRecordListReq) {
		CommunityAssistRecordQueryRequest queryReq = KsBeanUtil.convert(communityAssistRecordListReq, CommunityAssistRecordQueryRequest.class);
		List<CommunityAssistRecord> communityAssistRecordList = communityAssistRecordService.list(queryReq);
		List<CommunityAssistRecordVO> newList = communityAssistRecordList.stream().map(entity -> communityAssistRecordService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new CommunityAssistRecordListResponse(newList));
	}

	@Override
	public BaseResponse<CommunityAssistRecordByIdResponse> getById(@RequestBody @Valid CommunityAssistRecordByIdRequest communityAssistRecordByIdRequest) {
		CommunityAssistRecord communityAssistRecord = communityAssistRecordService.getOne(communityAssistRecordByIdRequest.getId());
		return BaseResponse.success(new CommunityAssistRecordByIdResponse(communityAssistRecordService.wrapperVo(communityAssistRecord)));
	}

	@Override
	public BaseResponse<CommunityAssistRecordCountLeaderResponse> countLeader(@RequestBody @Valid CommunityAssistRecordLeaderCountRequest request) {
		Map<String, Long> result = communityAssistRecordService.countLeaderGroupActivity(request);
		return BaseResponse.success(new CommunityAssistRecordCountLeaderResponse(result));
	}
}

