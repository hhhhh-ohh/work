package com.wanmi.sbc.marketing.api.provider.communityactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityactivity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区团购活动表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityActivityProvider")
public interface CommunityActivityProvider {

	/**
	 * 新增社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityAddRequest 社区团购活动表新增参数结构 {@link CommunityActivityAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/add")
	BaseResponse add(@RequestBody @Valid CommunityActivityAddRequest communityActivityAddRequest);

	/**
	 * 修改社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityModifyRequest 社区团购活动表修改参数结构 {@link CommunityActivityModifyRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/modify")
	BaseResponse modify(@RequestBody @Valid CommunityActivityModifyRequest communityActivityModifyRequest);

	/**
	 * 单个删除社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityDelByIdRequest 单个删除参数结构 {@link CommunityActivityDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid CommunityActivityDelByIdRequest communityActivityDelByIdRequest);

	/**
	 * 批量删除社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityDelByIdListRequest 批量删除参数结构 {@link CommunityActivityDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid CommunityActivityDelByIdListRequest communityActivityDelByIdListRequest);

	/**
	 * 批量删除社区团购活动表API
	 *
	 * @author dyt
	 * @param communityActivityDelByIdListRequest 批量删除参数结构 {@link CommunityActivityDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityactivity/modify-generate-flag-by-id-list")
	BaseResponse modifyGenerateFlagByIdList(@RequestBody @Valid CommunityActivityModifyGenerateFlagByIdListRequest communityActivityDelByIdListRequest);
}

