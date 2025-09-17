package com.wanmi.sbc.account.credit.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.request.credit.CreditApplyRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditRequest;
import com.wanmi.sbc.account.api.request.credit.CreditStateChangeEvent;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecord;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditAccount;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRecord;
import com.wanmi.sbc.account.credit.repository.ApplyRecordRepository;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.account.credit.repository.CreditRecordRepository;
import com.wanmi.sbc.account.mq.CreditAccountOutService;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CreditStateChangeType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

/***
 * 授信审批Service
 * @author zhengyang
 * @since 2021-03-01
 */
@Service
public class CreditAuditService {

    @Resource
    private ApplyRecordRepository applyRecordRepository;
    @Resource
    private CreditAccountRepository creditAccountRepository;
    @Resource
    private CreditRecordRepository creditRecordRepository;
    @Resource
    private CreditAccountOutService creditAccountSink;

    /***
     * 同意审批
     * 1.请求数据和当前数据库完整性校验
     * 2.账号状态校验
     * 3.生成授信记录
     * 4.account表相关状态变更
     * 5.如果是额度变更申请需要将前一条状态失效并发送失效MQ
     * 6.申请记录表相关状态变更
     * 7.发送授信事件MQ
     *
     * @param request
     * @return 审批的账户，用于记录操作日志
     */
    @Transactional(rollbackFor = Exception.class)
    public CreditAccountDetailResponse applyAgree(CreditAuditRequest request) {
        // 根据ID查询
        CustomerApplyRecord record = null;
        CustomerCreditAccount account = null;

        if (applyRecordRepository.existsById(request.getId())) {
            account = creditAccountRepository.findByApplyRecordId(request.getId());
            record = applyRecordRepository.findById(request.getId()).orElseThrow(() -> new SbcRuntimeException(AccountErrorCodeEnum.K020013));
        }

        // 判断是否存在
        if (Objects.isNull(record) || StringUtils.isEmpty(record.getId()) || Objects.isNull(account)
                || StringUtils.isEmpty(account.getId())) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 判断账号状态是否正确
        // 如果状态不为审核中/变更额度或在变更额度Id为空的情况下授信ID不为空
        // 都是状态错误
        if ((CreditAuditStatus.WAIT != record.getAuditStatus()
                && CreditAuditStatus.RESET_WAIT != record.getAuditStatus())
                || (!StringUtils.isEmpty(account.getCreditRecordId())
                && StringUtils.isEmpty(account.getChangeRecordId()))) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
        }
        // 计算消息发送状态
        CreditStateChangeType eventType = CreditAuditStatus.WAIT == record.getAuditStatus()
                ? CreditStateChangeType.AUDIT_PASS : CreditStateChangeType.AMOUNT_CHANGE_PASS;

        // 计算开始时间和结束时间
        LocalDateTime startTime = LocalDateTime.now();
        // 结束时间计算方式：当前时间+生效天数= 结束日期 + 23:59:59
        LocalDateTime endTime = startTime
                .plusDays(request.getEffectiveDays())
                .with(LocalTime.of(23, 59, 59));

        // 3.保存授信记录
        CustomerCreditRecord creditRecord = new CustomerCreditRecord();
        // 将账号表的customer赋给授信记录表，当前request里的customer是审核人
        creditRecord.setCustomerId(account.getCustomerId());
        creditRecord.setCreditAmount(request.getCreditAmount());
        creditRecord.setUsedAmount(BigDecimal.ZERO);
        creditRecord.setEffectiveDays(request.getEffectiveDays());
        creditRecord.setStartTime(startTime);
        creditRecord.setEndTime(endTime);
        creditRecord.setUsedStatus(BoolFlag.YES);
        creditRecord.setDelFlag(DeleteFlag.NO);
        creditRecord.setCreatePerson(request.getCustomerId());
        creditRecord.setCreateTime(LocalDateTime.now());
        creditRecordRepository.save(creditRecord);

        // 保留原有的授信ID，如果是额度变更时将其失效
        String originalCreditRecordId = account.getCreditRecordId();
        String changRecordId = account.getChangeRecordId();

