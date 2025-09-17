package com.wanmi.sbc.empower.api.request.Ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉进件校验
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema
public class LakalaVerifyContractInfoRequest extends LakalaBaseRequest{

    /**
     * 商户注册名称
     */
    @Schema(description = "商户注册名称")
    @Max(32)
    private String merRegName;

    /**
     * 商户经营名称
     */
    @Schema(description = "商户经营名称")
    @Max(32)
    private String merBizName;

    /**
     * 营业执照
     */
    @Schema(description = "营业执照")
    @Max(32)
    private String merBlis;

    /**
     * 法人身份证号
     */
    @Schema(description = "法人身份证号")
    @Max(32)
    private String larIdcard;

    /**
     * 结算账号
     */
    @Schema(description = "结算账号")
    @Max(32)
    private String acctNo;


    /**
     * 结算人证件号
     */
    @Schema(description = "结算人证件号")
    @Max(32)
    private String acctIdcard;

}
