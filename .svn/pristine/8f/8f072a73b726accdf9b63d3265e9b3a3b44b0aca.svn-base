package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelAddressProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelAddressRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelAddressResponse;
import com.wanmi.sbc.empower.channel.base.ChannelAddressBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Objects;

/**
 * @author wur
 * @className VopAddressController
 * @description vop地址api
 * @date 2021/5/11 10:33
 */
@RestController
public class ChannelAddressController implements ChannelAddressProvider {

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse<ChannelAddressResponse> getAddress(@RequestBody @Valid ChannelAddressRequest request) {
        ThirdPlatformType type = request.getThirdPlatformType();

        ChannelAddressResponse addressResponse =
                channelServiceFactory
                        .getChannelService(ChannelAddressBaseService.class, type)
                        .getAddress(request);
        if (Objects.isNull(addressResponse)) {
            return BaseResponse.FAILED();
        }
        return BaseResponse.success(addressResponse);
    }
}
