package com.wanmi.sbc.marketing.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 1:23 PM 2018/9/28
 * @Description: 查询优惠券码列表请求
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeQueryRequest extends BaseQueryRequest  {

    private static final long serialVersionUID = -1924077031934211666L;

    /**
     * 领取人id
     */
    @Schema(description = "领取人id")
    private String customerId;

    /**
     * 优惠券是否已使用,0(未使用)，1(使用)
     */
    @Schema(description = "优惠券是否已使用")
    private DefaultFlag useStatus;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券id")
    private String couponId;

    /**
     * 优惠券活动id
     */
    @Schema(description = "优惠券活动id")
    private String activityId;

    /**
     * 优惠券活动id
     */
    @Schema(description = "优惠券活动id")
    private List<String> activityIdList;

    /**
     * 查询未过期的券
     */
    @Schema(description = "查询未过期的券")
    private Boolean notExpire;

    /**
     * 查询已过期的券
     */
    @Schema(description = "查询已过期的券")
    private Boolean alrExpire;

    /**
     * 删除标记
     */
    @Schema(description = "是否已删除")
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 优惠券券码id集合
     */
    @Schema(description = "优惠券券码id集合")
    private List<String> couponCodeIds;

    /**
     * 优惠券id集合
     */
    @Schema(description = "优惠券id集合")
    private List<String> couponIds;

    /**
     * 优惠券活动id集合
     */
    @Schema(description = "优惠券活动id集合")
    private List<String> activityIds;

    @Schema(description = "获得优惠券-开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireStartTime;

    @Schema(description = "获得优惠券-结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireEndTime;

    @Schema(description = "优惠券使用到期时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

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

    /**
     * 优惠券状态列表
     */
    @Schema(description = "优惠券状态列表")
    private List<CouponStatus> couponStatusList;

    @Schema(description = "结束时间-开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endStartTime;

    @Schema(description = "结束时间-结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endEndTime;
}
