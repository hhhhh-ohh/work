package com.wanmi.sbc.job;

import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeProvider;
import com.wanmi.sbc.order.api.request.thirdplatformtrade.ThirdPlatformTradeCompensateRequest;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务Handler（Bean模式）
 * 第三方平台订单补偿定时任务
 */
@Component
@Slf4j
public class ThirdPlatformOrderPayJobHandler {

    @Autowired
    private ThirdPlatformTradeProvider thirdPlatformTradeProvider;

    /**
     * 第三方平台订单定时任务
     */
    @XxlJob(value = "thirdPlatformOrderPayJobHandler")
    public void execute() throws Exception {
        log.info("第三方平台订单补偿定时任务执行");
        thirdPlatformTradeProvider.compensate(ThirdPlatformTradeCompensateRequest.builder().build());
        log.info("第三方平台订单补偿任务执行结束");
    }
}
