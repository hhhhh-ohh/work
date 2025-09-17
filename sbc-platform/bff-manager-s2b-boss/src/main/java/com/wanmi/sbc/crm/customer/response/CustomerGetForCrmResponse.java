package com.wanmi.sbc.crm.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.GenderType;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.bean.vo.EnterpriseInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema
@Data
public class CustomerGetForCrmResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String headImg;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 账户
     */
    @Schema(description = "账户")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 账号状态 0：启用中  1：禁用中
     */
    @Schema(description = "账号状态")
    private CustomerStatus customerStatus;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState checkState;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String forbidReason;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String contactName;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String contactPhone;

    /**
     * 省
     */
    @Schema(description = "省")
    private Long provinceId;

    /**
     * 市
     */
    @Schema(description = "市")
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    @SensitiveWordsField(signType = SignWordType.ADDRESS)
    private String customerAddress;

    /**
     * 业务员
     */
    @Schema(description = "业务员")
    private String employeeId;

    /**
     * 业务员姓名
     */
    @Schema(description = "业务员姓名")
    private String employeeName;
    /**
     * 业务员手机号
     */
    @Schema(description = "业务员手机号")
    private String employeeMobile;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

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
     * 账户余额
     */
    @Schema(description = "账户余额")
    private BigDecimal accountBalance;

    /**
     * 是否为分销员
     */
    @Schema(description = "是否为分销员")
    private DefaultFlag isDistributor;

    /**
     * 是否有分销员资格0：否，1：是
     */
    @Schema(description = "是否有分销员资格0：否，1：是")
    private DefaultFlag distributorFlag;

    /**
     * 分销员等级名称
     */
    @Schema(description = "分销员等级名称 ")
    private String distributorLevelName;

    /**
     * 邀新人数
     */
    @Schema(description = "邀新人数")
    private Integer inviteCount;

    /**
     * 有效邀新人数
     */
    @Schema(description = "有效邀新人数")
    private Integer inviteAvailableCount;

    /**
     * 邀新奖金(元)
     */
    @Schema(description = "邀新奖金(元)")
    private BigDecimal rewardCash;

    /**
     * 未入账邀新奖金(元)
     */
    @Schema(description = "未入账邀新奖金(元)")
    private BigDecimal rewardCashNotRecorded;

    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthDay;

    /**
     * 性别，0女，1男
     */
    @Schema(description = "性别，0女，1男")
    private GenderType gender;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

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
     * 企业信息
     */
    @Schema(description = "企业信息")
    private EnterpriseInfoVO enterpriseInfo;

    /**
     * 企业会员名称
     */
    @Schema(description = "企业会员名称")
    private String enterpriseCustomerName;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 付费会员等级名称
     */
    @Schema(description = "付费会员等级名称")
    private String levelName;

    /**
     * 付费会员升级还差金额
     */
    @Schema(description = "付费会员升级还差金额")
    private BigDecimal remainingAmount;

    /**
     * 付费会员到期时间
     */
    @Schema(description = "付费会员到期时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate expirationDate;

    /**
     * 会员礼品卡余额
     */
    @Schema(description = "会员礼品卡余额")
    private BigDecimal giftCardBalance;
}
