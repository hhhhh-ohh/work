package com.wanmi.sbc.marketing.api.provider.communityassist;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordLeaderCountRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordListRequest;
import com.wanmi.sbc.marketing.api.response.communityassist.CommunityAssistRecordByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityassist.CommunityAssistRecordCountLeaderResponse;
import com.wanmi.sbc.marketing.api.response.communityassist.CommunityAssistRecordListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>社区团购活动帮卖转发记录查询服务Provider</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityAssistRecordQueryProvider")
public interface CommunityAssistRecordQueryProvider {

	/**
	 * 列表查询社区团购活动帮卖转发记录API
	 *
	 * @author dyt
	 * @param communityAssistRecordListReq 列表请求参数和筛选对象 {@link CommunityAssistRecordListRequest}
	 * @return 社区团购活动帮卖转发记录的列表信息 {@link CommunityAssistRecordListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/list")
	BaseResponse<CommunityAssistRecordListResponse> list(@RequestBody @Valid CommunityAssistRecordListRequest communityAssistRecordListReq);

	/**
	 * 单个查询社区团购活动帮卖转发记录API
	 *
	 * @author dyt
	 * @param communityAssistRecordByIdRequest 单个查询社区团购活动帮卖转发记录请求参数 {@link CommunityAssistRecordByIdRequest}
	 * @return 社区团购活动帮卖转发记录详情 {@link CommunityAssistRecordByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/get-by-id")
	BaseResponse<CommunityAssistRecordByIdResponse> getById(@RequestBody @Valid CommunityAssistRecordByIdRequest communityAssistRecordByIdRequest);


	/**
	 * 帮卖团长人数
	 *
	 * @author dyt
	 * @param request 统计请求 {@link CommunityAssistRecordLeaderCountRequest}
	 * @return 帮卖团长人数 {@link CommunityAssistRecordCountLeaderResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/count-leader")
	BaseResponse<CommunityAssistRecordCountLeaderResponse> countLeader(@RequestBody @Valid CommunityAssistRecordLeaderCountRequest request);

}

