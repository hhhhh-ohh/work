package com.wanmi.sbc.marketing.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xuyunpeng
 * @className ElectronicCardDTO
 * @description 卡密
 * @date 2022/2/3 11:33 下午
 **/
@Schema
@Data
public class ElectronicCardDTO {
    /**
     * 卡密Id
     */
    @Schema(description = "卡密Id")
    private String id;

    /**
     * 卡券id
     */
    @Schema(description = "卡券id")
    private Long couponId;

    /**
     * 卡号
     */
    @Schema(description = "卡号")
    private String cardNumber;

    /**
     * 卡密
     */
    @Schema(description = "卡密")
    private String cardPassword;

    /**
     * 优惠码
     */
    @Schema(description = "优惠码")
    private String cardPromoCode;

    /**
     * 卡密状态  0、未发送 1、已发送 2、已失效
     */
    @Schema(description = "卡密状态  0、未发送 1、已发送 2、已失效")
    private Integer cardState;

    /**
     * 卡密销售开始时间
     */
    @Schema(description = "卡密销售开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime saleStartTime;

    /**
     * 卡密销售结束时间
     */
    @Schema(description = "卡密销售结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime saleEndTime;

    /**
     * 批次id
     */
    @Schema(description = "批次id")
    private String recordId;

    /**
     * 是否删除 0、未删除 1、已删除
     */
    @Schema(description = "是否删除 0、未删除 1、已删除")
    private DeleteFlag delFlag;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;
}
