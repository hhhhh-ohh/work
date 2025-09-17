package com.wanmi.sbc.vas.provider.impl.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.filterrulessetting.FilterRulesSettingQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingPageRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingQueryRequest;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingListResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingPageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.FilterRulesSettingVO;
import com.wanmi.sbc.vas.recommend.filterrulessetting.model.root.FilterRulesSetting;
import com.wanmi.sbc.vas.recommend.filterrulessetting.service.FilterRulesSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>查询服务接口实现</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@RestController
@Validated
public class FilterRulesSettingQueryController implements FilterRulesSettingQueryProvider {
	@Autowired
	private FilterRulesSettingService filterRulesSettingService;

	@Override
	public BaseResponse<FilterRulesSettingPageResponse> page(@RequestBody @Valid FilterRulesSettingPageRequest filterRulesSettingPageReq) {
		FilterRulesSettingQueryRequest queryReq = KsBeanUtil.convert(filterRulesSettingPageReq, FilterRulesSettingQueryRequest.class);
		Page<FilterRulesSetting> filterRulesSettingPage = filterRulesSettingService.page(queryReq);
		Page<FilterRulesSettingVO> newPage = filterRulesSettingPage.map(entity -> filterRulesSettingService.wrapperVo(entity));
		MicroServicePage<FilterRulesSettingVO> microPage = new MicroServicePage<>(newPage, filterRulesSettingPageReq.getPageable());
		FilterRulesSettingPageResponse finalRes = new FilterRulesSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<FilterRulesSettingListResponse> list() {
		FilterRulesSettingQueryRequest queryReq = new FilterRulesSettingQueryRequest();
		queryReq.setDelFlag(DeleteFlag.NO);
		List<FilterRulesSetting> filterRulesSettingList = filterRulesSettingService.list(queryReq);
		List<FilterRulesSettingVO> newList = filterRulesSettingList.stream().map(entity -> filterRulesSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new FilterRulesSettingListResponse(newList));
	}

	@Override
	public BaseResponse<FilterRulesSettingByIdResponse> getById(@RequestBody @Valid FilterRulesSettingByIdRequest filterRulesSettingByIdRequest) {
		FilterRulesSetting filterRulesSetting =
		filterRulesSettingService.getOne(filterRulesSettingByIdRequest.getId());
		return BaseResponse.success(new FilterRulesSettingByIdResponse(filterRulesSettingService.wrapperVo(filterRulesSetting)));
	}

}

