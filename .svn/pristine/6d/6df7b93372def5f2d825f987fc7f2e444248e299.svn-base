package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员资金-新增对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsAddRequest extends BaseRequest {

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 会员账号
     */
    @Schema(description = "会员账号")
    private String customerAccount;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;


    /**
     * 账户余额
     */
    @Schema(description = "账户余额")
    private BigDecimal accountBalance;

    /**
     * 是否是分销员
     */
    @Schema(description = "是否是分销员，0：否 1：是")
    private Integer distributor = NumberUtils.INTEGER_ZERO;

    /**
     * 是否是社区团长
     */
    @Schema(description = "是否是社区团长，0：否 1：是")
    private Integer communityLeader = NumberUtils.INTEGER_ZERO;
}
