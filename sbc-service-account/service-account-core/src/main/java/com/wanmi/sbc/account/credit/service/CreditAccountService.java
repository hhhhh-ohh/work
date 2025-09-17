package com.wanmi.sbc.account.credit.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.request.credit.CreditAccountPageRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAmountRestoreRequest;
import com.wanmi.sbc.account.api.request.credit.CreditStateChangeEvent;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRecord;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.account.credit.repository.CreditRecordRepository;
import com.wanmi.sbc.account.mq.CreditAccountOutService;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CreditStateChangeType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 授信账户相关操作
 *
 * @author zhengyang
 * @since 2021-03-03
 */
@Slf4j
@Service
public class CreditAccountService {

    @Resource
    private CreditAccountRepository creditAccountRepository;
    @Resource
    private CreditRecordRepository recordRepository;
    @Resource
    private CreditAccountOutService creditAccountSink;


    /***
     * 还原授信额度
     */
    @Transactional
    @GlobalTransactional
    public void restoreCreditAmount(CreditAmountRestoreRequest request) {
        // 查询账户
        Optional<CustomerCreditAccount> accountOptional = creditAccountRepository
                .findByCustomerIdAndDelFlag(request.getCustomerId(), DeleteFlag.NO);
        if (!accountOptional.isPresent()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 账户
        CustomerCreditAccount account = accountOptional.get();
        if (!recordRepository.existsById(account.getCreditRecordId())) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
        }
        if (Objects.isNull(account.getCustomerCreditRecord())) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
        }
        // 保存
        // 已使用额度不减  支付后即增加
        int account_result = creditAccountRepository.restoreCreditAmount(request.getAmount(), account.getId());

//        int record_result = recordRepository.restoreCreditAmount(request.getAmount(), account.getCreditRecordId());

        //暂不做业务处理 给出错误提示
        if (account_result == 0) {
            log.error("还原授信额度 失败，account_result = {}", account_result);
        }

