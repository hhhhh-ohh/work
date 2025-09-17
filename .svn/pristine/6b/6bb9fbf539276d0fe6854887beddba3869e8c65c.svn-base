package com.wanmi.sbc.marketing.api.provider.communityassist;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordAddRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordModifyRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>社区团购活动帮卖转发记录保存服务Provider</p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunityAssistRecordProvider")
public interface CommunityAssistRecordProvider {

	/**
	 * 新增社区团购活动帮卖转发记录API
	 *
	 * @author dyt
	 * @param communityAssistRecordAddRequest 社区团购活动帮卖转发记录新增参数结构 {@link CommunityAssistRecordAddRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/add")
	BaseResponse add(@RequestBody @Valid CommunityAssistRecordAddRequest communityAssistRecordAddRequest);

	/**
	 * 修改社区团购活动帮卖转发记录API
	 *
	 * @author dyt
	 * @param communityAssistRecordModifyRequest 社区团购活动帮卖转发记录修改参数结构 {@link CommunityAssistRecordModifyRequest}
	 * @return 操作结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/modify")
	BaseResponse modify(@RequestBody @Valid CommunityAssistRecordModifyRequest communityAssistRecordModifyRequest);

	/**
	 * 单个删除社区团购活动帮卖转发记录API
	 *
	 * @author dyt
	 * @param communityAssistRecordDelByIdRequest 单个删除参数结构 {@link CommunityAssistRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid CommunityAssistRecordDelByIdRequest communityAssistRecordDelByIdRequest);

	/**
	 * 批量删除社区团购活动帮卖转发记录API
	 *
	 * @author dyt
	 * @param communityAssistRecordDelByIdListRequest 批量删除参数结构 {@link CommunityAssistRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/communityassistrecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid CommunityAssistRecordDelByIdListRequest communityAssistRecordDelByIdListRequest);

}

