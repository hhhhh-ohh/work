package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.provider.ledger.LedgerProvider;
import com.wanmi.sbc.empower.api.request.Ledger.QuerySplitMerRequest;
import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaQuerySplitMerRequest;
import com.wanmi.sbc.job.service.LedgerCallBackJobService;
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
public class LedgerBindQueryJobHandler {

    @Autowired
    LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired
    LedgerProvider ledgerProvider;

    @Autowired
    GeneratorService generatorService;

    @XxlJob(value = "LedgerBindQueryJobHandler")
    public void execute() throws Exception {
        log.info("拉卡拉查询商户分账业务开通申请开始=========");
        String param = XxlJobHelper.getJobParam();
        if (StringUtils.isNotBlank(param)) {
            //获取商户清分账户
            LedgerAccountVO supplierLedgerAccountVO = ledgerAccountQueryProvider.findByBusiness(LedgerAccountFindRequest.builder()
                    .businessId(param)
                    .setFileFlag(Boolean.FALSE)
                    .build()).getContext().getLedgerAccountVO();
            //获取拉卡拉的内部号
            String thirdMemNo = supplierLedgerAccountVO.getThirdMemNo();
            BaseResponse baseResponse = ledgerProvider.querySplitMer(QuerySplitMerRequest.builder()
                    .lakalaQuerySplitMerRequest(LakalaQuerySplitMerRequest.builder()
                            .merInnerNo(thirdMemNo)
                            .orderNo(generatorService.generateLedgerOrderNo())
                            .build())
                    .build());
            log.info("拉卡拉查询商户分账业务开通申请返回结果：{}", baseResponse);
        }
    }
}
