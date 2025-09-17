package com.wanmi.ares.report.goods.model.root;

import lombok.*;

import java.math.BigDecimal;

/**
 * 商品日报表信息
 * Created by daiyitian on 2017/9/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SkuReport extends GoodsReport {

    /**
     * SKU独立访客的下单笔数
     */
    private Long customerCount = 0L;

    /**
     * SKU独立访客的付款笔数
     */
    private Long payCustomerCount = 0L;

    /**
     * SKU浏览量
     */
    private Long viewNum = 0L;

    /**
     * 访问人数
     */
    private Long totalUv = 0L;

    /**
     * 浏览量
     */
    private Long totalPv = 0L;

    /**
     * 单品转换率
     */
    private BigDecimal orderConversion = BigDecimal.ZERO;

    /**
     * 付款转换率
     */
    private BigDecimal payConversion = BigDecimal.ZERO;

    /**
     * 商品名称
     */
    private String name;

    /**
     * sku编码
     */
    private String skuNo;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;
}
