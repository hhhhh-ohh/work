package com.wanmi.sbc.customer.model.root;

import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.common.enums.LogOutStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Enumerated;
import java.io.Serializable;

/**
 * 会员资金统计映射实体对象
 * @author: Geek Wang
 * @createDate: 2019/2/19 9:42
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBase implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 账户
     */
    private String customerAccount;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 会员等级ID
     */
    private Long customerLevelId;

    /**
     * 会员等级名称
     */
    private String customerLevelName;

    /**
     * 客户成长值
     */
    private Long growthValue;

    /**
     * 客户类型（0:平台客户,1:商家客户）
     */
    @Enumerated
    private CustomerType customerType;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Enumerated
    private CheckState checkState;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Enumerated
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业购会员审核拒绝原因
     */
    private String enterpriseCheckReason;

    /**
     * 可用积分
     */
    private Long pointsAvailable;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Enumerated
    private LogOutStatus logOutStatus;

    /**
     * 注销原因Id
     */
    private String cancellationReasonId;

    /**
     * 注销原因
     */
    private String cancellationReason;


    public CustomerBase(String customerId, String customerAccount) {
        this.customerId = customerId;
        this.customerAccount = customerAccount;
    }

    public CustomerBase(String customerId, Long customerLevelId) {
        this.customerId = customerId;
        this.customerLevelId = customerLevelId;
    }

    public CustomerBase(String customerId, String customerAccount,String customerName) {
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.customerName = customerName;
    }

    public CustomerBase(String customerId, String customerAccount,Long growthValue,CustomerType customerType) {
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.growthValue = growthValue;
        this.customerType = customerType;
    }

    public CustomerBase(String customerId, String customerAccount,Long customerLevelId, CheckState checkState, CustomerType customerType, EnterpriseCheckState enterpriseCheckState, String enterpriseCheckReason,Long pointsAvailable) {
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.customerLevelId = customerLevelId;
        this.checkState = checkState;
        this.customerType = customerType;
        this.enterpriseCheckState = enterpriseCheckState;
        this.enterpriseCheckReason = enterpriseCheckReason;
        this.pointsAvailable = pointsAvailable;
    }

    public CustomerBase(String customerId, String customerAccount,LogOutStatus logOutStatus) {
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.logOutStatus = logOutStatus;
    }

    public CustomerBase(String customerId, String customerAccount,Long customerLevelId, CheckState checkState, CustomerType customerType, EnterpriseCheckState enterpriseCheckState, String enterpriseCheckReason,Long pointsAvailable,LogOutStatus logOutStatus
    ,String cancellationReasonId,String cancellationReason) {
        this.customerId = customerId;
        this.customerAccount = customerAccount;
        this.customerLevelId = customerLevelId;
        this.checkState = checkState;
        this.customerType = customerType;
        this.enterpriseCheckState = enterpriseCheckState;
        this.enterpriseCheckReason = enterpriseCheckReason;
        this.pointsAvailable = pointsAvailable;
        this.logOutStatus = logOutStatus;
        this.cancellationReasonId = cancellationReasonId;
        this.cancellationReason = cancellationReason;
    }
}
