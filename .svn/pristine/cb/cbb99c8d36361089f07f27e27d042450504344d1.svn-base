package com.wanmi.sbc.customer.bean.dto;


import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员增票资质-共用DTO
 */
@Schema
@Data
public class CustomerInvoiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 增专资质ID
     */
    @Schema(description = "增专资质ID")
    private Long customerInvoiceId;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 单位全称
     */
    @Schema(description = "单位全称")
    @NotBlank
    private String companyName;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
//    @NotBlank
    private String taxpayerNumber;

    /**
     * 单位电话
     */
    @Schema(description = "单位电话")
//    @NotBlank
    private String companyPhone;

    /**
     * 单位地址
     */
    @Schema(description = "单位地址")
//    @NotBlank
    private String companyAddress;

    /**
     * 银行基本户号
     */
    @Schema(description = "银行基本户号")
//    @NotBlank
    private String bankNo;

    /**
     * 开户行
     */
    @Schema(description = "开户行")
//    @NotBlank
    private String bankName;

    /**
     * 营业执照复印件
     */
    @Schema(description = "营业执照复印件")
//    @NotBlank
    private String businessLicenseImg;

    /**
     * 一般纳税人认证资格复印件
     */
    @Schema(description = "一般纳税人认证资格复印件")
//    @NotBlank
    private String taxpayerIdentificationImg;

    /**
     * 增票资质审核状态
     */
    @Schema(description = "增票资质审核状态")
    private CheckState checkState;

    @Schema(description = "发票类型：0-增值税发票；1-个人发票；2-单位发票")
    @NotNull
    private InvoiceStyle invoiceStyle;
}
