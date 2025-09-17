package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenli
 * @Date: Created In 10:18 AM 2018/9/19
 * @Description: 查询我的优惠券信息
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeVO extends BasicResponse {

    private static final long serialVersionUID = 1864819276514535422L;

    /**
     * 优惠券码id
     */
    @Schema(description = "优惠券码id")
    private String couponCodeId;

    /**
     * 优惠券活动Id
     */
    @Schema(description = "优惠券活动Id")
    private String activityId;

    /**
     *  优惠券码
     */
    @Schema(description = "优惠券码")
    private String couponCode;

    /**
     *  使用状态,0(未使用)，1(使用)
     */
    @Schema(description = "优惠券是否已使用")
    private DefaultFlag useStatus;

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
     *  领取时间
     */
    @Schema(description = "领取时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireTime;

    /**
     * 优惠券Id
     */
    @Schema(description = "优惠券Id")
    private String couponId;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    @Schema(description = "购满类型")
    private FullBuyType fullBuyType;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    /**
     * 优惠券面值
     */
    @Schema(description = "优惠券面值")
    private BigDecimal denomination;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    @Schema(description = "优惠券营销类型 0满减券 1满折券 2运费券")
    private CouponMarketingType couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    @Schema(description = "优惠券优惠方式 0减免 1包邮")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Schema(description = "最大优惠金额限制")
    private BigDecimal maxDiscountLimit;

    /**
     *  优惠券创建时间
     */
    @Schema(description = "优惠券创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 营销类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "营销范围类型")
    private ScopeType scopeType;

    /**
     * 优惠券说明
     */
    @Schema(description = "优惠券说明")
    private String couponDesc;

    /**
     * 商户id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商户id
     */
    @Schema(description = "店铺ids")
    private List<Long> storeIds;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;


    /**
     * 是否即将过期 true 是 false 否
     */
    @Schema(description = "是否即将过期")
    private boolean nearOverdue;

    /**
     * 是否可以立即使用 true 是(立即使用) false(查看可用商品)
     */
    @Schema(description = "是否可以立即使用")
    private boolean couponCanUse;

    /**
     * 优惠券适用平台类目名称
     */
    @Schema(description = "优惠券适用平台类目名称集合")
    private List<String> goodsCateNames;

    /**
     * 优惠券适用店铺分类名称
     */
    @Schema(description = "优惠券适用店铺分类名称集合")
    private List<String> storeCateNames;

    /**
     * 优惠券适用品牌名称
     */
    @Schema(description = "优惠券适用品牌名称集合")
    private List<String> brandNames;

    /**
     * 优惠券码状态(使用优惠券页券码的状态)
     */
    @Schema(description = "使用优惠券码状态")
    private CouponCodeStatus status;

    /**
     * 参与成功通知标题
     */
    @Schema(description = "参与成功通知标题")
    private String activityTitle;

    /**
     * 参与成功通知描述
     */
    @Schema(description = "参与成功通知描述")
    private String activityDesc;

    /***
     * 优惠券插件类型
     */
    @Schema(description = "优惠券插件类型")
    private PluginType pluginType;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    @Schema(description = "门店营销类型(0,1) 0全部门店，1自定义门店")
    private ParticipateType participateType;

    /**
     *  领取人id
     */
    @Schema(description = "领取人id")
    private String customerId;

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

    /**
     * 优惠券状态
     */
    @Schema(description = "优惠券状态")
    private int couponStatus;

    /**
     * 生效天数
     */
    private Integer effectiveDays;

    /**
     * couponCodeIds
     */
    private List<String> couponCodeIds;

    /**
     * 指定商品-只有scopeName
     */
    @Schema(description = "关联的商品范围名称集合，如分类名、品牌名")
    private  List<String> scopeNames = new ArrayList<>();

    /**
     * 是否满系券（满减、满折）
     */
    public boolean isFullAmount() {
        return CouponMarketingType.REDUCTION_COUPON == couponMarketingType || CouponMarketingType.DISCOUNT_COUPON == couponMarketingType;
    }

    /**
     * 是否运费券
     */
    public boolean isFreight() {
        return CouponMarketingType.FREIGHT_COUPON == couponMarketingType;
    }

    /**
     * 判断是否为指定的 CouponListType
     */
    public boolean isCouponListType(BoolFlag couponListType) {
        if (BoolFlag.NO == couponListType) {
            // 满系券 =>（满减、满折）
            return this.isFullAmount();
        }  else if (BoolFlag.YES == couponListType) {
            // 运费券 => 运费券
            return this.isFreight();
        }
        return false;
    }

    /**
     * 判断是否为指定的 QueryCouponType
     */
    public boolean isQueryCouponType(QueryCouponType queryCouponType) {
        switch (queryCouponType) {
            case GENERAL_REDUCTION: {
                // 通用+满减券
                return couponType == CouponType.GENERAL_VOUCHERS && couponMarketingType == CouponMarketingType.REDUCTION_COUPON;
            }
            case STORE_REDUCTION: {
                // 店铺+满减券
                return couponType == CouponType.STORE_VOUCHERS && couponMarketingType == CouponMarketingType.REDUCTION_COUPON;
            }
            case STORE_DISCOUNT: {
                // 店铺+满折券
                return couponType == CouponType.STORE_VOUCHERS && couponMarketingType == CouponMarketingType.DISCOUNT_COUPON;
            }
            case STORE_FREIGHT: {
                // 店铺+运费券
                return couponType == CouponType.STORE_VOUCHERS && couponMarketingType == CouponMarketingType.FREIGHT_COUPON;
            }
            default: return false;
        }
    }
}
