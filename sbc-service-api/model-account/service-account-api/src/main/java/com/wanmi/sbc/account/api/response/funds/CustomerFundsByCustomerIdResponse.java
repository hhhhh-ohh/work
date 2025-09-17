package com.wanmi.sbc.account.api.response.funds;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.SensitiveUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员资金-根据会员ID查询
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFundsByCustomerIdResponse extends BasicResponse {
    /**
     * 主键
     */
    private String customerFundsId;

    /**
     * 会员登录账号|手机号
     */
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 会员名称
     */
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 账户余额
     */
    private BigDecimal accountBalance;

    /**
     * 冻结余额
     */
    private BigDecimal blockedBalance;

    /**
     * 可提现金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 已提现金额
     */
    private BigDecimal alreadyDrawAmount;

    /**
     * 是否分销员，0：否，1：是
     */
    private Integer distributor;

    /**
     * 收入笔数
     */
    private Long income;

    /**
     * 收入金额
     */
    private BigDecimal amountReceived;

    /**
     * 支出笔数
     */
    private Long expenditure;

    /**
     * 支出金额
     */
    private BigDecimal amountPaid;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    public String getCustomerAccount() {
        return SensitiveUtils.handlerMobilePhone(customerAccount);
    }
}
