package com.wanmi.sbc.empower.sm;

import com.wanmi.sbc.empower.bean.enums.StratagemPlatformType;
import com.wanmi.sbc.empower.bean.enums.StratagemServiceType;
import com.wanmi.sbc.setting.api.provider.statisticssetting.StatisticsSettingProvider;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
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
public class StratagemContext implements InitializingBean {

    @Autowired(required = false)
    List<StratagemBaseService> stratagemServiceList;

    @Autowired
    private StatisticsSettingProvider statisticsSettingProvider;

    private Map<StratagemPlatformType, Map<StratagemServiceType, StratagemBaseService>> stratagemServiceMap;

    @Override
    public void afterPropertiesSet() {
        stratagemServiceMap = new HashMap<>();
        if (CollectionUtils.isEmpty(stratagemServiceList)) {
            return;
        }
        for(StratagemBaseService thirdPlatformService : stratagemServiceList) {
            Map<StratagemServiceType, StratagemBaseService> serviceMap = stratagemServiceMap.get(thirdPlatformService.getPlatformType());
            if (null == serviceMap || serviceMap.isEmpty()) {
                serviceMap = new HashMap<>();
            }
            serviceMap.put(thirdPlatformService.getServiceType(), thirdPlatformService);
            stratagemServiceMap.put(thirdPlatformService.getPlatformType(), serviceMap);
        }
    }

    /**
     * @param serviceType
     * @param <T>
     * @return
     */
    public <T> T getStratagemService(StratagemServiceType serviceType) {
        StratagemPlatformType platformType = this.getPlatform();
        if (Objects.isNull(platformType)) {
            return  null;
        }
        if (!stratagemServiceMap.containsKey(platformType)
                || !stratagemServiceMap.get(platformType).containsKey(serviceType)) {
            return null;
        }
        return (T) stratagemServiceMap.get(platformType).get(serviceType);
    }

    /**
     * @description   获取数谋平台类型
     * @author  wur
     * @date: 2022/11/18 9:27
     * @return
     **/
    private StratagemPlatformType getPlatform() {
        //查询是否开启op数模
        QmStatisticsSettingResponse settingResponse =
                statisticsSettingProvider.getQmSetting().getContext();
        if (Objects.isNull(settingResponse) || Objects.isNull(settingResponse.getStatus()) || settingResponse.getStatus() == 0) {
            return null;
        }
        return StratagemPlatformType.OP;
    }
}