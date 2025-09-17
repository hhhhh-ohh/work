package com.wanmi.sbc.job;

import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务Handler
 * 0点执行登录次数清0操作
 *
 * @author xufeng
 */
@Component
@Slf4j
public class LoginErrorClear0JobHandler {

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private CustomerProvider customerProvider;

    @XxlJob(value = "loginErrorClear0JobHandler")
    public void execute() throws Exception {
        log.info("0点执行登录次数清0操作");
        employeeProvider.modifyLoginErrorTime();
        customerProvider.modifyLoginErrorTime();
    }
}
