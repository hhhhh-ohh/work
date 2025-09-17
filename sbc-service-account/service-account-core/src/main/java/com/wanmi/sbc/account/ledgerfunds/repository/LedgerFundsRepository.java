package com.wanmi.sbc.account.ledgerfunds.repository;

import com.wanmi.sbc.account.ledgerfunds.model.root.LedgerFunds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * <p>会员分账资金DAO</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@Repository
public interface LedgerFundsRepository extends JpaRepository<LedgerFunds, String>,
        JpaSpecificationExecutor<LedgerFunds> {

    LedgerFunds findByCustomerId(String customerId);

    /**
     * 资金提现
     * @param customerId
     * @param amount
     */
    @Modifying
    @Query("update LedgerFunds set withdrawnAmount = withdrawnAmount - ?2, alreadyDrawAmount = alreadyDrawAmount + ?2 where customerId = ?1 ")
    void grantAmount(String customerId, BigDecimal amount);

    /**
     * 修改可提现金额
     * @param customerId
     * @param amount
     */
    @Modifying
    @Query("update LedgerFunds set withdrawnAmount = withdrawnAmount + ?2 where customerId = ?1 ")
    void updateWithdrawnAmount(String customerId, BigDecimal amount);

}