        // 4.更新账户
        account.setCreditAmount(request.getCreditAmount());
        account.setCreditNum(account.getCreditNum() + 1);
        // 额度相关初始化 0:可用额度,1待还款额度,2已使用额度 3 已还款额度
        account.setUsableAmount(request.getCreditAmount());
        account.setRepayAmount(BigDecimal.ZERO);
        account.setUsedAmount(BigDecimal.ZERO);
        account.setHasRepaidAmount(BigDecimal.ZERO);

        account.setCreditRecordId(creditRecord.getId());
        account.setStartTime(startTime);
        account.setEndTime(endTime);
        account.setEnabled(BoolFlag.YES);
        account.setUpdatePerson(request.getCustomerId());
        account.setUpdateTime(LocalDateTime.now());
        creditAccountRepository.save(account);

        // 5.如果是额度变更申请需要将前一条状态失效并发送失效MQ
        restoreOrInvalidAccount(account, originalCreditRecordId, request, record, Boolean.TRUE);

        // 6.更新申请记录
        record.setAuditStatus(CreditAuditStatus.PASS);
        record.setEffectStatus(BoolFlag.YES);
        record.setAuditPerson(request.getCustomerId());
        record.setAuditTime(LocalDateTime.now());
        applyRecordRepository.save(record);

        // 7.构建消息对象并发送
        creditAccountSink.sendCreditStateChangeEvent(JSONObject.toJSONString(CreditStateChangeEvent.builder()
                .creditStateChangeType(eventType).amount(request.getCreditAmount())
                .customerId(request.getCustomerId()).build()));

