package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashQueryProvider;
import com.wanmi.sbc.account.api.request.customerdrawcash.CustomerDrawCashExportRequest;
import com.wanmi.sbc.account.bean.vo.CustomerDrawCashVO;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.mq.report.entity.ExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @return
 * @description 会员提现
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class DrawCashBaseService {

    @Autowired
    private CustomerDrawCashQueryProvider customerDrawCashQueryProvider;

    @ReturnSensitiveWords(functionName = "f_boss_draw_cash_export_sign_word")
    public MicroServicePage<CustomerDrawCashVO> queryExport(Operator operator, CustomerDrawCashExportRequest queryReq){
         return customerDrawCashQueryProvider.export(queryReq).getContext().getCustomerDrawCashVOPage();
    }
}
