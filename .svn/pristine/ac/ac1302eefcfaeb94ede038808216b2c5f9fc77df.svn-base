package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 商品SKU实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsInfoForGoodsSimpleVO extends BasicResponse {

    private static final long serialVersionUID = -7042706482503509905L;

    /**
     * 商品SKU编号
     */
    @Schema(description = "商品SKU编号")
    private String goodsInfoId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称")
    private String goodsInfoName;

    /**
     * 商品SKU编码
     */
    @Schema(description = "商品SKU编码")
    private String goodsInfoNo;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsInfoImg;

    /**
     * 商品条形码
     */
    @Schema(description = "商品条形码")
    private String goodsInfoBarcode;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 供货价
     */
    @Schema(description = "供货价")
    private BigDecimal supplyPrice;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态", contentSchema = com.wanmi.sbc.goods.bean.enums.AddedFlag.class)
    private Integer addedFlag;

    /**
     * 是否定时上架
     */
    @Schema(description = "是否定时上架 true:是,false:否")
    private Boolean addedTimingFlag;

    /**
     * 定时上架时间
     */
    @Schema(description = "定时上架时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime addedTimingTime;

    /**
     * 按客户单独定价
     */
    @Schema(description = "按客户单独定价", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer customFlag;

    /**
     * 是否可售
     */
    @Schema(description = "是否可售", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer vendibility;

    /**
     * 设价类型 0:客户,1:订货
     */
    @Schema(description = "设价类型", contentSchema = com.wanmi.sbc.goods.bean.enums.PriceType.class)
    private Integer priceType;

    /**
     * 规格名称规格值
     */
    @Schema(description = "规格名称规格值", example = "如红色 16G")
    private String specText;

    /**
     * 购买积分
     */
    @Schema(description = "购买积分")
    private Long buyPoint;

    /**
     * 所属供应商商品skuId
     */
    @Schema(description = "所属供应商商品skuId")
    private String providerGoodsInfoId;

    /**
     * 供应商店铺状态 0：关店 1：开店
     */
    @Schema(description = "供应商店铺状态 0：关店 1：开店")
    private Integer providerStatus;

    public Integer getVendibility(){
        if (Objects.nonNull(providerGoodsInfoId)) {
            //供应商商品可售（商品上架、未删除、已审核，店铺开店）
            if((Objects.nonNull(vendibility) && DefaultFlag.YES.toValue() == vendibility)
                    && Constants.yes.equals(providerStatus)){
                return Constants.yes;
            } else {
                return Constants.no;
            }
        }
        return Constants.yes;
    }
}