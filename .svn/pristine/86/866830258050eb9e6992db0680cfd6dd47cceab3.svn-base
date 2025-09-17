package com.wanmi.sbc.account.api.request.funds;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员资金-导出查询对象
 * @author: of2975
 * @createDate: 2019/4/30 11:06
 * @version: 1.0
 */
@Schema
@Data
public class CustomerFundsExportRequest extends BaseQueryRequest implements Serializable {

    /**
     * jwt token
     */
    @Schema(description = "token")
    private String token;

    /**
     * 批量查询-会员资金主键List
     */
    @Schema(description = "批量查询-会员资金主键List")
    private List<String> customerFundsIdList;

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
     * 是否分销员，-1：全部，0：否，1：是
     */
    @Schema(description = "是否分销员")
    private Integer distributor;

    /**
     * 是否社区团长，-1：全部，0：否，1：是
     */
    @Schema(description = "是否社区团长")
    private Integer communityLeader;

    /**
     * 账户余额开始
     */
    @Schema(description = "账户余额开始")
    private BigDecimal startAccountBalance;

    /**
     * 账户余额结束
     */
    @Schema(description = "账户余额结束")
    private BigDecimal endAccountBalance;

    /**
     * 冻结余额开始
     */
    @Schema(description = "冻结余额开始")
    private BigDecimal startBlockedBalance;

    /**
     * 冻结余额结束
     */
    @Schema(description = "冻结余额结束")
    private BigDecimal endBlockedBalance;

    /**
     * 可提现金额开始
     */
    @Schema(description = "可提现金额开始")
    private BigDecimal startWithdrawAmount;

    /**
     * 可提现金额结束
     */
    @Schema(description = "可提现金额结束")
    private BigDecimal endWithdrawAmount;

    /**
     * 批量查询-业务员相关会员id
     */
    @Schema(description = "批量查询-业务员相关会员id", hidden = true)
    private List<String> employeeCustomerIds;
}
