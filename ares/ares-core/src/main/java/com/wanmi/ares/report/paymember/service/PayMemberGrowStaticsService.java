package com.wanmi.ares.report.paymember.service;

import com.wanmi.ares.report.paymember.dao.PayMemberGrowReportMapper;
import com.wanmi.ares.report.paymember.dao.PayMemberMapper;
import com.wanmi.ares.report.paymember.dao.PayMemberRecordMapper;
import com.wanmi.ares.report.paymember.model.root.PayMemberGrowReport;
import com.wanmi.ares.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className PayMemberGrowStaticsService
 * @description 付费会员增长统计服务
 * @date 2022/5/26 10:26 AM
 **/
@Service
public class PayMemberGrowStaticsService {

    @Autowired
    private PayMemberGrowReportMapper payMemberGrowReportMapper;

    @Autowired
    private PayMemberMapper payMemberMapper;
    
    @Autowired
    private PayMemberRecordMapper payMemberRecordMapper;

    /**
     * 每天定时任务跑的
     */
    @Transactional
    public void generateYesterdayGrowth() {
        generateAllCustomerGrowth(LocalDate.now().minusDays(1));
    }


    /**
     * 5分钟跑的
     */
    @Transactional
    public void generateTodayGrowth() {
        generateAllCustomerGrowth(LocalDate.now());
    }

    /**
     * 增长数据统计
     * @param date
     */
    public void generateAllCustomerGrowth(LocalDate date) {
        PayMemberGrowReport report = new PayMemberGrowReport();
        String localDate = DateUtil.formatLocalDate(date, DateUtil.FMT_DATE_1);
        String beginTime = DateUtil.getBeginTime(date,DateUtil.FMT_TIME_1);
        String endTime = DateUtil.getEndTime(date,DateUtil.FMT_TIME_1);

        //付费会员总数
        long total = payMemberMapper.queryTotal(date);
        //新增会员数
        long dayGrowthCount = payMemberMapper.queryGrowthCount(date);
        //续费会员数
        long dayRenewalCount = payMemberRecordMapper.queryRenewalCount(beginTime, endTime);
        //到期未续费会员数
        long dayOvertimeCount = payMemberMapper.queryOverTimeCount(date);
        report.setAllCount(total);
        report.setBaseDate(localDate);
        report.setDayGrowthCount(dayGrowthCount);
        report.setDayRenewalCount(dayRenewalCount);
        report.setDayOvertimeCount(dayOvertimeCount);
        payMemberGrowReportMapper.deletePayMemberGrowthReportByDate(date.toString());
        payMemberGrowReportMapper.savePayMemberGrowthReport(report);
    }
}
