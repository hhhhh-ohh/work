package com.wanmi.sbc.setting.api.provider.storeresourcecate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateByIdRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateCheckChildRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateCheckResourceRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCateListRequest;
import com.wanmi.sbc.setting.api.request.systemresourcecate.SystemResourceCatePageRequest;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateByIdResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCateListResponse;
import com.wanmi.sbc.setting.api.response.systemresourcecate.SystemResourceCatePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>店铺资源资源分类表查询服务Provider</p>
 * @author lq
 * @date 2019-11-05 16:13:19
 */
@FeignClient(value = "${application.setting.name}", contextId = "StoreResourceCateQueryProvider")
public interface StoreResourceCateQueryProvider {

	/**
	 * 分页查询店铺资源资源分类表API
	 *
	 * @author lq
	 * @param storeResourceCatePageReq 分页请求参数和筛选对象 {@link SystemResourceCatePageRequest}
	 * @return 店铺资源资源分类表分页列表信息 {@link SystemResourceCatePageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresourcecate/page")
	BaseResponse<SystemResourceCatePageResponse> page(@RequestBody @Valid SystemResourceCatePageRequest
                                                             storeResourceCatePageReq);

	/**
	 * 列表查询店铺资源资源分类表API
	 *
	 * @author lq
	 * @param storeResourceCateListReq 列表请求参数和筛选对象 {@link SystemResourceCateListRequest}
	 * @return 店铺资源资源分类表的列表信息 {@link SystemResourceCateListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresourcecate/list")
	BaseResponse<SystemResourceCateListResponse> list(@RequestBody @Valid SystemResourceCateListRequest storeResourceCateListReq);

	/**
	 * 单个查询店铺资源资源分类表API
	 *
	 * @author lq
	 * @param storeResourceCateByIdRequest 单个查询店铺资源资源分类表请求参数 {@link SystemResourceCateByIdRequest}
	 * @return 店铺资源资源分类表详情 {@link SystemResourceCateByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/storeresourcecate/get-by-id")
	BaseResponse<SystemResourceCateByIdResponse> getById(@RequestBody @Valid SystemResourceCateByIdRequest storeResourceCateByIdRequest);

	/**
	 * 验证是否有子类
	 *
	 * @param storeResourceCateCheckChildRequest 验证是否有子类 {@link SystemResourceCateCheckChildRequest}
	 * @return 验证是否有子类 {@link SystemResourceCateListResponse}
	 * @author lq
	 */
	@PostMapping("/setting/${application.setting.version}/storeresourcecate/check-child")
	BaseResponse<Integer> checkChild(@RequestBody @Valid SystemResourceCateCheckChildRequest
											 storeResourceCateCheckChildRequest);

	/**
	 * 验证是否有素材
	 *
	 * @param storeResourceCateCheckResourceRequest 验证是否有素材 {@link SystemResourceCateCheckResourceRequest}
	 * @return 验证是否有素材 {@link SystemResourceCateListResponse}
	 * @author lq
	 */
	@PostMapping("/setting/${application.setting.version}/storeresourcecate/check-resource")
	BaseResponse<Integer> checkResource(@RequestBody @Valid SystemResourceCateCheckResourceRequest storeResourceCateCheckResourceRequest);


}

