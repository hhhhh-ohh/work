package com.wanmi.sbc.third.platformconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.thirdplatformconfig.ThirdPlatformConfigProvider;
import com.wanmi.sbc.setting.api.provider.thirdplatformconfig.ThirdPlatformConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.thirdplatformconfig.ThirdPlatformConfigByTypeRequest;
import com.wanmi.sbc.setting.api.request.thirdplatformconfig.ThirdPlatformConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.thirdplatformconfig.ThirdPlatformConfigQueryResponse;
import com.wanmi.sbc.setting.api.response.thirdplatformconfig.ThirdPlatformConfigResponse;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "第三方平台配置管理API", description =  "ThirdPlatformConfigController")
@RestController
@Validated
@RequestMapping(value = "/third/platformconfig")
public class ThirdPlatformConfigController {

    @Autowired
    private ThirdPlatformConfigQueryProvider thirdPlatformConfigQueryProvider;

    @Autowired
    private ThirdPlatformConfigProvider thirdPlatformConfigProvider;

    @Operation(summary = "第三方平台配置列表")
    @PostMapping("/list")
    public BaseResponse<ThirdPlatformConfigQueryResponse> list() {
        return thirdPlatformConfigQueryProvider.list();
    }

    @Operation(summary = "修改第三方平台配置")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid ThirdPlatformConfigModifyRequest request) {
        thirdPlatformConfigProvider.modify(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询第三方平台配置信息")
    @Parameter(name = "configType",
            description = "第三方平台配置", required = true)
    @RequestMapping(value = "/{configType}", method = RequestMethod.GET)
    public BaseResponse<ThirdPlatformConfigResponse> list(@PathVariable String configType) {
        return thirdPlatformConfigQueryProvider.get(ThirdPlatformConfigByTypeRequest.builder().configType(configType).build());
    }
}
