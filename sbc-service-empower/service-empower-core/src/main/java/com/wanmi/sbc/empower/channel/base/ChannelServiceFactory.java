package com.wanmi.sbc.empower.channel.base;

import com.wanmi.sbc.common.annotation.ThirdService;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ReflectUtils;
import com.wanmi.sbc.common.util.SpringContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author daiyitian
 * @description 渠道服务工厂
 * @date 2021/5/11 14:17
 */
@Component
public class ChannelServiceFactory {

    /**
     * Service名称-实例映射，用于快速获得Service
     */
    private static final Map<String, Map<ThirdPlatformType, ChannelBaseService>> BASE_SERVICE_MAP =
            new ConcurrentHashMap();

    /**
     * * 获得渠道Service
     *
     * @param serviceCls        渠道接口类
     * @param thirdPlatformType 三方类型
     * @param <T>               泛型
     * @return
     */
    public <T> T getChannelService(Class<T> serviceCls, ThirdPlatformType thirdPlatformType) {
        if (Objects.isNull(thirdPlatformType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 初始化缓存
        initChannelBaseMap(serviceCls.getName(), thirdPlatformType);
        if (!BASE_SERVICE_MAP.containsKey(serviceCls.getName())
                || !BASE_SERVICE_MAP.get(serviceCls.getName()).containsKey(thirdPlatformType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return (T) BASE_SERVICE_MAP.get(serviceCls.getName()).get(thirdPlatformType);
    }

    /**
     * * 初始化baseServiceMap缓存
     *
     * @param serviceName       Service名称
     * @param thirdPlatformType 三方类型
     */
    private synchronized void initChannelBaseMap(
            String serviceName, ThirdPlatformType thirdPlatformType) {
        // 判断是否存在
        if (BASE_SERVICE_MAP.containsKey(serviceName)
                && BASE_SERVICE_MAP.get(serviceName).containsKey(thirdPlatformType)) {
            return;
        }
        Map<String, ChannelBaseService> maps = SpringContextHolder.getBeanType(ChannelBaseService.class);
        if (CollectionUtils.isEmpty(maps.values())) {
            return;
        }
        List<ChannelBaseService> baseServiceList =
                maps.values().stream().filter(channel -> ReflectUtils.hasInterface(channel.getClass(), serviceName)).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(baseServiceList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }

        baseServiceList.forEach(channelBaseService -> {
            // 获得标准对象
            ThirdService thirdService = ReflectUtils.getAnnotation(channelBaseService.getClass(), ThirdService.class);
            if (Objects.isNull(thirdService)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }

            Map<ThirdPlatformType, ChannelBaseService> second = BASE_SERVICE_MAP.get(serviceName);
            if (Objects.isNull(second)) {
                second = new ConcurrentHashMap();
                BASE_SERVICE_MAP.putIfAbsent(serviceName, second);
            }
            second.put(thirdService.type(), channelBaseService);
        });
    }
}
