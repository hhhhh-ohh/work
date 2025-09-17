package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerDetailInitEsVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 账户
     */
    @Schema(description = "账号")
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
    private String customerAddress;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
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
     * 用户类型
     */
    @Schema(description = "用户类型")
    private CustomerType customerType;


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

    @Schema(description = "店铺关联客户关系ID集合")
    private List<StoreCustomerRelaVO> esStoreCustomerRelaList;

    @Schema(description = "企业会员")
    private EnterpriseInfoVO enterpriseInfo;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业会员状态")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业购会员审核拒绝原因
     */
    @Schema(description = "企业会员审核拒绝原因")
    private String enterpriseCheckReason;

    /**
     * 可用积分
     */
    @Schema(description = "可用积分")
    private Long pointsAvailable;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态")
    private Long logOutStatus;

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
     * 付费会员等级名称
     */
    @Schema(description = "付费会员等级名称")
    private List<PayingMemberLevelBaseVO> payingMemberLevelList;

}
