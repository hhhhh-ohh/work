package com.wanmi.sbc.personalconfig;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigContextModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.personalconfig.PersonalConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.personalconfig.PersonalConfigResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @author huangzhao c端个人中心配置
 */
@Tag(name =  "c端个人中心配置API", description =  "PersonalConfigController")
@RestController
@Validated
@RequestMapping(value = "/personal/config")
public class PersonalConfigController {

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Operation(summary = "个人中心设置修改")
    @PostMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid PersonalConfigModifyRequest request) {
        ConfigContextModifyByTypeAndKeyRequest req = new ConfigContextModifyByTypeAndKeyRequest();
        req.setConfigKey(ConfigKey.MOBILE_SETTING);
        req.setConfigType(ConfigType.PERSONAL_CONFIG);
        req.setContext(request.getContext());
        req.setStatus(Constants.yes);
        systemConfigSaveProvider.modify(req);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "个人中心设置查看")
    @GetMapping("/details")
    public BaseResponse<PersonalConfigResponse> details() {
        PersonalConfigResponse personalConfigResponse = new PersonalConfigResponse();
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigKey(ConfigKey.MOBILE_SETTING.toValue());
        configQueryRequest.setConfigType(ConfigType.PERSONAL_CONFIG.toValue());
        configQueryRequest.setDelFlag(Constants.no);
        ConfigVO config = systemConfigQueryProvider
                .findByConfigTypeAndDelFlag(configQueryRequest)
                .getContext()
                .getConfig();
        if (Objects.nonNull(config) && StringUtils.isNotBlank(config.getContext())){
            personalConfigResponse.setContext(config.getContext());
        }
        return BaseResponse.success(personalConfigResponse);
    }

}
