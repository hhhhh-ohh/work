package com.wanmi.sbc.job;

import com.wanmi.sbc.goods.api.provider.priceadjustmentrecord.PriceAdjustmentRecordProvider;
import com.wanmi.sbc.goods.api.request.priceadjustmentrecord.PriceAdjustmentRecordDelByTimeRequest;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PriceAdjustJobHandler {

    @Autowired
    private PriceAdjustmentRecordProvider priceAdjustmentRecordProvider;

    @XxlJob(value = "priceAdjustJobHandler")
    public void execute() throws Exception {
        // 前一天
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        PriceAdjustmentRecordDelByTimeRequest request = PriceAdjustmentRecordDelByTimeRequest.builder().time(time).build();
        priceAdjustmentRecordProvider.deleteByTime(request);
    }
}
