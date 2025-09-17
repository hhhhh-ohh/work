package com.wanmi.sbc.job;

import com.wanmi.sbc.job.service.LedgerCallBackJobService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 进件 补偿
 */
@Component
@Slf4j
public class LedgerCompensateJobHandler {

    @Autowired
    LedgerCallBackJobService ledgerCallBackJobService;

    @XxlJob(value = "LedgerCompensateJobHandler")
    public void execute() throws Exception {
        log.info("进件LedgerCompensateJobHandler start");
        String param = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(param)) {
            ledgerCallBackJobService.executeContract(param);
        }
    }
}
