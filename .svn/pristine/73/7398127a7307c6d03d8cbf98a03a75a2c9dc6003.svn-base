package com.wanmi.ares.scheduled.customer;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * @ClassName ReplayCustomerOrderGenerate
 * @Description 客户统计客户客户订货报表统计
 * @Author lvzhenwei
 * @Date 2019/9/23 11:40
 **/
@Component
public class ReportCustomerGenerate {

    @Resource
    private CustomerDataStatisticsService customerOrderDataStatisticsService;

    @XxlJob(value = "reportCustomerGenerate")
    public void execute() throws Exception {
        String type = XxlJobHelper.getJobParam();
        customerOrderDataStatisticsService.generateCustomerOrderData(type);
    }
}
