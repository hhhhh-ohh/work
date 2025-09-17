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
public interface CouponEffectMapper {

    void deleteActivityRecent();

    void deleteCouponRecent();

    void deleteStoreRecent();

    void saveBossCouponRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime,@Param("typeId")String typeId,@Param("statType")Integer statType);

    void saveBossActivityRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("typeId")String typeId,@Param("statType")Integer statType);

    void saveSupplierCouponRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime,@Param("typeId")String typeId,@Param("statType")Integer statType);

    void saveSupplierActivityRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime,@Param("typeId")String typeId,@Param("statType")Integer statType);

    void saveCouponByRecent(@Param("statType")Integer statType);

    void saveActivityByRecent(@Param("statType")Integer statType);

    void saveStoreRecent(@Param("beginTime") String beginTime, @Param("endTime") String endTime,@Param("statType")Integer statType);

    void saveStoreRecentByOverview(@Param("statType")Integer statType);

    void saveStoreByRecent(@Param("statType")Integer statType);

}
