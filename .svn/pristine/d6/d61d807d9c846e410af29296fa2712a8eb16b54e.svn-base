package com.wanmi.sbc.mq.report.service.base;

import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsDetailExportRequest;
import com.wanmi.sbc.account.bean.vo.CustomerFundsDetailVO;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.mq.report.entity.ExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 资金明细
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class CustomerFundsDetailBaseService {

    @Autowired
    private CustomerFundsDetailQueryProvider customerFundsDetailQueryProvider;

    @ReturnSensitiveWords(functionName = "f_boss_customer_funds_detail_export_sign_word")
    public  List<CustomerFundsDetailVO> query (Operator operator, CustomerFundsDetailExportRequest queryReq){
        return customerFundsDetailQueryProvider.export(queryReq).getContext().getMicroServicePage();
    }


}
