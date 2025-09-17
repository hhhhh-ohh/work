package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;
import com.wanmi.sbc.marketing.api.response.coupon.GetRegisterOrStoreCouponResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录返回
 * Created by daiyitian on 15/11/28.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse extends BasicResponse {

    /**
     * jwt验证token
     */
    @Schema(description = "jwt验证token")
    private String token;

    /**
     * 账号名称
     */
    @Schema(description = "账号名称")
    private String accountName;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.customer.bean.enums.CheckState.class)
    private Integer checkState;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业购会员审核状态")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业购会员审核原因
     */
    @Schema(description = "企业购会员审核原因")
    private String enterpriseCheckReason;

    /**
     * 企业购会员公司详情信息
     */
    @Schema(description = "企业购会员公司详情信息")
    private EnterpriseInfoVO enterpriseInfoVO;

    /**
     * 客户明细
     */
    @Schema(description = "客户明细信息")
    private CustomerDetailVO customerDetail;

    /**
     * 是否直接可以登录 0 否 1 是
     */
    @Schema(description = "是否直接可以登录")
    private Boolean isLoginFlag;

    /**
     * 注册赠券信息
     */
    @Schema(description = "注册赠券信息")
    private GetRegisterOrStoreCouponResponse couponResponse = new GetRegisterOrStoreCouponResponse();

    /**
     * 被邀请会员邀请码
     */
    @Schema(description = "被邀请会员邀请码")
    private String inviteCode;

    /**
     * 新用户初始登录标志
     */
    @Schema(description = "新用户初始登录标志")
    private Boolean newFlag;


    /**
     * 企业用户注册状态
     */
    @Schema(description = "企业用户注册状态")
    private Boolean enterpriseRegisterState;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

}
