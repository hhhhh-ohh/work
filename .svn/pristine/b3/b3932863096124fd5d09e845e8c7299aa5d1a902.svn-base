package com.wanmi.sbc.supplier.register.service;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.request.employee.EmployeeLoginRequest;
import com.wanmi.sbc.customer.api.response.employee.StoreInformationResponse;

/**
 * 公司信息Service
 * @author zhengyang
 * @className SupplierCompanyInfoService
 * @date 2021/6/28 15:32
 **/
public interface SupplierCompanyInfoService {

    /***
     * 商家注册
     * 1.验证手机号
     * 2.验证验证码
     * @param loginRequest  请求参数
     * @return              是否注册成功
     * @throws SbcRuntimeException  验证异常
     */
    StoreInformationResponse register(EmployeeLoginRequest loginRequest) throws SbcRuntimeException;
}
