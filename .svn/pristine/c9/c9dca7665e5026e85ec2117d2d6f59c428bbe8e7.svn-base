package com.wanmi.sbc.themecolorsystem;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigContextModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


/**
 * @author EDZ
 * @className ThemeColorSystemController
 * @description TODO
 * @date 2022/3/14 14:44
 **/
@Tag(name =  "ThemeColorSystemController", description =  "主题色管理API")
@RestController
@Validated
@RequestMapping(value = "/theme-color")
public class ThemeColorSystemController {

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Operation(summary = "获取主题色配置")
    @GetMapping("/get")
    public BaseResponse<SystemConfigTypeResponse> get(){
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.THEME_COLOR_SETTING.toValue());
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(request);
    }

    @Operation(summary = "修改主题色配置")
    @PostMapping("/update")
    public BaseResponse<SystemConfigTypeResponse> update(@RequestBody @Valid ConfigContextModifyByTypeAndKeyRequest request){
        return systemConfigSaveProvider.modify(request);
    }
}