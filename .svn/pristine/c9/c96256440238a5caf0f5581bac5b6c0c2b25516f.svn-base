package com.wanmi.sbc.elastic.bean.dto.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 10:18 AM 2018/9/12
 * @Description: 优惠券信息
 */
@Schema
@Data
public class EsCouponInfoDTO  {


    /**
     * 优惠券主键Id
     */
    @Schema(description = "优惠券主键Id")
    private String couponId;

    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    @Schema(description = "起止时间类型")
    private RangeDayType rangeDayType;

    /**
     * 优惠券开始时间
     */
    @Schema(description = "优惠券开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 优惠券结束时间
     */
    @Schema(description = "优惠券结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 有效天数
     */
    @Schema(description = "有效天数")
    private Integer effectiveDays;

    /**
     * 购满多少钱
     */
    @Schema(description = "购满多少钱")
    private BigDecimal fullBuyPrice;

    /**
     * 购满类型 0：无门槛，1：满N元可使用
     */
    @Schema(description = "购满类型")
    private FullBuyType fullBuyType;

    /**
     * 优惠券面值
     */
    @Schema(description = "优惠券面值")
    private BigDecimal denomination;

    /**
     * 商户id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 是否平台优惠券 1平台 0店铺
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

    /**
     * 门店营销类型(0,1) 0全部门店，1自定义门店
     */
    @Schema(description = "门店营销范围类型")
    private ParticipateType participateType;

    /**
     * 门店促销范围Ids
     */
    @Schema(description = "门店促销范围Id列表")
    private List<Long> storeIds;


    /**
     * 营销范围类型(0,1,2,3,4) 0全部商品，1品牌，2平台(boss)类目,3店铺分类，4自定义货品（店铺可用）
     */
    @Schema(description = "优惠券营销范围")
    private ScopeType scopeType;

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
     * 是否删除标志 0：否，1：是
     */
    @Schema(description = "是否已删除")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 促销范围Ids
     */
    @Schema(description = "优惠券关联的商品范围id集合(可以为0(全部)，brand_id(品牌id)，cate_id(分类id), goods_info_id(货品id))")
    private List<String> scopeIds = new ArrayList<>();

    /**
     * 优惠券分类Ids
     */
    @Schema(description = "优惠券分类Id集合")
    private List<String> cateIds = new ArrayList<>();


}
