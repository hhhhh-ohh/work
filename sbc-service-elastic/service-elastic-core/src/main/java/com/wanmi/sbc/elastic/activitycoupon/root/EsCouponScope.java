package com.wanmi.sbc.elastic.activitycoupon.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Joiner;
import com.wanmi.sbc.common.enums.AuditState;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.marketing.bean.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠券关联范围ES文档
 */
@Document(indexName = EsConstants.DOC_COUPON_SCOPE_TYPE)
@Data
public class EsCouponScope {


    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 范围id
     */
    private String scopeId;

    /**
     * 营销范围类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    private ScopeType scopeType;

    /**
     * 商家id
     */
    private Long storeId;

    /**
     * 优惠券id
     */
    private String couponId;


    /**
     * 优惠券活动id
     */
    private String activityId;

    /**
     * 是否平台等级 （1平台（自营店铺属于平台等级） 0店铺）
     */
    private DefaultFlag joinLevelType;

    /**
     * 关联的客户等级  -2指定客户 -1:全部客户 0:全部等级 other:其他等级 ,
     */
    private List<String> joinLevels;

    /**
     * 活动状态
     */
    private DefaultFlag pauseFlag;

    /**
     * 活动所属平台 (0 商家  1 平台  2 门店)
     */
    private PluginType pluginType;

    /**
     *  是否审核
     */
    private AuditState auditState;


    /**
     * 优惠券开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityStartTime;

    /**
     * 优惠券结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityEndTime;

    /**
     * 优惠券名称
     */
    private String couponName;

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
     * 是否平台优惠券 1平台 0店铺
     */
    private DefaultFlag platformFlag;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    private ParticipateType participateType;

    /**
     * 优惠券面值
     */
    private BigDecimal denomination;

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
     * 购满类型 0：无门槛，1：满N元可使用
     */
    private FullBuyType fullBuyType;

    /**
     * 购满多少钱
     */
    private BigDecimal fullBuyPrice;

    /**
     * 门店促销范围Ids
     */
    private List<Long> storeIds;


    /**
     *  关联分类id集合
     */
    private List<String> couponCateIds;


    /**
     * 优惠券创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 是否有剩余, 1 有，0 没有
     */
    @Schema(description = "活动优惠券是否有剩余")
    @Enumerated
    private DefaultFlag hasLeft;

    /**
     * 总数量
     */
    @Schema(description = "总数量")
    private Long totalCount;

    public String getJoinLevel() {
        return CollectionUtils.isNotEmpty(joinLevels) ? Joiner.on(",").skipNulls().join(joinLevels) : "";
    }

}
