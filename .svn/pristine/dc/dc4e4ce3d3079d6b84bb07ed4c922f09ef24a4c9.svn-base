package com.wanmi.sbc.marketing.coupon.repository;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivityConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优惠券活动配置表
 */
@Repository
public interface CouponActivityConfigRepository extends JpaRepository<CouponActivityConfig, String>, JpaSpecificationExecutor<CouponActivityConfig> {

    /**
     * 根据优惠券id获取活动配置信息
     *
     * @param couponIds 优惠券id
     * @return
     */
    @Query("from CouponActivityConfig c where c.couponId in ?1")
    List<CouponActivityConfig> findByCouponIds(List<String> couponIds);

    /**
     * 根据优惠券id获取活动配置信息
     *
     * @param couponId 优惠券id
     * @return
     */
    List<CouponActivityConfig> findByCouponId(String couponId);


    /**
     * 根据活动id获取活动配置信息
     *
     * @param activityId 活动id
     * @return
     */
    List<CouponActivityConfig> findByActivityId(String activityId);

    /**
     * 根据活动id获取活动配置信息
     *
     * @param activityIds 活动id
     * @return
     */
    List<CouponActivityConfig> findByActivityIdIn(List<String> activityIds);

    /**
     * 根据小程序二维码scene获取活动配置信息
     *
     * @param scene 小程序二维码scene
     * @return
     */
    List<CouponActivityConfig> findByScene(String scene);

    /**
     * 删除活动关联的优惠券信息
     *
     * @param activityId
     */
    int deleteByActivityId(String activityId);

    /**
     * 根据活动id和优惠券id，查询具体规则
     *
     * @return
     */
    CouponActivityConfig findByActivityIdAndCouponId(String activityId, String couponId);

    /**
     * 根据活动id集合和优惠券id集合查询
     * @param activityIds
     * @param couponIds
     * @return
     */
    List<CouponActivityConfig> findByActivityIdInAndCouponIdIn(List<String> activityIds, List<String> couponIds);


    /**
     * 根据活动id和优惠券id，查询具体规则
     *
     * @return
     */
    long countByActivityIdAndCouponId(String activityId, String couponId);


    /**
     * 更新hasLeft
     * @param activityId
     * @param couponId
     * @param hasLeft
     */
    @Modifying
    @Query("UPDATE CouponActivityConfig c SET c.hasLeft = ?3 WHERE c.activityId = ?1 AND c.couponId = ?2")
    void updateHasLeft(String activityId, String couponId, DefaultFlag hasLeft);

    /**
     * 新人购活动id更新
     * @param activityId
     */
    @Modifying
    @Query("UPDATE CouponActivityConfig c SET c.activityId = ?1 WHERE c.activityId = '-999' ")
    void updateActivityId(String activityId);
}
