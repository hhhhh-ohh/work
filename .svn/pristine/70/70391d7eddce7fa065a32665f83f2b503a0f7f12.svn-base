package com.wanmi.sbc.setting.api.provider.appexternalconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigPageRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigPageResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigListRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigListResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigByIdRequest;
import com.wanmi.sbc.setting.api.response.appexternalconfig.AppExternalConfigByIdResponse;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigExportRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>AppExternalConfig查询服务Provider</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@FeignClient(value = "${application.setting.name}", contextId = "AppExternalConfigQueryProvider")
public interface AppExternalConfigQueryProvider {

	/**
	 * 分页查询AppExternalConfigAPI
	 *
	 * @author 黄昭
	 * @param appExternalConfigPageReq 分页请求参数和筛选对象 {@link AppExternalConfigPageRequest}
	 * @return AppExternalConfig分页列表信息 {@link AppExternalConfigPageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/page")
	BaseResponse<AppExternalConfigPageResponse> page(@RequestBody @Valid AppExternalConfigPageRequest appExternalConfigPageReq);

	/**
	 * 列表查询AppExternalConfigAPI
	 *
	 * @author 黄昭
	 * @param appExternalConfigListReq 列表请求参数和筛选对象 {@link AppExternalConfigListRequest}
	 * @return AppExternalConfig的列表信息 {@link AppExternalConfigListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/list")
	BaseResponse<AppExternalConfigListResponse> list(@RequestBody @Valid AppExternalConfigListRequest appExternalConfigListReq);

	/**
	 * 单个查询AppExternalConfigAPI
	 *
	 * @author 黄昭
	 * @param appExternalConfigByIdRequest 单个查询AppExternalConfig请求参数 {@link AppExternalConfigByIdRequest}
	 * @return AppExternalConfig详情 {@link AppExternalConfigByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/get-by-id")
	BaseResponse<AppExternalConfigByIdResponse> getById(@RequestBody @Valid AppExternalConfigByIdRequest appExternalConfigByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author 黄昭
	 * @param request {tableDesc}导出数量查询请求 {@link AppExternalConfigExportRequest}
	 * @return AppExternalConfig数量 {@link Long}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternalconfig/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid AppExternalConfigExportRequest request);

}

