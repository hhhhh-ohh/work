package com.wanmi.sbc.empower.logisticssetting.service;

import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @className LogisticsQueryServiceFactory
 * @description 物流查询服务工厂
 * @author songhanlin
 * @date 2021/4/13 上午11:28
 **/
@Component
public class LogisticsQueryServiceFactory {

    /**
     * @className LogisticsQueryServiceFactory
     * @description 自动注入到map
     * @author songhanlin
     * @date 2021/4/12 下午3:18
     **/
    @Autowired
    private Map<String , LogisticsQueryBaseService> expressQueryServiceMap;

    /**
     * @description 获取物流查询接口
     * @author  songhanlin
     * @date: 2021/4/13 上午11:28
     * @param logisticsType
     * @return com.wanmi.sbc.empower.logisticssetting.service.LogisticsQueryBaseService
     **/
    public LogisticsQueryBaseService create(LogisticsType logisticsType){
        return expressQueryServiceMap.get(logisticsType.getLogisticsType());
    }
}
