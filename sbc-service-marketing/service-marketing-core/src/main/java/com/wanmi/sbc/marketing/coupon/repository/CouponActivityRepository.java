package com.wanmi.sbc.marketing.coupon.repository;

import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 11:39 AM 2018/9/12
 * @Description: 优惠券活动Repository
 */
@Repository
public interface CouponActivityRepository extends JpaRepository<CouponActivity, String>,
        JpaSpecificationExecutor<CouponActivity> {

    @Modifying
    @Query("update CouponActivity a set a.pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO where a.activityId = ?1")
    int startActivity(String id);

    @Modifying
    @Query("update CouponActivity a set a.pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.YES where a.pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO and a.activityId = ?1")
    int pauseActivity(String id);

    @Modifying
    @Query("update CouponActivity a set a.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES ,a.delTime = now() ,a.delPerson = ?2 where a.activityId = ?1")
    int deleteActivity(String id, String operatorId);

    @Query("from CouponActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and startTime < now() and endTime > now()")
    List<CouponActivity> getLastActivity(Pageable pageable);


    /**
     * 查询指定时间内有多少活动
     * @param statTime
     * @param endTime
     * @param type
     * @param storeId
     * @return
     */
    @Query("from CouponActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and startTime <= ?2 and endTime >= ?1 and couponActivityType = ?3 and storeId = ?4 and (pluginType <> 2 or pluginType is null)")
    List<CouponActivity> queryActivityByTime(LocalDateTime statTime, LocalDateTime endTime, CouponActivityType type, Long storeId);

    /**
     * 通过活动类型查询正在进行中的活动,并且活动优惠券组数>0
     * @param type
     * @return
     */
    @Query("from CouponActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponActivityType = ?1 and storeId = ?2 and leftGroupNum > 0 and pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO and startTime < now() and endTime > now()")
    List<CouponActivity> queryGoingActivityByType(CouponActivityType type,Long storeId);

    @Query("from CouponActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponActivityType = ?1 " +
            "and storeId = ?2 and leftGroupNum >= 0 and pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO and startTime < now() and endTime > now()")
    List<CouponActivity> queryActivityByType(CouponActivityType type,Long storeId);

    /**
     * 领取一组优惠券
     * @param activityId
     * @return
     */
    @Modifying
    @Query("update CouponActivity a set a.leftGroupNum = a.leftGroupNum - 1 where a.activityId = ?1 and leftGroupNum " +
            "> 0 and a.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO ")
    int getCouponGroup(String activityId);

    /**
     * 查询活动（注册赠券活动、进店赠券活动）不可用的时间范围
     * @param type
     * @param storeId
     * @return
     */
    @Query("from CouponActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO  and couponActivityType = ?1 and storeId =?2 and endTime>= now() and pluginType <> 2 order by startTime ")
    List<CouponActivity> queryActivityDisableTime(CouponActivityType type , Long storeId);

    /**
     * 查询分销邀新赠券活动
     * @return
     */
    @Query("from CouponActivity where couponActivityType = 5")
    CouponActivity findDistributeCouponActivity();

    @Query(" select w.activityId from CouponActivity w where w.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and w.businessSource = 0")
    List<String> listByPage(Pageable pageable);

    /**
     * @description 关闭活动
     * @author  xuyunpeng
     * @date 2021/6/23 10:14 上午
     * @param id
     * @param operatorId
     * @return
     */
    @Modifying
    @Query("update CouponActivity set endTime = now(), updatePerson = ?2, updateTime = now() where activityId = ?1")
    void closeActivity(String id, String operatorId);

    /**
     * @description 查询距离开始指定时间范围内的数据
     * @author  edz
     * @date: 2021/9/9 17:44
     * @param minute
     * @return java.util.List<java.lang.String>
     **/
    @Query(value = "select activity_id, start_time, store_id, scan_version from coupon_activity where del_flag = 0 " +
            "and activity_type = 1 and (scan_type != 1 or scan_type is null ) and pause_flag" +
            " = 0 and DATE_SUB(start_time,INTERVAL ?1 minute) <= now() and start_time >= now()", nativeQuery = true )
    List<Object> getActivityByStartTime(int minute);

    @Query(value = "update coupon_activity set scan_type = ?2 where activity_id = ?1", nativeQuery = true)
    @Modifying
    int updateForSanType(String activityId, Integer scanType);

    @Query("from CouponActivity where drawActivityId = ?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    CouponActivity getActivityByDrawActivityId(Long drawActivityId);

    @Modifying
    @Query("update CouponActivity a set a.pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.NO where a.drawActivityId = ?1")
    int startActivityByDrawActivityId(Long id);

    @Modifying
    @Query("update CouponActivity a set a.pauseFlag = com.wanmi.sbc.common.enums.DefaultFlag.YES where a.pauseFlag = " +
            "com.wanmi.sbc.common.enums.DefaultFlag.NO and a.drawActivityId = ?1")
    int pauseActivityByDrawActivityId(Long id);


    /**
     * 查询新人购赠券活动
     * @return
     */
    @Query("from CouponActivity where couponActivityType = 9")
    CouponActivity findNewCustomerCouponActivity();

    @Query(value = "update CouponActivity set startTime =?1, endTime = ?2 where couponActivityType = 9")
    @Modifying
    void modifyNewCustomerCouponActivity(LocalDateTime startTime,LocalDateTime endTime);

    /**
     * 查询新人专享券活动
     * @return
     */
    @Query(value = "from CouponActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and couponActivityType = 9")
    CouponActivity findNewComerActivity();

    @Modifying
    @Query("update CouponActivity a set a.endTime = now(), a.updateTime = now() where a.drawActivityId = ?1")
    int closeActivityByDrawActivityId(Long id);
}
