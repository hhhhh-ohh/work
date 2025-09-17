package com.wanmi.ares.report.paymember.service;

import com.wanmi.ares.report.paymember.dao.PayMemberAreaDistributeReportMapper;
import com.wanmi.ares.report.paymember.model.request.PayMemberAreaDistributeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className PayMemberAreaStatisticsService
 * @description 客户区域统计服务
 * @date 2022/5/25 11:11 AM
 **/
@Service
public class PayMemberAreaStatisticsService {

    @Autowired
    private PayMemberAreaDistributeReportMapper payMemberAreaDistributeReportMapper;

    /**
     * 生成付费会员统计--按客户地区分布数据
     * @param targetDate
     */
    @Transactional
    public void generatePayMemberAreaDistribute(LocalDate targetDate){
        PayMemberAreaDistributeRequest areaRequest = new PayMemberAreaDistributeRequest();
        areaRequest.setTargetDate(targetDate);
        payMemberAreaDistributeReportMapper.deleteReport(targetDate.toString());
        payMemberAreaDistributeReportMapper.generateBossPayMemberAreaDistribute(areaRequest);
    }
}
