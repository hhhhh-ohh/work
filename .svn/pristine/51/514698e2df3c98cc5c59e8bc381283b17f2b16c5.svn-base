package com.wanmi.sbc.job.ledger;

import com.wanmi.sbc.customer.api.provider.ledger.LakalaProvider;
import com.wanmi.sbc.customer.api.request.ledger.LakalaJobRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuyunpeng
 * @className LedgerJobHandler
 * @description 分账接口错误补偿定时任务
 * @date 2022/7/9 4:10 PM
 **/
@Component
public class LedgerJobHandler{

    @Autowired
    private LakalaProvider lakalaProvider;

    @XxlJob(value = "ledgerJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        lakalaProvider.ledgerJob(LakalaJobRequest.builder().param(param).build());
    }
}
