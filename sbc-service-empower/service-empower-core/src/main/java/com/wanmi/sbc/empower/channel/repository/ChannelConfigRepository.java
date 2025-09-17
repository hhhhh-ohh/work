package com.wanmi.sbc.empower.channel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.empower.channel.model.root.ChannelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 渠道配置
 * @author  wur
 * @date: 2021/5/20 10:01
 *
 **/
@Repository
public interface ChannelConfigRepository extends JpaRepository<ChannelConfig, Long>,
        JpaSpecificationExecutor<ChannelConfig> {

    /**
    *
     * 根据渠道标识查询
     * @author  wur
     * @date: 2021/5/20 10:20
     * @param channelType   渠道标识
     * @param delFlag       删除标识
     * @return     渠道配置信息
     **/
    ChannelConfig findByChannelTypeAndDelFlag(ThirdPlatformType channelType, DeleteFlag delFlag);

    /**
    *  根据删除标识查询
     * @author  wur
     * @date: 2021/5/20 10:54
     * @param delFlag  删除标识
     * @return 渠道配置信息
     **/
    List<ChannelConfig> findByDelFlag(DeleteFlag delFlag);
}
