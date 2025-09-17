package com.wanmi.sbc.vas.api.provider.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingPageRequest;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingListResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>查询服务Provider</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@FeignClient(value = "${application.vas.name}", contextId = "FilterRulesSettingQueryProvider")
public interface FilterRulesSettingQueryProvider {

	/**
	 * 分页查询API
	 *
	 * @author zhongjichuan
	 * @param filterRulesSettingPageReq 分页请求参数和筛选对象 {@link FilterRulesSettingPageRequest}
	 * @return 分页列表信息 {@link FilterRulesSettingPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/page")
    BaseResponse<FilterRulesSettingPageResponse> page(@RequestBody @Valid FilterRulesSettingPageRequest filterRulesSettingPageReq);

	/**
	 * 列表查询API
	 *
	 * @author zhongjichuan
	 * @return 的列表信息 {@link FilterRulesSettingListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/list")
    BaseResponse<FilterRulesSettingListResponse> list();

	/**
	 * 单个查询API
	 *
	 * @author zhongjichuan
	 * @param filterRulesSettingByIdRequest 单个查询请求参数 {@link FilterRulesSettingByIdRequest}
	 * @return 详情 {@link FilterRulesSettingByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/filterrulessetting/get-by-id")
    BaseResponse<FilterRulesSettingByIdResponse> getById(@RequestBody @Valid FilterRulesSettingByIdRequest filterRulesSettingByIdRequest);

}

