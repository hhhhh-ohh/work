package com.wanmi.sbc.marketing.grouponactivity.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.grouponactivity.model.root.GrouponActivity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>拼团活动信息表DAO</p>
 *
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Repository
public interface GrouponActivityRepository extends JpaRepository<GrouponActivity, String>,
        JpaSpecificationExecutor<GrouponActivity> {


    /**
     * 批量审核拼团活动
     *
     * @param grouponActivityIds
     * @return
     */
    @Modifying
    @Query("update GrouponActivity w set w.auditStatus = com.wanmi.sbc.marketing.bean.enums.AuditStatus.CHECKED, w" +
            ".updateTime = now() where w.grouponActivityId in ?1")
    int batchCheckMarketing(List<String> grouponActivityIds);

    /**
     * 驳回或禁止拼团活动
     *
     * @param grouponActivityId
     * @param auditStatus
     * @param auditReason
     * @return
     */
    @Modifying
    @Query("update GrouponActivity w set w.auditStatus = ?2, w.auditFailReason = ?3, w.updateTime =" +
            " now() where w.grouponActivityId = ?1")
    int refuseCheckMarketing(String grouponActivityId, AuditStatus auditStatus,
                             String auditReason);


    /**
     * 批量修改拼团活动精选状态
     *
     * @param grouponActivityIds
     * @return
     */
    @Modifying
    @Query("update GrouponActivity w set w.sticky = ?2, w.updateTime = now() where w.grouponActivityId in ?1")
    int batchStickyMarketing(List<String> grouponActivityIds, Boolean sticky);


    /**
     * 根据商品ids，查询正在进行的活动(时间段)
     *
     * @param goodsIds  商品ids
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Query("from GrouponActivity g where g.goodsId in ?1 and not (g.startTime > ?3 or g.endTime < " +
            "?2) and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<GrouponActivity> listActivitying(List<String> goodsIds, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据商品ids，查询正在进行的活动的商品ids(当前时间)
     *
     * @param goodsIds 商品ids
     * @return
     */
    @Query("select g.goodsId from GrouponActivity g where g.goodsId in ?1 " +
            "and now() >= g.startTime and now() <= g.endTime and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<String> listActivityingSpuIds(List<String> goodsIds);


    /**
     * 根据活动ID更新待成团人数
     *
     * @param grouponActivityId
     * @param waitGrouponNum
     * @param updateTime
     * @return
     */
    @Transactional
    @Modifying
    @Query("update GrouponActivity g set g.waitGrouponNum = g.waitGrouponNum + :waitGrouponNum,g.updateTime = " +
            ":updateTime where g.grouponActivityId = " +
            ":grouponActivityId")
    int updateWaitGrouponNumByGrouponActivityId(@Param("grouponActivityId") String grouponActivityId,
                                                @Param("waitGrouponNum") Integer waitGrouponNum,
                                                @Param("updateTime") LocalDateTime updateTime);

    /**
     * 根据活动ID更新已成团人数
     *
     * @param grouponActivityId
     * @param alreadyGrouponNum
     * @param updateTime
     * @return
     */
    @Transactional
    @Modifying
    @Query("update GrouponActivity g set g.alreadyGrouponNum = g.alreadyGrouponNum + :alreadyGrouponNum," +
            "g.waitGrouponNum = g.waitGrouponNum - :alreadyGrouponNum,g.updateTime = :updateTime where g" +
            ".grouponActivityId = " +
            ":grouponActivityId")
    int updateAlreadyGrouponNumByGrouponActivityId(@Param("grouponActivityId") String grouponActivityId,
                                                   @Param("alreadyGrouponNum") Integer alreadyGrouponNum,
                                                   @Param("updateTime") LocalDateTime updateTime);

    /**
     * 根据活动ID更新团失败人数和待成团人数
     *
     * @param grouponActivityId
     * @param failGrouponNum
     * @param updateTime
     * @return
     */
    @Transactional
    @Modifying
    @Query("update GrouponActivity g set g.failGrouponNum = g.failGrouponNum + :failGrouponNum," +
            "g.waitGrouponNum = g.waitGrouponNum - :failGrouponNum,g.updateTime = :updateTime where g" +
            ".grouponActivityId = :grouponActivityId")
    int updateFailGrouponNumByGrouponActivityId(@Param("grouponActivityId") String grouponActivityId,
                                                @Param("failGrouponNum") Integer failGrouponNum,
                                                @Param("updateTime") LocalDateTime updateTime);



    @Query("select count(distinct storeId ) from GrouponActivity g " +
            "where now() >= g.startTime and now() <= g.endTime and g.auditStatus = ?1 ")
    int querySupplierNum(AuditStatus status);

    @Modifying
    @Query("update GrouponActivity set endTime = now() where grouponActivityId = ?1")
    void closeActivity(String grouponActivityId);

    List<GrouponActivity> findAllByGrouponActivityIdIn(List<String> id);

    @Query(" from GrouponActivity a where a.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and a.auditStatus= com" +
            ".wanmi.sbc.marketing.bean.enums.AuditStatus.CHECKED " +
            "and a.startTime<=now() and a.endTime >= now() ")
    List findByInProcess();

    /**
     * 清空[拼团活动分类
     * @param cateId
     * @param no
     */
    @Modifying
    @Query("update GrouponActivity set grouponCateId = null,updateTime = now() where grouponCateId = ?1 and delFlag = ?2")
    void updateGrouponCateIdByGrouponCateId(String cateId, DeleteFlag no);

    @Modifying
    @Query("update GrouponActivity set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where grouponActivityId = ?1")
    void deleteById(String id);

    /**
     * 根据活动ids，查询正在进行的活动(时间段)
     *
     * @param activityIds  活动ids
     * @return
     */
    @Query("select count(1) from GrouponActivity g where g.grouponActivityId in ?1  and g.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    int getExistByActivityIds(List<String> activityIds);
}
