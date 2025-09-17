package com.wanmi.sbc.setting.api.provider.openapisetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.openapisetting.OpenApiSettingByStoreIdRequest;
import com.wanmi.sbc.setting.api.request.openapisetting.OpenApiSettingPageRequest;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingPageResponse;
import com.wanmi.sbc.setting.api.request.openapisetting.OpenApiSettingListRequest;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingListResponse;
import com.wanmi.sbc.setting.api.request.openapisetting.OpenApiSettingByIdRequest;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * 开放平台api设置查询服务Provider
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@FeignClient(value = "${application.setting.name}", contextId = "OpenApiSettingQueryProvider")
public interface OpenApiSettingQueryProvider {

    /**
     * 分页查询开放平台api设置API
     *
     * @author lvzhenwei
     * @param openApiSettingPageReq 分页请求参数和筛选对象 {@link OpenApiSettingPageRequest}
     * @return 开放平台api设置分页列表信息 {@link OpenApiSettingPageResponse}
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/page")
    BaseResponse<OpenApiSettingPageResponse> page(
            @RequestBody @Valid OpenApiSettingPageRequest openApiSettingPageReq);

    /**
     * 列表查询开放平台api设置API
     *
     * @author lvzhenwei
     * @param openApiSettingListReq 列表请求参数和筛选对象 {@link OpenApiSettingListRequest}
     * @return 开放平台api设置的列表信息 {@link OpenApiSettingListResponse}
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/list")
    BaseResponse<OpenApiSettingListResponse> list(
            @RequestBody @Valid OpenApiSettingListRequest openApiSettingListReq);

    /**
     * 单个查询开放平台api设置API
     *
     * @author lvzhenwei
     * @param openApiSettingByIdRequest 单个查询开放平台api设置请求参数 {@link OpenApiSettingByIdRequest}
     * @return 开放平台api设置详情 {@link OpenApiSettingByIdResponse}
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/get-by-id")
    BaseResponse<OpenApiSettingByIdResponse> getById(
            @RequestBody @Valid OpenApiSettingByIdRequest openApiSettingByIdRequest);


    /**
    *  根据商户ID查询
     * @author  wur
     * @date: 2021/4/25 11:09
     * @param openApiSettingByStoreIdRequest 商户ID查询开放平台api设置请求参数 {@link OpenApiSettingByStoreIdRequest}
     * @return 开放平台api设置详情 {@link OpenApiSettingByIdResponse}
     **/
    @PostMapping("/setting/${application.setting.version}/openapisetting/get-by-storeId")
    BaseResponse<OpenApiSettingByIdResponse> getByStoreId(
            @RequestBody @Valid OpenApiSettingByStoreIdRequest openApiSettingByStoreIdRequest);
}
