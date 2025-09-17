package com.wanmi.sbc.setting.provider.impl.storeresource;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storeresource.StoreResourceSaveProvider;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceAddRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceDelByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceModifyRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceMoveRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceAddResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceModifyResponse;
import com.wanmi.sbc.setting.systemresource.model.root.SystemResource;
import com.wanmi.sbc.setting.systemresource.service.SystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>店铺资源库保存服务接口实现</p>
 * @author lq
 * @date 2019-11-05 16:12:49
 */
@RestController
@Validated
public class StoreResourceSaveController implements StoreResourceSaveProvider {
	@Autowired
	private SystemResourceService storeResourceService;

	@Override
	public BaseResponse<SystemResourceAddResponse> add(@RequestBody @Valid SystemResourceAddRequest storeResourceAddRequest) {
		SystemResource storeResource = new SystemResource();
		KsBeanUtil.copyPropertiesThird(storeResourceAddRequest, storeResource);
		return BaseResponse.success(new SystemResourceAddResponse(
				storeResourceService.wrapperVo(storeResourceService.add(storeResource))));
	}

	@Override
	public BaseResponse<SystemResourceModifyResponse> modify(@RequestBody @Valid SystemResourceModifyRequest storeResourceModifyRequest) {
		SystemResource storeResource = new SystemResource();
		KsBeanUtil.copyPropertiesThird(storeResourceModifyRequest, storeResource);
		return BaseResponse.success(new SystemResourceModifyResponse(
				storeResourceService.wrapperVo(storeResourceService.modify(storeResource))));
	}


	@Override
	public BaseResponse move(@RequestBody @Valid SystemResourceMoveRequest
									 moveRequest) {
		storeResourceService.updateCateByIds(moveRequest.getCateId(), moveRequest.getResourceIds(),moveRequest.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid SystemResourceDelByIdRequest storeResourceDelByIdRequest) {
		storeResourceService.deleteById(storeResourceDelByIdRequest.getResourceId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid SystemResourceDelByIdListRequest storeResourceDelByIdListRequest) {
		storeResourceService.delete(storeResourceDelByIdListRequest.getResourceIds());
		return BaseResponse.SUCCESSFUL();
	}

}

