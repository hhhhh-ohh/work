package com.wanmi.sbc.order.plugin;

import com.wanmi.sbc.common.annotation.PluginService;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ReflectUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wur
 * @description 插件服务工厂
 * @date 2021/6/21 14:17
 */
@Component
public class PluginOrderServiceFactory {

    @Resource
    private List<PluginOrderBaseService> pluginBaseServices;

    /**
     * Service名称-实例映射，用于快速获得Service
     */
    private static final Map<String, Map<PluginType, PluginOrderBaseService>> BASE_SERVICE_MAP =
            new ConcurrentHashMap();

    /**
     * * 获得渠道Service
     *
     * @param serviceCls  插件接口类
     * @param pluginType 插件类型
     * @param <T>        泛型
     * @return
     */
    public <T> T getPluginService(Class<T> serviceCls, PluginType pluginType) {
        if (Objects.isNull(pluginType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 初始化缓存
        initChannelBaseMap(serviceCls.getName(), pluginType);
        if (!BASE_SERVICE_MAP.containsKey(serviceCls.getName())
                || !BASE_SERVICE_MAP.get(serviceCls.getName()).containsKey(pluginType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return (T) BASE_SERVICE_MAP.get(serviceCls.getName()).get(pluginType);
    }

    /**
     * * 初始化baseServiceMap缓存
     *
     * @param serviceName Service名称
     * @param pluginType 三方类型
     */
    private synchronized void initChannelBaseMap(
            String serviceName, PluginType pluginType) {
        // 判断是否存在
        if (BASE_SERVICE_MAP.containsKey(serviceName)
                && BASE_SERVICE_MAP.get(serviceName).containsKey(pluginType)) {
            return;
        }
        if (CollectionUtils.isEmpty(pluginBaseServices)) {
            return;
        }
        List<PluginOrderBaseService> baseServiceList =
                pluginBaseServices.stream().filter(channel -> ReflectUtils.hasInterface(channel.getClass(), serviceName)).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(baseServiceList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }

        baseServiceList.forEach(pluginBaseService -> {
            // 获得标准对象
            PluginService pluginService = ReflectUtils.getAnnotation(pluginBaseService.getClass(), PluginService.class);
            if (Objects.isNull(pluginService)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }

            Map<PluginType, PluginOrderBaseService> second = BASE_SERVICE_MAP.get(serviceName);
            if (Objects.isNull(second)) {
                second = new ConcurrentHashMap();
                BASE_SERVICE_MAP.putIfAbsent(serviceName, second);
            }
            second.put(pluginService.type(), pluginBaseService);
        });
    }
}
