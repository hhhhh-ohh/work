package com.wanmi.sbc.empower.bean.vo.sellplatform.returnorder;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className WxChannelsReturnOrderProductInfoVO
 * @description 退单商品信息
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformReturnOrderProductInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 微信侧SPU ID
     */
    @Schema(description = "微信侧商品ID  可以不传")
    private String product_id;

    /**
     * 外部商品spuid
     */
    @Schema(description = "外部商品spuid 自己的SPUID")
    private String out_product_id;

    /**
     * 微信侧SKU ID
     */
    @Schema(description = "商品skuid")
    private String sku_id;

    @Schema(description = "外部商品SKUid 自己的SKUID")
    private String out_sku_id;

    @NotNull
    @Schema(description = "售后数量")
    private Integer product_cnt;

}