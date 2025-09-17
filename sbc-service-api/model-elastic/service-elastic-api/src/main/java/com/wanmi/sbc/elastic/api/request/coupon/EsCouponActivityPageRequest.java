package com.wanmi.sbc.elastic.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.elastic.api.request.base.EsBaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class EsCouponActivityPageRequest extends EsBaseQueryRequest {

    private static final long serialVersionUID = 4243718077145628609L;

    @Schema(description = "活动idList")
    private List<String> couponActivityIdList;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "店铺Ids")
    private List<Long> storeIds;

    /**
     * 优惠券活动名称
     */
    @Schema(description = "优惠券活动名称")
    private String activityName;


    /**
     * 优惠券活动类型
     */
    @Schema(description = "优惠券活动类型")
    private CouponActivityType couponActivityType;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 目标客户 -1:全部客户 0:全部等级 other:其他等级 ,
     */
    @Schema(description = "关联的客户等级", contentSchema = com.wanmi.sbc.marketing.bean.enums.MarketingJoinLevel.class)
    private String joinLevel;

    @Schema(description = "查询类型")
    private MarketingStatus queryTab;

    /**
     * 活动所属平台（目前只有门店用到）
     */
    @Schema(description = "活动所属平台")
    private PluginType pluginType;

    /**
     * 只查询所有商家活动
     */
    @Schema(description = "是否只查询所有商家活动")
    private DefaultFlag supplierFlag = DefaultFlag.NO;

    /**
     * boss端查询客户等级
     */
    @Schema(description = "boss端查询客户等级")
    private Long bossJoinLevel;

    /**
     * 只查询所有商家活动
     */
    @Schema(description = "是否只查询权益、分销、积分")
    private Boolean couponLimit = false;
}
