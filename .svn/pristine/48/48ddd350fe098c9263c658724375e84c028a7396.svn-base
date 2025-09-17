package com.wanmi.sbc.account.finance.record.repository;

import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.finance.record.model.entity.PayItemRecord;
import com.wanmi.sbc.account.finance.record.model.entity.PaySummarize;
import com.wanmi.sbc.account.finance.record.model.entity.Reconciliation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>对账单持久化Bean</p>
 * Created by of628-wenzhi on 2017-12-05-下午4:49.
 */
@Repository
public interface ReconciliationRepository extends JpaRepository<Reconciliation, String>, JpaSpecificationExecutor<Reconciliation> {
    @Query(value = "SELECT min(r.supplier_id) supplier_id," +
            "r.store_id," +
            "SUM(r.amount) AS totalAmount," +
            "SUM(if(r.gift_card_type is null or r.gift_card_type != 1, r.points, 0)) AS totalPoints, " +
            "SUM(if(r.gift_card_type is null or r.gift_card_type != 1, COALESCE(r.points_price,0), 0)) AS totalPointsPrice, " +
            "COALESCE(SUM(r.gift_card_price), 0) AS totalGiftCardPrice " +
            "FROM reconciliation r " +
            "WHERE " +
            "(:supplierId is NULL or r.supplier_id = :supplierId) " +
            "AND (:storeIdsSize = 0 or r.store_id in (:storeIds)) " +
            "AND (:typeFlag is NULL or r.type = :typeFlag) " +
            "AND (:beginTime is NULL or r.trade_time >= :beginTime) " +
            "AND (:endTime is NULL or r.trade_time <= :endTime) " +
            "AND (:startPartition is NULL or r.id >= :startPartition) " +
            "AND (:endPartition is NULL or r.id <= :endPartition) " +
            "GROUP BY r.store_id " +
            "ORDER BY totalAmount DESC",
            countQuery = "SELECT COUNT(1) FROM " +
                    "(" +
                    "SELECT DISTINCT store_id FROM reconciliation r " +
                    "WHERE " +
                    "(:supplierId is NULL or r.supplier_id = :supplierId) " +
                    "AND (:storeIdsSize = 0 or r.store_id in (:storeIds)) " +
                    "AND (:typeFlag is NULL or r.type = :typeFlag) " +
                    "AND (:beginTime is NULL or r.trade_time >= :beginTime) " +
                    "AND (:endTime is NULL or r.trade_time <= :endTime) " +
                    "AND (:startPartition is NULL or r.id >= :startPartition) " +
                    "AND (:endPartition is NULL or r.id <= :endPartition) " +
                    "GROUP BY r.store_id" +
                    ") a", nativeQuery = true
    )
    Page<Object> queryTotalRecord(@Param("beginTime") LocalDateTime beginTime,
                                       @Param("endTime") LocalDateTime endTime,
                                       @Param("supplierId") Long supplierId,
                                       @Param("storeIdsSize") Integer storeIdsSize,
                                       @Param("storeIds") List<Long> storeIds,
                                       @Param("typeFlag") Byte typeFlag,
                                       @Param("startPartition") String startPartition,
                                       @Param("endPartition") String endPartition,
                                       Pageable pageable);


    @Query(value = "SELECT new com.wanmi.sbc.account.finance.record.model.entity.PayItemRecord(" +
            "r.storeId,r.payWay,SUM(r.amount) AS amount) " +
            "FROM Reconciliation r " +
            "WHERE r.storeId in (:storeIds) " +
            "AND (:beginTime is NULL OR r.tradeTime >= :beginTime ) " +
            "AND (:endTime is NULL OR r.tradeTime <= :endTime ) " +
            "AND r.type = :typeFlag " +
            "AND (:startPartition IS NULL OR r.id >= :startPartition) " +
            "AND (:endPartition IS NULL  OR r.id <= :endPartition) " +
            "GROUP BY r.storeId,r.payWay"
    )
    List<PayItemRecord> queryPayItemRecord(@Param("beginTime") LocalDateTime beginTime,
                                           @Param("endTime") LocalDateTime endTime,
                                           @Param("storeIds") List<Long> storeIds,
                                           @Param("typeFlag") Byte typeFlag,
                                           @Param("startPartition") String startPartition,
                                           @Param("endPartition") String endPartition
    );

    @Query(value = "FROM Reconciliation r WHERE r.storeId = :storeId AND r.type = :typeFlag " +
            "AND (:payWay is NULL OR r.payWay = :payWay) " +
            "AND (:beginTime is NULL OR r.tradeTime >= :beginTime ) " +
            "AND (:endTime is NULL OR r.tradeTime <= :endTime ) " +
            "AND (:tradeNo = '' OR r.tradeNo like concat('%', :tradeNo, '%') ) " +
            "ORDER BY r.orderTime DESC")
    Page<Reconciliation> queryDetails(@Param("storeId") Long storeId,
                                      @Param("beginTime") LocalDateTime beginTime,
                                      @Param("endTime") LocalDateTime endTime,
                                      @Param("typeFlag") Byte typeFlag,
                                      @Param("payWay") PayWay payWay,
                                      @Param("tradeNo") String tradeNo,
                                      Pageable pageable
    );

