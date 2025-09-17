package com.wanmi.ares.scheduled.employee;


import com.wanmi.ares.report.employee.service.ReplayStoreCustomerRelaService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReplayStoreCustomerRelaGenerate {
    @Autowired
    ReplayStoreCustomerRelaService replayStoreCustomerRelaService;

    @XxlJob(value = "ReplayStoreCustomerRelaGenerate")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        replayStoreCustomerRelaService.generateData(param, LocalDate.now());
    }
}
