package com.wanmi.ares.marketing.coupon.dao;

import com.wanmi.ares.marketing.coupon.model.CouponOverviewDay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponOverviewDayMapper {


    List<CouponOverviewDay> selectByStoreId(@Param("storeId") Long storeId, @Param("startDate") String startDate);


}