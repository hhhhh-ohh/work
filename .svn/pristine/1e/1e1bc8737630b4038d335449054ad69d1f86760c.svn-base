package com.wanmi.ares.marketing.coupon.dao;

import com.wanmi.ares.marketing.coupon.model.CouponOverviewWeek;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponOverviewWeekMapper {


    List<CouponOverviewWeek> selectByStoreId(@Param("storeId") Long storeId, @Param("startDate") String startDate);

}