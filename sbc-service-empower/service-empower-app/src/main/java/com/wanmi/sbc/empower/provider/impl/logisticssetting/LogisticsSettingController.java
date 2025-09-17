package com.wanmi.sbc.empower.provider.impl.logisticssetting;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.api.provider.logisticssetting.LogisticsSettingProvider;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingAddRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingAddResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingModifyResponse;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingDelByIdListRequest;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsSettingService;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>物流配置保存服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@RestController
@Validated
public class LogisticsSettingController implements LogisticsSettingProvider {
	@Autowired
	private LogisticsSettingService logisticsSettingService;

	@Override
	public BaseResponse<LogisticsSettingAddResponse> add(@RequestBody @Valid LogisticsSettingAddRequest logisticsSettingAddRequest) {
		LogisticsSetting logisticsSetting = KsBeanUtil.convert(logisticsSettingAddRequest, LogisticsSetting.class);
		return BaseResponse.success(new LogisticsSettingAddResponse(
				logisticsSettingService.wrapperVo(logisticsSettingService.add(logisticsSetting))));
	}

	@Override
	public BaseResponse<LogisticsSettingModifyResponse> modify(@RequestBody @Valid LogisticsSettingModifyRequest logisticsSettingModifyRequest) {
		LogisticsSetting logisticsSetting = KsBeanUtil.convert(logisticsSettingModifyRequest, LogisticsSetting.class);
		return BaseResponse.success(new LogisticsSettingModifyResponse(
				logisticsSettingService.wrapperVo(logisticsSettingService.modify(logisticsSetting))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LogisticsSettingDelByIdRequest logisticsSettingDelByIdRequest) {
		LogisticsSetting logisticsSetting = KsBeanUtil.convert(logisticsSettingDelByIdRequest, LogisticsSetting.class);
		logisticsSetting.setDelFlag(DeleteFlag.YES);
		logisticsSettingService.deleteById(logisticsSetting);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LogisticsSettingDelByIdListRequest logisticsSettingDelByIdListRequest) {
		List<LogisticsSetting> logisticsSettingList = logisticsSettingDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				LogisticsSetting logisticsSetting = KsBeanUtil.convert(Id, LogisticsSetting.class);
				logisticsSetting.setDelFlag(DeleteFlag.YES);
				return logisticsSetting;
			}).collect(Collectors.toList());
		logisticsSettingService.deleteByIdList(logisticsSettingList);
		return BaseResponse.SUCCESSFUL();
	}

}

