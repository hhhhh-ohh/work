package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 会员详情查询参数
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailInitEsRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员详细信息标识UUID
     */
    @Schema(description = "会员详细信息标识UUID")
    private String customerDetailId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 客户IDs
     */
    @Schema(description = "客户IDs")
    private List<String> customerIds;

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
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

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
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.customer.bean.enums.CheckState.class)
    private Integer checkState;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员")
    private String employeeId;

    /**
     * 负责业务员集合
     */
    @Schema(description = "负责业务员集合")
    private List<String> employeeIds;

    /**
     * 精确查询-账户
     */
    @Schema(description = "精确查询-账户")
    private String equalCustomerAccount;

    /**
     * 精确查找-商家下的客户
     */
    @Schema(description = "精确查找-商家下的客户")
    private Long companyInfoId;

    /**
     * 禁用原因
     */
    @Schema(description = "禁用原因")
    private String forbidReason;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private CustomerType customerType;

    /**
     * 关键字搜索，目前范围：会员名称、客户账户
     */
    @Schema(description = "关键字搜索，目前范围：会员名称、客户账户")
    private String keyword;

    /**
     * 可用积分段查询开始
     */
    @Schema(description = "可用积分段查询开始")
    private Long pointsAvailableBegin;

    /**
     * 是否为分销员
     */
    @Schema(description = "是否为分销员")
    private DefaultFlag isDistributor;

    /**
     * 可用积分段查询结束
     */
    @Schema(description = "可用积分段查询结束")
    private Long pointsAvailableEnd;

    /**
     * 是否是我的客户（S2b-Supplier使用）
     */
    @Schema(description = "是否是我的客户（S2b-Supplier使用")
    private Boolean isMyCustomer;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 是否查询所有会员
     */
    @Schema(description = "是否查询所有会员 ")
    private DefaultFlag defaultFlag;

    /**
     * 是否反查省市区
     */
    @Schema(description = "是否反查省市区")
    private Boolean showAreaFlag;

    /**
     * 批量客户ID
     */
    @Schema(description = "批量客户ID")
    private List<String> idList;
}
