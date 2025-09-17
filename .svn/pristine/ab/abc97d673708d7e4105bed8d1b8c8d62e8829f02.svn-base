package com.wanmi.ares.report.base.service;


import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.CustomerOrderReportMapper;
import com.wanmi.ares.report.customer.model.request.CustomerOrderDataRequest;
import com.wanmi.ares.report.customer.model.root.CustomerReport;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 客户订货报表(按客户)
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class CustomerTradeBaseService {

    @Autowired
    private CustomerOrderReportMapper customerOrderReportMapper;

    @ReturnSensitiveWords(functionName = "f_boss_customer_trade_export_sign_word")
    public List<CustomerReport> queryExport(Operator operator, ExportQuery exportQuery, CustomerOrderDataRequest customerOrderDataRequest) {

        int storeType = exportQuery.getStoreSelectType() == null ? 0 : exportQuery.getStoreSelectType().toValue();

        List<CustomerReport> exportCustomerReports = customerOrderReportMapper.exportCustomerOrderForBoss(customerOrderDataRequest);

        if (customerOrderDataRequest.getCompanyInfoId() != 0) {
            exportCustomerReports = customerOrderReportMapper.exportCustomerOrderForSupplier(customerOrderDataRequest);
        }

        //查询说有门店或者商家
        if (storeType != 0 && customerOrderDataRequest.getCompanyInfoId() == 0) {
            exportCustomerReports = customerOrderReportMapper.exportCustomerOrderForSupplierByStoreType(customerOrderDataRequest);
        }
        return exportCustomerReports;
    }
}