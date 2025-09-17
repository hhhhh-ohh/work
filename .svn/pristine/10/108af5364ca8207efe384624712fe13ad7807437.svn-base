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
 * <p>拼团活动商品信息表entity</p>
 *
 * @author chenli
 * @date 2019-05-21 14:49:12
 */

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsVO extends BasicResponse {
    private static final long serialVersionUID = -7055329782656413104L;

    /**
     * 拼团活动ID
     */
    @Schema(description = "拼团活动ID")
    private String grouponActivityId;

    /**
     * SPU编号
     */
    @Schema(description = "SPU编号")
    private String goodsId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * spu商品名称
     */
    @Schema(description = "spu商品名称")
    private String goodsName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsImg;

    /**
     * 商品Sku图片
     */
    @Schema(description = "商品Sku图片")
    private String goodsInfoImg;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 拼团价格
     */
    @Schema(description = "拼团价格")
    private BigDecimal grouponPrice;

    /**
     * 拼团人数
     */
    @Schema(description = "拼团人数")
    private Integer grouponNum;

    /**
     * 已成团人数
     */
    @Schema(description = "已成团人数")
    private Integer alreadyGrouponNum;

    /**
     * 拼团订单数量
     */
    @Schema(description = "拼团订单数量")
    private Integer orderSalesNum;

    /**
     * 商品规格
     */
    @Schema(description = "商品规格")
    private String specText;

    /**
     * 活动开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "活动开始时间")
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "活动结束时间")
    private LocalDateTime endTime;

}