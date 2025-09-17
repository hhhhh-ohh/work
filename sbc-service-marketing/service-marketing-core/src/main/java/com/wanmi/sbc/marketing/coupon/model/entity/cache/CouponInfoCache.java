package com.wanmi.sbc.marketing.coupon.model.entity.cache;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.common.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: hht
 * @Date: Created In 11:15 AM 2018/9/12
 * @Description: 优惠券缓存
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponInfoCache extends BaseBean {

    private static final long serialVersionUID = -6068538173458853808L;

    /**
     * 优惠券主键Id
     */
    private String couponId;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    private RangeDayType rangeDayType;

    /**
     * 优惠券开始时间
     */
    private LocalDateTime startTime;

    /**
     * 优惠券结束时间
     */
    private LocalDateTime endTime;

    /**
     * 有效天数
     */
    private Integer effectiveDays;

    /**
     * 购满多少钱
     */
    private Double fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    private Double denomination;

    /**
     * 商户id
     */
    private Long storeId;

    /***
     * O2O门店List（BOSS新增选择部分时）
     */
    private List<Long> o2oStoreList;

    /**
     * 是否平台优惠券 0平台 1店铺
     */
    private DefaultFlag platformFlag;

    /**
     * 营销类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    private ScopeType scopeType;

    /**
     * 优惠券说明
     */
    private String couponDesc;

    /**
     * 优惠券类型，用于分页排序
     */
    private Integer couponTypeInteger;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    private CouponType couponType;

    /**
     * 优惠券营销类型（0满减券 1满折券 2运费券）
     */
    private CouponMarketingType couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    private Double maxDiscountLimit;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    private ParticipateType participateType;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}
