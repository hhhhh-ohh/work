package com.wanmi.sbc.customer.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * boss端保存客户
 * Created by CHENLI on 2017/4/19.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerEditRequest extends BaseRequest {

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
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

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
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState checkState;

    /**
     * 客户类型 0:平台客户,1:商家客户
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

    /**
     * 所属商家Id
     */
    @Schema(description = "所属商家Id")
    private Long companyInfoId;

    /**
     * 所属店铺Id
     */
    @Schema(description = "所属店铺Id")
    private Long storeId;

    /**
     * 是否重置密码
     */
    @Schema(description = "是否重置密码")
    private boolean passReset;

    /**
     * 重置密码对应的新账号
     */
    @Schema(description = "重置密码对应的新账号")
    private String customerAccountForReset;

    /**
     * 商家和客户关联关系id，用于修改等级
     */
    @Schema(description = "商家和客户关联关系id")
    private String storeCustomerRelaId;

}
