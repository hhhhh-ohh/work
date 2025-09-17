package com.wanmi.ares.source.service;

import com.wanmi.ares.report.customer.dao.EmployeeMapper;
import com.wanmi.ares.request.mq.EmployeeRequest;
import com.wanmi.ares.source.model.root.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 业务员基础信息service
 * Created by sunkun on 2017/9/22.
 */
@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private CustomerService customerService;

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 按照id集合查询业务员集合
     * @param employeeIds
     * @return
     */
    public List<Map<String,Object>> findByEmployeeIds(List<String> employeeIds){
        return employeeMapper.queryEmployeeByIds(employeeIds);
    }

    /**
     * 按照关键字查询业务员集合
     * @param keyWords
     * @return
     */
    public List<Map<String,Object>> findByKeyWords(String keyWords){
        return employeeMapper.queryEmployeeByKeyWords(keyWords);
    }

}
