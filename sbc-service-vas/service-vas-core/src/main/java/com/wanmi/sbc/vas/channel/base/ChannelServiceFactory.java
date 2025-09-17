package com.wanmi.sbc.vas.channel.base;

import com.wanmi.sbc.common.annotation.ThirdService;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 渠道服务工厂
 * @author zhengyang
 * @date 2021/5/11 14:17
 */
@Slf4j
@Component
public class ChannelServiceFactory {

    @Resource
    private List<ChannelBaseService> channelBaseServices;

    /***
     * Service名称-实例映射，用于快速获得Service
     */
    private final static Map<String,Map<ThirdPlatformType,ChannelBaseService>> baseServiceMap
            = new ConcurrentHashMap();

    /***
     * 获得渠道Service
     * @param serviceCls            渠道接口类
     * @param thirdPlatformType     三方类型
     * @param <T>                   泛型
     * @return
     */
    public <T> T getChannelService(Class<T> serviceCls, ThirdPlatformType thirdPlatformType) {
        if (!baseServiceMap.containsKey(serviceCls.getName())
                || !baseServiceMap.get(serviceCls.getName()).containsKey(thirdPlatformType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return (T) baseServiceMap.get(serviceCls.getName()).get(thirdPlatformType);
    }

    /***
     * 初始化baseServiceMap缓存
     */
    @PostConstruct
    public synchronized void initChannelBaseMap(){
        if(CollectionUtils.isEmpty(channelBaseServices)){
            return;
        }

        channelBaseServices.forEach(channelBaseService -> {
            final Class<?> serviceClz = channelBaseService.getClass();
            // 获得标准对象
            ThirdService thirdService = serviceClz.getAnnotation(ThirdService.class);

            if(Objects.isNull(thirdService)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }

            Class<?>[] interfaces = serviceClz.getInterfaces();
            if(interfaces == null || interfaces.length < 1){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
            }
            // TODO 鉴别Interface是否需要的
            final String serviceName = interfaces[0].getName();

            Map<ThirdPlatformType,ChannelBaseService> second = baseServiceMap.get(serviceName);
            if(Objects.isNull(second)){
                second = new ConcurrentHashMap();
                baseServiceMap.putIfAbsent(serviceName, second);
            }

            second.put(thirdService.type(),channelBaseService);
        });
    }
}
