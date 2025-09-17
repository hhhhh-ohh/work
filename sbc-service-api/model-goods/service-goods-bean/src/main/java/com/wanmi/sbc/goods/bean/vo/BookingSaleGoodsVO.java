package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>预售商品信息VO</p>
 *
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleGoodsVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 预售id
     */
    @Schema(description = "预售id")
    private Long bookingSaleId;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long storeId;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * spuID
     */
    @Schema(description = "spuID")
    private String goodsId;

    /**
     * 定金
     */
    @Schema(description = "定金")
    private BigDecimal handSelPrice;

    /**
     * 膨胀价格
     */
    @Schema(description = "膨胀价格")
    private BigDecimal inflationPrice;

    /**
     * 预售价
     */
    @Schema(description = "预售价")
    private BigDecimal bookingPrice;

    /**
     * 预售数量
     */
    @Schema(description = "预售数量")
    private Integer bookingCount;

    /**
     * 实际可售数量
     */
    @Schema(description = "实际可售数量")
    private Integer canBookingCount;

    /**
     * 定金支付数量
     */
    @Schema(description = "定金支付数量")
    private Integer handSelCount;

    /**
     * 尾款支付数量
     */
    @Schema(description = "尾款支付数量")
    private Integer tailCount;

    /**
     * 全款支付数量
     */
    @Schema(description = "全款支付数量")
    private Integer payCount;

    @Schema(description = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    @Schema(description = "spu信息")
    private GoodsVO goodsVO;

    private String skuName;

    private String skuPic;

    @Schema(description = "店铺名称")
    private String storeName;


    /**
     * 商品规格
     */
    @Schema(description = "商品规格")
    private String specText;

    /**
     * spu商品名称
     */
    @Schema(description = "spu商品名称")
    private String goodsName;

    /**
     * 商品spu图片
     */
    @Schema(description = "商品spu图片")
    private String goodsImg;

    /**
     * 商品sku图片
     */
    @Schema(description = "商品sku图片")
    private String goodsInfoImg;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 划线价
     */
    @Schema(description = "划线价")
    private BigDecimal linePrice;


    /**
     * 预售类型 0：全款预售  1：定金预售
     */
    @Schema(description = "预售类型 0：全款预售  1：定金预售")
    private Integer bookingType;

    /**
     * 定金支付开始时间
     */
    @Schema(description = "定金支付开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime handSelStartTime;

    /**
     * 定金支付结束时间
     */
    @Schema(description = "定金支付结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime handSelEndTime;

    /**
     * 尾款支付开始时间
     */
    @Schema(description = "尾款支付开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tailStartTime;

    /**
     * 尾款支付结束时间
     */
    @Schema(description = "尾款支付结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tailEndTime;

    /**
     * 预售开始时间
     */
    @Schema(description = "预售开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime bookingStartTime;

    /**
     * 预售结束时间
     */
    @Schema(description = "预售结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime bookingEndTime;

    /**
     * 服务器时间
     */
    @Schema(description = "服务器时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime serverTime;

}