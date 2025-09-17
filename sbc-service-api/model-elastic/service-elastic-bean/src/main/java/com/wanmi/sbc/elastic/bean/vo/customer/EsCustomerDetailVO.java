package com.wanmi.sbc.elastic.bean.vo.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.EsPayingMemberLevelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
public class EsCustomerDetailVO extends BasicResponse {

    /**
     * 会员ID
     */
    @Schema(description = "客户ID")
    private String customerId;

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
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String contactName;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;


    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String contactPhone;


    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState checkState;


    /**
     * 账号状态 0：启用中  1：禁用中
     */
    @Schema(description = "账号状态")
    private CustomerStatus customerStatus;


    /**
     * 负责业务员
     */
    @Schema(description = "业务员ID")
    private String employeeId;


    /**
     * 是否为分销员 0：否  1：是
     */
    @Schema(description = "是否分销员")
    private DefaultFlag isDistributor;


    /**
     * 审核驳回理由
     */
    @Schema(description = "审核驳回原因")
    private String rejectReason;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String forbidReason;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;



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
     * 业务员名称
     */
    @Schema(description = "业务员名称")
    private String employeeName;

    /**
     * 是否是我的客户（S2b-Supplier使用）
     */
    @Schema(description = "是否是我的客户（S2b-Supplier使用")
    private Boolean myCustomer;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private CustomerType customerType;

    /**
     * 企业名称
     */
    @Schema(description = "企业名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String enterpriseName;

    /**
     * 企业性质
     */
    @Schema(description = "企业性质")
    private Integer businessNatureType;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业会员审核状态")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业购会员审核拒绝原因
     */
    @Schema(description = "企业会员审核拒绝原因")
    private String enterpriseCheckReason;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    private Long logOutStatus;

    /**logOutStatus
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
     * 付费会员等级
     */
    @Schema(description = "付费会员等级")
    private List<EsPayingMemberLevelVO> payingMemberLevelList;

    /**
     * 付费会员等级名称
     */
    @Schema(description = "付费会员等级名称")
    private String payingMemberLevelName;

}
