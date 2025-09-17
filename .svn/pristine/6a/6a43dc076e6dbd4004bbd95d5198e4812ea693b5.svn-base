package com.wanmi.sbc.supplier.register.controller;

import com.wanmi.sbc.BossApplication;
import com.wanmi.sbc.customer.api.request.employee.EmployeeLoginRequest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.annotation.Resource;

/***
 * SupplierCompanyInfoController测试类
 * @className SupplierCompanyInfoControllerTest
 * @author zhengyang
 * @date 2021/6/28 18:49
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BossApplication.class})
public class SupplierCompanyInfoControllerTest {
    @Resource
    private SupplierCompanyInfoController supplierCompanyInfoController;

    @Test
    public void registerTest(){
        EmployeeLoginRequest request = new EmployeeLoginRequest();
        request.setAccount("123456");
        request.setPassword("123456".toCharArray());
        supplierCompanyInfoController.register(request);
    }
}
