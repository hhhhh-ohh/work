package com.wanmi.ares.report.base.service;


import com.wanmi.ares.report.base.model.ExportQuery;
import com.wanmi.ares.report.customer.dao.CustomerOrderReportMapper;
import com.wanmi.ares.report.customer.model.request.CustomerOrderDataRequest;
import com.wanmi.ares.report.customer.model.root.CustomerAreaReport;
import com.wanmi.ares.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 客户订货报表(按地区)
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class CustomerAreaTradeBaseService {

    @Autowired
    private CustomerOrderReportMapper customerOrderReportMapper;

    public  List<CustomerAreaReport> queryExport(ExportQuery exportQuery, CustomerOrderDataRequest customerOrderDataRequest){
        int storeType = exportQuery.getStoreSelectType() == null ? 0 : exportQuery.getStoreSelectType().toValue();
        List<CustomerAreaReport> customerReports;
        if (customerOrderDataRequest.getShopType() == 1) {
            customerReports =
                    customerOrderReportMapper.exportCustomerAreaOrderForSupplier(customerOrderDataRequest);
        } else {
            customerReports =
                    customerOrderReportMapper.exportCustomerAreaOrderForBoss(customerOrderDataRequest);
        }

        if (storeType != 0 && exportQuery.getCompanyId().equals(Constants.BOSS_ID)){
            customerReports =
                    customerOrderReportMapper.exportCustomerAreaOrderForSupplierByStoreType(customerOrderDataRequest);
        }
        return customerReports;
    }
}
