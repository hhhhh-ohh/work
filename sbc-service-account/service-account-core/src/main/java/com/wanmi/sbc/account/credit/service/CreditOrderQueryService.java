package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.RepayOrderPageRequest;
import com.wanmi.sbc.account.api.response.credit.order.RepayOrderPageResponse;
import com.wanmi.sbc.account.bean.vo.CustomerCreditOrderVO;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import com.wanmi.sbc.account.credit.repository.CreditOrderRepository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author houshuai
 * @date 2021/3/1 16:20
 * @description <p> 授信订单service </p>
 */
@Service
public class CreditOrderQueryService {

    @Autowired
    private CreditOrderRepository creditOrderRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    /**
     * 已还款订单信息
     *
     * @param request
     * @return
     */
    public Page<RepayOrderPageResponse> findRepayOrderPage(RepayOrderPageRequest request) {
        Specification<CustomerCreditOrder> condition = CreditOrderWhereCriteriaBuilder.build(request);

        Page<CustomerCreditOrder> creditOrderPage = creditOrderRepository.findAll(condition, request.getPageable());
        List<CustomerCreditOrder> creditOrderList = creditOrderPage.getContent();
        String customerId = request.getCustomerId();
        //获取账户信息
        CustomerCreditAccount creditAccount = this.getAcccountInfo(creditOrderList, customerId);

        return creditOrderPage.map(target -> {
            RepayOrderPageResponse source = RepayOrderPageResponse.builder().build();
            source.setOrderNo(target.getOrderId());
            if (Objects.nonNull(creditAccount)) {
                source.setCustomerName(creditAccount.getCustomerName());
                source.setCustomerAccount(creditAccount.getCustomerAccount());
            }
            return source;
        });

    }

    /**
     * 获取账户信息
     *
     * @param creditOrderList
     * @param customerId
     * @return
     */
    private CustomerCreditAccount getAcccountInfo(List<CustomerCreditOrder> creditOrderList, String customerId) {
        if (CollectionUtils.isEmpty(creditOrderList)) {
            return null;
        }
        Optional<CustomerCreditAccount> creditAccountOptional =
                creditAccountRepository.findCustomerCreditAccountByIdAndDelFlag(customerId, DeleteFlag.NO);

        return creditAccountOptional.orElse(null);
    }

    /**
     * 查询还款关联订单
     *
     * @param request
     * @return
     */
    public List<CustomerCreditOrder> getCreditOrderList(RepayOrderPageRequest request) {
        return creditOrderRepository.findAll(CreditOrderWhereCriteriaBuilder.build(request));
    }

    /**
     * 将实体包装成VO
     * @author zhongjichuan
     */
    public CustomerCreditOrderVO wrapperVo(CustomerCreditOrder customerCreditOrder) {
        if (customerCreditOrder != null){
            CustomerCreditOrderVO customerCreditOrderVO = KsBeanUtil.convert(customerCreditOrder, CustomerCreditOrderVO.class);
            return customerCreditOrderVO;
        }
        return null;
    }
}
