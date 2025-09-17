package com.wanmi.sbc.init.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.redis.bean.RedisHsetBean;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelConfigQueryProvider;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelConfigVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** @Author: songhanlin @Date: Created In 13:44 2020/3/2 @Description: 增值服务公用服务类 */
@Slf4j
@Service
public class VASCommonService {
    private static final String SETTING_KEY = "other_setting";
    @Autowired private RedisUtil redisService;

    @Autowired private AuditQueryProvider auditQueryProvider;

    @Autowired private IEPService iepService;

    @Autowired private ChannelConfigQueryProvider channelConfigQueryProvider;

    /** 初始化缓存增值服务信息 */
    public void init() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigKey(ConfigKey.VALUE_ADDED_SERVICES.toString());
        List<ConfigVO> list =
                auditQueryProvider.getByConfigKey(request).getContext().getConfigVOList();

        List<RedisHsetBean> redisHsetBeans =
                list.stream()
                        .map(
                                item -> {
                                    RedisHsetBean redisHsetBean = new RedisHsetBean();
                                    redisHsetBean.setField(item.getConfigType());
                                    redisHsetBean.setValue(
                                            item.getStatus() == 0
                                                    ? VASStatus.DISABLE.toValue()
                                                    : VASStatus.ENABLE.toValue());
                                    return redisHsetBean;
                                })
                        .collect(Collectors.toList());

        List<ChannelConfigVO> channelConfigList =
                channelConfigQueryProvider.list().getContext().getConfigVOList();
        if (CollectionUtils.isNotEmpty(channelConfigList)) {
            channelConfigList.forEach(
                    channelConfigVO -> {
                        RedisHsetBean redisHsetBean = new RedisHsetBean();
                        String field = channelConfigVO.getChannelType().toString();
                        if (ThirdPlatformType.LINKED_MALL.equals(channelConfigVO.getChannelType())) {
                            field = RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG;
                        } else if (ThirdPlatformType.VOP.equals(channelConfigVO.getChannelType())) {
                            field = RedisKeyConstant.VOP_CHANNEL_CONFIG;
                        }
                        redisHsetBean.setField(field);
                        redisHsetBean.setValue(
                                EnableStatus.DISABLE.equals(channelConfigVO.getStatus())
                                        ? VASStatus.DISABLE.toValue()
                                        : VASStatus.ENABLE.toValue());
                        redisHsetBeans.add(redisHsetBean);
                    });
        }

        if (CollectionUtils.isNotEmpty(redisHsetBeans)) {
            if (list.stream()
                    .anyMatch(
                            i ->
                                    ConfigType.VAS_IEP_SETTING
                                                    .toValue()
                                                    .equalsIgnoreCase(i.getConfigType())
                                            && Constants.yes.equals(i.getStatus()))) {
                iepService.init(list);
            }
            redisService.delete(ConfigKey.VALUE_ADDED_SERVICES.toString());
            redisService.hsetPipeline(ConfigKey.VALUE_ADDED_SERVICES.toString(), redisHsetBeans);
        } else {
            log.info("无增值服务");
        }
        otherInit();
    }

    private void otherInit() {
        redisService.delete(SETTING_KEY);
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigKey(SETTING_KEY);
        List<ConfigVO> list =
                auditQueryProvider.getByConfigKey(request).getContext().getConfigVOList();
        List<RedisHsetBean> redisHsetBeans =
                list.stream()
                        .map(
                                item -> {
                                    RedisHsetBean redisHsetBean = new RedisHsetBean();
                                    redisHsetBean.setField(item.getConfigType());
                                    redisHsetBean.setValue(item.getContext());
                                    return redisHsetBean;
                                })
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(redisHsetBeans)) {
            redisService.hsetPipeline(SETTING_KEY, redisHsetBeans);
        } else {
            log.info("无配置服务");
        }
    }
}
