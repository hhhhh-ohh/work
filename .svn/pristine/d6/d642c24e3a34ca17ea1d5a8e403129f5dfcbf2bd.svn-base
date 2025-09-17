package com.wanmi.sbc.setting.provider.impl.storeresourcecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.storeresourcecate.StoreResourceCateSaveProvider;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateAddRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateDelByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateInitRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateModifyRequest;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateAddResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateModifyResponse;
import com.wanmi.sbc.setting.systemresourcecate.model.root.SystemResourceCate;
import com.wanmi.sbc.setting.systemresourcecate.service.SystemResourceCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>店铺资源资源分类表保存服务接口实现</p>
 * @author lq
 * @date 2019-11-05 16:13:19
 */
@RestController
@Validated
public class StoreResourceCateSaveController implements StoreResourceCateSaveProvider {
	@Autowired
	private SystemResourceCateService systemResourceCateService;

	@Override
	public BaseResponse<SystemResourceCateAddResponse> add(@RequestBody @Valid SystemResourceCateAddRequest storeResourceCateAddRequest) {
		SystemResourceCate storeResourceCate = new SystemResourceCate();
		KsBeanUtil.copyPropertiesThird(storeResourceCateAddRequest, storeResourceCate);
		return BaseResponse.success(new SystemResourceCateAddResponse(
				systemResourceCateService.wrapperVo(systemResourceCateService.add(storeResourceCate))));
	}

	@Override
	public BaseResponse<SystemResourceCateModifyResponse> modify(@RequestBody @Valid SystemResourceCateModifyRequest storeResourceCateModifyRequest) {
		SystemResourceCate storeResourceCate = new SystemResourceCate();
		KsBeanUtil.copyPropertiesThird(storeResourceCateModifyRequest, storeResourceCate);
		return BaseResponse.success(new SystemResourceCateModifyResponse(
				systemResourceCateService.wrapperVo(systemResourceCateService.edit(storeResourceCate))));
	}

	@Override
	public BaseResponse delete(@RequestBody @Valid SystemResourceCateDelByIdRequest storeResourceCateDelByIdRequest) {
		systemResourceCateService.delete(storeResourceCateDelByIdRequest.getCateId(),storeResourceCateDelByIdRequest.getStoreId());
		return BaseResponse.SUCCESSFUL();
	}


	@Override
	public BaseResponse init(@RequestBody @Valid SystemResourceCateInitRequest storeResourceCate) {
		systemResourceCateService.init(storeResourceCate);
		return BaseResponse.SUCCESSFUL();
	}



}

