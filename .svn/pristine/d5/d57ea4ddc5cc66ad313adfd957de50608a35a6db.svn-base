package com.wanmi.sbc.empower.sm.op;

import com.wanmi.sbc.setting.api.provider.statisticssetting.StatisticsSettingProvider;
import com.wanmi.sbc.setting.api.response.statisticssetting.QmStatisticsSettingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @description  op数谋配置服处理
 * @author  wur
 * @date: 2022/11/17 10:46
 **/
@Service
public class OpConfigService {

    @Autowired private StatisticsSettingProvider statisticsSettingProvider;

    public QmStatisticsSettingResponse queryConfig() {
        QmStatisticsSettingResponse settingResponse =
                statisticsSettingProvider.getQmSetting().getContext();
        if (Objects.isNull(settingResponse) || Objects.isNull(settingResponse.getStatus()) || settingResponse.getStatus() == 0) {
            return null;
        }
        return settingResponse;
    }


}