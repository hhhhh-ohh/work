package com.wanmi.sbc.empower.channel.base;

import com.wanmi.sbc.empower.api.request.channel.base.ChannelConfigModifyRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelConfigResponse;

/**
 * 渠道配置服务
 * @author  wur
 * @date: 2021/5/21 11:44
 **/
public interface ChannelConfigBaseService extends ChannelBaseService{

    /**
     * 渠道信息修改
     * @author  wur
     * @date: 2021/5/21 11:46
     * @param request  渠道信息
     **/
    void modify(ChannelConfigModifyRequest request);

    /**
     * 查询渠道信息
     * @author  wur
     * @date: 2021/5/21 11:46
     * @return 渠道信息
     **/
    ChannelConfigResponse find();

}
