package com.wanmi.sbc.setting.provider.impl.openapisetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.openapisetting.OpenApiSettingProvider;
import com.wanmi.sbc.setting.api.request.openapisetting.*;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingAddResponse;
import com.wanmi.sbc.setting.api.response.openapisetting.OpenApiSettingModifyResponse;
import com.wanmi.sbc.setting.openapisetting.model.root.OpenApiSetting;
import com.wanmi.sbc.setting.openapisetting.service.OpenApiSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * 开放平台api设置保存服务接口实现
 *
 * @author lvzhenwei
 * @date 2021/4/14 3:16 下午
 */
@RestController
@Validated
public class OpenApiSettingController implements OpenApiSettingProvider {

    @Autowired
    private OpenApiSettingService openApiSettingService;

    @Override
    public BaseResponse<OpenApiSettingAddResponse> add(@RequestBody @Valid OpenApiSettingAddRequest openApiSettingAddRequest) {
        OpenApiSetting openApiSetting =
                KsBeanUtil.convert(openApiSettingAddRequest, OpenApiSetting.class);

        // BOSS端不再做重复性校验
        if(PlatformType.BOSS != openApiSetting.getPlatformType()) {
            // 验证是否有已经有数据
            OpenApiSetting apiSetting =
                    PlatformType.STORE.equals(openApiSetting.getPlatformType())
                            ? openApiSettingService.getByStoreId(openApiSetting.getStoreId())
                            : openApiSettingService.getBossSetting();
            if (Objects.nonNull(apiSetting)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "请勿重复申请开放权限！");
            }
        }

        return BaseResponse.success(
                new OpenApiSettingAddResponse(
                        openApiSettingService.wrapperVo(openApiSettingService.add(openApiSetting))));
    }

    @Override
    public BaseResponse<OpenApiSettingModifyResponse> modify(
            @RequestBody @Valid OpenApiSettingModifyRequest openApiSettingModifyRequest) {
        OpenApiSetting openApiSetting =
                KsBeanUtil.convert(openApiSettingModifyRequest, OpenApiSetting.class);
        return BaseResponse.success(
                new OpenApiSettingModifyResponse(
                        openApiSettingService.wrapperVo(openApiSettingService.modify(openApiSetting))));
    }

    @Override
    public BaseResponse deleteById(
            @RequestBody @Valid OpenApiSettingDelByIdRequest openApiSettingDelByIdRequest) {
        openApiSettingService.deleteById(openApiSettingDelByIdRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse checkAuditState(
            @RequestBody @Valid
                    OpenApiSettingCheckAuditStateRequest openApiSettingCheckAuditStateRequest) {
        if (openApiSettingCheckAuditStateRequest.getAuditState() == AuditStatus.NOT_PASS) {
            openApiSettingService.checkAuditStateReason(openApiSettingCheckAuditStateRequest);
        } else {
            openApiSettingService.checkAuditState(openApiSettingCheckAuditStateRequest);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse changeDisableState(
            @RequestBody @Valid
                    OpenApiSettingChangeDisableStateRequest openApiSettingChangeDisableStateRequest) {
        openApiSettingService.changeDisableState(openApiSettingChangeDisableStateRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse resetAppSecret(
            @RequestBody @Valid OpenApiSettingResetSecretRequest openApiSettingResetSecretRequest) {
        openApiSettingService.resetAppSecret(openApiSettingResetSecretRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse initOpenApiCache() {
        openApiSettingService.initOpenApiCache();
        return BaseResponse.SUCCESSFUL();
    }
}
