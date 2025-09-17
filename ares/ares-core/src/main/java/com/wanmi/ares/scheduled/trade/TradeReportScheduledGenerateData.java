package com.wanmi.ares.scheduled.trade;

import com.wanmi.ares.report.trade.service.TradeReportService;
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
public class TradeReportScheduledGenerateData {

    @Resource
    private TradeReportService tradeReportService;

    @XxlJob(value = "tradeReportScheduledGenerateData")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        tradeReportService.generateData(param,LocalDate.now());
    }
}
