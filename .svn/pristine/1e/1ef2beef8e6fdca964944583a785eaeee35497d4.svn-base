package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import com.wanmi.sbc.common.annotation.CanEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

/**
 * 拉卡拉分账绑定request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaApplyBindRequest extends LakalaBaseRequest{


    /**
     * 拉卡拉内部商户号
     */
    @Schema(description = "拉卡拉内部商户号")
    @NotBlank
    @Max(32)
    private String merInnerNo;


    /**
     * 分账接收方编号
     */
    @Schema(description = "分账接收方编号")
    @NotBlank
    @Max(32)
    private String receiverNo;

    /**
     * 合作协议附件名称
     */
    @Schema(description = "合作协议附件名称")
    @Max(32)
    @Builder.Default
    private String entrustFileName = "分账合作协议.pdf";

    /**
     * 合作协议附件路径
     */
    @Schema(description = "合作协议附件路径")
    @NotBlank
    @Max(32)
    private String entrustFilePath;


    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    @Max(64)
    @CanEmpty
    private String retUrl;

}
