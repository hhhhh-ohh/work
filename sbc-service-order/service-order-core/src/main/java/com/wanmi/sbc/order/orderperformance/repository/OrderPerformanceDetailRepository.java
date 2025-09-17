package com.wanmi.sbc.order.orderperformance.repository;

import com.wanmi.sbc.order.api.request.orderperformance.OrderPerformanceByUniqueCodesRequest;
import com.wanmi.sbc.order.orderperformance.model.root.OrderPerformanceDetail;
import com.wanmi.sbc.order.orderperformance.model.root.dto.AreaSummaryDto;
import com.wanmi.sbc.order.orderperformance.model.root.dto.SeasonSummaryDto;
import com.wanmi.sbc.order.orderperformance.model.root.dto.ShopSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderPerformanceDetailRepository extends JpaRepository<OrderPerformanceDetail, Long>, JpaSpecificationExecutor<OrderPerformanceDetail> {

    /**
     * 根据业绩ID查询明细
     * @param performanceId 业绩ID
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByPerformanceId(String performanceId);

    /**
     * 根据订单ID查询明细
     * @param orderId 订单ID
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByOrderId(String orderId);

    /**
     * 根据退货单ID查询明细
     * @param returnOrderId 退货单ID
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByReturnOrderId(String returnOrderId);

    /**
     * 根据代理ID查询明细
     * @param agentId 代理ID
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByAgentId(String agentId);

    /**
     * 根据客户ID查询明细
     * @param customerId 客户ID
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByCustomerId(String customerId);

    /**
     * 根据明细类型查询明细
     * @param detailType 明细类型（1-购买商品，2-退货商品）
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByDetailType(Integer detailType);

    /**
     * 根据代理唯一码查询明细
     * @param agentUniqueCode 代理唯一码
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByAgentUniqueCode(String agentUniqueCode);

    /**
     * 根据订单ID列表查询明细
     * @param orderIds 订单ID列表
     * @return 订单业绩明细列表
     */
    List<OrderPerformanceDetail> findByOrderIdIn(List<String> orderIds);

    @Query("SELECT o FROM OrderPerformanceDetail o WHERE 1=1 " +
            "AND (o.agentUniqueCode IN :uniqueCodes) " +
            "AND (:startTime IS NULL OR o.createTime >= :startTime) " +
            "AND (:endTime IS NULL OR o.createTime <= :endTime) " +
            "ORDER BY o.createTime DESC")
    List<OrderPerformanceDetail> findListByAgentUniqueCodeAndTime(  @Param("uniqueCodes") List<String> uniqueCodes,
                                                                    @Param("startTime") LocalDateTime startTime,
                                                                    @Param("endTime") LocalDateTime endTime);

    /**
     * 根据代理唯一码和时间查询明细
     * @param agentUniqueCode 代理唯一码
     * @return 订单业绩明细列表
     */
    @Query("SELECT o FROM OrderPerformanceDetail o WHERE 1=1 " +
            "AND (o.agentUniqueCode = :agentUniqueCode) " +
            "AND (:startTime IS NULL OR o.createTime >= :startTime) " +
            "AND (:endTime IS NULL OR o.createTime <= :endTime) " +
            "ORDER BY o.createTime DESC")
    List<OrderPerformanceDetail> findByAgentUniqueCodeAndTime(@Param("agentUniqueCode") String agentUniqueCode,
                                                              @Param("startTime") LocalDateTime startTime,
                                                              @Param("endTime") LocalDateTime endTime);

    @Query("SELECT new com.wanmi.sbc.order.orderperformance.model.root.dto.SeasonSummaryDto(" +
            "o.season, SUM(o.quantity), SUM(o.uniformTotalAmount), o.detailType) " +
            "FROM OrderPerformanceDetail o " +
            "WHERE  o.areaId IS NOT NULL " +
            "AND ((COALESCE(?#{#request.cityList}, NULL) IS NULL OR o.cityId IN ?#{#request.cityList}) " +
            "OR (COALESCE(?#{#request.areaList}, NULL) IS NULL OR o.areaId IN ?#{#request.areaList})) " +
            "AND (?#{#request.areaId} IS NULL OR o.areaId = ?#{#request.areaId}) " +
            "AND (?#{#request.startTime} IS NULL OR o.createTime >= ?#{#request.startTime}) " +
            "AND (?#{#request.endTime} IS NULL OR o.createTime <= ?#{#request.endTime}) " +
            "GROUP BY  o.detailType,  o.season " +
            "ORDER BY o.season")
    List<SeasonSummaryDto> findSeasonSummary(@Param("request") OrderPerformanceByUniqueCodesRequest request);

    @Query("SELECT new com.wanmi.sbc.order.orderperformance.model.root.dto.AreaSummaryDto(" +
            "o.cityId,o.cityName,o.areaId,o.areaName,o.season, SUM(o.quantity), SUM(o.commissionAmount), o.detailType) " +
            "FROM OrderPerformanceDetail o " +
            "WHERE  o.areaId IS NOT NULL " +
            "AND ((COALESCE(?#{#request.cityList}, NULL) IS NULL OR o.cityId IN ?#{#request.cityList}) " +
            "OR (COALESCE(?#{#request.areaList}, NULL) IS NULL OR o.areaId IN ?#{#request.areaList})) " +
            "AND (?#{#request.areaId} IS NULL OR o.areaId = ?#{#request.areaId}) " +
            "AND (?#{#request.startTime} IS NULL OR o.createTime >= ?#{#request.startTime}) " +
            "AND (?#{#request.endTime} IS NULL OR o.createTime <= ?#{#request.endTime}) " +
            "GROUP BY o.cityId, o.cityName, o.areaId, o.areaName, o.season, o.detailType " +
            "ORDER BY o.cityId, o.areaId,o.season")
    List<AreaSummaryDto> findAreaSummary(@Param("request") OrderPerformanceByUniqueCodesRequest request);

    @Query("SELECT new com.wanmi.sbc.order.orderperformance.model.root.dto.ShopSummaryDto(" +
            "o.areaId, o.areaName, o.shopName, o.season, SUM(o.quantity), SUM(o.commissionAmount), o.detailType) " +
            "FROM OrderPerformanceDetail o " +
            "WHERE  o.areaId IS NOT NULL " +
            "AND ((COALESCE(?#{#request.cityList}, NULL) IS NULL OR o.cityId IN ?#{#request.cityList}) " +
            "OR (COALESCE(?#{#request.areaList}, NULL) IS NULL OR o.areaId IN ?#{#request.areaList})) " +
            "AND (?#{#request.areaId} IS NULL OR o.areaId = ?#{#request.areaId}) " +
            "AND (?#{#request.startTime} IS NULL OR o.createTime >= ?#{#request.startTime}) " +
            "AND (?#{#request.endTime} IS NULL OR o.createTime <= ?#{#request.endTime}) " +
            "GROUP BY o.areaId, o.areaName, o.shopName, o.season, o.detailType " +
            "ORDER BY o.areaId,o.season")
    List<ShopSummaryDto> findShopSummary(@Param("request") OrderPerformanceByUniqueCodesRequest request);

    @Query("SELECT new com.wanmi.sbc.order.orderperformance.model.root.dto.SeasonSummaryDto(" +
            "o.season, SUM(o.quantity), SUM(o.uniformTotalAmount), o.detailType) " +
            "FROM OrderPerformanceDetail o " +
            "WHERE  o.areaId IS NOT NULL " +
            "AND (COALESCE(?#{#request.uniqueCodes}, NULL) IS NULL OR o.agentUniqueCode IN ?#{#request.uniqueCodes}) " +
            "AND (?#{#request.startTime} IS NULL OR o.createTime >= ?#{#request.startTime}) " +
            "AND (?#{#request.endTime} IS NULL OR o.createTime <= ?#{#request.endTime}) " +
            "GROUP BY o.season, o.detailType " +
            "ORDER BY o.season")
    List<SeasonSummaryDto> findSeasonSummaryByUniqueCode(@Param("request") OrderPerformanceByUniqueCodesRequest request);

    @Query("SELECT new com.wanmi.sbc.order.orderperformance.model.root.dto.AreaSummaryDto(" +
            "o.cityId,o.cityName,o.areaId,o.areaName,o.season, SUM(o.quantity), SUM(o.commissionAmount), o.detailType) " +
            "FROM OrderPerformanceDetail o " +
            "WHERE  o.areaId IS NOT NULL " +
            "AND (COALESCE(?#{#request.uniqueCodes}, NULL) IS NULL OR o.agentUniqueCode IN ?#{#request.uniqueCodes}) " +
            "AND (?#{#request.startTime} IS NULL OR o.createTime >= ?#{#request.startTime}) " +
            "AND (?#{#request.endTime} IS NULL OR o.createTime <= ?#{#request.endTime}) " +
            "GROUP BY o.cityId, o.cityName, o.areaId, o.areaName, o.season, o.detailType " +
            "ORDER BY o.cityId, o.areaId,o.season")
    List<AreaSummaryDto> findAreaSummaryByUniqueCode(@Param("request") OrderPerformanceByUniqueCodesRequest request);

    @Query("SELECT new com.wanmi.sbc.order.orderperformance.model.root.dto.ShopSummaryDto(" +
            "o.areaId, o.areaName, o.shopName, o.season, SUM(o.quantity), SUM(o.commissionAmount), o.detailType) " +
            "FROM OrderPerformanceDetail o " +
            "WHERE  o.areaId IS NOT NULL " +
            "AND (COALESCE(?#{#request.uniqueCodes}, NULL) IS NULL OR o.agentUniqueCode IN ?#{#request.uniqueCodes}) " +
            "AND (?#{#request.startTime} IS NULL OR o.createTime >= ?#{#request.startTime}) " +
            "AND (?#{#request.endTime} IS NULL OR o.createTime <= ?#{#request.endTime}) " +
            "GROUP BY o.areaId, o.areaName, o.shopName, o.season, o.detailType " +
            "ORDER BY o.areaId,o.season")
    List<ShopSummaryDto> findShopSummaryByUniqueCode(@Param("request") OrderPerformanceByUniqueCodesRequest request);



}
