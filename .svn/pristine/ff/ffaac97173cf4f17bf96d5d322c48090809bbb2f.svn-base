package com.wanmi.sbc.empower.provider.impl.channel.base;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelCateQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGetAllCateRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelCateGetAllResponse;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelGoodsCateVO;
import com.wanmi.sbc.empower.channel.base.ChannelCateBaseService;
import com.wanmi.sbc.empower.channel.base.ChannelServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChannelCateQueryController implements ChannelCateQueryProvider {

    @Autowired private ChannelServiceFactory channelServiceFactory;

    @Override
    public BaseResponse<ChannelCateGetAllResponse> getAllCate(ChannelGetAllCateRequest request) {
        List<ChannelGoodsCateVO> allCategory =
                channelServiceFactory
                        .getChannelService(ChannelCateBaseService.class, request.getChannelType())
                        .getAllCategory();
        ChannelCateGetAllResponse getAllLinkedMallCateResponse = new ChannelCateGetAllResponse();
        getAllLinkedMallCateResponse.setChannelGoodsCateVOList(allCategory);
        return BaseResponse.success(getAllLinkedMallCateResponse);
    }
}
