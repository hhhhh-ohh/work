package com.wanmi.sbc.empower.bean.dto.channel.base;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 渠道商品查询库存查询请求
 * @author daiyitian
 * @date 2021/5/10 17:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelGoodsBuyNumDTO implements Serializable {
    private static final long serialVersionUID = 7361562139884111206L;

    @NotBlank
    @Schema(description = "商品编号")
    private String thirdSkuId;

    @Schema(description = "商品SPU编号")
    private String thirdSpuId;

    @Schema(description = "购买数量")
    private Integer buyNum;
}
