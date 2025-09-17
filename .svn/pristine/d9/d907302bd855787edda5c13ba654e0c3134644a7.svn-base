package com.wanmi.sbc.setting.api.provider.appexternallink;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkPageRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkPageResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkListRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkListResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkByIdRequest;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalLinkByIdResponse;
import com.wanmi.sbc.setting.api.request.appexternallink.AppExternalLinkExportRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>AppExternalLink查询服务Provider</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@FeignClient(value = "${application.setting.name}", contextId = "AppExternalLinkQueryProvider")
public interface AppExternalLinkQueryProvider {

	/**
	 * 分页查询AppExternalLinkAPI
	 *
	 * @author 黄昭
	 * @param appExternalLinkPageReq 分页请求参数和筛选对象 {@link AppExternalLinkPageRequest}
	 * @return AppExternalLink分页列表信息 {@link AppExternalLinkPageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/page")
	BaseResponse<AppExternalLinkPageResponse> page(@RequestBody @Valid AppExternalLinkPageRequest appExternalLinkPageReq);

	/**
	 * 列表查询AppExternalLinkAPI
	 *
	 * @author 黄昭
	 * @param appExternalLinkListReq 列表请求参数和筛选对象 {@link AppExternalLinkListRequest}
	 * @return AppExternalLink的列表信息 {@link AppExternalLinkListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/list")
	BaseResponse<AppExternalLinkListResponse> list(@RequestBody @Valid AppExternalLinkListRequest appExternalLinkListReq);

	/**
	 * 单个查询AppExternalLinkAPI
	 *
	 * @author 黄昭
	 * @param appExternalLinkByIdRequest 单个查询AppExternalLink请求参数 {@link AppExternalLinkByIdRequest}
	 * @return AppExternalLink详情 {@link AppExternalLinkByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/get-by-id")
	BaseResponse<AppExternalLinkByIdResponse> getById(@RequestBody @Valid AppExternalLinkByIdRequest appExternalLinkByIdRequest);

	/**
	 * {tableDesc}导出数量查询API
	 *
	 * @author 黄昭
	 * @param request {tableDesc}导出数量查询请求 {@link AppExternalLinkExportRequest}
	 * @return AppExternalLink数量 {@link Long}
	 */
	@PostMapping("/setting/${application.setting.version}/appexternallink/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid AppExternalLinkExportRequest request);

}

