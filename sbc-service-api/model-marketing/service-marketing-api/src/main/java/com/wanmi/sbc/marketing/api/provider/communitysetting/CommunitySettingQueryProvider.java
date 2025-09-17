package com.wanmi.sbc.marketing.api.provider.communitysetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingListRequest;
import com.wanmi.sbc.marketing.api.response.communitysetting.CommunitySettingListResponse;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingByIdRequest;
import com.wanmi.sbc.marketing.api.response.communitysetting.CommunitySettingByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区拼团商家设置表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunitySettingQueryProvider")
public interface CommunitySettingQueryProvider {

	/**
	 * 列表查询社区拼团商家设置表API
	 *
	 * @author dyt
	 * @param communitySettingListReq 列表请求参数和筛选对象 {@link CommunitySettingListRequest}
	 * @return 社区拼团商家设置表的列表信息 {@link CommunitySettingListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitysetting/list")
	BaseResponse<CommunitySettingListResponse> list(@RequestBody @Valid CommunitySettingListRequest communitySettingListReq);

	/**
	 * 单个查询社区拼团商家设置表API
	 *
	 * @author dyt
	 * @param communitySettingByIdRequest 单个查询社区拼团商家设置表请求参数 {@link CommunitySettingByIdRequest}
	 * @return 社区拼团商家设置表详情 {@link CommunitySettingByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communitysetting/get-by-id")
	BaseResponse<CommunitySettingByIdResponse> getById(@RequestBody @Valid CommunitySettingByIdRequest communitySettingByIdRequest);

}

