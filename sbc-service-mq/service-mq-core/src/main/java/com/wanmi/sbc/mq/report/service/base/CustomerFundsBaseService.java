package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsExportRequest;
import com.wanmi.sbc.account.bean.vo.CustomerFundsVO;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.mq.report.entity.ExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 会员资金
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class CustomerFundsBaseService {

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    @ReturnSensitiveWords(functionName = "f_boss_customer_funds_export_sign_word")
    public List<CustomerFundsVO> query(Operator operator, CustomerFundsExportRequest queryReq){
       return customerFundsQueryProvider.export(queryReq).getContext().getMicroServiceList();
    }
}
