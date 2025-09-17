package com.wanmi.sbc.setting.api.provider.storeresource;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceAddRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceDelByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceModifyRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceMoveRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceAddResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>店铺资源库保存服务Provider</p>
 * @author lq
 * @date 2019-11-05 16:12:49
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreResourceSaveProvider")
public interface StoreResourceSaveProvider {

	/**
	 * 新增店铺资源库API
	 *
	 * @author lq
	 * @param storeResourceAddRequest 店铺资源库新增参数结构 {@link SystemResourceAddRequest}
	 * @return 新增的店铺资源库信息 {@link SystemResourceAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/add")
	BaseResponse<SystemResourceAddResponse> add(@RequestBody @Valid SystemResourceAddRequest storeResourceAddRequest);

	/**
	 * 修改店铺资源库API
	 *
	 * @author lq
	 * @param storeResourceModifyRequest 店铺资源库修改参数结构 {@link SystemResourceModifyRequest}
	 * @return 修改的店铺资源库信息 {@link SystemResourceModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/modify")
	BaseResponse<SystemResourceModifyResponse> modify(@RequestBody @Valid SystemResourceModifyRequest
                                                             storeResourceModifyRequest);

	/**
	 * 单个删除店铺资源库API
	 *
	 * @author lq
	 * @param storeResourceDelByIdRequest 单个删除参数结构 {@link SystemResourceDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid SystemResourceDelByIdRequest storeResourceDelByIdRequest);

	/**
	 * 移动店铺素材资源API
	 *
	 * @author lq
	 * @param moveRequest 素材资源修改参数结构  {@link SystemResourceMoveRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/move")
	BaseResponse move(@RequestBody @Valid SystemResourceMoveRequest
									moveRequest);

	/**
	 * 批量删除店铺资源库API
	 *
	 * @author lq
	 * @param storeResourceDelByIdListRequest 批量删除参数结构 {@link SystemResourceDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid SystemResourceDelByIdListRequest storeResourceDelByIdListRequest);

}

