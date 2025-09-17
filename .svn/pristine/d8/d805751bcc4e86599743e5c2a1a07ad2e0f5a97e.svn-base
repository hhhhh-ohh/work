package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordAddDTO
 * @description
 * @date 2022/2/11 10:50 上午
 **/
@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordAddDTO {

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    private String orderNo;

    /**
     * sku编码
     */
    @Schema(description = "sku编码")
    private String skuNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    @NotBlank
    private String account;

    /**
     * 卡券id
     */
    @Schema(description = "卡券id")
    @NotNull
    private Long couponId;

    /**
     * 发放数量
     */
    @Schema(description = "发放数量")
    @Min(1)
    private Long num;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;
}
