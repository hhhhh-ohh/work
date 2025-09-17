package com.wanmi.sbc.elastic.coupon.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.marketing.bean.enums.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券ES文档
 */
@Document(indexName = EsConstants.DOC_COUPON_INFO_TYPE)
@Data
public class EsCouponInfo {


    /**
     * 优惠券主键Id
     */
    @Id
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
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 优惠券结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 有效天数
     */
    private Integer effectiveDays;

    /**
     * 购满多少钱
     */
    private BigDecimal fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    private BigDecimal denomination;

    /**
     * 商户id
     */
    private Long storeId;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    private DefaultFlag platformFlag;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    private ParticipateType participateType;

    /**
     * 门店促销范围Ids
     */
    private List<Long> storeIds;


    /**
     * 营销范围类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    private ScopeType scopeType;

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
    private BigDecimal maxDiscountLimit;

    /**
     * 是否删除标志 0：否，1：是
     */
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


    /**
     *  关联分类id集合
     */
    private List<String> cateIds;

    /**
     * 促销范围Ids
     */
    private List<String> scopeIds = new ArrayList<>();

    @Transient
    private String activityId;


}
