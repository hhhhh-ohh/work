package com.wanmi.sbc.account.credit.repository;

import com.wanmi.sbc.account.api.request.credit.CreditRepayPageRequest;
import com.wanmi.sbc.account.credit.model.root.CustomerCreditOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/3/1 16:18
 * @description <p> 授信订单关联DO </p>
 */
@Repository
public interface CreditOrderRepository extends JpaRepository<CustomerCreditOrder, String>,
        JpaSpecificationExecutor<CustomerCreditOrder> {

    /**
     * 分页查询订单关联表信息
     *
     * @param repayOrderCod
     * @param pageable
     * @return
     */
    Page<CustomerCreditOrder> findByRepayOrderCode(String repayOrderCod, Pageable pageable);

    /**
     * 根据还款单号查询已还款订单数量
     *
     * @param repayOrderCode
     * @return
     */
    long countByRepayOrderCode(String repayOrderCode);

    /**
     * 通过订单号查询关联表数量
     * @param orderId
     * @return
     */
    long countByOrderId(String orderId);


    /**
     * 查询当前周期的还款单号
     *
     * @param request
     * @return
     */
    @Query(value = "SELECT DISTINCT repay_order_code  FROM customer_credit_order " +
            "WHERE repay_order_code IS NOT NULL " +
            "AND IF(:#{#request.startTime} IS NOT NULL,create_time >= :#{#request.startTime},1 = 1) " +
            "AND IF(:#{#request.endTime} IS NOT NULL,create_time <= :#{#request.endTime},1 = 1) " +
            "AND customer_id = :#{#request.customerId}", nativeQuery = true)
    List<String> findRepayOrderCode(@Param("request") CreditRepayPageRequest request);

    /**
     * 根据订单id集合查询关联订单
     * @param orderIds
     * @return
     */
    @Query(value = "SELECT order_id, min(create_time) as create_time  FROM customer_credit_order " +
            "WHERE order_id IN (:orderIds) " +
            "AND del_flag = 0 group by order_id", nativeQuery = true)
    List<Object> findCreditOrderListByOrderIdInAndDelFlag(@Param("orderIds")List<String> orderIds);

}
