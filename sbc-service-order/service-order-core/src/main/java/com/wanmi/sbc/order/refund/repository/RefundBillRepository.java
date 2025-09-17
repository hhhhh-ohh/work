package com.wanmi.sbc.order.refund.repository;

import com.wanmi.sbc.order.refund.model.root.RefundBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 退款流水
 * Created by zhangjin on 2017/4/21.
 */
@Repository
public interface RefundBillRepository extends JpaRepository<RefundBill, String> {

    /**
     * 根据支付单id删除流水
     * @param refundId refundId
     * @return rows
     */
    @Modifying
    @Query("update RefundBill r set r.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, r.refundId = null where r.refundId = :refundId")
    int deleteBillByRefundId(@Param("refundId") String refundId);

    /**
     * 根据refundIds 批量查询
     * @param refundIds
     * @return
     */
    @Query("from RefundBill r where r.delFlag = 0  and r.refundId in :refundIds")
    List<RefundBill> findByRefundIds(@Param("refundIds") List<String> refundIds);

}
