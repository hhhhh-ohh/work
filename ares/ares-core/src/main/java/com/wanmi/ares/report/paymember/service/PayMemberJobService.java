package com.wanmi.ares.report.paymember.service;

import com.wanmi.ares.enums.StatisticsDataType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className PayMemberJobService
 * @description
 * @date 2022/5/26 1:58 PM
 **/
@Service
public class PayMemberJobService {

    private static final int YESTERDAY_NUM = 1;


    @Autowired
    private PayMemberAreaStatisticsService payMemberAreaStatisticsService;

    @Autowired
    private PayMemberGrowStaticsService payMemberGrowStaticsService;

    @Transactional
    public void generatePayMemberData(String type){
        if(StringUtils.isNotBlank(type)){
            String[] typeArr = type.split(",");
            for(String generateType : typeArr){
                if(generateType.equals(StatisticsDataType.TODAY.toValue()+"")){
                    //执行当天的统计数据
                    payMemberAreaStatisticsService.generatePayMemberAreaDistribute(LocalDate.now());
                    payMemberGrowStaticsService.generateTodayGrowth();
                } else if(generateType.equals(StatisticsDataType.YESTERDAY.toValue()+"")){
                    //执行昨天的统计数据
                    payMemberAreaStatisticsService.generatePayMemberAreaDistribute(LocalDate.now().minusDays(YESTERDAY_NUM));
                    payMemberGrowStaticsService.generateYesterdayGrowth();
                }
            }
        }
    }
}
