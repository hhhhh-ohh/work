package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>预约抢购DTO</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleGoodsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 预约id
     */
    @Schema(description = "预约id")
    private Long appointmentSaleId;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long storeId;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    @NotBlank
    private String goodsInfoId;

    /**
     * spuID
     */
    @Schema(description = "spuID")
    @NotBlank
    private String goodsId;

    /**
     * 预约价
     */
    @Schema(description = "预约价")
    private BigDecimal price;

    /**
     * 预约数量
     */
    @Schema(description = "预约数量")
    @NotNull
    private Integer appointmentCount;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @NotNull
    private Integer buyerCount;

    @Schema(description = "sku", hidden = true)
    private GoodsInfoVO goodsInfoVO;

    /**
     * stock
     */
    @Schema(description = "stock")
    private Integer stock;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "goodsType")
    private Integer goodsType;

}