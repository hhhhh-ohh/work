package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发票信息
 * Created by jinwei on 20/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class InvoiceDTO implements Serializable {

    /**
     * 订单开票id
     */
    @Schema(description = "订单开票id")
    private String orderInvoiceId;

    /**
     * 类型 0：普通发票 1：增值税发票 -1：无
     */
    @Schema(description = "发票类型", contentSchema = com.wanmi.sbc.account.bean.enums.InvoiceType.class)
    private Integer type;

    /**
     * 普通发票与增票至少一项必传
     */
    @Schema(description = "普通发票与增票至少一项必传")
    private GeneralInvoiceDTO generalInvoice;

    /**
     * 增值税发票与普票至少一项必传
     */
    @Schema(description = "增值税发票与普票至少一项必传")
    private SpecialInvoiceDTO specialInvoice;

    /**
     * 收货地址ID
     */
    @Schema(description = "收货地址ID")
    private String addressId;

    /**
     * 是否单独的收货地址
     */
    @Schema(description = "是否单独的收货地址")
    private boolean sperator;

    /**
     * 发票的收货地址
     */
    @Schema(description = "发票的收货地址")
    private String address;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    private String contacts;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 收货地址的修改时间
     */
    @Schema(description = "收货地址的修改时间")
    private String updateTime;

    /**
     * 开票项目id
     */
    @Schema(description = "开票项目id")
    private String projectId;

    /**
     * 开票项目名称
     */
    @Schema(description = "开票项目名称")
    private String projectName;

    /**
     * 开票项修改时间
     */
    @Schema(description = "开票项修改时间")
    private String projectUpdateTime;

    /**
     * 省市区
     */
    @Schema(description = "省")
    private Long provinceId;

    @Schema(description = "市")
    private Long cityId;

    @Schema(description = "区")
    private Long areaId;

    /**
     * 纳税人识别码
     */
    @Schema(description = "纳税人识别码")
    private String taxNo;
}
