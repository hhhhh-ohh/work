package com.wanmi.sbc.job;

import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author edz
 * @className CommunityCommissionJobHandler
 * @description 社区团购佣金发放
 * @date 2023/7/28 17:40
 **/
@Component
@Slf4j
public class CommunityCommissionJobHandler {

    @Autowired
    TradeProvider tradeProvider;

    @XxlJob(value = "CommunityCommissionJobHandler")
    public void execute() throws Exception {
        tradeProvider.communityCommission();
    }
}
