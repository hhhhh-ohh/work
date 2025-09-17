package com.wanmi.sbc.setting.api.provider.storeresource;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourceListRequest;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourcePageRequest;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceByIdResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourceListResponse;
import com.wanmi.sbc.setting.api.response.systemresource.SystemResourcePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>店铺资源库查询服务Provider</p>
 * @author lq
 * @date 2019-11-05 16:12:49
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreResourceQueryProvider")
public interface StoreResourceQueryProvider {

	/**
	 * 分页查询店铺资源库API
	 *
	 * @author lq
	 * @param storeResourcePageReq 分页请求参数和筛选对象 {@link SystemResourcePageRequest}
	 * @return 店铺资源库分页列表信息 {@link SystemResourcePageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/page")
	BaseResponse<SystemResourcePageResponse> page(@RequestBody @Valid SystemResourcePageRequest storeResourcePageReq);

	/**
	 * 列表查询店铺资源库API
	 *
	 * @author lq
	 * @param storeResourceListReq 列表请求参数和筛选对象 {@link SystemResourceListRequest}
	 * @return 店铺资源库的列表信息 {@link SystemResourceListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/list")
	BaseResponse<SystemResourceListResponse> list(@RequestBody @Valid SystemResourceListRequest storeResourceListReq);

	/**
	 * 单个查询店铺资源库API
	 *
	 * @author lq
	 * @param storeResourceByIdRequest 单个查询店铺资源库请求参数 {@link SystemResourceByIdRequest}
	 * @return 店铺资源库详情 {@link SystemResourceByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresource/get-by-id")
	BaseResponse<SystemResourceByIdResponse> getById(@RequestBody @Valid SystemResourceByIdRequest
                                                            storeResourceByIdRequest);

}

