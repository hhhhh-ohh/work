package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>抢购商品表修改参数</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsModifyRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 活动日期：2019-06-11
     */
    @Schema(description = "活动日期：2019-06-11")
    private String activityDate;

    /**
     * 活动时间：13:00
     */
    @Schema(description = "活动时间：13:00")
    private String activityTime;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    @NotBlank
    private String goodsInfoId;

    /**
     * spuID
     */
    @Schema(description = "spuID")
    @NotBlank
    private String goodsId;


    /**
     * 抢购价
     */
    @Schema(description = "抢购价")
    @NotNull
    private BigDecimal price;

    /**
     * 上限数量
     */
    @Schema(description = "上限数量")
    @NotNull
    private Integer stock;

    /**
     * 增加上限数量
     */
    @Schema(description = "增加上限数量")
    private Integer increaseStock;

    /**
     * 抢购销量
     */
    @Schema(description = "抢购销量")
    @NotNull
    private Long salesVolume;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long cateId;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Integer maxNum;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    @NotNull
    private Integer minNum;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 包邮标志，0：不包邮 1:包邮
     */
    @Schema(description = "包邮标志，0：不包邮 1:包邮")
    @NotNull
    private Integer postage;

    /**
     * 删除标志，0:未删除 1:已删除
     */
    @Schema(description = "删除标志，0:未删除 1:已删除")
    private DeleteFlag delFlag;

    /**
     * 活动日期+时间
     */
    @Schema(description = "活动日期+时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityFullTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 类型 1:限时购 0:秒杀
     */
    @Schema(description = "类型 1:限时购 0:秒杀")
    private Integer type;


    @Override
    public void checkParam() {
        if (maxNum>stock){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (minNum>maxNum){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}