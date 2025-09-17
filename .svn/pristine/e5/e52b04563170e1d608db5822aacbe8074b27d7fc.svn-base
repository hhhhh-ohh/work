package com.wanmi.sbc.empower.sellplatform;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wur
 * @className ThirdPlatformContext
 * @description
 * @date 2022/4/15 11:05
 **/
@Component
@Slf4j
public class PlatformContext implements InitializingBean {

    @Autowired(required = false)
    List<PlatformBaseService> thirdPlatformServiceList;

    private Map<SellPlatformType, Map<PlatformServiceType, PlatformBaseService>> thirdPlatformServiceMap;

    @Override
    public void afterPropertiesSet() {
        thirdPlatformServiceMap = new HashMap<>();
        if (CollectionUtils.isEmpty(thirdPlatformServiceList)) {
            return;
        }
        for(PlatformBaseService thirdPlatformService : thirdPlatformServiceList) {
            Map<PlatformServiceType, PlatformBaseService> serviceMap = thirdPlatformServiceMap.get(thirdPlatformService.getType());
            if (null == serviceMap || serviceMap.isEmpty()) {
                serviceMap = new HashMap<>();
            }
            serviceMap.put(thirdPlatformService.getServiceType(), thirdPlatformService);
            thirdPlatformServiceMap.put(thirdPlatformService.getType(), serviceMap);
        }
    }

    /**
     * @param thirdPlatformType
     * @param serviceType
     * @param <T>
     * @return
     */
    public <T> T getPlatformService(SellPlatformType thirdPlatformType, PlatformServiceType serviceType) {
        if (Objects.isNull(thirdPlatformType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (!thirdPlatformServiceMap.containsKey(thirdPlatformType)
                || !thirdPlatformServiceMap.get(thirdPlatformType).containsKey(serviceType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000021);
        }
        return (T) thirdPlatformServiceMap.get(thirdPlatformType).get(serviceType);
    }
}