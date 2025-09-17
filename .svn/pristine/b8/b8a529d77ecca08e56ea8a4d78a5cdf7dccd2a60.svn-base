package com.wanmi.ares.marketing.coupon.dao;

import com.wanmi.ares.request.coupon.CouponEffectRequest;
import com.wanmi.ares.view.coupon.CouponInfoEffectView;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CouponEffectRecentMapper继承基类
 */
@Repository
public interface CouponInfoEffectRecentMapper extends EffectMapper<CouponInfoEffectView> {

    List<CouponInfoEffectView> selectListByActivityId(CouponEffectRequest request);

    Long couponInfoTotalCount(CouponEffectRequest request);
}