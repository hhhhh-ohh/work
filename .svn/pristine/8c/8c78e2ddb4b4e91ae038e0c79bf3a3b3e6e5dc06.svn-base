package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;
import com.wanmi.sbc.goods.bean.enums.SaleType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>营销商品调价详情VO</p>
 * Created by of628-wenzhi on 2020-12-16-11:20 上午.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarketingPriceAdjustDetailVO extends BasicResponse {
    private static final long serialVersionUID = -5799884186710226025L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    private String priceAdjustmentNo;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsInfoImg;

    /**
     * SKU编码
     */
    @Schema(description = "SKU编码")
    private String goodsInfoNo;

    /**
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String goodsInfoId;

    /**
     * 商品规格
     */
    @Schema(description = "商品规格")
    private String goodsSpecText;

    /**
     * 销售类别(0:批发,1:零售)
     */
    @Schema(description = "销售类别(0:批发,1:零售)")
    private SaleType saleType;

    /**
     * 设价方式,0:按客户(等级)1:按订货量(阶梯价)2:按市场价
     */
    @Schema(description = "设价类型,0:按客户(等级)1:按订货量(阶梯价)2:按市场价")
    private GoodsPriceType priceType;

    /**
     * 原市场价
     */
    @Schema(description = "原市场价")
    private BigDecimal originalMarketPrice;

    /**
     * 调整后市场价
     */
    @Schema(description = "调整后市场价")
    private BigDecimal adjustedMarketPrice;

    /**
     * 差异
     */
    @Schema(description = "差异")
    private BigDecimal priceDifference;

    /**
     * 执行结果：0 未执行、1 执行成功、2 执行失败
     */
    @Schema(description = "执行结果：0 未执行、1 执行成功、2 执行失败")
    private PriceAdjustmentResult adjustResult;

    /**
     * 失败原因
     */
    @Schema(description = "失败原因")
    private String failReason;

    /**
     * 是否确认：0 未确认、1 已确认
     */
    @Schema(description = "是否确认：0 未确认、1 已确认")
    private DefaultFlag confirmFlag;

}
