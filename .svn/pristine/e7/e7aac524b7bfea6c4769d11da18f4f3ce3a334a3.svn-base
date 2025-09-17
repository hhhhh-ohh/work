package com.wanmi.sbc.elastic.api.request.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.elastic.api.request.base.EsBaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponStatus;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品库查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsCouponInfoPageRequest extends EsBaseQueryRequest implements Serializable {

    private static final long serialVersionUID = -4485444157498437822L;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

    /**
     * 优惠券类型 0通用券 1店铺券 3门店券
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private CouponMarketingType couponMarketingType;

    /**
     * 商户id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商户ids
     */
    @Schema(description = "店铺ids")
    private List<Long> storeIds;

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
    private CouponStatus couponStatus;

    /**
     * 是否只查询有效的优惠券（优惠券活动选择有效的优惠券）
     */
    @Schema(description = "是否只查询有效的优惠券")
    private DefaultFlag isMarketingChose;

    /**
     * 查询开始时间，精确到天
     */
    @Schema(description = "查询开始时间，精确到天")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 查询结束时间，精确到天
     */
    @Schema(description = "查询结束时间，精确到天")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;


    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     * 批量优惠券id
     */
    @Schema(description = "优惠券id列表")
    private List<String> couponIds;

    /**
     * 查询supplier端优惠券
     */
    @Schema(description = "查询supplier端优惠券")
    private DefaultFlag supplierFlag = DefaultFlag.NO;

    /**
     * 优惠券类型集合
     */
    @Schema(description = "优惠券类型集合")
    private List<CouponType> couponTypes;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private List<CouponMarketingType> couponMarketingTypes;

    /**
     * 是否查询门店券
     */
    @Schema(description = "是否查询门店券")
    private PluginType pluginType;
}
