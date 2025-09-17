package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelConfigQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigByTypeRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigListResponse;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigResponse;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelConfigVO;
import com.wanmi.sbc.empower.channel.base.ChannelConfigBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelConfigService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import com.wanmi.sbc.empower.channel.model.root.ChannelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author wur
 * @className ChannelConfigQueryController
 * @description 第三方渠道api
 * @date 2021/5/11 10:33
 */
@RestController
public class ChannelConfigQueryController implements ChannelConfigQueryProvider {

    @Autowired private ChannelConfigService channelConfigService;

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse<ChannelConfigListResponse> list() {
        List<ChannelConfig> configs = channelConfigService.getByDelFlag(DeleteFlag.NO);
        return BaseResponse.success(
                ChannelConfigListResponse.builder()
                        .configVOList(KsBeanUtil.convert(configs, ChannelConfigVO.class))
                        .build());
    }

    @Override
    public BaseResponse<ChannelConfigResponse> getByType(
            @RequestBody @Valid ChannelConfigByTypeRequest request) {
        ThirdPlatformType type = request.getChannelType();
        return BaseResponse.success(
                channelServiceFactory
                        .getChannelService(ChannelConfigBaseService.class, type)
                        .find());
    }
}
