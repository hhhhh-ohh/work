package com.wanmi.sbc.vas.bean.vo.sellplatform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wur
 * @className WxChannelsGoodsInfoNoAuditVO
 * @description 商品免审信息
 * @date 2022/4/19 9:2
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellPlatformGoodsInfoNoAuditVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义skuID
     */
    @Schema(description = "商品属性项（自定义），字符类型，最长不超过40")
    @NotEmpty
    private String out_sku_id;

    /**
     * 售卖价格，以分为单位，数字类型，最大不超过10000000（1000万元
     */
    @Schema(description = "售卖价格，以分为单位")
    private Integer sale_price;

    /**
     * 市场价格，以分为单位，数字类型，最大不超过10000000（1000万元）
     */
    @Schema(description = "市场价格，以分为单位")
    private Integer market_price;

    /**
     * 库存，数字类型，最大不超过10000000（1000万
     */
    @Schema(description = "库存，数字类型，最大不超过10000000（1000万")
    private Integer stock_num;

    /**
     *  条形码
     */
    @Schema(description = "条形码")
    private String barcode;

    /**
     * 商品编码，字符类型，最长不超过20
     */
    @Schema(description = "商品编码，字符类型，最长不超过20")
    private String sku_code;

}
