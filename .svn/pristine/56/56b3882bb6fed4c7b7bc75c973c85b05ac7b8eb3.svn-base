package com.wanmi.sbc.marketing.api.provider.communityregionsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingAddRequest;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingAddResponse;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingModifyRequest;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingModifyResponse;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.CommunityRegionSettingDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区拼团区域设置表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityRegionSettingProvider")
public interface CommunityRegionSettingProvider {

	/**
	 * 新增社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingAddRequest 社区拼团区域设置表新增参数结构 {@link CommunityRegionSettingAddRequest}
	 * @return 新增的社区拼团区域设置表信息 {@link CommunityRegionSettingAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/add")
	BaseResponse add(@RequestBody @Valid CommunityRegionSettingAddRequest communityRegionSettingAddRequest);

	/**
	 * 修改社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingModifyRequest 社区拼团区域设置表修改参数结构 {@link CommunityRegionSettingModifyRequest}
	 * @return 修改的社区拼团区域设置表信息 {@link CommunityRegionSettingModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/modify")
	BaseResponse modify(@RequestBody @Valid CommunityRegionSettingModifyRequest communityRegionSettingModifyRequest);

	/**
	 * 单个删除社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingDelByIdRequest 单个删除参数结构 {@link CommunityRegionSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid CommunityRegionSettingDelByIdRequest communityRegionSettingDelByIdRequest);

	/**
	 * 批量删除社区拼团区域设置表API
	 *
	 * @author dyt
	 * @param communityRegionSettingDelByIdListRequest 批量删除参数结构 {@link CommunityRegionSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityregionsetting/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid CommunityRegionSettingDelByIdListRequest communityRegionSettingDelByIdListRequest);

}

