package com.wanmi.sbc.marketing.provider.impl.communitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communitysetting.CommunitySettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingByIdRequest;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingListRequest;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingQueryRequest;
import com.wanmi.sbc.marketing.api.response.communitysetting.CommunitySettingByIdResponse;
import com.wanmi.sbc.marketing.api.response.communitysetting.CommunitySettingListResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunitySettingVO;
import com.wanmi.sbc.marketing.communitysetting.model.root.CommunitySetting;
import com.wanmi.sbc.marketing.communitysetting.service.CommunitySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区拼团商家设置表查询服务接口实现</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@RestController
@Validated
public class CommunitySettingQueryController implements CommunitySettingQueryProvider {
	@Autowired
	private CommunitySettingService communitySettingService;

	@Override
	public BaseResponse<CommunitySettingListResponse> list(@RequestBody @Valid CommunitySettingListRequest communitySettingListReq) {
		CommunitySettingQueryRequest queryReq = KsBeanUtil.convert(communitySettingListReq, CommunitySettingQueryRequest.class);
		List<CommunitySetting> communitySettingList = communitySettingService.list(queryReq);
		List<CommunitySettingVO> newList = communitySettingList.stream().map(entity -> communitySettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new CommunitySettingListResponse(newList));
	}

	@Override
	public BaseResponse<CommunitySettingByIdResponse> getById(@RequestBody @Valid CommunitySettingByIdRequest communitySettingByIdRequest) {
		CommunitySetting communitySetting = communitySettingService.getOne(communitySettingByIdRequest.getStoreId());
		return BaseResponse.success(new CommunitySettingByIdResponse(communitySettingService.wrapperVo(communitySetting)));
	}
}

