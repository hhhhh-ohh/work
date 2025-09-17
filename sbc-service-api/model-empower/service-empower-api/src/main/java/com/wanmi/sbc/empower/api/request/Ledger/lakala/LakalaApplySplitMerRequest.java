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

import java.math.BigDecimal;

/**
 * 拉卡拉创建分账商户request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaApplySplitMerRequest extends LakalaBaseRequest{

    /**
     * 拉卡拉内部商户号
     */
    @Schema(description = "拉卡拉内部商户号")
    @NotBlank
    @Max(32)
    private String merInnerNo;


    /**
     * 联系手机号
     */
    @Schema(description = "联系手机号")
    @NotBlank
    @Max(32)
    private String contactMobile;


    /**
     * 最低分账比例（百分比，支持2位精度）默认是 10
     */
    @Schema(description = "最低分账比例（百分比，支持2位精度）")
    @Builder.Default
    @Max(32)
    private BigDecimal splitLowestRatio = new BigDecimal("70");


    /**
     * 	分账结算委托书文件名称
     */
    @Schema(description = "分账结算委托书文件名称")
    @Builder.Default
    @Max(64)
    private String splitEntrustFileName = "分账结算委托书文件名称.pdf";



    /**
     * 	分账结算委托书文件名称
     */
    @Schema(description = "分账结算委托书文件名称")
    @Max(64)
    @NotBlank
    private String splitEntrustFilePath;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    @Max(128)
    @CanEmpty
    private String retUrl;
}
