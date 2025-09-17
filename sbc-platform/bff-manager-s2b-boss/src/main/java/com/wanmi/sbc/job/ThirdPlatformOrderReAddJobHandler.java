package com.wanmi.sbc.job;

import com.wanmi.sbc.order.api.provider.thirdplatformtrade.ThirdPlatformTradeProvider;
import com.wanmi.sbc.order.api.request.thirdplatformtrade.ThirdPlatformTradeReAddRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 定时任务Handler（Bean模式）
 * 第三方平台订单重新同步渠道定时任务
 * @author daiyitian
 */

@Component
@Slf4j
public class ThirdPlatformOrderReAddJobHandler {

    @Autowired
    private ThirdPlatformTradeProvider thirdPlatformTradeProvider;

    /**
     * 第三方平台订单定时任务
     */
    @XxlJob(value = "thirdPlatformOrderReAddJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        log.info("第三方平台订单重新同步定时任务执行");
        List<String> tradeIds = null;
        if (StringUtils.isNotBlank(param)) {
            tradeIds = Arrays.asList(param.split(","));
        }
        thirdPlatformTradeProvider.reAdd(ThirdPlatformTradeReAddRequest.builder().tradeIds(tradeIds).build());
        log.info("第三方平台订单重新同步任务执行结束");
    }
}
