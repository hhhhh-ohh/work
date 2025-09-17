package com.wanmi.sbc.vas.api.provider.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>保存服务Provider</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@FeignClient(value = "${application.vas.name}", contextId = "FilterRulesSettingProvider")
public interface FilterRulesSettingProvider {

	/**
	 * 新增API
	 *
	 * @author zhongjichuan
	 * @param filterRulesSettingAddRequest 新增参数结构 {@link FilterRulesSettingAddRequest}
	 * @return 新增的信息 {@link FilterRulesSettingAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/add")
    BaseResponse<FilterRulesSettingAddResponse> add(@RequestBody @Valid FilterRulesSettingAddRequest filterRulesSettingAddRequest);

	/**
	 * 修改API
	 *
	 * @author zhongjichuan
	 * @param filterRulesSettingModifyRequest 修改参数结构 {@link FilterRulesSettingModifyRequest}
	 * @return 修改的信息 {@link FilterRulesSettingModifyResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/")
    BaseResponse<FilterRulesSettingListResponse> modify(@RequestBody @Valid FilterRulesSettingModifyRequest filterRulesSettingModifyRequest);

	/**
	 * 单个删除API
	 *
	 * @author zhongjichuan
	 * @param filterRulesSettingDelByIdRequest 单个删除参数结构 {@link FilterRulesSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid FilterRulesSettingDelByIdRequest filterRulesSettingDelByIdRequest);

	/**
	 * 批量删除API
	 *
	 * @author zhongjichuan
	 * @param filterRulesSettingDelByIdListRequest 批量删除参数结构 {@link FilterRulesSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid FilterRulesSettingDelByIdListRequest filterRulesSettingDelByIdListRequest);

}

