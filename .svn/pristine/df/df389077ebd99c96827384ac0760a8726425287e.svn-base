package com.wanmi.sbc.customer.bean.dto;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员收货地址-共用查询DTO
 */
@Schema
@Data
public class CustomerDeliveryAddressDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID", hidden = true)
    private String customerId;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    @NotBlank
    private String consigneeName;

    /**
     * 收货人手机号码
     */
    @Schema(description = "收货人手机号码")
    @NotBlank
    private String consigneeNumber;

    /**
     * 省
     */
    @Schema(description = "省")
    @NotNull
    private Long provinceId;

    /**
     * 市
     */
    @Schema(description = "市")
    @NotNull
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    @NotNull
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    @NotBlank
    private String deliveryAddress;

    /**
     * 是否默认地址
     */
    @Schema(description = "是否默认地址")
    private DefaultFlag isDefaltAddress = DefaultFlag.NO;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal latitude;

    /**
     * 门牌号
     */
    @Schema(description = "门牌号")
    private String houseNum;
}
