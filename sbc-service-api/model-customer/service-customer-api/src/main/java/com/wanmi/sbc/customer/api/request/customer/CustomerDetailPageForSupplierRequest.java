package com.wanmi.sbc.customer.api.request.customer;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Created by hht on 2017/11/16.
 */
@Schema
@Data
public class CustomerDetailPageForSupplierRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

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
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 账号状态 0：启用中  1：禁用中
     */
    @Schema(description = "账号状态")
    private CustomerStatus customerStatus;

    /**
     * 客户账户
     */
    @Schema(description = "客户账户")
    private String customerAccount;

    /**
     * 商铺Id
     */
    @Schema(description = "商铺Id")
    private Long storeId;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long companyInfoId;

    /**
     * 客户类型
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

    /**
     * 关键字搜索，目前范围：会员名称、客户账户
     */
    @Schema(description = "关键字搜索，目前范围：会员名称、客户账户")
    private String keyword;

    /**
     * 所属业务员id
     */
    @Schema(description = "所属业务员id")
    private String employeeId;

    /**
     * 所属业务员id集合
     */
    @Schema(description = "所属业务员id集合")
    private List<String> employeeIds;


    /**
     * 是否是我的客户（S2b-Supplier使用）
     */
    @Schema(description = "是否是我的客户（S2b-Supplier使用")
    private Boolean isMyCustomer;

    /**
     * 根据会员的IDs批量查询会员
     */
    @Schema(description = "根据会员的IDs批量查询会员")
    private List<String> customerIds;

    /**
     * 是否反查省市区
     */
    @Schema(description = "是否反查省市区")
    private Boolean showAreaFlag;

}
