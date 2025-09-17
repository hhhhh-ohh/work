package com.wanmi.sbc.marketing.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingCustomerType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Schema
@Data
public class CouponCodeDTO {

    /**
     *  优惠券码id
     */
    @Schema(description = "优惠券码id")
    private String couponCodeId;

    /**
     *  优惠券码
     */
    @Schema(description = "优惠券券码")
    private String couponCode;

    /**
     * 优惠券Id
     */
    @Schema(description = "优惠券Id")
    private String couponId;

    /**
     *  优惠券活动id
     */
    @Schema(description = "优惠券活动id")
    private String activityId;

    /**
     *  领取人id,同时表示领取状态
     */
    @Schema(description = "领取人id")
    private String customerId;

    /**
     *  使用状态,0(未使用)，1(使用)
     */
    @Schema(description = "优惠券是否已使用")
    private DefaultFlag useStatus;

    /**
     *  获得优惠券时间
     */
    @Schema(description = "获得优惠券时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireTime;

    /**
     *  使用时间
     */
    @Schema(description = "使用时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime useDate;

    /**
     *  使用的订单号
     */
    @Schema(description = "使用的订单号")
    private String orderCode;

    /**
     *  开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     *  结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     *  是否删除标志 0：否，1：是
     */
    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     *  创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 营销会员类型 0、普通会员 1、付费会员
     */
    @Schema(description = "营销会员类型 0、普通会员 1、付费会员")
    private MarketingCustomerType marketingCustomerType;

    /**
     * 付费会员记录id
     */
    @Schema(description = "付费会员记录id")
    private String payingMemberRecordId;

    /**
     * 优惠券过期前24小时,是否发送订阅消息 false 否  true 是
     */
    @Schema(description = "优惠券过期前24小时,是否发送订阅消息 false 否  true 是")
    private Boolean couponExpiredSendFlag;
}
