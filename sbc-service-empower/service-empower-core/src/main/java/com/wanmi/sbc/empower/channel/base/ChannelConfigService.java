package com.wanmi.sbc.empower.channel.base;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.channel.model.root.ChannelConfig;
import com.wanmi.sbc.empower.channel.repository.ChannelConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 渠道配置服务
 * @author wur
 * @date: 2021/5/20 10:13
 */
@Service("ChannelConfigService")
public class ChannelConfigService {

    @Autowired private ChannelConfigRepository channelConfigRepository;

    /**
     * 根据删除标识查询
     * @author  wur
     * @date: 2021/5/20 10:57
     * @param delFlag 删除标识
     * @return    渠道配置列表
     **/
    public List<ChannelConfig> getByDelFlag (DeleteFlag delFlag) {
        return channelConfigRepository.findByDelFlag(delFlag);
    }
}
