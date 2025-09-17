package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class SellPlatformGoodsInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID
     */
    @Schema(description = "商家自定义商品ID")
    private String out_product_id;
    /**
     * 商家自定义skuID
     */
    @Schema(description = "商家自定义skuID")
    private String out_sku_id;

    /**
     * 交易组件平台自定义skuID
     */
    @Schema(description = "交易组件平台自定义skuID")
    private String sku_id;

    /**
     * sku小图
     */
    @Schema(description = "sku小图")
    @NotEmpty
    private String thumb_img;

    /**
     * 售卖价格，以分为单位，数字类型，最大不超过10000000（1000万元
     */
    @Min(1)
    @NotNull
    @Schema(description = "售卖价格，以分为单位，数字类型，最大不超过10000000（1000万元")
    private Integer sale_price;

    /**
     * 市场价格，以分为单位，数字类型，最大不超过10000000（1000万元）
     */
    @Min(1)
    @NotNull
    @Schema(description = "市场价格，以分为单位，数字类型，最大不超过10000000（1000万元）")
    private Integer market_price;

    @Min(1)
    @NotNull
    @Schema(description = "库存，数字类型，最大不超过10000000（1000万")
    private Integer stock_num;

    @Schema(description = "条形码")
    private String barcode;

    @Schema(description = "商品编码，字符类型，最长不超过20")
    private String sku_code;


    @Schema(description = "商品属性")
    @NotEmpty
    @Valid
    private List<SellPlatformGoodsInfoAttrVO> sku_attrs;

}