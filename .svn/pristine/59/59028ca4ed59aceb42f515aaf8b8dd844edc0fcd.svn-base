package com.wanmi.sbc.account.credit.service;

import com.wanmi.sbc.account.api.request.credit.CreditStateChangeEvent;
import com.wanmi.sbc.account.api.response.credit.CreditOverviewResponse;
import com.wanmi.sbc.account.credit.model.root.CreditOverviewBo;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOverview;
import com.wanmi.sbc.account.credit.repository.CreditAccountRepository;
import com.wanmi.sbc.account.credit.repository.CreditOverviewRepository;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * * 根据MQ消息事件维护授信消息总览
 * +------+------------------------------------------+--------------------------------------+--------------------------------------------+
 * | 编号 | 事件枚举 | 事件 | 处理 |
 * +------+------------------------------------------+--------------------------------------+--------------------------------------------+
 * | 0 | CreditStateChangeType.AUDIT_PASS | 授信通过 | 授信总额增加、客户总数增加、可用总额增加 | | 1 |
 * CreditStateChangeType.AMOUNT_CHANGE_PASS | 额度表更申请通过 | 授信总额增加、可用总额增加 | | 2 |
 * CreditStateChangeType.PAY | 授信支付 | 可用总额扣减、已使用总额增加、待还总额增加 | | 3 | CreditStateChangeType.REFUND |
 * 授信退款 | 可用总额增加、已使用总额扣减、待还总额扣减 | | 4 | CreditStateChangeType.REPAY | 授信还款 | 可用总额增加、待还总额扣减、已还总额增加 |
 * | 5 | CreditStateChangeType.EXPIRED | 授信过期 | 可用总额扣减 | | 6 | CreditStateChangeType.RESTORE | 额度恢复
 * | 授信总额增加、可用总额增加 |
 * +------+------------------------------------------+--------------------------------------+--------------------------------------------+
 *
 * @author zhengyang
 * @since 2021/3/12 10:32
 */
@Service
@Slf4j
public class CreditOverviewService {

    @Autowired
    private CreditOverviewRepository creditOverviewRepository;

    @Autowired private CreditAccountRepository creditAccountRepository;

    /** * 默认主键 */
    public static final String DEFAULT_PRIMARY_KEY = "OVERVIEW";

    /**
     * * 授信通过
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditPass(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信审核通过发消息：{}", event);
        modifyCreditOverview(
                CreditOverviewBo.builder()
                        .totalCreditAmount(event.getAmount())
                        .totalCustomer(1)
                        .totalUsableAmount(event.getAmount())
                        .build());
    }

    /**
     * * 额度表更申请通过
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void amountChangePass(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信额度变更发消息：{}", event);
        modifyCreditOverview(
                CreditOverviewBo.builder()
                        .totalCreditAmount(event.getAmount())
                        .totalUsableAmount(event.getAmount())
                        .build());
    }

    /**
     * * 授信支付
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void pay(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信支付发消息：{}", event);
        modifyCreditOverview(
                CreditOverviewBo.builder()
                        .totalUsableAmount(event.getAmount().negate())
                        .totalUsedAmount(event.getAmount())
                        .totalRepayAmount(event.getAmount())
                        .build());
    }

    /**
     * * 授信退款
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void refund(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信退款发消息：{}", event);
        modifyCreditOverview(
                CreditOverviewBo.builder()
                        .totalUsableAmount(event.getAmount())
                        //                .totalUsedAmount(event.getAmount().negate())
                        .totalRepayAmount(event.getAmount().negate())
                        .build());
    }

    /**
     * * 授信还款
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void repay(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信还款发消息：{}", event);
        // 授信账户已过期
        if (event.getExpiredFlag()) {
            // 如果过期的话 不需要恢复统计中可用总额度，待还款、已还款 还是正常做变动
            modifyCreditOverview(
                    CreditOverviewBo.builder()
                            .totalRepayAmount(event.getAmount().negate())
                            .totalRepaidAmount(event.getAmount())
                            .build());
        } else {
            modifyCreditOverview(
                    CreditOverviewBo.builder()
                            .totalUsableAmount(event.getAmount())
                            .totalRepayAmount(event.getAmount().negate())
                            .totalRepaidAmount(event.getAmount())
                            .build());
        }
    }

    /**
     * * 授信额度过期
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void expired(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信额度过期发消息：{}", event);
        // 扣减可用额度，总授信额度因为包含历史额度因此不扣减
        modifyCreditOverview(
                CreditOverviewBo.builder().totalUsableAmount(event.getAmount().negate()).build());

        //授信过期扣减后 更改扣减状态 防止重复扣减
        log.info("授信额度变更-授信额度过期更改扣减状态");
        creditAccountRepository.updateCreditAccountFlag(event.getCustomerId(), BoolFlag.YES);
    }

    /**
     * * 授信额度恢复
     *
     * @param event 事件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void restore(CreditStateChangeEvent event) {
        log.info("授信额度变更-授信额度恢复发消息：{}", event);
        modifyCreditOverview(
                CreditOverviewBo.builder()
                        .totalUsableAmount(event.getAmount())
                        .totalCreditAmount(event.getAmount())
                        .build());

        //授信额度恢复后 更改扣减状态
        log.info("授信额度变更-授信额度恢复后更改扣减状态");
        creditAccountRepository.updateCreditAccountFlag(event.getCustomerId(), BoolFlag.NO);
    }

    /** * 更新授信额度概览 */
    @Transactional(rollbackFor = Exception.class)
    public void modifyCreditOverview(CreditOverviewBo overview) {
        creditOverviewRepository.modifyCreditOverview(
                overview.getTotalCreditAmount(),
                overview.getTotalCustomer(),
                overview.getTotalUsableAmount(),
                overview.getTotalUsedAmount(),
                overview.getTotalRepayAmount(),
                overview.getTotalRepaidAmount(),
                overview.getId());
    }

    /** * 查询授信总览 */
    public CreditOverviewResponse findCreditOverview() {
        // 查询返回值
        Optional<CustomerCreditOverview> creditOverviewOptional =
                creditOverviewRepository.findById(DEFAULT_PRIMARY_KEY);
        if (creditOverviewOptional.isPresent()) {
            return BeanUtils.beanCopy(creditOverviewOptional.get(), CreditOverviewResponse.class);
        }
        return BeanUtils.beanCopy(CreditOverviewBo.builder().build(), CreditOverviewResponse.class);
    }
}
