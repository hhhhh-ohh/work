package com.wanmi.sbc.elastic.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.EsInitRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponStatus;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 初始化优惠券活动范围
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsCouponScopeInitRequest extends EsInitRequest {

    private static final long serialVersionUID = -4485444157498437822L;

    /**
     * 优惠券活动id集合
     */
    private List<String> couponActivityIds;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime queryStartTime;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime queryEndTime;


    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime createTimeStart;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime createTimeEnd;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime updateTimeStart;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime updateTimeEnd;

    /***
     * 是否O2O
     */
    private Boolean isO2o;
}
