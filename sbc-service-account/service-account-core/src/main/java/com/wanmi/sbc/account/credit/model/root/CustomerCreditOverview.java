package com.wanmi.sbc.account.credit.model.root;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;

/***
 * 授信情况概览
 * @author zhengyang
 * @since 2021/3/12 10:26
 */
@Data
@Entity
@Table(name = "customer_credit_overview")
public class CustomerCreditOverview {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /***
     * 授信总额
     */
    @Column(name = "total_credit_amount")
    private BigDecimal totalCreditAmount;

    /***
     * 客户总数
     */
    @Column(name = "total_customer")
    private int totalCustomer;

    /***
     * 可用总额
     */
    @Column(name = "total_usable_amount")
    private BigDecimal totalUsableAmount;

    /***
     * 已使用总额
     */
    @Column(name = "total_used_amount")
    private BigDecimal totalUsedAmount;

    /***
     * 待还总额
     */
    @Column(name = "total_repay_amount")
    private BigDecimal totalRepayAmount;

    /***
     * 已还总额
     */
    @Column(name = "total_repaid_amount")
    private BigDecimal totalRepaidAmount;
}
