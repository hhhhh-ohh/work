package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigStatusModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.Objects;


@Tag(name =  "支付设置管理API", description =  "BossPaySettingConfigController")
@RestController
@Validated
@RequestMapping(value = "/boss/sysconfig/paysetting")
public class BossPaySettingConfigController {
    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    /**
     * 线下支付开关
     *
     * @return BaseResponse
     */
    @Operation(summary = "线下支付开关")
    @Parameter(name = "status", description = "状态", required = true)
    @PutMapping(value = "/modify-offline-pay-setting/{status}")
    public BaseResponse modifyOfflinePaySetting(@PathVariable Integer status) {
        DefaultFlag newDefaultFlag = Arrays.stream(DefaultFlag.values())
                .filter(defaultFlag -> defaultFlag.toValue() == status)
                .findFirst().orElse(null);
        if (Objects.isNull(newDefaultFlag)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        ConfigStatusModifyByTypeAndKeyRequest request = new ConfigStatusModifyByTypeAndKeyRequest();
        request.setConfigKey(ConfigKey.PAY_SETTING);
        request.setConfigType(ConfigType.OFFLINE_PAY_SETTING);
        request.setStatus(status);

        systemConfigSaveProvider.modifyOfflinePaySetting(request);

        return BaseResponse.SUCCESSFUL();
    }


}
