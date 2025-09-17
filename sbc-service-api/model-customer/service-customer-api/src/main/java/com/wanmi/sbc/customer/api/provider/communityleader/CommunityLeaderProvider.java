package com.wanmi.sbc.customer.api.provider.communityleader;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.communityleader.*;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购团长表保存服务Provider</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@FeignClient(value = "${application.customer.name}", contextId = "CommunityLeaderProvider")
public interface CommunityLeaderProvider {

	/**
	 * 新增社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderAddRequest 社区团购团长表新增参数结构 {@link CommunityLeaderAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/add")
	BaseResponse<CommunityLeaderAddResponse> add(@RequestBody @Valid CommunityLeaderAddRequest communityLeaderAddRequest);

	/**
	 * 修改社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderModifyRequest 社区团购团长表修改参数结构 {@link CommunityLeaderModifyRequest}
	 * @return 修改的社区团购团长表信息 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/modify")
	BaseResponse modify(@RequestBody @Valid CommunityLeaderModifyRequest communityLeaderModifyRequest);

	/**
	 * 单个审核社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderCheckByIdRequest 单个审核参数结构 {@link CommunityLeaderCheckByIdRequest}
	 * @return 审核结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/check-by-id")
	BaseResponse checkById(@RequestBody @Valid CommunityLeaderCheckByIdRequest communityLeaderCheckByIdRequest);

	/**
	 * 单个帮卖社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderModifyAssistByIdRequest 单个帮卖参数结构 {@link CommunityLeaderModifyAssistByIdRequest}
	 * @return 审核结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/assist-by-id")
	BaseResponse assistById(@RequestBody @Valid CommunityLeaderModifyAssistByIdRequest communityLeaderModifyAssistByIdRequest);

	/**
	 * 单个删除社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderDelByIdRequest 单个删除参数结构 {@link CommunityLeaderDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid CommunityLeaderDelByIdRequest communityLeaderDelByIdRequest);

	/**
	 * 批量删除社区团购团长表API
	 *
	 * @author dyt
	 * @param communityLeaderDelByIdListRequest 批量删除参数结构 {@link CommunityLeaderDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/communityleader/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid CommunityLeaderDelByIdListRequest communityLeaderDelByIdListRequest);

}

