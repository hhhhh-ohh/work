package com.wanmi.sbc.account.credit.repository;

import com.wanmi.sbc.account.bean.enums.CreditAuditStatus;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecord;
import com.wanmi.sbc.account.credit.model.root.CustomerApplyRecordWithAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRecordRepository extends JpaRepository<CustomerApplyRecord, String>, JpaSpecificationExecutor<CustomerApplyRecord> {

    @Query(value = "select new com.wanmi.sbc.account.credit.model.root.CustomerApplyRecordWithAccount(" +
            "record.id,record.auditStatus,record.applyNotes,record.rejectReason,record.effectStatus," +
            "record.createTime,record.updateTime,account.customerId,account.customerAccount,account.customerName," +
            "account.creditRecordId,account.applyRecordId,account.creditNum,account.creditAmount,account.repayAmount," +
            "account.hasRepaidAmount,account.startTime,account.endTime,account.usedStatus) " +
            "FROM CustomerApplyRecord record,CustomerCreditAccount account WHERE " +
            "(account.changeRecordId is null or record.id = account.changeRecordId) " +
            "AND (account.changeRecordId is not null or record.id = account.applyRecordId) " +
            "AND record.deleteFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO AND account.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO " +
            "AND account.customerAccount like concat('%', :customerAccount, '%')" +
            "AND account.customerName like concat('%', :customerName, '%') " +
            "AND record.auditStatus in (:authStatusList) " +
            "AND (COALESCE(:employeeCustomerIds, null) is null or record.customerId in :employeeCustomerIds) " +
            "ORDER BY record.createTime DESC")
    Page<CustomerApplyRecordWithAccount> queryApplyRecord(@Param("customerAccount") String customerAccount,
                                                          @Param("customerName") String customerName,
                                                          @Param("authStatusList") List<CreditAuditStatus> authStatusList,
                                                          @Param("employeeCustomerIds") List<String> employeeCustomerIds,
                                                          Pageable pageable);

    @Modifying
    @Query("update CustomerApplyRecord c set c.auditStatus = com.wanmi.sbc.account.bean.enums.CreditAuditStatus.REJECT,c.rejectReason='用户注销' where c.customerId = ?1 and " +
            " c.auditStatus = com.wanmi.sbc.account.bean.enums.CreditAuditStatus.WAIT")
    Integer updateCustomerApplyRecordByCustomerId(String customerId);
}
