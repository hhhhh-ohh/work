package com.wanmi.ares.marketing.coupon.dao;

import com.wanmi.ares.marketing.coupon.model.CouponOverviewRecent;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponOverviewRecentMapper {

    CouponOverviewRecent selectByStoreId(Long storeId);

}