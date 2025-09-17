package com.wanmi.ares.source.service;

import com.wanmi.ares.report.customer.dao.CustomerMapper;
import com.wanmi.ares.request.CustomerQueryRequest;
import com.wanmi.ares.source.model.root.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 商品sku基础信息service
 * Created by sunkun on 2017/9/22.
 */
@Slf4j
@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    /**
     * 解绑客户(将本来属于某个业务员的客户的业务员设置为null)
     * @param employeeId
     * @return
     */
    public int unbindCustomer(String employeeId){
        return customerMapper.unbindCustomer(employeeId);
    }


}
