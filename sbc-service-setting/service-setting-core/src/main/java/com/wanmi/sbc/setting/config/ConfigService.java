package com.wanmi.sbc.setting.config;

import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.ConfigModifyRequest;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.ConfigStatusModifyByTypeAndKeyRequest;
import com.wanmi.sbc.setting.api.request.ConfigUpdateRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.OfflinePaySettingResponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.setting.util.SpecificationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by feitingting on 2019/11/6.
 */

@Service
/**
 * 系统配置类
 */
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private RedisUtil redisService;
    /**
     * 根据key查询配置信息
     *
     * @param configKey
     * @param delFlag
     * @return
     */
    public List<Config> findByConfigKeyAndDelFlag(String configKey, DeleteFlag delFlag) {
        //只做基本的查询，不带出具体的context信息
        List<Config> configs = configRepository.findByConfigKeyAndDelFlag(configKey, delFlag);
        final String key = "liveSwitch";
        if (key.equals(configKey) && CollectionUtils.isEmpty(configs)) {
            Config config = new Config();
            config.setConfigKey(key);
            config.setConfigType(key);
            config.setStatus(0);
            config.setCreateTime(LocalDateTime.now());
            config.setDelFlag(DeleteFlag.NO);
            config.setConfigName("小程序直播");
            configRepository.save(config);
            configs.add(config);
            return configs;
        }
        return configs.stream().map(config -> {
            config.setContext(null);
            return config;
        }).collect(Collectors.toList());

    }

    /**
     * 根据key查询配置信息
     *
     * @param configKey
     * @param delFlag
     * @return
     */
    public List<Config> findByConfigKeyAndDelFlagNew(String configKey, DeleteFlag delFlag) {
        return configRepository.findByConfigKeyAndDelFlag(configKey, delFlag);
    }

    @Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'systemConfigType:'+#configType")
    public SystemConfigTypeResponse findByConfigTypeAndDelFlag(String configType, DeleteFlag deleteFlag) {
        Config config = configRepository.findByConfigTypeAndDelFlag(configType, deleteFlag);
        SystemConfigTypeResponse response = new SystemConfigTypeResponse();
        response.setConfig(wrapperVo(config));
        return response;
    }

    /**
     * 订单列表展示设置
     *
     * @return 0:订单精简 1:订单明细
     */
    @Transactional
    public Integer queryOrderListShowType() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setDelFlag(DeleteFlag.NO.toValue());
        request.setConfigKey(ConfigKey.ORDER_LIST_SHOW_TYPE.toString());
        request.setConfigType(ConfigType.ORDER_LIST_SHOW_TYPE.toValue());
        List<Config> configList = configRepository.findAll(SpecificationUtil.getWhereCriteria(request));
        if (CollectionUtils.isEmpty(configList)) {
            Config config = new Config();
            config.setConfigKey(ConfigKey.ORDER_LIST_SHOW_TYPE.toString());
            config.setConfigType(ConfigType.ORDER_LIST_SHOW_TYPE.toValue());
            config.setStatus(0);
            config.setDelFlag(DeleteFlag.NO);
            config.setConfigName("订单列表展示设置");
            config.setCreateTime(LocalDateTime.now());
            config.setUpdateTime(LocalDateTime.now());
            configRepository.save(config);
            return config.getStatus();
        }
        return configList.get(0).getStatus();
    }

    /**
     * 订单列表展示设置更新
     *
     * @param status 状态
     */
    @Transactional
    public void modifyOrderListShowType(Integer status) {
        configRepository.updateStatusByType(ConfigType.ORDER_LIST_SHOW_TYPE.toValue(), status, LocalDateTime.now());
    }


    public ConfigVO wrapperVo(Config config) {
        if (config != null) {
            ConfigVO configVO = new ConfigVO();
            KsBeanUtil.copyPropertiesThird(config, configVO);
            return configVO;
        }
        return null;
    }

    /**
     * 修改直播开关
     *
     * @param request
     */
    public void update(ConfigUpdateRequest request) {

        configRepository.updateStatusConfigKey(request.getConfigKey(), request.getStatus());
    }

    /**
     * 获取线下支付设置
     *
     * @return
     */
    public OfflinePaySettingResponse getOfflinePaySetting() {
        return this.getStatusByType(ConfigType.OFFLINE_PAY_SETTING.toValue(), CacheKeyConstant.OFFLINE_PAY_SETTING);
    }

    /**
     * 修改线下支付设置
     *
     * @param request
     */
    @Transactional
    public void modifyOfflinePaySetting(ConfigStatusModifyByTypeAndKeyRequest request) {
        this.modifyStatusByTypeAndKey(request, CacheKeyConstant.OFFLINE_PAY_SETTING);
    }

    @Transactional
    public void update(List<ConfigModifyRequest> configModifyRequests) {
        configModifyRequests.stream().forEach(configModifyRequest -> {
            if (Objects.nonNull(configModifyRequest.getId())) {
                configRepository.updateConfigById(configModifyRequest.getId(), configModifyRequest.getStatus(), configModifyRequest.getContext());
            }
        });
    }

    /**
     * 修改设置
     * type:offline_pay_setting-线下支付设置，status 0：关闭 1：开启
     *
     * @param request
     * @param cacheKey
     */
    private void modifyStatusByTypeAndKey(ConfigStatusModifyByTypeAndKeyRequest request, String cacheKey) {
        configRepository.updateStatusByTypeAndConfigKey(request.getConfigType().toValue(), request.getConfigKey().toValue(),
                request.getStatus(), null);

        this.cacheStatus(cacheKey, request.getStatus());
    }

    /**
     * 获取设置
     *
     * @return
     */
    private OfflinePaySettingResponse getStatusByType(String type, String cacheKey) {
        Integer status = null;

        Config config = configRepository.findByConfigTypeAndDelFlag(type,
                DeleteFlag.NO);

        if (config != null && config.getStatus() != null) {
            status = config.getStatus();

            this.cacheStatus(cacheKey, status);
        }

        return OfflinePaySettingResponse.builder().status(status).build();
    }

    /**
     * 缓存线下支付设置
     *
     * @param status
     */
    private void cacheStatus(String key, Integer status) {
        redisService.setString(key, String.valueOf(status));
    }
}
