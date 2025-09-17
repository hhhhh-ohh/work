package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

@Data
@Schema
public class PurchaseMergeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号", required = true)
    @NotBlank
    private String goodsInfoId;

    /**
     * 全局购买数
     */
    @Schema(description = "全局购买数", required = true)
    @NotNull
    @Min(1)
    private Long goodsNum;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;
}
