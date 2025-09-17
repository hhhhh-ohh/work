package com.wanmi.sbc.marketing.api.provider.communityregionsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingPageRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingUsedAreaRequest;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingPageResponse;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingListRequest;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingListResponse;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingByIdRequest;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingUsedAreaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区拼团区域设置表查询服务Provider</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityRegionSettingQueryProvider")
public interface CommunityRegionSettingQueryProvider {

	/**
	 * 分页查询社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingPageReq 分页请求参数和筛选对象 {@link CommunityRegionSettingPageRequest}
	 * @return 社区拼团区域设置表分页列表信息 {@link CommunityRegionSettingPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/page")
	BaseResponse<CommunityRegionSettingPageResponse> page(@RequestBody @Valid CommunityRegionSettingPageRequest communityRegionSettingPageReq);

	/**
	 * 列表查询社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingListReq 列表请求参数和筛选对象 {@link CommunityRegionSettingListRequest}
	 * @return 社区拼团区域设置表的列表信息 {@link CommunityRegionSettingListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/list")
	BaseResponse<CommunityRegionSettingListResponse> list(@RequestBody @Valid CommunityRegionSettingListRequest communityRegionSettingListReq);

	/**
	 * 单个查询社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingByIdRequest 单个查询社区拼团区域设置表请求参数 {@link CommunityRegionSettingByIdRequest}
	 * @return 社区拼团区域设置表详情 {@link CommunityRegionSettingByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/get-by-id")
	BaseResponse<CommunityRegionSettingByIdResponse> getById(@RequestBody @Valid CommunityRegionSettingByIdRequest communityRegionSettingByIdRequest);

	/**
	 * 获取已被使用地区和自提点的API
	 *
	 * @author dyt
	 * @param request 列表请求参数和筛选对象 {@link CommunityRegionSettingUsedAreaRequest}
	 * @return 社区拼团区域设置表已被使用的地区结果 {@link CommunityRegionSettingUsedAreaResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/get-used-area")
	BaseResponse<CommunityRegionSettingUsedAreaResponse> getUsedArea(@RequestBody @Valid CommunityRegionSettingUsedAreaRequest request);

}

