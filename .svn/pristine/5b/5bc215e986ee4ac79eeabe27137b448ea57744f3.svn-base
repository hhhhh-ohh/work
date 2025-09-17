package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditOrderRequest;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import com.wanmi.sbc.account.credit.repository.CreditOrderRepository;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/1 16:20
 * @description <p> 授信订单service </p>
 */
@Service
public class CreditOrderService {

    @Autowired
    private CreditOrderRepository creditOrderRepository;

    /**
     * 新增授信支付订单关联
     * @param request
     */
    public void addCreditOrder(CreditOrderRequest request){
        CustomerCreditOrder customerCreditOrder = new CustomerCreditOrder();
        KsBeanUtil.copyPropertiesThird(request,customerCreditOrder);
        customerCreditOrder.setCreateTime(LocalDateTime.now());
        if(creditOrderRepository.countByOrderId(request.getOrderId()) == 0){
            creditOrderRepository.save(customerCreditOrder);
        }

    }

}
