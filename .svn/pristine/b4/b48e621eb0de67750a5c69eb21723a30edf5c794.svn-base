package com.wanmi.ares.marketing.coupon.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName CouponScheduled
 * @Description
 * @Author zhanggaolei
 * @Date 2021/1/11 9:36
 * @Version 1.0
 **/
@Repository
public interface CouponOverViewMapper {

    void deleteDay(@Param("statDate") String statDate);

    void deleteWeek(@Param("beginTime") String beginTime);

    void deleteRecent();

    void saveBossDay(@Param("statDate") String statDate, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    void saveBossWeek(@Param("statDate") String statDate, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    void saveBossRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    void saveSupplierDay(@Param("statDate") String statDate, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    void saveSupplierWeek(@Param("statDate") String statDate, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    void saveSupplierRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
