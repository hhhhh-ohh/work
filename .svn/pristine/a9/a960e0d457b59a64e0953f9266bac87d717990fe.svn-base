package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 客户信息主表
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
public class CustomerSimplifyOrderCommitVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业购会员审核状态")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 可用积分
     */
    @Schema(description = "可用积分")
    private Long pointsAvailable;

    /**
     * 商家和客户的关联关系
     */
    @Schema(description = "商家和客户的关联关系")
    private List<StoreCustomerRelaVO> storeCustomerRelaListByAll;

    /**
     * 会员详情对象
     */
    private CustomerDetailSimplifyVO customerDetail;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

}
