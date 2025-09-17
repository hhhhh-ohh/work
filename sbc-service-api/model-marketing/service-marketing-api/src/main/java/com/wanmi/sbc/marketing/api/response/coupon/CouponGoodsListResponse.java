package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 16:08
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponGoodsListResponse extends BasicResponse {

    /**
     *  是否全部商品
     */
    @Schema(description = "是否全部商品")
    private DefaultFlag isAll;

    /**
     *  商品Id集合
     */
    @Schema(description = "商品Id集合")
    private List<String> goodsInfoId;

    /**
     *  品牌Id集合
     */
    @Schema(description = "品牌Id集合")
    private List<Long> brandIds;

    /**
     *  品牌Id集合(过滤之后的)
     */
    @Schema(description = "品牌Id集合(过滤之后的)")
    private List<Long> queryBrandIds;

    /**
     *  店铺分类Id集合
     */
    @Schema(description = "店铺分类Id集合")
    private List<Long> storeCateIds;

    /**
     *  平台分类Id集合
     */
    @Schema(description = "平台分类Id集合")
    private List<Long> cateIds;

    /**
     *  平台分类Id集合--es查询商品范围
     */
    @Schema(description = "平台分类Id集合--es查询商品范围")
    private List<Long> cateIds4es;

    /**
     *  优惠券开始时间
     */
    @Schema(description = "优惠券开始时间")
    private String startTime;

    /**
     *  优惠券结束时间
     */
    @Schema(description = "优惠券结束时间")
    private String endTime;

    /**
     *  优惠券作用范围类型
     */
    @Schema(description = "优惠券作用范围类型")
    private ScopeType scopeType;

    /**
     *  是否平台优惠券
     */
    @Schema(description = "是否平台优惠券")
    private DefaultFlag platformFlag;

    /**
     *  店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

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
     * 平台类目名称
     */
    @Schema(description = "平台类目名称map<key为平台类目id，value为平台类目名称>")
    private Map<Long, String> cateMap;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称map<key为品牌id，value为品牌名称>")
    private Map<Long, String> brandMap;

    /**
     * 店铺分类名称
     */
    @Schema(description = "店铺分类名称map<key为店铺分类id，value为店铺分类名称>")
    private Map<Long, String> storeCateMap;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 优惠券类型 3 门店券
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
    @Schema(description = "优惠券营销类型 0满减券 1满折券 2运费券")
    private CouponDiscountMode couponDiscountMode;

    /**
     * 起止时间类型 0：按起止时间，1：按N天有效
     */
    @Schema(description = "起止时间类型 0：按起止时间，1：按N天有效")
    private RangeDayType rangeDayType;

    /**
     * 有效天数
     */
    @Schema(description = "有效天数")
    private Integer effectiveDays;

    /**
     * 店铺集合
     */
    private List<Long> storeIds;

    /**
     * 店铺名称
     */
    private Map<Long, String> storeMap;

}
