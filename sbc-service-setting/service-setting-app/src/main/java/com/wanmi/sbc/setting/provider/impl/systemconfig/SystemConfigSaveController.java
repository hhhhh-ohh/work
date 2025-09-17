package com.wanmi.sbc.setting.provider.impl.systemconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.*;
import com.wanmi.sbc.setting.api.request.systemconfig.PayingMemberModifyRequest;
import com.wanmi.sbc.setting.api.request.systemconfig.SystemConfigQueryRequest;
import com.wanmi.sbc.setting.config.ConfigService;
import com.wanmi.sbc.setting.systemconfig.model.root.SystemConfig;
import com.wanmi.sbc.setting.systemconfig.service.SystemConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;


/**
 * Created by feitingting on 2019/11/6.
 */
@RestController
public class SystemConfigSaveController implements SystemConfigSaveProvider{
    @Autowired
    private ConfigService configService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public BaseResponse modify(@Valid ConfigContextModifyByTypeAndKeyRequest request) {
        List<SystemConfig> systemConfigList =
                systemConfigService.list(SystemConfigQueryRequest.builder().configKey(request.getConfigKey().toValue())
                        .configType(request.getConfigType().toValue()).build());

        if (CollectionUtils.isNotEmpty(systemConfigList)){
            SystemConfig systemConfig = systemConfigList.get(0);
            Integer status = request.getStatus();
            if(Objects.nonNull(status)){
                systemConfig.setStatus(status);
            }
            systemConfig.setContext(request.getContext());
            systemConfigService.modify(systemConfig);
            return BaseResponse.SUCCESSFUL();
        }
        return BaseResponse.FAILED();
    }

    @Override
    public BaseResponse update(@Valid ConfigUpdateRequest request) {
        configService.update(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse update(@RequestBody @Valid List<ConfigModifyRequest> configModifyRequests) {
        configService.update(configModifyRequests);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyOfflinePaySetting(@RequestBody @Valid ConfigStatusModifyByTypeAndKeyRequest request) {
        configService.modifyOfflinePaySetting(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyEsGoodsBoost(ModifyEsGoodsBoostRequest request) {
        systemConfigService.modifyEsGoodsBoost(request.getContext());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyPayingMemberSetting(@Valid @RequestBody PayingMemberModifyRequest request) {
        systemConfigService.modifyPayingMemberSetting(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifySeoSetting(@Valid @RequestBody SeoSettingModifyRequest request) {
        systemConfigService.modifySeoSetting(request);
        return BaseResponse.SUCCESSFUL();
    }
}
