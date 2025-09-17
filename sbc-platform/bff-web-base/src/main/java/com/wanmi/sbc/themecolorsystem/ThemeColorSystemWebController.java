package com.wanmi.sbc.themecolorsystem;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

/**
 * @author EDZ
 * @className ThemeColorSystemController
 * @description TODO
 * @date 2022/3/14 18:55
 **/
@Tag(description = "ThemeColorSystemWebController", name = "主题色查询API")
@RestController
@Validated
@RequestMapping(value = "/theme-color")
public class ThemeColorSystemWebController {

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Operation(summary = "获取主题色配置")
    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME,key = "'systemColor'")
    @GetMapping("/get")
    public BaseResponse<SystemConfigTypeResponse> get(){
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.THEME_COLOR_SETTING.toValue());
        return systemConfigQueryProvider.findByConfigTypeAndDelFlag(request);
    }
}