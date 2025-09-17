package com.wanmi.sbc.supplier.register.service.impl;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.plugin.annotation.Routing;
import com.wanmi.sbc.common.plugin.enums.MethodRoutingRule;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByAccountNameRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeLoginRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeRegisterRequest;
import com.wanmi.sbc.customer.api.response.employee.StoreInformationResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInformationRequest;
import com.wanmi.sbc.supplier.register.service.SupplierCompanyInfoService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.Resource;
import java.util.Objects;

/***
 * SBC公司信息Service实现类
 * @className NormalCompanyInfoServiceImpl
 * @author zhengyang
 * @date 2021/6/28 18:16
 **/
@Slf4j
public abstract class AbstractCompanyInfoServiceImpl implements SupplierCompanyInfoService {

    @Resource
    protected EmployeeQueryProvider employeeQueryProvider;
    @Resource
    protected EmployeeProvider employeeProvider;
    @Resource
    protected EsStoreInformationProvider esStoreInformationProvider;

    @Override
    @Routing(routingRule= MethodRoutingRule.PLUGIN_TYPE,pluginType= PluginType.NORMAL)
    @GlobalTransactional
    public StoreInformationResponse register(EmployeeLoginRequest loginRequest) {
        EmployeeLoginRequest employeeLoginRequest = KsBeanUtil.copyPropertiesThird(loginRequest,EmployeeLoginRequest.class);
        // 没传账号类型，默认2，商家
        if(Objects.isNull(loginRequest.getAccountType())){
            employeeLoginRequest.setAccountType(2);
        }
        // 没传店铺类型，默认1，商家
        if(Objects.isNull(loginRequest.getStoreType())){
            employeeLoginRequest.setStoreType(1);
        }

        // 验证手机号
        if(!ValidateUtil.isPhone(employeeLoginRequest.getAccount())){
            log.error("手机号码:{}格式错误", loginRequest.getAccount());
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 账号在门店/店铺中只能存在一个
        EmployeeByAccountNameRequest.EmployeeByAccountNameRequestBuilder employeeBuilder = EmployeeByAccountNameRequest.builder()
                .accountName(employeeLoginRequest.getAccount())
                .accountType(AccountType.fromValue(employeeLoginRequest.getAccountType()));
        if(AccountType.s2bSupplier.toValue() == employeeLoginRequest.getAccountType()
                || AccountType.O2O.toValue() == employeeLoginRequest.getAccountType()){
            employeeBuilder.accountTypes(Lists.newArrayList(AccountType.s2bSupplier, AccountType.O2O));
        }

        // 同种商家类型的该手机号是否已注册
        if (Objects.nonNull(employeeQueryProvider.getByAccountName(employeeBuilder.build()).getContext().getEmployee())){
            log.error("手机号码:{}已注册", employeeLoginRequest.getAccount());
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010076);
        }

        StoreInformationResponse response = employeeProvider.registerGetStoreInfo(KsBeanUtil
                                                            .copyPropertiesThird(employeeLoginRequest, EmployeeRegisterRequest.class)).getContext();
        if (Objects.nonNull(response)){
            // 存储到es中
            esStoreInformationProvider.initStoreInformation(KsBeanUtil.copyPropertiesThird(response,StoreInformationRequest.class));

            return response;
        }
        return null;
    }

}
