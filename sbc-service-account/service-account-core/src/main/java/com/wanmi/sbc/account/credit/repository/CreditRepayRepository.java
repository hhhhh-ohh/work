package com.wanmi.sbc.account.credit.repository;

import com.wanmi.sbc.account.api.response.credit.repay.CreditRepayOverviewPageResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditRepay;
import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author houshuai
 * @date 2021/3/2 17:01
 * @description <p> 授信还款DO </p>
 */
@Repository
public interface CreditRepayRepository extends JpaRepository<CustomerCreditRepay, String>,
        JpaSpecificationExecutor<CustomerCreditRepay> {


    /**
     * 只查询还款中数据
     * @param tid
     * @return
     */

    @Query("SELECT repay " +
            "FROM CustomerCreditOrder order,CustomerCreditRepay repay " +
            "WHERE order.repayOrderCode = repay.repayOrderCode AND order.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND " +
            "repay.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND  repay.repayStatus IN (com.wanmi.sbc.account.bean.enums.CreditRepayStatus.WAIT,com.wanmi.sbc.account.bean.enums.CreditRepayStatus.AUDIT) AND order.orderId = ?1 ")
    Optional<CustomerCreditRepay> findRepayOrderCodeByOrderId(String tid);

    /**
     * 只查询还款完成的数据
     * @param tid
     * @return
     */

    @Query("SELECT repay " +
            "FROM CustomerCreditOrder order,CustomerCreditRepay repay " +
            "WHERE order.repayOrderCode = repay.repayOrderCode AND order.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND " +
            "repay.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND  repay.repayStatus = com.wanmi.sbc.account.bean.enums.CreditRepayStatus.FINISH AND order.orderId = ?1 ")
    CustomerCreditRepay findFinishRepayByOrderId(String tid);


    /**
     * 根据客户id查询该客户存在的待还款的记录
     * @param customerId
     * @param repayStatus
     * @param deleteFlag
     * @return
     */
    @Query("from CustomerCreditRepay where customerId = :customerId and repayStatus in :repayStatus and delFlag = :deleteFlag")
    CustomerCreditRepay findByCustomerIdAndRepayStatusAndDelFlag(@Param("customerId") String customerId,
                                                                 @Param("repayStatus")List<CreditRepayStatus> repayStatus,
                                                                 @Param("deleteFlag") DeleteFlag deleteFlag);


    /***
     * 分页查询授信还款概览
     * @param statusList
     * @param customerAccount
     * @param customerName
     * @param repayOrderCode
     * @param pageable
     * @return
     */
    @Query("SELECT new com.wanmi.sbc.account.api.response.credit.repay.CreditRepayOverviewPageResponse(" +
            "repay.id,repay.repayOrderCode,repay.customerId,account.customerAccount,account.customerName," +
            "record.creditAmount,record.effectiveDays,repay.repayAmount," +
            "repay.repayStatus,repay.repayType,repay.repayTime,repay.repayNotes) " +
            "FROM CustomerCreditRepay repay,CustomerCreditRecord record,CustomerCreditAccount account " +
            "WHERE repay.creditRecordId = record.id and account.customerId = repay.customerId " +
            "AND repay.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and record.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and account.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO " +
            "AND repay.repayStatus in (:repayStatus) " +
            "AND account.customerAccount like concat('%', :customerAccount, '%') " +
            "AND account.customerName like concat('%', :customerName, '%') " +
            "AND repay.repayOrderCode like concat('%', :repayOrderCode, '%') ")
    Page<CreditRepayOverviewPageResponse> findRepayOrderPage(@Param("repayStatus") List<CreditRepayStatus> statusList,
                                                             @Param("customerAccount") String customerAccount,
                                                             @Param("customerName") String customerName,
                                                             @Param("repayOrderCode") String repayOrderCode,
                                                             Pageable pageable);


    /**
     * 根据还款单号查询在线还款记录
     *
     * @param repayCode
     * @param deleteFlag
     * @return
     */
    CustomerCreditRepay findByRepayOrderCodeAndDelFlag(String repayCode, DeleteFlag deleteFlag);


}
