package com.wanmi.sbc.account.credit.repository;

import com.wanmi.sbc.account.credit.model.root.CustomerCreditOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/***
 * 授信概览
 * @author zhengyang
 * @since 2021-03-12 10:31
 */
@Repository
public interface CreditOverviewRepository extends JpaRepository<CustomerCreditOverview, String>,
        JpaSpecificationExecutor<CustomerCreditOverview> {

    /***
     * 重新计算授信概览
     * @param totalCreditAmount  授信总数
     * @param totalCustomer      总客户数
     * @param totalUsableAmount  可用总额
     * @param totalUsedAmount    已用总额
     * @param totalRepayAmount   待还总额
     * @param totalRepaidAmount  已还总额
     * @param id                 主键
     */
    @Modifying
    @Query("update CustomerCreditOverview set " +
            "totalCreditAmount = totalCreditAmount + :totalCreditAmount," +
            "totalCustomer = totalCustomer + :totalCustomer," +
            "totalUsableAmount = totalUsableAmount + :totalUsableAmount," +
            "totalUsedAmount = totalUsedAmount + :totalUsedAmount," +
            "totalRepayAmount = totalRepayAmount + :totalRepayAmount," +
            "totalRepaidAmount = totalRepaidAmount + :totalRepaidAmount " +
            "where id = :id")
    void modifyCreditOverview(@Param("totalCreditAmount") BigDecimal totalCreditAmount,
                             @Param("totalCustomer") Integer totalCustomer,
                             @Param("totalUsableAmount") BigDecimal totalUsableAmount,
                             @Param("totalUsedAmount") BigDecimal totalUsedAmount,
                             @Param("totalRepayAmount") BigDecimal totalRepayAmount,
                             @Param("totalRepaidAmount") BigDecimal totalRepaidAmount,
                             @Param("id") String id);
}
