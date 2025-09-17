package com.wanmi.sbc.elastic.api.request.customerInvoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 会员增票资质-编辑Request
 */
@Schema
@Data
public class EsCustomerInvoiceModifyRequest extends BaseRequest {
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

    @Schema(description = "负责业务员")
    private String employeeId;

    private InvoiceStyle invoiceStyle;
}
