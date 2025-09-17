package com.wanmi.sbc.marketing.communitystatistics.repository;

import com.wanmi.sbc.marketing.communitystatistics.model.root.CommunityStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * <p>社区团购活动统计信息表DAO</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Repository
public interface CommunityStatisticsRepository extends JpaRepository<CommunityStatistics, String>,
        JpaSpecificationExecutor<CommunityStatistics> {

    /**
     * 单个查询社区团购活动统计信息表
     * @author dyt
     */
    Optional<CommunityStatistics> findByIdAndStoreId(String id, Long storeId);

    Optional<CommunityStatistics> findByActivityIdAndLeaderIdAndCreateDate(String activityId, String leaderId,
                                                                           LocalDate localDate);

    /**
     * 单个团长的统计数据汇总
     * @param leaderId
     * @return
     */
    @Query(value = "SELECT " +
            "IFNULL(sum( commission_received ),0) AS received," +
            "IFNULL(sum( commission_received_pickup ),0) as received_pickup," +
            "IFNULL(sum( commission_received_assist ),0) as received_assist," +
            "IFNULL(sum( commission_pending ),0) as pending," +
            "IFNULL(sum( commission_pending_pickup ),0) as pending_pickup," +
            "IFNULL(sum( commission_pending_assist ),0) as pending_assist, " +
            "IFNULL(sum( assist_order_num ),0) as assist_order_num, " +
            "IFNULL(sum( assist_order_total ),0) as assist_order_total, " +
            "IFNULL(sum( pickup_service_order_num ),0) as pickup_service_order_num," +
            "IFNULL(sum( pickup_service_order_total ),0) as pickup_service_order_total, " +
            "IFNULL(sum( return_trade_commission ),0) as return_trade_commission, " +
            "IFNULL(sum( return_trade_commission_assist ),0) as return_trade_commission_assist, " +
            "IFNULL(sum( return_trade_commission_pickup ),0) as return_trade_commission_pickup " +
            " FROM " +
            " community_statistics " +
            " WHERE " +
            "leader_id =:leaderId", nativeQuery = true)
    Object findByLeaderIdGroup(@Param("leaderId") String leaderId);

    /**
     * 多个团长的统计数据汇总
     * @param leaderIds
     * @return
     */
    @Query(value = "SELECT " +
            "IFNULL(sum( commission_received ),0) AS received," +
            "IFNULL(sum( commission_received_pickup ),0) as received_pickup," +
            "IFNULL(sum( commission_received_assist ),0) as received_assist," +
            "IFNULL(sum( commission_pending ),0) as pending," +
            "IFNULL(sum( commission_pending_pickup ),0) as pending_pickup," +
            "IFNULL(sum( commission_pending_assist ),0) as pending_assist, " +
            "IFNULL(sum( assist_order_num ),0) as assist_order_num, " +
            "IFNULL(sum( assist_order_total ),0) as assist_order_total, " +
            "IFNULL(sum( pickup_service_order_num ),0) as pickup_service_order_num," +
            "IFNULL(sum( pickup_service_order_total ),0) as pickup_service_order_total, " +
            "IFNULL(sum( return_trade_commission ),0) as return_trade_commission, " +
            "IFNULL(sum( return_trade_commission_assist ),0) as return_trade_commission_assist, " +
            "IFNULL(sum( return_trade_commission_pickup ),0) as return_trade_commission_pickup, " +
            "leader_id " +
            " FROM " +
            " community_statistics " +
            " WHERE " +
            " leader_id in (:leaderIds) " +
            " GROUP BY leader_id", nativeQuery = true)
    List<Object> findByLeaderIdsGroup(@Param("leaderIds") List<String> leaderIds);

    /**
     * 多个活动的统计数据汇总
     * @param activityIds 活动id
     * @return 活动统计
     */
    @Query(value = "SELECT " +
            "IFNULL(sum( pay_num ),0) AS pay_num," +
            "IFNULL(sum( pay_total ),0) as pay_total," +
            "IFNULL(sum( assist_order_num ),0) as assist_order_num," +
            "IFNULL(sum( assist_order_total ),0) as pending," +
            "IFNULL(sum( commission_received ),0) as pending_pickup," +
            "IFNULL(sum( commission_pending ),0)-IFNULL(sum(return_trade_commission),0)-IFNULL(sum(commission_received),0) as pending_assist, " +
            "activity_id " +
            " FROM " +
            " community_statistics " +
            " WHERE " +
            " activity_id in (:activityIds) " +
            " GROUP BY activity_id", nativeQuery = true)
    List<Object> findByActivityIdsGroup(@Param("activityIds") List<String> activityIds);

    @Modifying
    @Query("update CommunityStatistics a set a.payNum = a.payNum + 1, a.payTotal = a.payTotal + ?1, a.assistOrderNum = a" +
            ".assistOrderNum + ?2, a.assistOrderTotal = a.assistOrderTotal + ?3, a.commissionPending = a" +
            ".commissionPending + ?4, a.commissionPendingPickup = a.commissionPendingPickup + ?5, a.commissionPendingAssist = a" +
            ".commissionPendingAssist + ?6, a.pickupServiceOrderNum = a.pickupServiceOrderNum + ?10, a" +
            ".pickupServiceOrderTotal = a.pickupServiceOrderTotal + ?11 where a" +
            ".activityId = ?7 and a.leaderId = " +
            "?8 and a.createDate = ?9")
    int update(BigDecimal payTotal, long assistOrderNum, BigDecimal assistOrderTotal, BigDecimal commissionPending,
               BigDecimal commissionPendingPickup, BigDecimal commissionPendingAssist, String activityId,
               String leader, LocalDate localDate, Long pickupServiceOrderNum, BigDecimal pickupServiceOrderTotal);

    @Modifying
    @Query("update CommunityStatistics a set a.returnNum = a.returnNum + 1, a.returnTotal = a.returnTotal + ?1, a" +
            ".assistReturnNum = a.assistReturnNum + ?2, a.assistReturnTotal = a.assistReturnTotal + ?3, a" +
            ".returnTradeCommission = a.returnTradeCommission + ?4, a.returnTradeCommissionAssist = a" +
            ".returnTradeCommissionAssist + ?8,a.returnTradeCommissionPickup = a.returnTradeCommissionPickup + ?9 " +
            "where a.activityId = ?5 and a.leaderId = ?6 and a.createDate = ?7")
    int returnUpdate(BigDecimal returnTotal, long assistReturnNum, BigDecimal assistReturnTotal,
                     BigDecimal returnTradeCommission, String activityId,
                     String leaderId, LocalDate createDate, BigDecimal returnTradeCommissionAssist,
                     BigDecimal returnTradeCommissionPickup);

    @Modifying
    @Query("update CommunityStatistics a set a.commissionReceived = a.commissionReceived + ?1, a" +
            ".commissionReceivedAssist = a.commissionReceivedAssist + ?3, a.commissionReceivedPickup = a" +
            ".commissionReceivedPickup + ?2 where a.activityId = ?4 and a.leaderId = ?5 and a.createDate = ?6")
    int commissionUpdate(BigDecimal commissionReceived, BigDecimal commissionReceivedPickup,
                         BigDecimal commissionReceivedAssist, String activityId,
                         String leaderId, LocalDate createDate);
}