        return CreditAccountDetailResponse.builder()
                .customerId(account.getCustomerId())
                .customerAccount(account.getCustomerAccount())
                .changeRecordId(changRecordId)
                .build();
    }

    /***
     * 审批驳回
     * 1.判断是否变更额度，是走2，否则走3
     * 2.恢复账户之前的使用状态
     * 3.更新申请记录
     * @param request
     *
     * @return 审批的账户，用于记录操作日志
     */
    @Transactional(rollbackFor = Exception.class)
    public CreditAccountDetailResponse applyReject(CreditAuditRequest request) {
        // 根据ID查询
        CustomerApplyRecord record = null;
        CustomerCreditAccount account = null;
        if (applyRecordRepository.existsById(request.getId())) {
            record = applyRecordRepository.getOne(request.getId());
            account = creditAccountRepository.findByApplyRecordId(request.getId());
        }

        // 判断是否存在
        if (Objects.isNull(record) || StringUtils.isEmpty(record.getId())
                || Objects.isNull(account)
                || StringUtils.isEmpty(account.getId())) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020013);
        }
        // 判断账号状态是否正确，不是待审核和变更审核，直接报错
        if (CreditAuditStatus.WAIT != record.getAuditStatus() &&
                CreditAuditStatus.RESET_WAIT != record.getAuditStatus()) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
        }

        // 留存变更额度记录ID
        String changRecordId = account.getChangeRecordId();
        // 恢复账号状态
        restoreOrInvalidAccount(account, null, request, record, Boolean.FALSE);

        // 更新申请记录
        record.setEffectStatus(BoolFlag.NO);
        record.setAuditStatus(CreditAuditStatus.REJECT);
        record.setAuditPerson(request.getCustomerId());
        record.setAuditTime(LocalDateTime.now());
        record.setRejectReason(request.getRejectReason());
        applyRecordRepository.save(record);

        return CreditAccountDetailResponse.builder()
                .customerId(account.getCustomerId())
                .customerAccount(account.getCustomerAccount())
                .changeRecordId(changRecordId)
                .build();
    }

    /***
     * 失效授信记录或者恢复账号状态
     * @param account
     * @param request
     * @param record
     * @param isInvalid
     */
    @Transactional(rollbackFor = Exception.class)
    public void restoreOrInvalidAccount(CustomerCreditAccount account,
                                        String originalCreditRecordId,
                                        CreditAuditRequest request,
                                        CustomerApplyRecord record,
                                        Boolean isInvalid) {
        // 判断是否需要恢复账号状态
        if (CreditAuditStatus.RESET_WAIT != record.getAuditStatus()) {
            return;
        }

        // 判断是否需要失效授信记录
        if (isInvalid) {
            Optional<CustomerCreditRecord> creditRecordOptional = creditRecordRepository.findById(originalCreditRecordId);
            if (!creditRecordOptional.isPresent()) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
            }
            CustomerCreditRecord creditRecord = creditRecordOptional.get();
            creditRecord.setUsedStatus(BoolFlag.NO);
            creditRecord.setEndTime(LocalDateTime.now());
            creditRecordRepository.save(creditRecord);
            // 失效完成后将account表变更记录ID 替换 申请记录ID，并将变更记录ID置空
            account.setApplyRecordId(account.getChangeRecordId());
            account.setChangeRecordId(null);
            creditAccountRepository.save(account);

            // 构建一条失效消息并发送
            creditAccountSink.sendCreditStateChangeEvent(JSONObject.toJSONString(CreditStateChangeEvent.builder()
                    .creditStateChangeType(CreditStateChangeType.EXPIRED).amount(creditRecord.getCreditAmount())
                    .customerId(request.getCustomerId()).build()));
        } else {
            account.setEnabled(BoolFlag.YES);
            account.setUpdateTime(LocalDateTime.now());
            account.setUpdatePerson(request.getCustomerId());
            creditAccountRepository.save(account);
        }
    }

    /***
     * 申请审批
     * 1.判断是否有使用中的额度，如果有走2，否则走4
     * 2.判断是否有未还款订单，如果有直接返回提示无法变更额度
     * 3.将账户置为不可用，并将账户中申请ID和授信记录ID记录到Redis，然后清空Mysql记录
     * 4.生成申请单，如果账户不存在则生成账户，将申请单关联到账户
     * 5.返回申请成功
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void applyAudit(CreditApplyRequest request) {
        // 查询账户
        Optional<CustomerCreditAccount> creditAccountOptional = creditAccountRepository
                .findByCustomerIdAndDelFlag(request.getCustomerId(), DeleteFlag.NO);
        // 设置一个账户对象
        CustomerCreditAccount account = null;
        // 生成申请单
        CustomerApplyRecord record = new CustomerApplyRecord();
        // 修复isChangeFlag由后端判断
        if (!creditAccountOptional.isPresent() || Objects.isNull(creditAccountOptional.get().getCreditAmount())){
            request.setIsChangeFlag(BoolFlag.NO);
        }else {
            request.setIsChangeFlag(BoolFlag.YES);
        }
        // 判断账号状态是否正确，如果账号存在并且变更额度标记为否
        // 账号状态错误
        if (creditAccountOptional.isPresent()
                && BoolFlag.YES.equals(creditAccountOptional.get().getEnabled())
                && (Objects.isNull(request.getIsChangeFlag())
                || BoolFlag.NO.equals(request.getIsChangeFlag()))) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020014);
        }
        // 判断账户是否存在
        if (creditAccountOptional.isPresent()
                && BoolFlag.YES.equals(request.getIsChangeFlag())) {
            account = creditAccountOptional.get();
            if (Objects.nonNull(account.getRepayAmount())
                    && account.getRepayAmount().compareTo(BigDecimal.ZERO) > 0) {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020015);
            }
            account.setEnabled(BoolFlag.NO);
            account.setUpdatePerson(request.getCustomerId());
            account.setUpdateTime(LocalDateTime.now());

            // 重新审批
            record.setAuditStatus(CreditAuditStatus.RESET_WAIT);
        } else {
            account = creditAccountOptional.orElseGet(() -> new CustomerCreditAccount());
            account.setCustomerId(request.getCustomerId());
            account.setCustomerName(request.getCustomerName());
            account.setCustomerAccount(request.getCustomerAccount());
            account.setCreatePerson(request.getCustomerId());
            account.setCreateTime(LocalDateTime.now());
            account.setUsedStatus(BoolFlag.NO);
            account.setEnabled(BoolFlag.NO);
            account.setDelFlag(DeleteFlag.NO);
            account.setCreditNum(0);

            // 待审批
            record.setAuditStatus(CreditAuditStatus.WAIT);
        }

        record.setApplyNotes(request.getApplyNotes());
        record.setCustomerId(request.getCustomerId());
        record.setEffectStatus(BoolFlag.NO);
        record.setDeleteFlag(DeleteFlag.NO);
        record.setCreatePerson(request.getCustomerId());
        applyRecordRepository.save(record);

        // 判断是否重新授信
        if (CreditAuditStatus.WAIT == record.getAuditStatus()) {
            account.setApplyRecordId(record.getId());
        } else {
            account.setChangeRecordId(record.getId());
        }
        creditAccountRepository.save(account);
    }
}
