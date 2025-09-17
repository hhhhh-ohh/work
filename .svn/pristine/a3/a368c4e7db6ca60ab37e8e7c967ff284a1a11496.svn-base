package com.wanmi.sbc.customer.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员信息-共用DTO
 */
@Schema
@Data
public class CustomerDTO implements Serializable {

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
     * 客户成长值
     */
    @Schema(description = "客户成长值")
    private Long growthValue;

    /**
     * 可用积分
     */
    @Schema(description = "可用积分")
    private Long pointsAvailable;

    /**
     * 已用积分
     */
    @Schema(description = "已用积分")
    private Long pointsUsed;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String customerPassword;

    /**
     * 支付密码
     */
    @Schema(description = "支付密码")
    private String customerPayPassword;

    /**
     * 密码安全等级：20危险 40低、60中、80高
     */
    @Schema(description = "密码安全等级")
    private Integer safeLevel;

    /**
     * 支付密码的安全级别：20危险 40低、60中、80高
     */
    @Schema(description = "支付密码的安全级别")
    private Integer paySafeLevel;

    /**
     * 盐值，用于密码加密
     */
    @Schema(description = "盐值")
    private String customerSaltVal;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState checkState;

    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime checkTime;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 登录IP
     */
    @Schema(description = "登录IP")
    private String loginIp;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;

    /**
     * 密码错误次数
     */
    @Schema(description = "密码错误次数")
    private Integer loginErrorCount = 0;

    /**
     * 创建|注册时间
     */
    @Schema(description = "创建|注册时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String deletePerson;

    /**
     * 会员的详细信息
     */
    @Schema(description = "会员的详细信息")
    private CustomerDetailDTO customerDetail;

    /**
     * 客户类型（0:平台客户,1:商家客户）
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

    /**
     * 锁定时间
     */
    @Schema(description = "锁定时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginLockTime;

    /**
     * 商家和客户的关联关系
     */
    @Schema(description = "商家和客户的关联关系")
    private List<StoreCustomerRelaDTO> storeCustomerRelaListByAll;

    /**
     * 连续签到天数
     */
    @Schema(description = "连续签到天数")
    private Integer signContinuousDays;

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
     * 支付密码错误次数
     */
    @Schema(description = "支付密码错误次数")
    private Integer payErrorTime;

    /**
     * 支付锁定时间
     */
    @Schema(description = "支付锁定时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime payLockTime;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 注销原因Id
     */
    @Schema(description = "注销原因Id")
    private String cancellationReasonId;

    /**
     * 注销原因
     */
    @Schema(description = "注销原因")
    private String cancellationReason;

    /**
     * 等级升级时间
     */
    @Schema(description = "等级升级时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime upgradeTim;

}
