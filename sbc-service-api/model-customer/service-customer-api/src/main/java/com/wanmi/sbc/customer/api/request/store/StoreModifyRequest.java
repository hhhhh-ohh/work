package com.wanmi.sbc.customer.api.request.store;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>店铺l修改request</p>
 * Created by of628-wenzhi on 2018-09-18-下午6:01.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreModifyRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    @NotNull
    private Long storeId;

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
    @Length(max = 13)
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
    @Schema(description = "使用的运费模板类别",contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private DefaultFlag freightTemplateType = DefaultFlag.NO;

    /**
     * 商家类型 0供应商 1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型")
    private StoreType storeType;

    /**
     * 签约开始日期
     */
    @Schema(description = "签约开始日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 申请入驻时间
     */
    @Schema(description = "申请入驻时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState auditState;

    /**
     * 二次签约审核状态
     */
    @Schema(description = "二次签约审核状态")
    private CheckState contractAuditState;

    /**
     * 二次签约拒绝原因
     */
    @Schema(description = "二次签约拒绝原因")
    private String contractAuditReason;
}
