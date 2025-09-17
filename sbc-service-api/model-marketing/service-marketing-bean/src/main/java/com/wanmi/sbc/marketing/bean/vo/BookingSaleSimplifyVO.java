package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>预售信息VO</p>
 *
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@Data
public class BookingSaleSimplifyVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

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


    @Schema(description = "活动商品相关信息")
    private BookingSaleGoodsSimplifyVO bookingSaleGoods;

}