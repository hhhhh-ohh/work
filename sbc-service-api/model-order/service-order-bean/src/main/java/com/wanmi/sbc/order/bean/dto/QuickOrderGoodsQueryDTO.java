package com.wanmi.sbc.order.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className QuickOrderGoodsQueryDTO
 * @description 快速下单搜索商品入参
 * @author edz
 * @date 2023/6/2 09:24
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuickOrderGoodsQueryDTO extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 3635486109211612000L;

    /**
     * 订货号
     */
    @Schema(description = "订货号")
    @NotBlank
    private String quickOrderNo;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long num;

}
