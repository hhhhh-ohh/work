package com.wanmi.sbc.marketing.coupon.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.coupon.utils.CouponTypeAttributeConverter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * @Author: songhanlin
 * @Date: Created In 10:18 AM 2018/9/12
 * @Description: 优惠券信息
 */
@Entity
@Table(name = "coupon_info")
@Data
public class CouponInfo {

    /**
     * 优惠券主键Id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "coupon_id")
    private String couponId;

    /**
     * 优惠券名称
     */
    @Column(name = "coupon_name")
    private String couponName;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    @Column(name = "range_day_type")
    @Enumerated
    private RangeDayType rangeDayType;

    /**
     * 优惠券开始时间
     */
    @Column(name = "start_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 优惠券结束时间
     */
    @Column(name = "end_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 有效天数
     */
    @Column(name = "effective_days")
    private Integer effectiveDays;

    /**
     * 购满多少钱
     */
    @Column(name = "fullbuy_price")
    private BigDecimal fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    @Column(name = "fullbuy_type")
    @Enumerated
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    @Column(name = "denomination")
    private BigDecimal denomination;

    /**
     * 商户id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Column(name = "platform_flag")
    @Enumerated
    private DefaultFlag platformFlag;


    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    @Column(name = "participate_type")
    @Enumerated
    private ParticipateType participateType;

    /**
     * 营销类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Column(name = "scope_type")
    @Enumerated
    private ScopeType scopeType;

    /**
     * 优惠券说明
     */
    @Column(name = "coupon_desc")
    private String couponDesc;

    /**
     * 优惠券类型 0通用券 1店铺券
     */
    @Column(name = "coupon_type")
    @Convert(converter = CouponTypeAttributeConverter.class)
    private CouponType couponType;

    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    @Column(name = "coupon_marketing_type")
    @Enumerated
    private CouponMarketingType couponMarketingType;

    /**
     * 优惠券优惠方式 0减免 1包邮
     */
    @Column(name = "coupon_discount_mode")
    @Enumerated
    private CouponDiscountMode couponDiscountMode;

    /**
     * 最大优惠金额限制（结合满折券使用）
     */
    @Column(name = "max_discount_limit")
    private BigDecimal maxDiscountLimit;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Column(name = "update_person")
    private String updatePerson;

    /**
     * 删除时间
     */
    @Column(name = "del_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    /**
     * 删除人
     */
    @Column(name = "del_person")
    private String delPerson;



}
