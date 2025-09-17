package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelConfigProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigModifyRequest;
import com.wanmi.sbc.empower.channel.base.ChannelConfigBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className ChannelConfigController
 * @description 第三方渠道api
 * @date 2021/5/11 10:33
 */
@RestController
public class ChannelConfigController implements ChannelConfigProvider {

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse modify(@RequestBody @Valid ChannelConfigModifyRequest request) {
        request.checkParam();
        ThirdPlatformType type = request.getThirdPlatformType();
        channelServiceFactory.getChannelService(ChannelConfigBaseService.class, type).modify(request);
        return BaseResponse.SUCCESSFUL();
    }
}
