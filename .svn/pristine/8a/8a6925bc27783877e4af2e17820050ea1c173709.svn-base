package com.wanmi.sbc.marketing.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import lombok.Data;

import java.util.List;

@Data
public class CouponCacheCenterRequest extends BaseRequest {

    /**
     * 优惠券分类id
     */
    private String couponCateId;

    /**
     * 优惠券类型
     */
    private CouponType couponType;

    /**
     * 优惠券营销类型 0满减券 1满折券 2运费券
     */
    private CouponMarketingType couponMarketingType;

    /**
     * 用户id
     */
    private String customerId;

    /**
     * 分页页码
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    private Long storeId;

    /**
     * 优惠券类型 0 普通优惠券  2 O2O运费券
     */
    private PluginType pluginType;

    /**
     * 当前缓存门店id
     */
    private Long storeFrontId;

}
