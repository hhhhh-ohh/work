package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.marketing.bean.enums.CardSaleState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className ElectronicGoodsVO
 * @description
 * @date 2022/1/27 3:48 下午
 **/
@Schema
@Data
public class ElectronicGoodsVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    /**
     * 商品类型
     */
    @Schema(description = "商品类型")
    private GoodsType goodsType;

    /**
     * SKU编码
     */
    @Schema(description = "SKU编码")
    private String skuNo;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String specName;

    /**
     * 分类
     */
    @Schema(description = "分类")
    private String cate;

    /**
     * 品牌
     */
    @Schema(description = "品牌")
    private String brand;

    /**
     * 单价
     */
    @Schema(description = "单价")
    private BigDecimal marketPrice;

    /**
     * 卡券名称
     */
    @Schema(description = "卡券名称")
    private String couponName;

    /**
     * 发送次数
     */
    @Schema(description = "发送次数")
    private Long sendNum;

    /**
     * 卡券销售状态
     */
    @Schema(description = "卡券销售状态")
    private CardSaleState cardSaleState;
}
