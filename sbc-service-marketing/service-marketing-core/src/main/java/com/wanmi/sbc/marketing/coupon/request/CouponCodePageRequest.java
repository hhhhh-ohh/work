package com.wanmi.sbc.marketing.coupon.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivity;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author CHENLI
 */
@Data
public class CouponCodePageRequest extends BaseQueryRequest {
    /**
     *  领取人id,同时表示领取状态
     */
    protected String customerId;

    /**
     *  使用状态,0(未使用)，1(使用)，2(已过期)
     */
    protected int useStatus;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    protected CouponType couponType;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private CouponMarketingType couponMarketingType;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

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
    private int couponStatus;

    /**
     * 查询开始时间，精确到天
     */
    @Schema(description = "查询开始时间，精确到天")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireStartTime;

    /**
     * 查询结束时间，精确到天
     */
    @Schema(description = "查询结束时间，精确到天")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireEndTime;


    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     * 批量优惠券id
     */
    @Schema(description = "优惠券id列表")
    private List<String> couponIds;

    /**
     * 领取后的优惠券ids
     */
    @Schema(description = "领取后的优惠券ids")
    private List<String> couponCodeIds;

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
     * 活动类型
     */
    protected List<CouponActivityType> couponActivityTypes;

    protected Long storeId;

    /**
     * 是否pc端
     */
    protected DefaultFlag defaultFlag;

    /**
     * 插件类型
     */
    protected PluginType pluginType;

    /**
     * 优惠券状态列表
     */
    @Schema(description = "优惠券状态列表")
    private List<CouponStatus> couponStatusList;

    public static CouponCodeVO converter(CouponCode couponCode, CouponInfo couponInfo, CouponActivity couponActivity) {
        return CouponCodeVO.builder()
                .couponCodeId(couponCode.getCouponCodeId())
                .couponCode(couponCode.getCouponCode())
                .useStatus(couponCode.getUseStatus())
                .useDate(couponCode.getUseDate())
                .orderCode(couponCode.getOrderCode())
                .startTime(couponCode.getStartTime())
                .endTime(couponCode.getEndTime())
                .activityId(couponCode.getActivityId())
                .couponId(couponCode.getCouponId())
                .couponName(couponInfo.getCouponName())
                .fullBuyType(couponInfo.getFullBuyType())
                .fullBuyPrice(couponInfo.getFullBuyPrice())
                .denomination(couponInfo.getDenomination())
                .platformFlag(couponInfo.getPlatformFlag())
                .couponType(couponInfo.getCouponType())
                .createTime(couponInfo.getCreateTime())
                .scopeType(couponInfo.getScopeType())
                .couponDesc(couponInfo.getCouponDesc())
                .couponMarketingType(couponInfo.getCouponMarketingType())
                .storeId(couponActivity.getStoreId())
                .activityTitle(couponActivity.getActivityTitle())
                .activityDesc(couponActivity.getActivityDesc())
                .couponStatus(getCouponStatus(couponCode))
                .acquireTime(couponCode.getAcquireTime())
                .couponDiscountMode(couponInfo.getCouponDiscountMode())
                .build();
    }

    public static CouponCodeVO converter(CouponCode couponCode, CouponInfo couponInfo) {
        return CouponCodeVO.builder()
                .couponCodeId(couponCode.getCouponCodeId())
                .couponCode(couponCode.getCouponCode())
                .useStatus(couponCode.getUseStatus())
                .useDate(couponCode.getUseDate())
                .orderCode(couponCode.getOrderCode())
                .startTime(couponCode.getStartTime())
                .endTime(couponCode.getEndTime())
                .activityId(couponCode.getActivityId())
                .couponId(couponCode.getCouponId())
                .couponName(couponInfo.getCouponName())
                .fullBuyType(couponInfo.getFullBuyType())
                .fullBuyPrice(couponInfo.getFullBuyPrice())
                .denomination(couponInfo.getDenomination())
                .platformFlag(couponInfo.getPlatformFlag())
                .couponType(couponInfo.getCouponType())
                .createTime(couponInfo.getCreateTime())
                .scopeType(couponInfo.getScopeType())
                .couponDesc(couponInfo.getCouponDesc())
                .storeId(couponInfo.getStoreId())
                .couponMarketingType(couponInfo.getCouponMarketingType())
                .couponStatus(getCouponStatus(couponCode))
                .acquireTime(couponCode.getAcquireTime())
                .couponDiscountMode(couponInfo.getCouponDiscountMode())
                .build();
    }

