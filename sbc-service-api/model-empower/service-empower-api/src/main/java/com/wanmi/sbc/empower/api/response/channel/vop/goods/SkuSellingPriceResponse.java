package com.wanmi.sbc.empower.api.response.channel.vop.goods;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: wangtao
 * @Date: 2020-3-1 13:49:55
 * @Description: 商品售卖价请求响应参数
 *
 */
@Data
public class SkuSellingPriceResponse implements Serializable {

    private static final long serialVersionUID = 3323989821355708654L;
    /**
     * sku编号
     */
    private String skuId;

    /**
     * 京东价（参考价）
     */
    private BigDecimal jdPrice;

    /**
     * 京东销售价
     */
    private BigDecimal price;

    /**
     * 京东的前台划线价
     */
    private BigDecimal marketPrice;

    /**
     * 税率
     */
    private BigDecimal tax;

    /**
     * 税额
     */
    private BigDecimal taxPrice;

    /**
     * 未税价
     */
    private BigDecimal nakedPrice;

    /**
     * 建议零售价，由商城系统计算得出
     */
    private BigDecimal salePrice;
}
