package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.enums.GenderType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 客户详细信息
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
public class CustomerDetailForPageVO extends BasicResponse {

    /**
     * 会员详细信息标识UUID
     */
    @Schema(description = "会员详细信息标识UUID")
    private String customerDetailId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 账户
     */
    @Schema(description = "账户")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String customerAccount;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

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
    private String customerAddress;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

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
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    private String contactName;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String contactPhone;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员")
    private String employeeId;

    /**
     * 业务员名称
     */
    @Schema(description = "业务员名称")
    private String employeeName;

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
     * 成长值
     */
    @Schema(description = "成长值")
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
     * 客户类型
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

    /**
     * 是否为分销员
     */
    @Schema(description = "是否为分销员")
    private DefaultFlag isDistributor;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String rejectReason;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String forbidReason;

    /**
     * 是否是我的客户（S2b-Supplier使用）
     */
    @Schema(description = "是否是我的客户（S2b-Supplier使用")
    private boolean isMyCustomer;

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
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业购会员审核拒绝原因
     */
    @Schema(description = "店铺名称")
    private String storeName;


    /**
     * 省
     */
    @Schema(description = "省")
    private String provinceName;

    /**
     * 市
     */
    @Schema(description = "市")
    private String cityName;

    /**
     * 区
     */
    @Schema(description = "区")
    private String areaName;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private String streetName;

    @Schema(description = "企业购会员审核拒绝原因")
    private String enterpriseCheckReason;

    /**
     * (企业购)企业名称
     */
    @Schema(description = "企业名称")
    private String enterpriseName;

    /**
     * (企业购)企业性质
     */
    @Schema(description = "企业性质")
    private Integer businessNatureType;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;
}
