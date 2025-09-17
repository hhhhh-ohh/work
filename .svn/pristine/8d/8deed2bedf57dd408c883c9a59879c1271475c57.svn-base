package com.wanmi.ares.sensitiveword;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.CheckFunctionInterface;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeFindTodoFunctionIdsRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeFindTodoFunctionIdsResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author wur
 * @className ReturnSensitiveWordsAspect
 * @description TODO
 * @date 2022/7/5 10:18
 **/
@Log4j2
@Component
public class SensitiveWordsCheckFunctionService implements CheckFunctionInterface {

    @Autowired(required = false)
    private EmployeeQueryProvider employeeQueryProvider;

    @Override
    public Boolean checkFunction(Operator operator, List<String> functionList) {
        EmployeeFindTodoFunctionIdsResponse functionIdsResponse =
                employeeQueryProvider
                        .findTodoFunctionIds(
                                EmployeeFindTodoFunctionIdsRequest.builder()
                                        .functionList(functionList)
                                        .operator(operator)
                                        .build())
                        .getContext();
        if (Objects.isNull(functionIdsResponse)
                || CollectionUtils.isEmpty(functionIdsResponse.getFunctions())) {
            return Boolean.FALSE;
        }
        return CollectionUtils.isNotEmpty(functionIdsResponse.getFunctions());
    }
}