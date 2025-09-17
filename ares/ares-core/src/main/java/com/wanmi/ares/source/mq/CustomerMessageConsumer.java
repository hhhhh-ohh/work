package com.wanmi.ares.source.mq;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.report.customer.dao.CustomerMapper;
import com.wanmi.ares.source.model.root.CustomerFirstPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CustomerMessageConsumer {

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public void customerFirstPay(String json){
        CustomerFirstPay entity = JSONObject.parseObject(json, CustomerFirstPay.class);
        customerMapper.customerFirstPay(entity);
    }
}
