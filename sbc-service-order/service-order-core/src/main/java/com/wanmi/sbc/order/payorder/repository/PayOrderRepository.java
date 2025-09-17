package com.wanmi.sbc.order.payorder.repository;


import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.order.payorder.model.root.PayOrderResult;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 支付单
 * Created by zhangjin on 2017/4/20.
 */
@Repository
public interface PayOrderRepository extends JpaRepository<PayOrder, String>, JpaSpecificationExecutor<PayOrder> {

    /**
     * 根据payOrderId列表查询
     *
     * @param payOrderIds    payOrderIds
     * @return rows
     */
    @Query("from PayOrder p where p.payOrderId in :payOrderIds")
    List<PayOrder> findByPayOrderIds(@Param("payOrderIds") List<String> payOrderIds);

    /**
     * 修改支付单状态
     *
     * @param payOrderIds    payOrderIds
     * @param payOrderStatus payOrderStatus
     * @return rows
     */
    @Query("update PayOrder p set p.payOrderStatus = :payOrderStatus where p.payOrderId in :payOrderIds")
    @Modifying
    int updatePayOrderStatus(@Param("payOrderIds") List<String> payOrderIds, @Param("payOrderStatus") PayOrderStatus
            payOrderStatus);

    /**
     * 根据定单编号查询支付单
     *
     * @param orderCode orderCode
     * @param delFlag   delFlag
     * @return Optional<payorder>
     */
    Optional<PayOrder> findByOrderCodeAndDelFlag(String orderCode, DeleteFlag delFlag);

    /**
     * 根据定单编号查询支付单
     * @param orderCode 订单编号
     * @return          支付单
     */
    @Query("from PayOrder p where p.orderCode = :orderCode and p.delFlag = 0 ")
    PayOrder findByCodeAndDelFlag(@Param("orderCode") String orderCode);


    /**
     * 通过订单编号列表查询支付单
     * @param orderNos
     * @return
     */
    @Query("from PayOrder p where p.orderCode in :orderNos ")
    List<PayOrder> findByOrderCodes(@Param("orderNos") List<String> orderNos);


    /**
     * 通过订单编号列表查询支付单
     * @param orderNos
     * @return
     */
    @Query("from PayOrder p where p.orderCode in :orderNos and p.payOrderStatus = :payOrderStatus")
    List<PayOrder> findByOrderNos(@Param("orderNos") List<String> orderNos, @Param("payOrderStatus") PayOrderStatus
            payOrderStatus);

    @Query("update PayOrder p set p.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where p.payOrderId = :payOrderId")
    @Modifying
    int deletePayOrderById(@Param("payOrderId") String payOrderId);

    /**
     * @description id更新支付单号
     * @author  edz
     * @date: 2022/7/12 11:01
     * @param tid
     * @param payOrderNo
     * @return int
     */
    @Query("update PayOrder p set p.payOrderNo = :payOrderNo where p.delFlag = 0 and p.orderCode = :tid")
    @Modifying
    int updatePayOrderNo(@Param("tid") String tid, @Param("payOrderNo") String payOrderNo);

}
