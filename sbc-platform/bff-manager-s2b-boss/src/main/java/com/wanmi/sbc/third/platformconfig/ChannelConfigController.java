package com.wanmi.sbc.third.platformconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelConfigProvider;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelConfigQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigByTypeRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigModifyRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigListResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


/**
 * 第三方渠道配置管理API
 * @author  wur
 * @date: 2021/5/20 13:33
 **/
@Tag(name =  "第三方渠道配置管理API", description =  "ChannelConfigController")
@RestController
@Validated
@RequestMapping(value = "/third/channel-config")
public class ChannelConfigController {

    @Autowired
    private ChannelConfigQueryProvider channelConfigQueryProvider;

    @Autowired
    private ChannelConfigProvider channelConfigProvider;

    @Operation(summary = "第三方平台配置列表")
    @PostMapping("/list")
    public BaseResponse<ChannelConfigListResponse> list() {
        return channelConfigQueryProvider.list();
    }

    @Operation(summary = "修改第三方平台配置")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid ChannelConfigModifyRequest request) {
        channelConfigProvider.modify(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询第三方平台配置信息")
    @Parameter(name = "configType",
            description = "第三方平台配置", required = true)
    @RequestMapping(value = "/{configType}", method = RequestMethod.GET)
    public BaseResponse<ChannelConfigResponse> list(@PathVariable Integer configType) {
        return channelConfigQueryProvider.getByType(ChannelConfigByTypeRequest.builder().channelType(ThirdPlatformType.fromValue(configType)).build());
    }
}
