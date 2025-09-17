package com.wanmi.sbc.empower.api.request.Ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉对接 基类
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LakalaBaseRequest {

    /**
     * 接口版本号
     * 当前默认 1.0
     */
    @Schema(description = "接口版本号")
    @Builder.Default
    @Max(32)
    private String version = "1.0";

    /**
     * 四方机构自定义订单编号
     * 建议：平台编号+14位年月日时（24小时制）分秒+8位的随机数（同一接入机构不重复）
     */
    @Schema(description = "四方机构自定义订单编号")
    @NotBlank
    @Max(32)
    private String orderNo;

    /**
     * 机构号
     * 签约方所属拉卡拉机构
     */
    @Schema(description = "机构号")
    @Max(32)
    private String orgCode;
}
