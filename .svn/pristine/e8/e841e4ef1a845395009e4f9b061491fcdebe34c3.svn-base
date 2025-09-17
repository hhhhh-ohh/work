package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * 店铺基本信息参数
 * Created by CHENLI on 2017/11/2.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreAddRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 店铺店招
     */
    @Schema(description = "店铺店招")
    private String storeSign;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    @NotBlank
    @Length(max = 20)
    private String supplierName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    @NotBlank
    @Length(max = 20)
    private String storeName;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    @NotBlank
    @Length(min = 2, max = 15)
    private String contactPerson;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    @NotBlank
    @Length(max = 11)
    private String contactMobile;

    /**
     * 联系邮箱
     */
    @Schema(description = "联系邮箱")
    @NotBlank
    @Length(min = 1, max = 100)
    @Email
    private String contactEmail;

    /**
     * 省
     */
    @Schema(description = "省")
    @NotNull
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
    @NotBlank
    @Length(max = 60)
    private String addressDetail;

    /**
     * 是否重置密码 0 否 1 是
     */
    @Schema(description = "是否重置密码")
    private Boolean isResetPwd = Boolean.FALSE;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String accountPassword;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    private String accountName;

    /**
     * 账号类型 0 b2b账号 1 s2b平台端账号 2 s2b商家端账号
     */
    @Schema(description = "账号类型")
    private AccountType accountType;

    /**
     * 使用的运费模板类别(0:店铺运费,1:单品运费)
     */
    @Schema(description = "使用的运费模板类别(0:店铺运费,1:单品运费)")
    private DefaultFlag freightTemplateType = DefaultFlag.NO;
}
