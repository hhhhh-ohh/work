package com.wanmi.sbc.empower.provider.impl.logisticssetting;

import com.wanmi.sbc.empower.api.request.logisticssetting.*;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingByLogisticsTypeResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.logisticssetting.LogisticsSettingQueryProvider;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingPageResponse;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingListResponse;
import com.wanmi.sbc.empower.api.response.logisticssetting.LogisticsSettingByIdResponse;
import com.wanmi.sbc.empower.bean.vo.LogisticsSettingVO;
import com.wanmi.sbc.empower.logisticssetting.service.LogisticsSettingService;
import com.wanmi.sbc.empower.logisticssetting.model.root.LogisticsSetting;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>物流配置查询服务接口实现</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@RestController
@Validated
public class LogisticsSettingQueryController implements LogisticsSettingQueryProvider {
	@Autowired
	private LogisticsSettingService logisticsSettingService;

	@Override
	public BaseResponse<LogisticsSettingPageResponse> page(@RequestBody @Valid LogisticsSettingPageRequest logisticsSettingPageReq) {
		LogisticsSettingQueryRequest queryReq = KsBeanUtil.convert(logisticsSettingPageReq, LogisticsSettingQueryRequest.class);
		Page<LogisticsSetting> logisticsSettingPage = logisticsSettingService.page(queryReq);
		Page<LogisticsSettingVO> newPage = logisticsSettingPage.map(entity -> logisticsSettingService.wrapperVo(entity));
		MicroServicePage<LogisticsSettingVO> microPage = new MicroServicePage<>(newPage, logisticsSettingPageReq.getPageable());
		LogisticsSettingPageResponse finalRes = new LogisticsSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LogisticsSettingListResponse> list(@RequestBody @Valid LogisticsSettingListRequest logisticsSettingListReq) {
		LogisticsSettingQueryRequest queryReq = KsBeanUtil.convert(logisticsSettingListReq, LogisticsSettingQueryRequest.class);
		List<LogisticsSetting> logisticsSettingList = logisticsSettingService.list(queryReq);
		List<LogisticsSettingVO> newList = logisticsSettingList.stream().map(entity -> logisticsSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LogisticsSettingListResponse(newList));
	}

	@Override
	public BaseResponse<LogisticsSettingByIdResponse> getById(@RequestBody @Valid LogisticsSettingByIdRequest logisticsSettingByIdRequest) {
		LogisticsSetting logisticsSetting =
		logisticsSettingService.getOne(logisticsSettingByIdRequest.getId());
		return BaseResponse.success(new LogisticsSettingByIdResponse(logisticsSettingService.wrapperVo(logisticsSetting)));
	}

	@Override
	public BaseResponse<LogisticsSettingByLogisticsTypeResponse> getByLogisticsType(@RequestBody @Valid LogisticsSettingByLogisticsTypeRequest logisticsSettingByLogisticsTypeRequest){
		LogisticsSetting logisticsSetting =
				logisticsSettingService.getOneByLogisticsType(logisticsSettingByLogisticsTypeRequest.getLogisticsType());
		return BaseResponse.success(new LogisticsSettingByLogisticsTypeResponse(logisticsSettingService.wrapperVo(logisticsSetting)));
	}

}

