package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.marketing.bean.dto.GoodsInfoDetailByGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.MarketingPluginDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>商品详情处理请求结构</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class MarketingPluginGoodsDetailFilterRequest extends MarketingPluginDTO {

    private static final long serialVersionUID = 6869081083181095059L;

    @Schema(description = "商品详情")
    @NotNull
    private GoodsInfoDetailByGoodsInfoDTO goodsInfoDetailByGoodsInfoDTO;
}