    /**
     * 查询对象转换
     * @param sqlResult
     * @return
     */
    public static List<CouponCodeVO> converter(List<Map<String, Object>> sqlResult) {
        return sqlResult.stream().map(item ->
            CouponCodeVO.builder()
                .couponCodeId(toStr(item, "couponCodeId"))
                .couponCode(toStr(item, "couponCode"))
                .useStatus(toInteger(item, "useStatus") != null ? (toInteger(item, "useStatus") == 0 ? DefaultFlag.NO : DefaultFlag.YES) : null)
                .useDate(toDate(item, "useDate"))
                .orderCode(toStr(item, "orderCode"))
                .startTime(toDate(item, "startTime"))
                .endTime(toDate(item, "endTime"))
                .activityId(toStr(item, "activityId"))
                .couponId(toStr(item, "couponId"))
                .couponName(toStr(item, "couponName"))
                .fullBuyType(toInteger(item, "fullBuyType") != null ? FullBuyType.fromValue(toInteger(item, "fullBuyType")) : null)
                .fullBuyPrice(toBigDecimal(item, "fullBuyPrice"))
                .denomination(toBigDecimal(item, "denomination"))
                .platformFlag(toInteger(item, "platformFlag") != null ? (toInteger(item, "platformFlag") == 0 ? DefaultFlag.NO : DefaultFlag.YES) : null)
                .couponType(toInteger(item, "couponType") != null ? CouponType.fromValue(toInteger(item, "couponType")) : null)
                .couponMarketingType(toInteger(item, "couponMarketingType") != null ? CouponMarketingType.fromValue(toInteger(item, "couponMarketingType")) : null)
                .couponDiscountMode(toInteger(item, "couponDiscountMode") != null ? CouponDiscountMode.fromValue(toInteger(item, "couponDiscountMode")) : null)
                .maxDiscountLimit(toBigDecimal(item, "maxDiscountLimit"))
                .createTime(toDate(item, "createTime"))
                .scopeType(toInteger(item, "scopeType") != null ? ScopeType.fromValue(toInteger(item, "scopeType")) : null)
                .couponDesc(toStr(item, "couponDesc"))
                .storeId(toLong(item, "storeId"))
                .storeName(toStr(item, "storeName"))
                .activityTitle(toStr(item, "activityTitle"))
                .activityDesc(toStr(item, "activityDesc"))
                .participateType(toInteger(item,"participateType") != null ? ParticipateType.fromValue(toInteger(item,"participateType")) : null)
                .couponStatus(toDate(item, "startTime").compareTo(LocalDateTime.now()) < 0 ? 1 : 2 )
                .acquireTime(toDate(item, "acquireTime"))
                .build()
        ).collect(Collectors.toList());
    }

    private static int getCouponStatus(CouponCode couponCode) {
        int couponStatus = 0;
        LocalDateTime now = LocalDateTime.now();
        if(couponCode != null) {
            if(now.isAfter(couponCode.getStartTime()) && (now.isEqual(couponCode.getEndTime()) || now.isBefore(couponCode.getEndTime()))) {
                couponStatus = 1;
            } else if(now.isBefore(couponCode.getStartTime()) || now.isEqual(couponCode.getStartTime())) {
                couponStatus = 2;
            }
        }
        return couponStatus;
    }

    private static String toStr(Map<String, Object> map, String key) {
        return map.get(key) != null ? map.get(key).toString() : null;
    }

    private static Long toLong(Map<String, Object> map, String key) {
        return map.get(key) != null ? Long.valueOf(map.get(key).toString()) : null;
    }

    private static Integer toInteger(Map<String, Object> map, String key) {
        return map.get(key) != null ? Integer.valueOf(map.get(key).toString()) : null;
    }

    private static LocalDateTime toDate(Map<String, Object> map, String key) {
        return map.get(key) != null ? DateUtil.parse(map.get(key).toString(), "yyyy-MM-dd HH:mm:ss.S") : null;
    }

    private static BigDecimal toBigDecimal(Map<String, Object> map, String key) {
        return map.get(key) != null ? new BigDecimal(map.get(key).toString()) : null;
    }
}
