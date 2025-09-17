package com.wanmi.sbc.marketing.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分页查询优惠券列表请求结构
 * @author CHENLI
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class CouponCodePageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 2389168657805595252L;

    /**
     *  领取人id,同时表示领取状态
     */
    @Schema(description = "领取人id")
    private String customerId;

    /**
     *  使用状态,0(未使用)，1(使用)，2(已过期)
     */
    @Schema(description = "优惠券使用状态，0(未使用)，1(使用)，2(已过期)")
    private int useStatus;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券
     */
    @Schema(description = "优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券")
    private QueryCouponType queryCouponType;

    @Schema(description = "店铺Id", hidden = true)
    private Long storeId;

    @Schema(description = "店铺Ids", hidden = true)
    private List<Long> storeIds;


    @Schema(description = "获得优惠券-开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireStartTime;

    @Schema(description = "获得优惠券-结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireEndTime;

    /**
     * 是否pc端
     */
    private DefaultFlag defaultFlag;

    /**
     * 插件类型
     */
    private PluginType pluginType;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;


    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private CouponMarketingType couponMarketingType;


    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 模糊条件-优惠券名称
     */
    @Schema(description = "优惠券名称模糊条件查询")
    private String likeCouponName;

    /**
     * 营销类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "营销范围类型")
    private ScopeType scopeType;


    @Schema(description = "查询类型")
    private Integer couponStatus;

    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     * 批量优惠券id
     */
    @Schema(description = "优惠券id列表")
    private List<String> couponIds;


    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private List<CouponMarketingType> couponMarketingTypes;

    /**
     * 过期优惠券开始时间
     */
    @Schema(description = "过期优惠券-结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expireStartTime;

    /**
     * 过期优惠券结束时间
     */
    @Schema(description = "过期优惠券-结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expireEndTime;

    /**
     * 批量优惠券id
     */
    @Schema(description = "优惠券id列表")
    private List<String> couponCodeIds;

    /**
     * 优惠券状态列表
     */
    @Schema(description = "优惠券状态列表")
    private List<CouponStatus> couponStatusList;
}