    @Query(value = "FROM Reconciliation r WHERE r.storeId = :storeId AND r.type = :typeFlag " +
            "AND (:payWay is NULL OR r.payWay = :payWay) " +
            "AND (:beginTime is NULL OR r.tradeTime >= :beginTime ) " +
            "AND (:endTime is NULL OR r.tradeTime <= :endTime ) " +
            "AND (:tradeNo = '' OR r.tradeNo like concat('%', :tradeNo, '%') ) " +
            "AND (:startPartition IS NULL OR r.id >= :startPartition) " +
            "AND (:endPartition IS NULL  OR r.id <= :endPartition) " +
            "AND (:points IS NULL  OR r.points > 0) " +
            "AND (:amount IS NULL  OR r.amount > 0) " +
            "AND (:giftCardPrice IS NULL  OR r.giftCardPrice > 0) " +
            "AND (:offlinePayType IS NULL OR r.payWay = 'CASH') " +
            "AND (:onlinePayType IS NULL OR r.payWay not in ('CASH','COUPON')) " +
            "ORDER BY r.orderTime DESC")
    List<Reconciliation> queryDetails(@Param("storeId") Long storeId,
                                      @Param("beginTime") LocalDateTime beginTime,
                                      @Param("endTime") LocalDateTime endTime,
                                      @Param("typeFlag") Byte typeFlag,
                                      @Param("payWay") PayWay payWay,
                                      @Param("tradeNo") String tradeNo,
                                      @Param("startPartition") String startPartition,
                                      @Param("endPartition") String endPartition,
                                      @Param("points") Long points,
                                      @Param("amount") BigDecimal amount,
                                      @Param("giftCardPrice") BigDecimal giftCardPrice,
                                      @Param("offlinePayType") Integer offlinePayType,
                                      @Param("onlinePayType") Integer onlinePayType

    );
    @Query(value = "SELECT new com.wanmi.sbc.account.finance.record.model.entity.PaySummarize(r.payWay,SUM(r.amount) AS sumAmount, " +
            "SUM (case when r.giftCardType <> com.wanmi.sbc.marketing.bean.enums.GiftCardType.PICKUP_CARD then " +
            "COALESCE(r.pointsPrice,0) when r.giftCardType is null then COALESCE(r.pointsPrice,0) else 0 end) AS " +
            "sumPointsPrice," +
            "COALESCE(SUM(r.giftCardPrice), 0) AS sumGiftCardPrice ) " +
            "FROM Reconciliation r " +
            "WHERE (:supplierId IS NULL OR r.supplierId = :supplierId) " +
            "AND r.type = :typeFlag " +
            "AND (:beginTime is NULL OR r.tradeTime >= :beginTime ) " +
            "AND (:endTime is NULL OR r.tradeTime <= :endTime ) " +
            "AND (:startPartition IS NULL OR r.id >= :startPartition) " +
            "AND (:endPartition IS NULL  OR r.id <= :endPartition) " +
            "GROUP BY r.payWay"
    )
    List<PaySummarize> summarizing(@Param("beginTime") LocalDateTime beginTime,
                                   @Param("endTime") LocalDateTime endTime,
                                   @Param("supplierId") Long supplierId,
                                   @Param("typeFlag") Byte typeFlag,
                                   @Param("startPartition") String startPartition,
                                   @Param("endPartition") String endPartition
    );

    @Query(value = "SELECT SUM(case when r.giftCardType <> com.wanmi.sbc.marketing.bean.enums.GiftCardType" +
            ".PICKUP_CARD then r.points when r.giftCardType is null then r.points else 0 end )  AS sumPoints " +
            " FROM Reconciliation r " +
            "WHERE (:supplierId IS NULL OR r.supplierId = :supplierId) " +
            "AND r.type = :typeFlag " +
            "AND (:beginTime is NULL OR r.tradeTime >= :beginTime ) " +
            "AND (:endTime is NULL OR r.tradeTime <= :endTime ) " +
            "AND (:startPartition IS NULL OR r.id >= :startPartition) " +
            "AND (:endPartition IS NULL  OR r.id <= :endPartition) "
    )
    Long sumPoints(@Param("beginTime") LocalDateTime beginTime,
                         @Param("endTime") LocalDateTime endTime,
                         @Param("supplierId") Long supplierId,
                         @Param("typeFlag") Byte typeFlag,
                         @Param("startPartition") String startPartition,
                         @Param("endPartition") String endPartition
    );


    int deleteByOrderCodeAndType(String orderCode, Byte typeFlag);

    int deleteByReturnOrderCodeAndType(String returnOrderCode, Byte typeFlag);

    /**
     * 导出需要记录总数
     *
     * @param beginTime
     * @param endTime
     * @param supplierId
     * @param storeIdsSize
     * @param storeIds
     * @param typeFlag
     * @return
     */
    @Query(value = "SELECT COUNT(distinct storeId) FROM Reconciliation " +
            "WHERE storeId IN " +
            "(" +
            "SELECT DISTINCT storeId FROM Reconciliation r " +
            "WHERE " +
            "(:supplierId IS NULL OR r.supplierId = :supplierId) " +
            "AND (:storeIdsSize = 0 OR r.storeId in (:storeIds)) " +
            "AND (:typeFlag IS NULL OR r.type = :typeFlag) " +
            "AND (:beginTime is NULL OR r.tradeTime >= :beginTime ) " +
            "AND (:endTime is NULL OR r.tradeTime <= :endTime ) " +
            "AND (:startPartition IS NULL OR r.id >= :startPartition) " +
            "AND (:endPartition IS NULL  OR r.id <= :endPartition) " +
            "GROUP BY r.storeId" +
            ")"
    )
    Integer queryCount(@Param("beginTime") LocalDateTime beginTime,
                       @Param("endTime") LocalDateTime endTime,
                       @Param("supplierId") Long supplierId,
                       @Param("storeIdsSize") Integer storeIdsSize,
                       @Param("storeIds") List<Long> storeIds,
                       @Param("typeFlag") Byte typeFlag,
                       @Param("startPartition") String startPartition,
                       @Param("endPartition") String endPartition
    );

    /**
     * Es初始化分页查询
     * @param pageable
     * @return
     */
    @Query("from Reconciliation")
    List<Reconciliation> listByPage(PageRequest pageable);
}
