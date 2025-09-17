package com.wanmi.sbc.vas.provider.impl.recommend.filterrulessetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.filterrulessetting.FilterRulesSettingProvider;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.filterrulessetting.FilterRulesSettingListResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.FilterRulesSettingVO;
import com.wanmi.sbc.vas.recommend.filterrulessetting.model.root.FilterRulesSetting;
import com.wanmi.sbc.vas.recommend.filterrulessetting.service.FilterRulesSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>保存服务接口实现</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@RestController
@Validated
public class FilterRulesSettingController implements FilterRulesSettingProvider {
	@Autowired
	private FilterRulesSettingService filterRulesSettingService;

	@Override
	public BaseResponse<FilterRulesSettingAddResponse> add(@RequestBody @Valid FilterRulesSettingAddRequest filterRulesSettingAddRequest) {
		FilterRulesSetting filterRulesSetting = KsBeanUtil.convert(filterRulesSettingAddRequest, FilterRulesSetting.class);
		return BaseResponse.success(new FilterRulesSettingAddResponse(
				filterRulesSettingService.wrapperVo(filterRulesSettingService.add(filterRulesSetting))));
	}

	@Override
	public BaseResponse<FilterRulesSettingListResponse> modify(@RequestBody @Valid FilterRulesSettingModifyRequest filterRulesSettingModifyRequest) {
		List<FilterRulesSetting> filterRulesSettingList = KsBeanUtil.convertList(filterRulesSettingModifyRequest.getFilterRulesSettingDTOList(), FilterRulesSetting.class);
		filterRulesSettingList.forEach(v -> {
			v.setUpdatePerson(filterRulesSettingModifyRequest.getUpdatePerson());
			v.setUpdateTime(filterRulesSettingModifyRequest.getUpdateTime());
		});
		List<FilterRulesSetting> modifiedList = filterRulesSettingService.modify(filterRulesSettingList);
		List<FilterRulesSettingVO> newList = modifiedList.stream().map(entity -> filterRulesSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new FilterRulesSettingListResponse(newList));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid FilterRulesSettingDelByIdRequest filterRulesSettingDelByIdRequest) {
		FilterRulesSetting filterRulesSetting = KsBeanUtil.convert(filterRulesSettingDelByIdRequest, FilterRulesSetting.class);
		filterRulesSetting.setDelFlag(DeleteFlag.YES);
		filterRulesSettingService.deleteById(filterRulesSetting);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid FilterRulesSettingDelByIdListRequest filterRulesSettingDelByIdListRequest) {
		List<FilterRulesSetting> filterRulesSettingList = filterRulesSettingDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				FilterRulesSetting filterRulesSetting = KsBeanUtil.convert(Id, FilterRulesSetting.class);
				filterRulesSetting.setDelFlag(DeleteFlag.YES);
				return filterRulesSetting;
			}).collect(Collectors.toList());
		filterRulesSettingService.deleteByIdList(filterRulesSettingList);
		return BaseResponse.SUCCESSFUL();
	}

}

