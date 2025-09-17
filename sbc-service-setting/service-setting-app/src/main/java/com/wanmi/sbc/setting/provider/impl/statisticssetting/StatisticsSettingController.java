package com.wanmi.sbc.setting.provider.impl.statisticssetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.statisticssetting.StatisticsSettingProvider;
import com.wanmi.sbc.setting.api.provider.storeexpresscompanyrela.StoreExpressCompanyRelaQueryProvider;
import com.wanmi.sbc.setting.api.request.statisticssetting.QmStatisticsSettingRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaByIdRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaListRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaPageRequest;
import com.wanmi.sbc.setting.api.request.storeexpresscompanyrela.StoreExpressCompanyRelaQueryRequest;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressCompanyRelaByIdResponse;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressCompanyRelaListResponse;
import com.wanmi.sbc.setting.api.response.storeexpresscompanyrela.StoreExpressCompanyRelaPageResponse;
import com.wanmi.sbc.setting.bean.vo.StoreExpressCompanyRelaVO;
import com.wanmi.sbc.setting.statisticssetting.service.StatisticsSettingService;
import com.wanmi.sbc.setting.storeexpresscompanyrela.model.root.StoreExpressCompanyRela;
import com.wanmi.sbc.setting.storeexpresscompanyrela.service.StoreExpressCompanyRelaService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className StatisticsSettingController
 * @description
 * @author    张文昌
 * @date      2022/1/6 18:27
 */
@RestController
@Validated
public class StatisticsSettingController implements StatisticsSettingProvider {

	@Autowired
	private StatisticsSettingService statisticsSettingService;

	/**
	 * 修改qm数谋基本配置
	 * @param qmStatisticsSettingRequest 修改qm数谋基本配置 {@link QmStatisticsSettingRequest}
	 * @return
	 */
	@Override
	public BaseResponse modifyQmSetting(@RequestBody @Valid QmStatisticsSettingRequest qmStatisticsSettingRequest) {
		statisticsSettingService.modifyQmSetting(qmStatisticsSettingRequest);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 查询qm数谋基本配置
	 * @return
	 */
	@Override
	public BaseResponse<QmStatisticsSettingResponse> getQmSetting() {
		return BaseResponse.success(statisticsSettingService.getQmSetting());
	}

	@Override
	public BaseResponse<QmStatisticsSettingResponse> getQmSettingCache() {
		return BaseResponse.success(statisticsSettingService.getQmSettingCache());
	}
}

