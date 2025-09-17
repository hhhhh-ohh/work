package com.wanmi.sbc.goods.info.model.entity;

import lombok.Data;
import java.math.BigDecimal;


/**
 * @Author: gaomuwei
 * @Date: Created In 上午10:53 2019/5/20
 * @Description: 单品参数对象
 */
@Data
public class GoodsInfoParams {

    public GoodsInfoParams(String goodsInfoId, String goodsInfoNo, String goodsInfoName, BigDecimal marketPrice, Integer goodsType) {
        this.goodsInfoId = goodsInfoId;
        this.goodsInfoNo = goodsInfoNo;
        this.goodsInfoName = goodsInfoName;
        this.marketPrice = marketPrice;
        this.goodsType = goodsType;
    }

    /**
     * 单品id
     */
    private String goodsInfoId;

    /**
     * SKU编码
     */
    private String goodsInfoNo;

    /**
     * 单品名称
     */
    private String goodsInfoName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 规格名称
     */
    private String specText;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;

}
