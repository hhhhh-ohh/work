package com.wanmi.sbc.order.leadertradedetail.repository;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.order.leadertradedetail.model.root.LeaderTradeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>团长订单DAO</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Repository
public interface LeaderTradeDetailRepository extends JpaRepository<LeaderTradeDetail, Long>,
        JpaSpecificationExecutor<LeaderTradeDetail> {

    /**
     * 单个删除团长订单
     * @author Bob
     */
    @Modifying
    @Query("update LeaderTradeDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除团长订单
     * @author Bob
     */
    @Modifying
    @Query("update LeaderTradeDetail set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    Optional<LeaderTradeDetail> findByIdAndDelFlag(Long id, DeleteFlag delFlag);

    /**
     * 获取当前跟团号
     * @author Bob
     */
    @Query("select max(activityTradeNo) from LeaderTradeDetail where communityActivityId = ?1")
    Long findActivityNo(String id);


    /**
     * 查询团长的跟团人数（支付成功+帮卖+去重）
     * @param leaderId
     * @return
     */
    @Query("select count(DISTINCT(customerId)) from LeaderTradeDetail where leaderId = ?1 and boolFlag = com.wanmi.sbc.common.enums.BoolFlag.YES and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    Long findFollowNum(String leaderId);

    @Query(value = "select d.* from leader_trade_detail d INNER JOIN " +
            "(select d.community_activity_id,MAX(d.activity_trade_no) - :topNum as limit_no from leader_trade_detail d " +
            "where d.community_activity_id in :activityIds and (COALESCE(:leaderIds,null) is null or d.leader_id in :leaderIds) and (:boolFlag is null or d.bool_flag = :boolFlag) and d.del_flag = 0 " +
            "GROUP BY d.community_activity_id) b on b.community_activity_id = d.community_activity_id and d.activity_trade_no > b.limit_no " +
            "where d.community_activity_id in :activityIds and (COALESCE(:leaderIds,null) is null or d.leader_id in :leaderIds) and (:boolFlag is null or d.bool_flag = :boolFlag) and d.del_flag = 0", nativeQuery = true)
    List<LeaderTradeDetail> queryTopByActivityIds(@Param("activityIds") List<String> activityIds, @Param("leaderIds") List<String> leaderIds, @Param("topNum") Integer topNum, @Param("boolFlag") Integer boolFlag);


    @Query(value = "select community_activity_id, count(DISTINCT d.trade_id) from leader_trade_detail d " +
            "where d.community_activity_id in :activityIds and (COALESCE(:leaderIds,null) is null or d.leader_id in :leaderIds) and (:boolFlag is null or d.bool_flag = :boolFlag) and d.del_flag = :delFlag  GROUP BY d.community_activity_id", nativeQuery = true)
    List<Object> totalTradeByActivityIds(@Param("activityIds") List<String> activityIds, @Param("leaderIds") List<String> leaderIds, @Param("delFlag") Integer delFlag, @Param("boolFlag") Integer boolFlag);

    /**
     * 活动分组的统计去重团长人数
     * @param activityIds 活动id
     * @param delFlag 删除标识
     * @param boolFlag 帮卖标识
     * @return 去重团长人数
     */
    @Query(value = "SELECT communityActivityId,count(DISTINCT(leaderId))" +
            " FROM LeaderTradeDetail WHERE communityActivityId in :activityIds and (:boolFlag is null or boolFlag = :boolFlag) and delFlag = :delFlag GROUP BY communityActivityId")
    List<Object> countLeaderGroupActivityIds(@Param("activityIds") List<String> activityIds, @Param("delFlag") DeleteFlag delFlag,  @Param("boolFlag") BoolFlag boolFlag);

    /**
     * 活动分组统计参团人数（去重）
     * @param activityIds 活动id
     * @param delFlag 删除标识
     * @param boolFlag 帮卖标识
     * @return 去重参团人数
     */
    @Query(value = "SELECT communityActivityId,count(DISTINCT(customerId))" +
            " FROM LeaderTradeDetail WHERE communityActivityId in :activityIds and (:boolFlag is null or boolFlag = :boolFlag) and delFlag = :delFlag GROUP BY communityActivityId")
    List<Object> countCustomerGroupActivityIds(@Param("activityIds") List<String> activityIds, @Param("delFlag") DeleteFlag delFlag,  @Param("boolFlag") BoolFlag boolFlag);
}
