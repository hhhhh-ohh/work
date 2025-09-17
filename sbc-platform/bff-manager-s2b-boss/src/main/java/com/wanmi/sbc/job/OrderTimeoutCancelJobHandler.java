package com.wanmi.sbc.job;


import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.request.trade.TradeTimeOutCancelRequest;
import com.wanmi.sbc.order.api.response.trade.TradeTimeOutCancelResponse;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 定时任务Handler（Bean模式）
 * 超时未支付取消订单
 *
 * @author bail 2019-3-24
 */
@Component
@Slf4j
public class OrderTimeoutCancelJobHandler {

    @Autowired
    private TradeProvider tradeProvider;

    @XxlJob(value = "orderTimeoutCancelJobHandler")
    public void execute() throws Exception {
        String s = XxlJobHelper.getJobParam();
        List<String> tidList = new ArrayList<>();
        Collections.addAll(tidList, s.split(","));
        TradeTimeOutCancelResponse tradeTimeOutCancelResponse = tradeProvider.timeOutCancel(TradeTimeOutCancelRequest.builder()
                .tidList(tidList)
                .build()).getContext();
        if (CollectionUtils.isNotEmpty(tradeTimeOutCancelResponse.getFailTidList())) {
            String failTidListStr = StringUtils.join(tradeTimeOutCancelResponse.getFailTidList().toArray(), ",");
            XxlJobHelper.handleFail("处理失败订单：" + failTidListStr);
        }
    }
}
