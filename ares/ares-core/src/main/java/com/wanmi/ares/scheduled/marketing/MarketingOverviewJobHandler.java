package com.wanmi.ares.scheduled.marketing;

import com.google.common.base.Stopwatch;
import com.wanmi.ares.marketing.overview.service.MarketingOverviewService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 营销总概览统计任务执行开始
 * @className MarketingOverviewJobHandler
 * @author gaobo
 * @date 2021/2/29 16:57
 **/
@Slf4j
@Component
public class MarketingOverviewJobHandler {

    @Autowired
    private MarketingOverviewService marketingOverviewService;

    @XxlJob(value = "marketingOverviewJobHandler")
    public void execute() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        if (log.isInfoEnabled()) {
            log.info("营销总概览任务执行开始");
        }
        marketingOverviewService.insert();
        if (log.isInfoEnabled()) {
            log.info("营销总概览任务执行结束, 时间 {}", stopwatch);
        }
    }
}
