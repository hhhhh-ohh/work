package com.wanmi.sbc.marketing.drawactivity.repository;

import com.wanmi.sbc.marketing.drawactivity.model.root.DrawActivity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>抽奖活动表DAO</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Repository
public interface DrawActivityRepository extends JpaRepository<DrawActivity, Long>,
        JpaSpecificationExecutor<DrawActivity> {


    /**
     * 单个修改抽奖活动表
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set activityName = :#{#activity.activityName}, startTime =:#{#activity.startTime}" +
            ",endTime = :#{#activity.endTime},formType = :#{#activity.formType},drawType = :#{#activity.drawType}" +
            ",consumePoints = :#{#activity.consumePoints},drawTimesType = :#{#activity.drawTimesType}," +
            "drawTimes = :#{#activity.drawTimes},winTimesType = :#{#activity.winTimesType},winTimes = " +
            ":#{#activity.winTimes},joinLevel = :#{#activity.joinLevel},notAwardTip = :#{#activity.notAwardTip}," +
            "maxAwardTip = :#{#activity.maxAwardTip} " +
            ",activityContent = :#{#activity.activityContent} ,updateTime = now() ,updatePerson = " +
            ":#{#activity.updatePerson} where id = :#{#activity.id}")
    int update(@Param("activity") DrawActivity drawActivity);




    /**
     * 单个删除抽奖活动表
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 单个删除抽奖活动表
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void updateActivity(Long id);

    /**
     * 批量删除抽奖活动表
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    int deleteByIdList(List<Long> idList);


    /**
     * 暂停或者开始抽奖活动
     * @param id
     * @return
     */
    @Modifying
    @Query("update DrawActivity set pauseFlag = :pauseFlag where id = :id")
    int pauseOrStartDrawActivity(@Param("id") Long id, @Param("pauseFlag") Integer pauseFlag);


    /**
     * 添加抽奖次数
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set drawCount = drawCount + 1 where id = ?1")
    void addDrawCount(Long id);

    /**
     * 添加中奖次数
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set awardCount = awardCount + 1 where id = ?1")
    void addAwardCount(Long id);

    /**
     * 关闭抽奖活动
     * @author wwc
     */
    @Modifying
    @Query("update DrawActivity set endTime = NOW() where id = ?1")
    int closeById(Long id);
}
