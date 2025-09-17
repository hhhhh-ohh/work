package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.QueryContractRequest;
import com.wanmi.sbc.empower.api.request.Ledger.QuerySplitMerRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaQueryContractRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaQuerySplitMerRequest;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 分账回调 补偿
 */
@Component
@Slf4j
public class LedgerAccountQueryJobHandler {

    @Autowired
    LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    LedgerProvider ledgerProvider;

    @Autowired
    GeneratorService generatorService;

    @XxlJob(value = "LedgerAccountQueryJobHandler")
    public void execute() throws Exception {
        log.info("拉卡拉查询商户清分账户开始=========");
        String param = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(param)) {
            BaseResponse baseResponse = ledgerProvider.queryContract(QueryContractRequest.builder()
                    .lakalaQueryContractRequest(LakalaQueryContractRequest.builder()
                            .contractId(param)
                            .orderNo(generatorService.generateLedgerOrderNo())
                            .build())
                    .build());
            log.info("拉卡拉查询商户清分账户返回结果：{}", baseResponse);
        }
    }
}
