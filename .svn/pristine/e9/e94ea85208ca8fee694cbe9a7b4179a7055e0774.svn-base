package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.account.api.provider.finance.record.AccountRecordQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.AccountDetailsExportRequest;
import com.wanmi.sbc.account.bean.vo.AccountDetailsVO;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.mq.report.entity.ExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 对账明细
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class FinanceBillDetailBaseService {

    @Autowired
    private AccountRecordQueryProvider accountRecordQueryProvider;

    @ReturnSensitiveWords(functionName = "f_boss_finance_bill_detail_export_sign_word")
    public List<AccountDetailsVO> queryExport(Operator operator, AccountDetailsExportRequest request){
         return accountRecordQueryProvider.exportAccountDetailsLoad(request).getContext().getAccountDetailsVOList();
    }
}
