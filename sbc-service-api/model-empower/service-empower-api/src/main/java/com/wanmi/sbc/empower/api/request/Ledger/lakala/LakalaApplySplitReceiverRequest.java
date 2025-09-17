package com.wanmi.sbc.empower.api.request.Ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 拉卡拉创建分账接收方request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaApplySplitReceiverRequest extends LakalaBaseRequest{

    /**
     * 分账接收方名称
     */
    @Schema(description = "分账接收方名称")
    @NotBlank
    @Max(64)
    private String receiverName;

    /**
     * 联系手机号
     */
    @Schema(description = "联系手机号")
    @NotBlank
    @Max(16)
    private String contactMobile;


    /**
     * 营业执照号码
     * 收款账户账户类型为对公，必须上送
     */
    @Schema(description = "营业执照号码")
    @Max(32)
    private String licenseNo;

    /**
     * 营业执照名称
     * 收款账户账户类型为对公，必须上送
     */
    @Schema(description = "营业执照名称")
    @Max(128)
    private String licenseName;

    /**
     * 法人姓名
     * 收款账户账户类型为对公，必须上送
     */
    @Schema(description = "法人姓名")
    @Max(32)
    private String legalPersonName;


    /**
     * 法人证件类型
     * 17 身份证，18 护照，19 港澳居民来往内地通行证 20 台湾居民来往内地通行证
     * 收款账户账户类型为对公，必须上送
     */
    @Schema(description = "法人证件类型")
    @Max(32)
    @Builder.Default
    private String legalPersonCertificateType = "17";


    /**
     * 法人证件号
     * 收款账户账户类型为对公，必须上送
     */
    @Schema(description = "法人证件号")
    @Max(32)
    private String legalPersonCertificateNo;


    /**
     * 	收款账户卡号
     */
    @Schema(description = "收款账户卡号")
    @Max(32)
    @NotBlank
    private String acctNo;

    /**
     * 	收款账户名称
     */
    @Schema(description = "收款账户名称")
    @Max(32)
    @NotBlank
    private String acctName;


    /**
     * 收款账户账户类型
     * 	57：对公 58：对私
     */
    @Schema(description = "收款账户账户类型")
    @Max(32)
    @NotBlank
    private String acctTypeCode;

    /**
     * 收款账户证件类型
     * 17 身份证，18 护照，19 港澳居民来往内地通行证 20 台湾居民来往内地通行证
     */
    @Schema(description = "收款账户证件类型")
    @Max(32)
    @NotBlank
    @Builder.Default
    private String acctCertificateType = "17";


    /**
     * 收款账户证件号
     */
    @Schema(description = "收款账户证件号")
    @Max(32)
    @NotBlank
    private String acctCertificateNo;

    /**
     * 收款账户开户行号
     */
    @Schema(description = "收款账户开户行号")
    @Max(32)
    @NotBlank
    private String acctOpenBankCode;

    /**
     * 收款账户开户名称
     */
    @Schema(description = "收款账户开户名称")
    @Max(64)
    @NotBlank
    private String acctOpenBankName;


    /**
     * 接收方附件资料
     */
    @Schema(description = "接收方附件资料")
    @NotNull
    @Size(min = 1)
    private List<Attach> attachList;
}
