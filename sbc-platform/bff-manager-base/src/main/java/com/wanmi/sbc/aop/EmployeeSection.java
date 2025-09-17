package com.wanmi.sbc.aop;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsByEmployeeIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByManageDepartmentIdsRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByIdResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailBaseVO;
import com.wanmi.sbc.setting.bean.enums.SystemAccount;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import jakarta.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sunkun on 2018/3/20.
 */
@Aspect
@Component
@Slf4j
public class EmployeeSection {

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Pointcut("@annotation(com.wanmi.sbc.aop.EmployeeCheck)")
    public void employeeCheck() {
    }

    @Before(value = "employeeCheck()")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException {
        Operator operator = commonUtil.getOperatorWithNull();
        if (Objects.isNull(operator) || StringUtils.isBlank(operator.getUserId())) {
            return;
        }
        //获取连接点的方法签名对象
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Object target = joinPoint.getTarget();
        //获取到当前执行的方法
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //获取方法的注解
        EmployeeCheck annotation = method.getAnnotation(EmployeeCheck.class);
        Object[] objects = joinPoint.getArgs();
        for (Object object : objects) {
            if (object instanceof BaseRequest) {
                EmployeeByIdResponse employee = employeeQueryProvider.getById(
                        EmployeeByIdRequest.builder().employeeId(operator.getUserId()).build()
                ).getContext();
                if (SystemAccount.SYSTEM.getDesc().equals(employee.getAccountName()) || Constants.yes.equals(employee.getIsMasterAccount())){
                    continue;
                }
                //是主管且业务员身份 或 （是业务员 && 不是主账号）
                if(Constants.no.equals(employee.getIsEmployee()) && ( StringUtils.isNotBlank(employee.getManageDepartmentIds()) ||  employee.getIsMasterAccount() == 0 )){
                    List<String> employeeIds = new ArrayList<>();
                    if (StringUtils.isNotBlank(employee.getManageDepartmentIds())) {
                        employeeIds = employeeQueryProvider.listByManageDepartmentIds(new EmployeeListByManageDepartmentIdsRequest(employee.getManageDepartmentIds())).getContext().getEmployeeIdList();
                    }else{
                        employeeIds.add(operator.getUserId());
                    }
                    List<String> customerIds = null;
					List<String> customerDetailIds = null;

                    if (StringUtils.isNotBlank(annotation.customerIdField())
							|| StringUtils.isNotBlank(annotation.customerDetailIdField())) {
                        List<CustomerDetailBaseVO> tmpCustomerIds =
                                customerQueryProvider
                                        .listCustomerIdsByEmployeeIds(
                                                new CustomerIdsByEmployeeIdsRequest(
                                                        employeeIds, DeleteFlag.NO))
                                        .getContext()
                                        .getCustomerIds();
                        if (CollectionUtils.isNotEmpty(tmpCustomerIds)) {
                            customerIds = tmpCustomerIds.stream().map(CustomerDetailBaseVO::getCustomerId).collect(Collectors.toList());
							customerDetailIds = tmpCustomerIds.stream().map(CustomerDetailBaseVO::getCustomerDetailId).collect(Collectors.toList());
                        } else {
                            // 如果没有相关会员就直接查不到
                            customerIds = Collections.singletonList("-1");
							customerDetailIds = Collections.singletonList("-1");
                        }
                    }
                    Class clazz = object.getClass();
                    try {
                        Field field = clazz.getDeclaredField(annotation.fieldName());
                        ReflectionUtils.makeAccessible(field);
                        field.set(object, employeeIds);
                    } catch (NoSuchFieldException e) {
                        log.error("employee id assignment failed.",e);
                    }

                    if (StringUtils.isNotBlank(annotation.customerIdField())) {
                        try {
                            Field tmpField =
                                    clazz.getDeclaredField(annotation.customerIdField());
                            ReflectionUtils.makeAccessible(tmpField);
                            tmpField.set(object, customerIds);
                        } catch (NoSuchFieldException e) {
                            log.error("customer id assignment failed.", e);
                        }
                    }

                    if (StringUtils.isNotBlank(annotation.customerDetailIdField())) {
                        try {
                            Field tmpField =
                                    clazz.getDeclaredField(annotation.customerDetailIdField());
                            ReflectionUtils.makeAccessible(tmpField);
							tmpField.set(object, customerDetailIds);
                        } catch (NoSuchFieldException e) {
                            log.error("customer detail id assignment failed.", e);
                        }
					}
                }
            }
        }

    }
}
