package com.wanmi.sbc.setting.api.response.businessconfig;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by feitingting on 2019/11/21.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessConfigRopResponse extends BasicResponse {
    /**
     * 招商页设置主键
     */
    @Schema(description = "已新增的招商页设置信息")
    private Long businessConfigId;

    /**
     * 招商页banner
     */
    @Schema(description = "已新增的招商页设置信息")
    private String businessBanner;

    /**
     * 招商页自定义
     */
    @Schema(description = "已新增的招商页设置信息")
    private String businessCustom;

    /**
     * 招商页注册协议
     */
    @Schema(description = "已新增的招商页设置信息")
    private String businessRegister;

    /**
     * 招商页入驻协议
     */
    @Schema(description = "已新增的招商页设置信息")
    private String businessEnter;

    /**
     * 招商页banner
     */
    @Schema(description = "商家的招商页设置信息")
    private String supplierBanner;

    /**
     * 招商页自定义
     */
    @Schema(description = "商家的招商页设置信息")
    private String supplierCustom;

    /**
     * 招商页注册协议
     */
    @Schema(description = "商家的招商页设置信息")
    private String supplierRegister;

    /**
     * 招商页入驻协议
     */
    @Schema(description = "商家的招商页设置信息")
    private String supplierEnter;

    /**
     * 跨境商家招商页入驻协议
     */
    @Schema(description = "跨境商家招商页入驻协议")
    private String crossSupplierEnter;
}
