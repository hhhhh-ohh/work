package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>预约抢购VO</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleGoodsVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 预约活动id
     */
    @Schema(description = "预约活动id")
    private Long appointmentSaleId;

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
     * 预约价
     */
    @Schema(description = "预约价")
    private BigDecimal price;

    /**
     * 预约数量
     */
    @Schema(description = "预约数量")
    private Integer appointmentCount;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    private Integer buyerCount;

    @Schema(description = "商品信息")
    private GoodsInfoVO goodsInfoVO;

    @Schema(description = "spu信息")
    private GoodsVO goodsVO;


    @Schema(description = "店铺名称")
    private String storeName;

    private String skuName;

    private String skuPic;

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
     * 预约开始时间
     */
    @Schema(description = "预约开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime appointmentStartTime;

    /**
     * 预约结束时间
     */
    @Schema(description = "预约结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime appointmentEndTime;

    /**
     * 抢购开始时间
     */
    @Schema(description = "抢购开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime snapUpStartTime;

    /**
     * 抢购结束时间
     */
    @Schema(description = "抢购结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime snapUpEndTime;

    /**
     * 划线价价
     */
    @Schema(description = "商品市场价")
    private BigDecimal linePrice;

}