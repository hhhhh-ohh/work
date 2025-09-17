package com.wanmi.sbc.setting.provider.impl.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingByStoreIdRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingListRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingQueryRequest;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingByIdResponse;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingListResponse;
import com.wanmi.sbc.setting.bean.vo.StoreMessageNodeSettingVO;
import com.wanmi.sbc.setting.storemessagenodesetting.model.root.StoreMessageNodeSetting;
import com.wanmi.sbc.setting.storemessagenodesetting.service.StoreMessageNodeSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商家消息节点设置查询服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@RestController
@Validated
public class StoreMessageNodeSettingQueryController implements StoreMessageNodeSettingQueryProvider {
	@Autowired
	private StoreMessageNodeSettingService storeMessageNodeSettingService;

	@Override
	public BaseResponse<StoreMessageNodeSettingListResponse> list(@RequestBody @Valid StoreMessageNodeSettingListRequest storeMessageNodeSettingListReq) {
		StoreMessageNodeSettingQueryRequest queryReq = KsBeanUtil.convert(storeMessageNodeSettingListReq, StoreMessageNodeSettingQueryRequest.class);
		List<StoreMessageNodeSetting> nodeSettings = storeMessageNodeSettingService.list(queryReq);
		List<StoreMessageNodeSettingVO> voList = nodeSettings.stream().map(entity -> storeMessageNodeSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new StoreMessageNodeSettingListResponse(voList));
	}

	@Override
	public BaseResponse<StoreMessageNodeSettingByIdResponse> getById(@RequestBody @Valid StoreMessageNodeSettingByIdRequest storeMessageNodeSettingByIdRequest) {
		StoreMessageNodeSetting storeMessageNodeSetting =
		storeMessageNodeSettingService.getOne(storeMessageNodeSettingByIdRequest.getId(), storeMessageNodeSettingByIdRequest.getStoreId());
		return BaseResponse.success(new StoreMessageNodeSettingByIdResponse(storeMessageNodeSettingService.wrapperVo(storeMessageNodeSetting)));
	}

	@Override
	public BaseResponse<StoreMessageNodeSettingByIdResponse> getWarningStock (@RequestBody @Valid StoreMessageNodeSettingByStoreIdRequest request) {
		StoreMessageNodeSetting warningStock = storeMessageNodeSettingService.getWarningStock(request.getStoreId());
		return BaseResponse.success(new StoreMessageNodeSettingByIdResponse(storeMessageNodeSettingService.wrapperVo(warningStock)));
	}
}

