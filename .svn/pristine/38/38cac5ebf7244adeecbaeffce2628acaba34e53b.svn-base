package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.bean.vo.CreditApplyRecordVo;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.repository.ApplyRecordRepository;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Optional;

/***
 * 申请记录查询Service
 * @author zhengyang
 * @since 2021-03-02
 */
@Slf4j
@Service
public class CreditApplyRecordQueryService {

    @Resource
    private CreditAccountRepository accountRepository;
    @Resource
    private ApplyRecordRepository applyRecordRepository;

    /***
     * 根据登录用户查询申请记录状态
     * @param customerId    用户ID
     * @return 申请记录状态
     */
    public CreditApplyRecordVo queryApplyInfoByCustomerId(String customerId) {
        Optional<CustomerCreditAccount> customerCreditAccount = accountRepository
                .findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO);

        // 状态错误
        if (!customerCreditAccount.isPresent()
                || StringUtils.isEmpty(customerCreditAccount.get().getApplyRecordId())
                || !applyRecordRepository.existsById(customerCreditAccount.get().getApplyRecordId())) {
            return new CreditApplyRecordVo();
        }

        // 查询状态
        return BeanUtils.beanCopy(applyRecordRepository
                .getOne(customerCreditAccount.get().getApplyRecordId()), CreditApplyRecordVo.class);
    }

    /***
     * 根据登录用户查询变更额度申请记录状态
     * @param customerId    用户ID
     * @return 申请记录状态
     */
    public CreditApplyRecordVo queryChangeInfoByCustomerId(String customerId) {
        Optional<CustomerCreditAccount> customerCreditAccount = accountRepository
                .findByCustomerIdAndDelFlag(customerId, DeleteFlag.NO);
        // 状态错误
        if (!customerCreditAccount.isPresent()
                || StringUtils.isEmpty(customerCreditAccount.get().getChangeRecordId())
                || !applyRecordRepository.existsById(customerCreditAccount.get().getChangeRecordId())) {
            return new CreditApplyRecordVo();
        }

        // 查询状态
        return BeanUtils.beanCopy(applyRecordRepository
                .getOne(customerCreditAccount.get().getChangeRecordId()), CreditApplyRecordVo.class);
    }
}
