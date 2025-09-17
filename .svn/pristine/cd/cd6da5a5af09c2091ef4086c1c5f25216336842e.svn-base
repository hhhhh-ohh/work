package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className SellPlatformProductInfoVO
 * @description 订单商品信息
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformProductInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 外部商品spuid
     */
    @Schema(description = "外部商品spuid 自己的SPUID")
    private String out_product_id;


    @Schema(description = "外部商品SKUid 自己的SKUID")
    private String out_sku_id;

    @Schema(description = "商品的购买数量")
    private Integer product_cnt;

    @Schema(description = "生成订单时商品的售卖价（单位：分），可以跟上传商品接口的价格不一致")
    private Integer sale_price;

    @Schema(description = "sku总实付价, 分为单位")
    private Integer sku_real_price;

}