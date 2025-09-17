package com.wanmi.sbc.supplier.register.service.impl;

import com.wanmi.sbc.customer.api.request.employee.EmployeeLoginRequest;
import com.wanmi.sbc.customer.api.response.employee.StoreInformationResponse;
import com.wanmi.sbc.supplier.register.service.SupplierCompanyInfoService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***
 * SBC公司信息Service实现类
 * @className NormalCompanyInfoServiceImpl
 * @author zhengyang
 * @date 2021/6/28 18:16
 **/
@Slf4j
@Service
public class NormalCompanyInfoServiceImpl extends AbstractCompanyInfoServiceImpl implements SupplierCompanyInfoService {

    @Override
    @GlobalTransactional
    public StoreInformationResponse register(EmployeeLoginRequest loginRequest) {
        return super.register(loginRequest);
    }

}
