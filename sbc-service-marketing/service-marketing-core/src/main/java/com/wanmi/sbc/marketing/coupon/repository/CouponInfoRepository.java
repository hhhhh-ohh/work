package com.wanmi.sbc.marketing.coupon.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: songhanlin
 * @Date: Created In 11:39 AM 2018/9/12
 * @Description: 优惠券信息Repository
 */
@Repository
public interface CouponInfoRepository extends JpaRepository<CouponInfo, String>, JpaSpecificationExecutor<CouponInfo> {

    @Modifying
    @Query("from CouponInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.couponId in ?1")
    List<CouponInfo> queryByIds(List<String> ids);

    Optional<CouponInfo> findByCouponIdAndStoreIdAndDelFlag(String couponId, Long storeId, DeleteFlag deleteFlag);

    @Modifying
    @Query("update CouponInfo a set a.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,a.delTime = now() ,a.delPerson = ?2 where a.couponId = ?1")
    int deleteCoupon(String id, String operatorId);

    @Query(" select w.couponId from CouponInfo w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> listByPage(Pageable pageable);

    @Query(value = "select distinct t2.* from coupon_activity_config t1 "
                            + "inner join coupon_info t2 on t1.coupon_id=t2.coupon_id "
                            + "where t1.activity_id in (?1) and t1.coupon_id in (?2) ",
            nativeQuery = true)
    List<CouponInfo> findMagicCoupons(List<String> activityIds, List<String> couponIds);

    @Query(value = "select distinct t2.* from coupon_activity_config t1 "
            + "inner join coupon_info t2 on t1.coupon_id=t2.coupon_id "
            + "where t1.activity_id = ?1 and t1.coupon_id = ?2 ",
            nativeQuery = true)
    CouponInfo findCouponsByActivityId(String activityId, String couponId);
}
