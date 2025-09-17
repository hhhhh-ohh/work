package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.AuditStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午11:03 2019/5/16
 * @Description: 拼团活动商品信息
 */
@Data
public class GrouponGoodsInfoForAddDTO implements Serializable {

    private static final long serialVersionUID = 2827949497232636258L;

    /**
     * 单品id
     */
    @Schema(description = "单品id")
    @NotBlank
    private String goodsInfoId;

    /**
     * 拼团价格
     */
    @Schema(description = "拼团价格")
    @NotNull
    private BigDecimal grouponPrice;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    private Integer startSellingNum;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Integer limitSellingNum;

    /**
     * 拼团活动id
     */
    private String grouponActivityId;

    /**
     * 拼团分类id
     */
    private String grouponCateId;

    /**
     * 店铺id
     */
    private String storeId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 活动开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 活动审核状态，0：待审核，1：审核通过，2：审核不通过
     */
    private AuditStatus auditStatus;

    /**
     * 预热开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime preStartTime;


}
