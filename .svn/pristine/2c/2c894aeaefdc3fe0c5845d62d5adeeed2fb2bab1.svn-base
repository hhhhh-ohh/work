package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 15:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheVO extends BasicResponse {


    private static final long serialVersionUID = 1500111404233787182L;
    /**
     * 优惠券主键Id
     */
    @Schema(description = "优惠券主键Id")
    private String id;

    /**
     * 优惠券活动配置表id
     */
    @Schema(description = "优惠券活动配置表id")
    private String activityConfigId;

    /**
     * 优惠券是否有剩余
     */
    @Schema(description = "优惠券是否有剩余")
    private DefaultFlag hasLeft;

    /**
     * 优惠券总张数
     */
    @Schema(description = "优惠券总张数")
    private Long totalCount;

    /**
     * 优惠券活动Id
     */
    @Schema(description = "优惠券活动Id")
    private String couponActivityId;

    /**
     * 优惠券Id
     */
    @Schema(description = "优惠券Id")
    private String couponInfoId;

    /**
     * 优惠券关联 商品/分类/品牌 ids
     */
    @Schema(description = "优惠券商品作用范围列表")
    private List<CouponMarketingScopeVO> scopes;

    /**
     * 优惠券缓存
     */
    @Schema(description = "优惠券信息缓存")
    private CouponInfoCacheVO couponInfo;

    /**
     * 优惠券活动缓存
     */
    @Schema(description = "优惠券活动缓存")
    private CouponActivityCacheVO couponActivity;

    /**
     * 当前优惠券分类Id
     */
    @Schema(description = "当前优惠券分类Id集合")
    private List<String> couponCateIds;

    /**
     * 当前优惠券的可用门店
     */
    private List<Long> couponStoreIds;

}
