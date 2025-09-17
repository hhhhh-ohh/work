package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.QueryCouponType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 14:52
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCacheCenterPageRequest extends BaseRequest {

    /**
     * 优惠券分类id
     */
    @Schema(description = "优惠券分类id")
    private String couponCateId;

    /**
     * 优惠券类型
     */
    @Schema(description = "优惠券类型")
    private CouponType couponType;

    /**
     * 优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券
     */
    @Schema(description = "优惠券筛选类型 0：通用满减券 1：店铺满减券 2：店铺满折券 3：店铺运费券")
    private QueryCouponType queryCouponType;

    /**
     * 用户id
     */
    @Schema(description = "客户id")
    private String customerId;

    /**
     * 分页页码
     */
    @Schema(description = "分页页码")
    private int pageNum;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量")
    private int pageSize;

    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 优惠券类型 0 普通优惠券  2 O2O运费券
     */
    @Schema(description = "优惠券类型")
    private PluginType pluginType;

    /**
     * 当前缓存门店id
     */
    @Schema(description = "当前缓存门店id")
    private Long storeFrontId;

}
