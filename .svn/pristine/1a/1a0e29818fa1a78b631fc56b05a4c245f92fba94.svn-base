package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.FlashSaleGoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>抢购商品表VO</p>
 *
 * @author xufeng
 * @date 2022-02-15 14:54:31
 */
@Schema
@Data
public class FlashPromotionActivityVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 删除标志，0:未删除 1:已删除
     */
    @Schema(description = "删除标志，0:未删除 1:已删除")
    private DeleteFlag delFlag;

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

    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 活动名称
     */
    @Schema(description = "activityId")
    private String activityId;

    /**
     * 活动名称
     */
    @Schema(description = "activityName")
    private String activityName;

    /**
     * 活动开始时间
     */
    @Schema(description = "startTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "endTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 状态 0:开始 1:暂停
     */
    @Schema(description = "status")
    private Integer status;

    /**
     * 预热时间
     */
    @Schema(description = "预热时间")
    private Integer preTime;

    /**
     * 抢购商品数
     */
    @Schema(description = "抢购商品数")
    private Integer goodsNum;

    /**
     * 秒杀商品状态
     */
    @Schema(description = "秒杀商品状态")
    private FlashSaleGoodsStatus flashSaleGoodsStatus;

}