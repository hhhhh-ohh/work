package com.wanmi.sbc.account.credit.repository;

import com.wanmi.sbc.account.credit.model.root.CustomerCreditRecord;
import com.wanmi.sbc.common.enums.BoolFlag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 授信记录
 *
 * @author zhengyang
 * @since 2021-3-1 10:40
 */
@Repository
public interface CreditRecordRepository extends JpaRepository<CustomerCreditRecord, String>,
        JpaSpecificationExecutor<CustomerCreditRecord> {

    /**
     * 分页查新当前用户的额度恢复记录
     *
     * @param customerId
     * @param pageable
     * @return
     */
    Page<CustomerCreditRecord> findAllByCustomerId(String customerId, Pageable pageable);

    /**
     * 修改授信记录表的已使用额度
     *
     * @param id
     * @param customerId
     * @param amount
     * @return
     */
    @Modifying
    @Query("UPDATE CustomerCreditRecord SET usedAmount = IFNULL(usedAmount, 0) + :amount " +
            "WHERE " +
            "id = :id AND " +
            "customerId = :customerId")
    int updateCreditRecord(
            @Param("id") String id,
            @Param("customerId") String customerId,
            @Param("amount") BigDecimal amount
    );


    /***
     * 恢复账户已使用余额
     * usedAmount 已使用额度减去订单金额
     * @param amount
     * @param id
     */
    @Modifying
    @Query("update CustomerCreditRecord set usedAmount = usedAmount - :amount where id = :id and usedAmount - :amount >= 0")
    int restoreCreditAmount(@Param("amount") BigDecimal amount, @Param("id") String id);

    /**
     * 修改记录表使用状态
     *
     * @param boolFlag
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE CustomerCreditRecord SET usedStatus = :boolFlag WHERE id = :id")
    void updateUsedStatus(@Param("boolFlag") BoolFlag boolFlag, @Param("id") String id);


}