        // 构建消息对象并发送
        creditAccountSink.sendCreditStateChangeEvent(JSONObject.toJSONString(CreditStateChangeEvent.builder()
                .creditStateChangeType(CreditStateChangeType.REFUND).amount(request.getAmount())
                .customerId(request.getCustomerId()).build()));
    }

    /**
     * 授信可用额度扣减
     *
     * @param request
     */
    @Transactional
    @GlobalTransactional
    public void updateCreditAmout(CreditAmountRestoreRequest request) {
        // 查询账户
        Optional<CustomerCreditAccount> accountOptional = creditAccountRepository
                .findByCustomerIdAndDelFlag(request.getCustomerId(), DeleteFlag.NO);
        if (!accountOptional.isPresent()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }

        // 额度变更
        int result = creditAccountRepository.updateCustomerCreditAccount(
                request.getCustomerId(),
                request.getAmount()
        );

        if (result > 0) {
            //授信记录已使用额度变更
            recordRepository.updateCreditRecord(
                    accountOptional.get().getCreditRecordId(),
                    request.getCustomerId(),
                    request.getAmount()
            );

            // 构建消息对象并发送
            creditAccountSink.sendCreditStateChangeEvent(JSONObject.toJSONString(CreditStateChangeEvent.builder()
                    .creditStateChangeType(CreditStateChangeType.PAY).amount(request.getAmount())
                    .customerId(request.getCustomerId()).build()));
        } else {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void recoverCreditAmount(CreditAccountPageRequest request) {
        Specification<CustomerCreditAccount> condition = CreditRecoverWhereCriteriaBuilder.build(request);
        List<CustomerCreditAccount> creditAccountList = creditAccountRepository.findAll(condition);

        List<CustomerCreditAccount> creditAccounts = creditAccountList.stream()
                //过期账户可用金额扣减
                .peek(creditAccount -> this.sendExpireMessage(creditAccount))
                //过滤已还款完成的
                .filter(creditAccount -> creditAccount.getRepayAmount().compareTo(BigDecimal.ZERO) == NumberUtils.INTEGER_ZERO)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(creditAccounts)) {
            log.info("{},暂无需要额度恢复的用户!!!!!!!!", LocalDate.now().minusDays(1L));
            return;
        }
        ArrayList<String> accountList = Lists.newArrayList();
        creditAccounts.forEach(customerCreditAccount -> {
            //新周期已恢复授信账户信息
            this.recoverCreditAccount(customerCreditAccount, request);
            accountList.add(customerCreditAccount.getCustomerAccount());
        });
        log.info("额度恢复成功，恢复账户：{}", accountList);
    }


    private void recoverCreditAccount(CustomerCreditAccount customerCreditAccount, CreditAccountPageRequest request) {
        CustomerCreditAccount recoverCreditInfo = this.recoverCreditInfo(customerCreditAccount);
        //变更时再次判断是否已经还清，是否已过当前周期
        int updateNum = creditAccountRepository.updateCreditAccountInfo(recoverCreditInfo, request.getNowTime());
        if (updateNum > NumberUtils.INTEGER_ZERO) {
            //新增成功，则修改当前额度恢复记录使用状态为已结束
            CustomerCreditRecord creditRecord = customerCreditAccount.getCustomerCreditRecord();
            recordRepository.updateUsedStatus(BoolFlag.NO, creditRecord.getId());
            //新增一笔已使用额度恢复记录
            CustomerCreditRecord recoverRecordInfo = this.recoverRecordInfo(creditRecord);
            CustomerCreditRecord newRecord = recordRepository.saveAndFlush(recoverRecordInfo);
            //修改关联新周期授信记录id
            creditAccountRepository.updateCreditAccountRecordId(newRecord.getId(), newRecord.getCustomerId());
            this.sendRecoverMessage(recoverCreditInfo.getCreditAmount(), recoverCreditInfo.getCustomerId());
        }
    }


    /**
     * 发送消息
     *
     * @param creditAmount
     * @param customerId
     */
    private void sendRecoverMessage(BigDecimal creditAmount, String customerId) {
        CreditStateChangeEvent changeEvent = CreditStateChangeEvent.builder()
                .amount(creditAmount)
                .customerId(customerId)
                .creditStateChangeType(CreditStateChangeType.RESTORE)
                .build();
        String msg = JSONObject.toJSONString(changeEvent);
        creditAccountSink.sendCreditStateChangeEvent(msg);
    }

    /**
     * 发送消息
     * @param customerCreditAccount
     */
    private void sendExpireMessage(CustomerCreditAccount customerCreditAccount) {
        BigDecimal creditAmount = customerCreditAccount.getUsableAmount();
        String customerId = customerCreditAccount.getCustomerId();
        BoolFlag boolFlag = customerCreditAccount.getExpiredChangeFlag();

        //如果已经扣减过的 就不再扣减
        if(boolFlag == null || boolFlag.equals(BoolFlag.NO)){
            CreditStateChangeEvent changeEvent = CreditStateChangeEvent.builder()
                    .amount(creditAmount)
                    .customerId(customerId)
                    .creditStateChangeType(CreditStateChangeType.EXPIRED)
                    .build();
            String msg = JSONObject.toJSONString(changeEvent);
            creditAccountSink.sendCreditStateChangeEvent(msg);
        }else{
            log.info("授信可用额度已扣除，无须再扣");
        }

    }


    /**
     * 授信新记录
     *
     * @param creditRecord
     * @return
     */
    private CustomerCreditRecord recoverRecordInfo(CustomerCreditRecord creditRecord) {

        //new一个新的对象，避免返回值中id依然为null
        creditRecord.setId(null);
        CustomerCreditRecord record = new CustomerCreditRecord();
        BeanUtils.copyProperties(creditRecord, record);
        //开始时间为上个周期的结束时间
        LocalDateTime startTime = creditRecord.getEndTime();
        //结束时间为开始时间加上周期
        Long effectiveDays = creditRecord.getEffectiveDays();
        LocalDateTime endTime = startTime.plusDays(effectiveDays);
        record.setUsedStatus(BoolFlag.YES);
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        record.setUsedAmount(BigDecimal.ZERO);
        record.setCreateTime(LocalDateTime.now());
        return record;
    }

    /**
     * 恢复授信账户信息
     *
     * @param account
     * @return
     */
    private CustomerCreditAccount recoverCreditInfo(CustomerCreditAccount account) {
        //授信记录
        CustomerCreditRecord customerCreditRecord = account.getCustomerCreditRecord();
        //授信周期
        Long effectiveDays = customerCreditRecord.getEffectiveDays();
        //开始时间
        LocalDateTime startTime = LocalDateTime.now().minusDays(1L)
                .withHour(23).withMinute(59).withSecond(59);
        //结束时间
        LocalDateTime endTime = startTime.plusDays(effectiveDays)
                .withHour(23).withMinute(59).withSecond(59);
        //恢复记录
        CustomerCreditAccount creditAccount = new CustomerCreditAccount();
        BeanUtils.copyProperties(account, creditAccount);
        Integer creditNum = Objects.nonNull(creditAccount.getCreditNum())
                ? creditAccount.getCreditNum() + 1 : 1;
        creditAccount.setCreditNum(creditNum)
                .setHasRepaidAmount(BigDecimal.ZERO)
                .setUsedAmount(BigDecimal.ZERO)
                .setUsableAmount(account.getCreditAmount())
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setUsedStatus(BoolFlag.NO);
        //若修改后能成功返回修改的条数，不能在原有对象操作
        return creditAccount;

    }

}
