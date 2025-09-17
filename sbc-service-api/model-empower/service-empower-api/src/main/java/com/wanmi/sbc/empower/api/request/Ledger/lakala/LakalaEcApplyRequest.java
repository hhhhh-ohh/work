package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import com.wanmi.sbc.common.annotation.CanEmpty;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉电子合同申请request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaEcApplyRequest extends LakalaBaseRequest{
    /**
     * 机构号
     * 签约方所属拉卡拉机构
     */
    @Schema(description = "机构号")
    @CanEmpty
    private Integer orgId;

    /**
     * 合同类别
     * EC001 特约商户支付服务合作协议V3.1(商户入网)
     * EC002 特约商户支付服务合作协议V3.1（商户入网+分账）
     */
    @Schema(description = "合同类别")
    @Builder.Default
    @Max(12)
    private String ecTypeCode = "EC005";

    /**
     * 证件类型
     * 当前固定传递： RESIDENT_ID （身份证）
     */
    @Schema(description = "证件类型")
    @Builder.Default
    @Max(16)
    private String certType = "RESIDENT_ID";

    /**
     * 证件名称
     * 个人或企业法人身份证名称
     */
    @Schema(description = "证件名称")
    @NotBlank
    @Max(32)
    private String certName;

    /**
     * 证件号
     * 个人或企业法人身份证号
     */
    @Schema(description = "证件号")
    @NotBlank
    @Max(32)
    private String certNo;

    /**
     * 手机号
     * 签约人手机号 合同签署人手机号，请慎重填写，不可修改
     */
    @Schema(description = "手机号")
    @NotBlank
    @Max(16)
    private String mobile;

    /**
     * 营业执照号
     * 企业用户必传
     */
    @Schema(description = "营业执照号")
    @NotBlank
    @Max(32)
    private String businessLicenseNo;

    /**
     * 营业执照名称
     * 企业用户必传
     */
    @Schema(description = "营业执照名称")
    @NotBlank
    @Max(32)
    private String businessLicenseName;

    /**
     * 开户行号
     * 个人或企业法人银行卡开户行号
     */
    @Schema(description = "开户行号")
    @NotBlank
    @Max(32)
    private String openningBankCode;

    /**
     * 开户行名称
     * 个人或企业法人银行卡开户行名称
     */
    @Schema(description = "开户行名称")
    @NotBlank
    @Max(128)
    private String openningBankName;

    /**
     * 银行卡性质
     * 个人或企业法人银行卡性质
     * 57 对公、 58 对私
     */
    @Schema(description = "银行卡性质")
    @Builder.Default
    @Max(2)
    private String acctTypeCode = "57";

    /**
     * 银行卡号
     * 个人或企业法人银行卡号
     */
    @Schema(description = "银行卡号")
    @NotBlank
    @Max(32)
    private String acctNo;

    /**
     * 银行卡名称
     * 个人或企业法人银行卡名称
     */
    @Schema(description = "银行卡名称")
    @NotBlank
    @Max(64)
    private String acctName;

    /**
     * 电子合同内容参数集合 JSONString
     * 按合同类型（ecTypeCode）传递不同的参数集合，EC001合同参数说明 ；EC002合同参数说明
     */
    @Schema(description = "电子合同内容参数集合")
    @NotBlank
    private String ecContentParameters;

    /**
     * 备注说明
     */
    @Schema(description = "电子合同签约结果回调通知")
    @CanEmpty
    @Max(128)
    private String remark;

    /**
     * 电子合同签约结果回调通知
     * 成功签约才通知
     */
    @Schema(description = "电子合同签约结果回调通知")
    @CanEmpty
    @Max(128)
    private String retUrl;

}
