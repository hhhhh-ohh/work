package com.wanmi.sbc.marketing.coupon.repository;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.MarketingCustomerType;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 优惠券码数据源
 */
@Repository
public interface CouponCodeRepository extends JpaRepository<CouponCode, String>, JpaSpecificationExecutor<CouponCode> {

    /**
     * 查询优惠券已经使用多少
     *
     * @param couponId
     * @param activityId
     * @return
     */
    @Query("select count(couponCodeId) from CouponCode where couponId = ?1 and activityId = ?2")
    int countCouponUsed(String couponId, String activityId);

    /**
     * 根据CouponCodeId查询优惠券码
     *
     * @param couponCodeId
     * @return
     */
    @Query(" from CouponCode c where c.couponCodeId = ?1 and c.customerId = ?2 ")
    CouponCode findByCouponCodeId(String couponCodeId, String customerId);

    /**
     * 统计用户的券数量
     */
    Integer countByCustomerId(String customerId);

    /**
     * 根据customerId和activityId查询优惠券数量
     */
    Integer countByCustomerIdAndActivityId(String customerId, String activityId);

    /**
     * 查询用户领取某个活动券的信息
     * @param customerId
     * @param activityId
     * @return
     */
    List<CouponCode> findByCustomerIdAndActivityId(String customerId, String activityId);

    @Modifying
    @Query("update CouponCode c set c.useStatus = ?3 , c.orderCode = ?4 , c.useDate=now() ,  c.updateTime = now()  where c.couponCodeId = ?1 and c.customerId = ?2 ")
    Integer updateCouponCode(String couponCodeId, String customerId, DefaultFlag useStatus,String orderCode);

    @Modifying
    @Query("update CouponCode c set c.useStatus = com.wanmi.sbc.common.enums.DefaultFlag.NO , c.orderCode = null, c" +
            ".useDate = null, c.updateTime = now() where c.couponCodeId = ?1 and c.customerId = ?2 ")
    Integer returnCoupon(String couponCodeId, String customerId);

    @Modifying
    @Query("update CouponCode c set c.endTime = now() where c.customerId = ?1 ")
    Integer updateCouponCodeByCustomerId(String customerId);

    @Query("select count(couponCodeId) from CouponCode where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and marketingCustomerType = ?4 and customerId = ?1 and activityId = ?2 and acquireTime >= ?3")
    Integer countByCouponHasToday(String customerId, String activityId, LocalDateTime time, MarketingCustomerType customerType);

    @Modifying
    @Query("update CouponCode c set c.useStatus = com.wanmi.sbc.common.enums.DefaultFlag.NO , c.orderCode = null, c" +
            ".useDate = null, c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, c.updateTime = now() where c.couponCodeId = ?1 and c.customerId = ?2 ")
    void recycleCoupon(String couponCodeId, String customerId);

    @Modifying
    @Query("update CouponCode c set c.couponExpiredSendFlag = TRUE where c.couponCodeId = ?1 ")
    Integer updateCouponExpiredSendFlagById(String id);
}
