package com.wanmi.sbc.vas.sellplatform;

import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
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
 * @className SellPlatformContext
 * @description
 * @date 2022/4/15 11:05
 **/
@Component
@Slf4j
public class SellPlatformContext implements InitializingBean {

    @Autowired(required = false)
    List<SellPlatformBaseService> sellPlatformServiceList;

    @Autowired private RedisUtil redisService;

    private Map<SellPlatformType, Map<SellPlatformServiceType, SellPlatformBaseService>> sellPlatformServiceMap;

    @Override
    public void afterPropertiesSet() {
        sellPlatformServiceMap = new HashMap<>();
        if (CollectionUtils.isEmpty(sellPlatformServiceList)) {
            return;
        }
        for(SellPlatformBaseService thirdPlatformService : sellPlatformServiceList) {
            Map<SellPlatformServiceType, SellPlatformBaseService> serviceMap = sellPlatformServiceMap.get(thirdPlatformService.getType());
            if (null == serviceMap || serviceMap.isEmpty()) {
                serviceMap = new HashMap<>();
            }
            serviceMap.put(thirdPlatformService.getServiceType(), thirdPlatformService);
            sellPlatformServiceMap.put(thirdPlatformService.getType(), serviceMap);
        }
    }

    /**
     * @param thirdPlatformType
     * @param serviceType
     * @param <T>
     * @return
     */
    public <T> T getPlatformService(SellPlatformType thirdPlatformType, SellPlatformServiceType serviceType) {
        if (Objects.isNull(thirdPlatformType)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //验证
        if (VASStatus.ENABLE.toValue().equals(redisService.hget(
                ConfigKey.VALUE_ADDED_SERVICES.toValue(),VASConstants.getBySellPlatformType(thirdPlatformType).toValue()))) {
            if (!sellPlatformServiceMap.containsKey(thirdPlatformType)
                    || !sellPlatformServiceMap.get(thirdPlatformType).containsKey(serviceType)) {
                return null;
            }
            return (T) sellPlatformServiceMap.get(thirdPlatformType).get(serviceType);
        } else {
            return null;
        }
    }
}