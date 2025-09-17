package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 企业会员查询参数
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseCustomerPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;



    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
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
     * 账号状态 0：启用中  1：禁用中
     */
    @Schema(description = "账号状态")
    private CustomerStatus customerStatus;

    /**
     * 删除标志 0未删除 1已删除
     */
    @Schema(description = "删除标志", contentSchema = com.wanmi.sbc.common.enums.DeleteFlag.class)
    private Integer delFlag;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员")
    private String employeeId;

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
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过")
    private EnterpriseCheckState enterpriseCheckState;
}
