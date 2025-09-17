package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商家名称
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SupplierVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 商家编码
     */
    @Schema(description = "商家编码")
    private String supplierCode;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long supplierId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 使用的运费模板类别(0:店铺运费,1:单品运费)
     */
    @Schema(description = "使用的运费模板类别")
    private DefaultFlag freightTemplateType;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 代理人Id，用于代客下单
     */
    @Schema(description = "代理人Id")
    private String employeeId;

    /**
     * 代理人名称，用于代客下单，相当于OptUserName
     */
    @Schema(description = "代理人名称")
    private String employeeName;

    /**
     * 是否平台自营
     */
    @Schema(description = "是否平台自营",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean isSelf;

    /**
     * 店铺类型
     */
    @Schema(description = "店铺类型 0：供应商，1：商家，2：o2o直营店")
    private StoreType storeType;
}
