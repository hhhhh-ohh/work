package com.wanmi.sbc.marketing.provider.impl.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponMarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCacheListRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponMarketingScopeByScopeIdRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCacheListResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponMarketingScopeByScopeIdResponse;
import com.wanmi.sbc.marketing.bean.vo.CouponCacheVO;
import com.wanmi.sbc.marketing.bean.vo.CouponMarketingScopeVO;
import com.wanmi.sbc.marketing.coupon.model.entity.cache.CouponCache;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.request.CouponCacheInitRequest;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityConfigService;
import com.wanmi.sbc.marketing.coupon.service.CouponCacheService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.coupon.service.CouponMarketingScopeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * <p>对优惠券商品作用范围查询接口</p>
 * Created by daiyitian on 2018-11-24-下午6:23.
 */
@Validated
@RestController
public class CouponMarketingScopeQueryController implements CouponMarketingScopeQueryProvider {

    @Autowired
    private CouponMarketingScopeService couponMarketingScopeService;

    @Autowired
    private CouponActivityConfigService couponActivityConfigService;

    @Autowired
    private CouponCacheService couponCacheService;

    @Autowired
    private CouponCodeService couponCodeService;


    /**
     * 根据优惠券范围id查询优惠券商品作用范列表
     *
     * @param request 包含优惠券范围id的查询请求结构 {@link CouponMarketingScopeByScopeIdRequest}
     * @return 优惠券商品作用范列表 {@link CouponMarketingScopeByScopeIdResponse}
     */
    @Override
    public BaseResponse<CouponMarketingScopeByScopeIdResponse> listByScopeId(@RequestBody @Valid
                                                                              CouponMarketingScopeByScopeIdRequest
                                                                              request){
        List<CouponMarketingScope> scopeList = couponMarketingScopeService.listScopeByScopeId(request.getScopeId());
        if(CollectionUtils.isEmpty(scopeList)){
            return BaseResponse.success(CouponMarketingScopeByScopeIdResponse.builder()
                    .scopeVOList(Collections.emptyList()).build());
        }
        return BaseResponse.success(CouponMarketingScopeByScopeIdResponse.builder()
                .scopeVOList(KsBeanUtil.convert(scopeList, CouponMarketingScopeVO.class)).build());
    }

    @Override
    public BaseResponse<CouponCacheListResponse> cachePage(CouponCacheListRequest request) {
        List<CouponCache> caches = couponCacheService.cachePage(KsBeanUtil.convert(request, CouponCacheInitRequest.class));
        if (CollectionUtils.isEmpty(caches)) {
            return BaseResponse.success(CouponCacheListResponse.builder()
                    .cacheList(Collections.emptyList()).build());
        }else {
            //有剩余但redis中不存在key的初始化
            caches.stream().filter(couponCache -> DefaultFlag.YES.equals(couponCache.getHasLeft())).forEach(couponCache -> {
                couponCodeService.initCouponLeftCount(couponCache.getCouponInfoId(),couponCache.getCouponActivityId(),couponCache.getTotalCount());
            });
        }
        return BaseResponse.success(CouponCacheListResponse.builder()
                .cacheList(KsBeanUtil.convert(caches, CouponCacheVO.class)).build());
    }
}