package com.wanmi.sbc.job.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditAccountProvider;
import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/12 09:49
 * @description <p> 授信额度恢复 </p>
 */

@Component
public class CreditRecoverJobHandler {

    @Autowired
    private CreditAccountProvider creditAccountProvider;

    @XxlJob(value = "CreditRecoverJobHandler")
    public void execute() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        CreditAccountPageRequest request = CreditAccountPageRequest.builder()
                .nowTime(now)
                .build();
        // 恢复额度
        creditAccountProvider.recoverCreditAmount(request);
    }
}

