package com.wanmi.sbc.empower.bean.vo.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsCheckAccessInfoVO
 * @description 审核状态
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformProductInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 微信侧SPU ID
     */
    @Schema(description = "微信侧商品ID  可以不传")
    private String product_id;

    /**
     * 外部商品spuid
     */
    @NotEmpty
    @Schema(description = "外部商品spuid 自己的SPUID")
    private String out_product_id;

    /**
     * 微信侧SKU ID
     */
    @Schema(description = "商品skuid")
    private String sku_id;

    @NotEmpty
    @Schema(description = "外部商品SKUid 自己的SKUID")
    private String out_sku_id;

    @NotNull
    @Schema(description = "商品的购买数量")
    private Integer product_cnt;

    @NotNull
    @Schema(description = "生成订单时商品的售卖价（单位：分），可以跟上传商品接口的价格不一致")
    private Integer sale_price;

    @NotNull
    @Schema(description = "sku总实付价, 分为单位")
    private Integer sku_real_price;

    @NotEmpty
    @Schema(description = "生成订单时商品的标题")
    private String title;

    @NotEmpty
    @Schema(description = "生成订单时商品的头图")
    private String head_img;

    @NotEmpty
    @Schema(description = "绑定的小程序商品路径")
    private String path;

}