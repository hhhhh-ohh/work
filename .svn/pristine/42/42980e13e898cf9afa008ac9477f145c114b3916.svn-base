package com.wanmi.sbc.setting.statisticssetting.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.api.request.statisticssetting.QmStatisticsSettingRequest;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.systemconfig.model.root.SystemConfig;
import com.wanmi.sbc.setting.systemconfig.service.SystemConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author 张文昌
 * @className StatisticsSettingService
 * @description 数谋基础设置
 * @date 2022/1/6 18:35
 */
@Service
public class StatisticsSettingService {
    @Autowired
    private SystemConfigService configService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 修改千米数谋基础设置
     *
     * @author lq
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyQmSetting(QmStatisticsSettingRequest request) {
        SystemConfig systemConfig = configService.findByConfigKeyAndConfigType(
                ConfigKey.STATISTICSSETTING.toValue(), ConfigType.QM_STATISTICS_SETTING.toValue());
        if (Objects.isNull(systemConfig)) {
            systemConfig = new SystemConfig();
            systemConfig.setConfigKey(ConfigKey.STATISTICSSETTING.toValue());
            systemConfig.setConfigType(ConfigType.QM_STATISTICS_SETTING.toValue());
            systemConfig.setStatus(1);
            systemConfig.setCreateTime(LocalDateTime.now());
            systemConfig.setDelFlag(DeleteFlag.NO);
            systemConfig.setConfigName("千米数谋基本配置");
        }
        systemConfig.setStatus(request.getStatus());
        systemConfig.setUpdateTime(LocalDateTime.now());
        systemConfig.setContext(JSONObject.toJSONString(request));
        configService.modify(systemConfig);
        redisUtil.setString(
                RedisKeyConstant.STATISTICS_SETTING.concat(systemConfig.getConfigType()),
                JSONObject.toJSONString(systemConfig));
    }

    /**
     * @return
     * @description 查询千米数谋基础设置
     * @author 张文昌
     * @date 2022/1/6 18:49
     */
    public QmStatisticsSettingResponse getQmSetting() {
        SystemConfig systemConfig = configService.findByConfigKeyAndConfigType(
                ConfigKey.STATISTICSSETTING.toValue(), ConfigType.QM_STATISTICS_SETTING.toValue());
        if (Objects.isNull(systemConfig)) {
            return new QmStatisticsSettingResponse();
        }
        return JSONObject.parseObject(systemConfig.getContext(),
                QmStatisticsSettingResponse.class);
    }

    /**
     * @description
     * @author  wur
     * @date: 2022/11/17 13:44
     * @return
     **/
    public QmStatisticsSettingResponse getQmSettingCache() {
        String redis = redisUtil.getString(RedisKeyConstant.STATISTICS_SETTING.concat(ConfigType.QM_STATISTICS_SETTING.toValue()));
        SystemConfig systemConfig;
        if (StringUtils.isNotEmpty(redis)) {
            systemConfig = JSONObject.parseObject(redis,
                    SystemConfig.class);
        } else {
            systemConfig = configService.findByConfigKeyAndConfigType(
                    ConfigKey.STATISTICSSETTING.toValue(), ConfigType.QM_STATISTICS_SETTING.toValue());
        }
        if (Objects.isNull(systemConfig)) {
            return new QmStatisticsSettingResponse();
        }
        return JSONObject.parseObject(systemConfig.getContext(),
                QmStatisticsSettingResponse.class);
    }

}
