package com.wanmi.sbc.setting.api.provider.openapisetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.openapisetting.*;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingAddResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * 开放平台api设置保存服务Provider
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@FeignClient(value = "${application.setting.name}", contextId = "OpenApiSettingProvider")
public interface OpenApiSettingProvider {

    /**
     * 新增开放平台api设置API
     *
     * @author lvzhenwei
     * @param openApiSettingAddRequest 开放平台api设置新增参数结构 {@link OpenApiSettingAddRequest}
     * @return 新增的开放平台api设置信息 {@link OpenApiSettingAddResponse}
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/add")
    BaseResponse<OpenApiSettingAddResponse> add(
            @RequestBody @Valid OpenApiSettingAddRequest openApiSettingAddRequest);

    /**
     * 修改开放平台api设置API
     *
     * @author lvzhenwei
     * @param openApiSettingModifyRequest 开放平台api设置修改参数结构 {@link OpenApiSettingModifyRequest}
     * @return 修改的开放平台api设置信息 {@link OpenApiSettingModifyResponse}
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/modify")
    BaseResponse<OpenApiSettingModifyResponse> modify(
            @RequestBody @Valid OpenApiSettingModifyRequest openApiSettingModifyRequest);

    /**
     * 单个删除开放平台api设置API
     *
     * @author lvzhenwei
     * @param openApiSettingDelByIdRequest 单个删除参数结构 {@link OpenApiSettingDelByIdRequest}
     * @return 删除结果 {@link BaseResponse}
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/delete-by-id")
    BaseResponse deleteById(
            @RequestBody @Valid OpenApiSettingDelByIdRequest openApiSettingDelByIdRequest);

    /**
     * 开放平台开放权限审核
     *
     * @author lvzhenwei
     * @date 2021/4/14 4:56 下午
     * @param openApiSettingCheckAuditStateRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/check-audit-state")
    BaseResponse checkAuditState(
            @RequestBody @Valid
                    OpenApiSettingCheckAuditStateRequest openApiSettingCheckAuditStateRequest);

    /**
     * 开放平台启用/禁用
     * @author lvzhenwei
     * @date 2021/4/14 5:02 下午
     * @param openApiSettingChangeDisableStateRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/change-disable-state")
    BaseResponse changeDisableState(
            @RequestBody @Valid
                    OpenApiSettingChangeDisableStateRequest
                            openApiSettingChangeDisableStateRequest);

    /**
     * 开放平台重置secret
     * @author lvzhenwei
     * @date 2021/4/14 4:57 下午
     * @param openApiSettingResetSecretRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/setting/${application.setting.version}/openapisetting/reset-app-secret")
    BaseResponse resetAppSecret(
            @RequestBody @Valid OpenApiSettingResetSecretRequest openApiSettingResetSecretRequest);

    /**
    *
     * 初始化openApi的缓存
     * @author  wur
     * @date: 2021/4/27 11:36
     * @return
     **/
    @PostMapping("/setting/${application.setting.version}/openapisetting/init-open-api-cache")
    BaseResponse initOpenApiCache();
}
