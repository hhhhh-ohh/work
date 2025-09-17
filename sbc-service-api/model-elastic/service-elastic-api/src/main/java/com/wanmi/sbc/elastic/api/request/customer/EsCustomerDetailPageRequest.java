package com.wanmi.sbc.elastic.api.request.customer;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.elastic.api.request.base.EsBaseQueryRequest;

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
public class EsCustomerDetailPageRequest extends EsBaseQueryRequest {

    private static final long serialVersionUID = -1281379836937760934L;

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
     * 账户列表
     */
    @Schema(description = "账户列表")
    private List<String> customerAccountList;

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
     * 用户类型
     */
    @Schema(description = "用户类型")
    private CustomerType customerType;


    /**
     * 是否为分销员
     */
    @Schema(description = "是否为分销员")
    private DefaultFlag isDistributor;

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
     * true: 填充省市区，false: 不填充省市区
     */
    @Schema(description = "是否填充省市区")
    private Boolean fillArea = Boolean.TRUE;

    /**
     * true: 查询企业会员，false:查询普通会员
     */
    @Schema(description = "是否查询企业会员")
    private Boolean enterpriseCustomer = Boolean.FALSE;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    @Schema(description = "企业购会员审核状态")
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业名称
     */
    @Schema(description = "企业名称")
    private String enterpriseName;

    /**
     * 企业性质
     */
    @Schema(description = "企业性质")
    private Integer businessNatureType;

    /**
     * 可用积分段查询开始
     */
    @Schema(description = "可用积分段查询开始")
    private Long pointsAvailableBegin;

    /**
     * 可用积分段查询结束
     */
    @Schema(description = "可用积分段查询结束")
    private Long pointsAvailableEnd;


    /**
     * true: 积分分页 ，false: 其他
     */
    @Schema(description = "是否积分分页")
    private Boolean pointsPage = Boolean.FALSE;

    /**
     * 是否是我的客户（S2b-Supplier使用）
     */
    @Schema(description = "是否是我的客户（S2b-Supplier使用")
    private Boolean isMyCustomer;

    @Schema(description = "搜索项")
    private String keyword;

    /**
     * 所属平台（目前只有门店用到）
     */
    @Schema(description = "所属平台")
    private PluginType pluginType;

    /**
     * 是否筛选所有注销状态
     */
    @Schema(description = "是否筛选所有注销状态")
    private Boolean filterAllLogOutStatusFlag = false;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 付费会员等级ID
     */
    @Schema(description = "付费会员等级ID")
    private Long levelId;
}
