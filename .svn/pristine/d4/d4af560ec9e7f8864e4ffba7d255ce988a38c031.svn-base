package com.wanmi.sbc.empower.bean.dto.channel.vop;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 请求查询运费明细
 * @author daiyitian
 * @date 2021/5/10 17:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class VopFreightSkuDTO implements Serializable {
    private static final long serialVersionUID = 7361562139884111206L;

    @NotBlank
    @Schema(description = "商品编号")
    private String skuId;

    @NotNull
    @Schema(description = "数量")
    private Integer num;
}
