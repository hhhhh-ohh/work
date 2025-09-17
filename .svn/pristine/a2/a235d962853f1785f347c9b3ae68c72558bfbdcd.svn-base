package com.wanmi.sbc.account.credit.model.root;

import com.wanmi.sbc.account.credit.service.CreditOverviewService;
import com.wanmi.sbc.common.util.Nutils;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/***
 * 授信概览BO对象
 * @author zhengyang
 * @since 2021/3/12 13:57
 */
@Data
@Builder
public class CreditOverviewBo {

    public CreditOverviewBo(String id, BigDecimal totalCreditAmount, int totalCustomer,
                            BigDecimal totalUsableAmount, BigDecimal totalUsedAmount,
                            BigDecimal totalRepayAmount, BigDecimal totalRepaidAmount) {
        this.id = Nutils.defaultVal(id, CreditOverviewService.DEFAULT_PRIMARY_KEY);
        this.totalCreditAmount = Nutils.defaultVal(totalCreditAmount, BigDecimal.ZERO);
        this.totalCustomer = Nutils.defaultVal(totalCustomer, 0);
        this.totalUsableAmount = Nutils.defaultVal(totalUsableAmount, BigDecimal.ZERO);
        this.totalUsedAmount = Nutils.defaultVal(totalUsedAmount, BigDecimal.ZERO);
        this.totalRepayAmount = Nutils.defaultVal(totalRepayAmount, BigDecimal.ZERO);
        this.totalRepaidAmount = Nutils.defaultVal(totalRepaidAmount, BigDecimal.ZERO);
    }

    /**
     * 主键
     */
    private String id;

    /***
     * 授信总额
     */
    private BigDecimal totalCreditAmount;

    /***
     * 客户总数
     */
    private int totalCustomer;

    /***
     * 可用总额
     */
    private BigDecimal totalUsableAmount;

    /***
     * 已使用总额
     */
    private BigDecimal totalUsedAmount;

    /***
     * 待还总额
     */
    private BigDecimal totalRepayAmount;

    /***
     * 已还总额
     */
    private BigDecimal totalRepaidAmount;
}
