package com.wanmi.ares.scheduled.paymember;

import com.wanmi.ares.report.paymember.service.PayMemberJobService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PayMemberJobHandler extends IJobHandler {

    @Autowired
    private PayMemberJobService payMemberJobService;

    @XxlJob(value = "payMemberJobHandler")
    public void execute() throws Exception {
        String type = XxlJobHelper.getJobParam();
        payMemberJobService.generatePayMemberData(type);
    }
}
