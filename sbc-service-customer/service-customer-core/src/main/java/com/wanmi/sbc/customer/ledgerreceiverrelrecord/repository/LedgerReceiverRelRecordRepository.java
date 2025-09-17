package com.wanmi.sbc.customer.ledgerreceiverrelrecord.repository;

import com.wanmi.sbc.customer.ledgerreceiverrelrecord.model.root.LedgerReceiverRelRecord;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.service.LedgerReceiverRelRecordService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>分账绑定关系补偿记录DAO</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@Repository
public interface LedgerReceiverRelRecordRepository extends JpaRepository<LedgerReceiverRelRecord, String>,
        JpaSpecificationExecutor<LedgerReceiverRelRecord> {

    /**
     * 批量删除分账绑定关系补偿记录
     * @author xuyunpeng
     */
    @Modifying
    @Query("delete LedgerReceiverRelRecord where accountId = ?1")
    void deleteByAccountId(String accountId);

    LedgerReceiverRelRecord findByAccountId(String accountId);

}
