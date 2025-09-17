package com.wanmi.sbc.job;

import com.wanmi.sbc.customer.api.provider.storeevaluate.StoreEvaluateStatisticsProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 店铺评价统计
 *
 * @author liutao 2019-02-27
 */
@Component
@Slf4j
public class StoreEvaluateStatisticsHandler {

    @Autowired
    private StoreEvaluateStatisticsProvider storeEvaluateStatisticsProvider;

    @XxlJob(value = "storeEvaluateStatisticsHandler")
    public void execute() throws Exception {
        storeEvaluateStatisticsProvider.statistics();
    }
}
