package com.wanmi.sbc.marketing.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponActivityPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 4243718077145628609L;


    @Schema(description = "店铺id")
    private Long storeId;

    /**
     *优惠券活动名称
     */
    @Schema(description = "优惠券活动名称")
    private String activityName;


    /**
     * 优惠券活动类型
     */
    @Schema(description = "优惠券活动类型")
    private CouponActivityType couponActivityType;

    /**
     *开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     *结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     *目标客户 -1:全部客户 0:全部等级 other:其他等级 ,
     */
    @Schema(description = "关联的客户等级", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    private String joinLevel;

    @Schema(description = "查询类型")
    private MarketingStatus queryTab;

}
