package com.wanmi.ares.scheduled.employee;

import com.wanmi.ares.report.employee.service.EmployeeReportGenerateService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.time.LocalDate;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-27 11:39
 */
@Component
public class EmployeeReportScheduledGenerate {

    @Resource
    private EmployeeReportGenerateService employeeReportGenerateService;

    @XxlJob(value = "employeeReportScheduledGenerate")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        employeeReportGenerateService.generateData(param,LocalDate.now());
    }
}
