package com.wanmi.sbc.job.agent.settlement;

import com.wanmi.sbc.order.api.provider.trade.TradeSettingProvider;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 订单业绩自动结账定时任务
 */
@Slf4j
@Component
public class AgentOrderJobHandler {


    @Autowired
    private TradeSettingProvider tradeSettingProvider;

    @XxlJob(value = "agentOrderJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("订单业绩自动结账定时任务执行 " + LocalDateTime.now());
        log.info("订单业绩自动结账定时任务执行开始 {}", LocalDateTime.now());

        tradeSettingProvider.modifyOrderPerformanceAutoSettle();

        log.info("订订单业绩自动结账定时任务执行结束 {}", LocalDateTime.now());
    }

}
