package com.wanmi.sbc.order.orderperformance.repository;

import com.wanmi.sbc.order.orderperformance.model.root.OrderPerformance;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderPerformanceRepository extends JpaRepository<OrderPerformance,String>, JpaSpecificationExecutor<OrderPerformance> {

    /**
     * 根据订单ID查询
     * @param orderId
     * @return
     */
    OrderPerformance findByOrderId(String orderId);


    /**
     * 根据代理唯一码查询订单业绩列表
     * @param agentUniqueCode 代理唯一码
     * @return 订单业绩列表
     */
    List<OrderPerformance> findByAgentUniqueCode(String agentUniqueCode);

    /**
     * 根据代理唯一码和时间查询订单业绩列表
     * @param agentUniqueCode 代理唯一码
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 订单业绩列表
     */
    @Query("SELECT o FROM OrderPerformance o WHERE o.agentUniqueCode = :agentUniqueCode AND o.createTime BETWEEN :startTime AND :endTime")
    List<OrderPerformance> findByAgentUniqueCodeAndCreateTimeBetween(@Param("agentUniqueCode") String agentUniqueCode,
                                                                     @Param("startTime") LocalDateTime startTime,
                                                                     @Param("endTime") LocalDateTime endTime);
    //List<OrderPerformance> findByAgentUniqueCodeAndCreateTimeBetween(String agentUniqueCode, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 根据代理唯一码分页查询订单业绩
     * @param agentUniqueCode 代理唯一码
     * @param pageable 分页参数
     * @return 订单业绩分页数据
     */
    Page<OrderPerformance> findByAgentUniqueCodeOrderByIdDesc(String agentUniqueCode, Pageable pageable);
}
