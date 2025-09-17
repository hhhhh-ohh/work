package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponCateSortDTO implements Serializable {

    private static final long serialVersionUID = -5743632183671026100L;

    /**
     * 优惠券分类Id
     */
    @Schema(description = "优惠券分类Id")
    @NotBlank
    private String couponCateId;

    /**
     * 优惠券排序顺序
     */
    @Schema(description = "优惠券排序顺序")
    @NotNull
    private Integer cateSort;
}
