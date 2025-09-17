package com.wanmi.sbc.marketing.provider.impl.communityassist;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communityassist.CommunityAssistRecordProvider;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordAddRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordDelByIdListRequest;
import com.wanmi.sbc.marketing.communityassist.service.CommunityAssistRecordService;
import com.wanmi.sbc.marketing.communityassist.model.root.CommunityAssistRecord;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>社区团购活动帮卖转发记录保存服务接口实现</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@RestController
@Validated
public class CommunityAssistRecordController implements CommunityAssistRecordProvider {
	@Autowired
	private CommunityAssistRecordService communityAssistRecordService;

	@Override
	public BaseResponse add(@RequestBody @Valid CommunityAssistRecordAddRequest communityAssistRecordAddRequest) {
		CommunityAssistRecord communityAssistRecord = KsBeanUtil.convert(communityAssistRecordAddRequest, CommunityAssistRecord.class);
		communityAssistRecordService.add(communityAssistRecord);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid CommunityAssistRecordModifyRequest communityAssistRecordModifyRequest) {
		CommunityAssistRecord communityAssistRecord = KsBeanUtil.convert(communityAssistRecordModifyRequest, CommunityAssistRecord.class);
		communityAssistRecordService.modify(communityAssistRecord);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid CommunityAssistRecordDelByIdRequest communityAssistRecordDelByIdRequest) {
		communityAssistRecordService.deleteById(communityAssistRecordDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid CommunityAssistRecordDelByIdListRequest communityAssistRecordDelByIdListRequest) {
		communityAssistRecordService.deleteByIdList(communityAssistRecordDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

