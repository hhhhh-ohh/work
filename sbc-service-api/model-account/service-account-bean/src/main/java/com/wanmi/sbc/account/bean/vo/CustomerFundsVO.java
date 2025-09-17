package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.SensitiveUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员资金VO
 * @author: Geek Wang
 * @createDate: 2019/2/19 9:42
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsVO extends BasicResponse {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String customerFundsId;

    /**
     * 会员登录账号|手机号
     */
    @Schema(description = "会员登录账号|手机号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 账户余额
     */
    @Schema(description = "账户余额")
    private BigDecimal accountBalance;

    /**
     * 冻结余额
     */
    @Schema(description = "冻结余额")
    private BigDecimal blockedBalance;

    /**
     * 可提现金额
     */
    @Schema(description = "可提现金额")
    private BigDecimal withdrawAmount;

    /**
     * 已提现金额
     */
    @Schema(description = "已提现金额")
    private BigDecimal alreadyDrawAmount;

    /**
     * 是否分销员，0：否，1：是
     */
    @Schema(description = "是否分销员，0：否，1：是")
    private Integer distributor;

    /**
     * 是否社区团长 0：否，1：是
     */
    @Schema(description = "是否社区团长，0：否，1：是")
    private Integer communityLeader;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    public String getCustomerAccount() {
        return SensitiveUtils.handlerMobilePhone(customerAccount);
    }
}
