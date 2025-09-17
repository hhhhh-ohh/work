package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * <p>砍价商品新增参数</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainGoodsInfoForAddDTO {
    private static final long serialVersionUID = 1L;

    /**
     * goodsInfoId
     */
    @Schema(description = "goodsInfoId")
    @NotBlank
    @Length(max = 32)
    private String goodsInfoId;

    /**
     * 市场价
     */
    @Schema(description = "市场价")
    @NotNull
    private BigDecimal marketPrice;

    /**
     * 帮砍金额
     */
    @Schema(description = "帮砍金额")
    @NotNull
    private BigDecimal bargainPrice;

    /**
     * 帮砍人数
     */
    @Schema(description = "帮砍人数")
    @NotNull
    @Max(99)
    @Min(1)
    private Integer targetJoinNum;

    /**
     * 砍价库存
     */
    @Schema(description = "砍价库存")
    @NotNull
    @Min(1)
    @Max(9999999999L)
    private Long bargainStock;

    /**
     * 新用户权重
     */
    @Schema(description = "新用户权重")
    @NotNull
    @Min(0)
    @Max(10L)
    private Double newUserWeight;

    /**
     * 新用户权重
     */
    @Schema(description = "老用户权重")
    @NotNull
    @Min(0)
    @Max(10L)
    private Double oldUserWeight;
}