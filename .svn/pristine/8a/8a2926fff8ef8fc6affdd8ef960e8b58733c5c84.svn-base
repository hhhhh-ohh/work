package com.wanmi.sbc.customer.child.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.request.child.CustomerChildSaveRequest;
import com.wanmi.sbc.customer.api.request.child.CustomerChildUpdateRequest;
import com.wanmi.sbc.customer.child.model.root.CustomerChild;
import com.wanmi.sbc.customer.child.repository.CustomerChildRepository;
import com.wanmi.sbc.customer.detail.repository.CustomerDetailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RefreshScope
public class CustomerChildService {
    @Autowired
    private CustomerChildRepository customerChildRepository;
    @Autowired
    private CustomerDetailRepository customerDetailRepository;
    @Value("${customer.childLimitNum:3}")
    private Integer childLimitNum;
    @Transactional
    public void saveChild( CustomerChildSaveRequest request) {
        CustomerChild customerChild = new CustomerChild();
        BeanUtils.copyProperties(request, customerChild);
        customerChild.setCreateTime(LocalDateTime.now());
        customerChild.setUpdateTime(LocalDateTime.now());
        // 判断添加数量是否超过限制
        List<CustomerChild> childList = customerChildRepository.findByCustomerId(customerChild.getCustomerId());
        if (childList.size() >= childLimitNum){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000040);
        }
        customerChildRepository.save(customerChild);
        customerDetailRepository.updateCustomerHasChildren();
    }

    public List<CustomerChild> findChildsByCustomerId(String customerId) {
        return customerChildRepository.findByCustomerId(customerId);
    }

    @Transactional
    public void updateChildSchool(CustomerChildUpdateRequest request) {
        CustomerChild customerChild = customerChildRepository.findByChildId(request.getChildId());
        if (customerChild == null){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        customerChild.setSchoolName(request.getSchoolName());
        customerChild.setSchoolCode(request.getSchoolCode());
        customerChild.setBadgeCode(request.getBadgeCode());
        customerChild.setUpdateTime(LocalDateTime.now());

        customerChildRepository.updateSchoolByChildId(customerChild.getChildId(), customerChild.getSchoolName(), customerChild.getSchoolCode(),customerChild.getBadgeCode(), customerChild.getUpdateTime());
    }
}
