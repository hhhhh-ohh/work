package com.wanmi.sbc.marketing.bean.vo;

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
import java.time.LocalDateTime;

/**
 * <p>预约抢购VO</p>
 *
 * @author zxd
 * @date 2020-05-21 10:32:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleSimplifyVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 预约类型 0：不预约不可购买  1：不预约可购买
     */
    @Schema(description = "预约类型 0：不预约不可购买  1：不预约可购买")
    private Integer appointmentType;

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
     * 预约商品活动
     */
    @Schema(description = "预约活动商品信息")
    private AppointmentSaleGoodsSimplifyVO appointmentSaleGood;

}